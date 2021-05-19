package org.echoiot.util;


import org.echoiot.service.PageViewService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alfalfa99
 * @version 1.0
 * @date 2020/11/3 15:26
 * 获取用户ip并请求高德地图ip
 */
@Component
public class IpUtil {
    private final RestTemplate restTemplate;
    private final String key = "230094e238fef8d09d9feee01b464b4f";
    private final PageViewService pageViewService;
    private final String url = "https://restapi.amap.com/v3/ip?key={key}&ip={ip}";
    public IpUtil(PageViewService pageViewService) {
        this.pageViewService = pageViewService;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(3 * 1000);
        factory.setConnectTimeout(3 * 1000);
        this.restTemplate = new RestTemplate(factory);
    }

    private String getUserIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String ip1 : ips) {
                if (!("unknown".equalsIgnoreCase(ip1))) {
                    ip = ip1;
                    break;
                }
            }
        }
        if (ip == null || "".equals(ip)){
            ip = "127.0.0.1";
        }
        return ip;
    }


    private LinkedHashMap getUserLocation(String ip) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("ip", ip);
        ResponseEntity<LinkedHashMap> result;
        try {
            result = restTemplate.getForEntity(url, LinkedHashMap.class, params);
        } catch (Exception e) {
            System.out.println(DateTimeTransferUtil.getFormatTime() + " 高德API接口异常");
            return null;
        }
        if (result.getBody() == null) {
            return null;
        }
        System.out.println(result.getBody());
        return result.getBody();
    }

    public void recording(HttpServletRequest req, String page, String uri) {
        HttpSession session = req.getSession();
        System.out.println("这是session");
        System.out.println(session);
        System.out.println("这是session的id和访问数");
        Integer ipRecord = (Integer) session.getAttribute("ipRecord");
        System.out.println(session.getId()+"        "+ipRecord);
        if (ipRecord == null) {
            session.setAttribute("ipRecord", 1);
        } else if (ipRecord >= 5){
            return;
        } else {
            session.setAttribute("ipRecord",ipRecord++);
        }
        String ip = getUserIp(req);
        LinkedHashMap<String, String> userLocation = getUserLocation(ip);
        try {
            if (userLocation != null) {
                pageViewService.addPageView(page, uri, ip, userLocation.get("province"), userLocation.get("city"));
            }
        } catch (Exception ignored) {
        }
    }
}
