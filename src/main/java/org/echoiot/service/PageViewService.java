package org.echoiot.service;

import org.echoiot.dao.PageViewDao;
import org.echoiot.entity.pojo.PageView;
import org.echoiot.util.DateTimeTransferUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author ATSELI
 * @version 1.0
 * @date 2020/11/2 20:39
 */
@Service
public class PageViewService {

    private final PageViewDao pageViewDao;

    public PageViewService(PageViewDao pageViewDao) {
        this.pageViewDao = pageViewDao;
    }


    /**
     * 添加对应接口的访问参数
     *
     * @param Page     页面中文名
     * @param uri      yri
     * @param ip       ip
     * @param province IP定位省份
     * @param city     IP定位市名
     */
    public void addPageView(String Page, String uri, String ip, String province, String city) {
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //今天零点零分零秒的毫秒数
        long zeroHour = (current / (1000 * 3600 * 24) * (1000 * 3600 * 24) -
                TimeZone.getDefault().getRawOffset()) / 1000;
        long nextZeroHour = zeroHour + 86400;
        Integer pageViews = pageViewDao.findSame(uri, ip, zeroHour, nextZeroHour);
        if (pageViews != 0){
            return;
        }else {
            Long nowTimeStamp = DateTimeTransferUtil.getNowTimeStamp();
            PageView pageView = new PageView(Page, uri, ip, province, city, nowTimeStamp);
            pageViewDao.addPageView(pageView);
        }


    }

    /**
     * 查询对应省份的查看人数
     *
     * @param province IP定位省份
     * @return 对应省份的查看人数
     */
    public Integer provinceCount(String province) {
        return pageViewDao.findProvince(province);
    }


    /**
     * 查询对应城市的查看人数
     *
     * @param city IP定位城市
     * @return 对应城市的查看人数
     */
    public Integer cityCount(String city) {
        return pageViewDao.findCity(city);
    }


    /**
     * 查询对应一段时间内访问省份TOP10
     *
     * @param startTime 起始查询时间戳
     * @param endTime   末尾查询时间戳
     * @return 返回省份TOP10
     */
    public List<Map<String, Integer>> findProvinceTop(Long startTime, Long endTime) {
        return pageViewDao.findProvinceTOP(startTime, endTime);
    }

    /**
     * 查询一段时间内查看网站的城市TOP10
     * @param startTime 起始查询时间戳
     * @param endTime   末尾查询时间戳
     * @return 返回城市TOP10
     */
    public List<Map<String, Integer>> findCityTop(Long startTime, Long endTime) {
        return pageViewDao.findCityTOP(startTime, endTime);
    }

    /**
     * 查询并倒序返回对应的省份访问量
     *
     * @return 返回省份访问量
     */
    public List<Map<String, Integer>> descSortProvince() {
        return pageViewDao.descSortProvince();

    }

    /**
     * 查询并倒序放回对应的城市访问量
     *
     * @param province 要排序的省份
     * @return 返回城市访问量
     */
    public List<Map<String, Integer>> descSortCity(String province) {
        return pageViewDao.DescSortCity(province);
    }

    /**
     * 查询并倒叙输出网站访问量
     *
     * @return 返回网站访问量
     */
    public List<Map<String, Integer>> descUri() {
        return pageViewDao.DescUri();
    }


}
