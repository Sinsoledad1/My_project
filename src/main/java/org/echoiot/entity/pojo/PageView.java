package org.echoiot.entity.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ATSELI
 * @version 1.0
 * @date 2020/11/2 20:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageView {
    /**
     * id: id
     * page: 页面中文名
     * uri: uri
     * ip: ip
     * province: ip定位省份
     * city: ip定位城市名
     * add_time: 添加时间
     */

    private Integer id;
    private String page;
    private String uri;
    private String ip;
    private String province;
    private String city;
    private Long add_time;

    public PageView(String page, String uri, String ip, String province, String city, Long add_time) {
        this.page = page;
        this.uri = uri;
        this.ip = ip;
        this.province = province;
        this.city = city;
        this.add_time = add_time;
    }

}
