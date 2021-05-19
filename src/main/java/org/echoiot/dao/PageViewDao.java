package org.echoiot.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.echoiot.entity.pojo.PageView;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ATSELI
 * @version 1.0
 * @date 2020/11/2 20:47
 */
@Repository
public interface PageViewDao {

    /**
     * 增加定位信息
     *
     * @param pageView
     */
    @Insert("Insert into tb_page_view(page, uri, ip, province, city, add_time) values(#{pageView.page}, " +
            "#{pageView.uri}, #{pageView.ip}, #{pageView.province}, #{pageView.city}, #{pageView.add_time})")
    void addPageView(@Param("pageView") PageView pageView);


    /**
     * 查询对应省份的人数
     *
     * @param province IP对应省份
     * @return 对应省份的人数
     */
    @Select("Select count(*) as value from tb_page_view where province = #{province}")
    Integer findProvince(@Param("province") String province);


    /**
     * 查询对应城市的人数
     *
     * @param city IP对应城市
     * @return 对应城市的访问人数
     */
    @Select("Select count(*) as value from tb_page_view where city = #{city}")
    Integer findCity(@Param("city") String city);


    /**
     * 查询某段时间内访问省份TOP10
     *
     * @param startTime 初始时间戳
     * @param endTime   截止时间戳
     * @return 返回某段时间内访问省份TOP10
     */
    @Select("SELECT province AS name, COUNT(*) AS value FROM tb_page_view where " +
            "add_time between #{startTime} and #{endTime} GROUP BY name " +
            "ORDER BY value DESC limit 10")
    List<Map<String, Integer>> findProvinceTOP(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    /**
     * 访问某段时间内访问城市TOP10
     *
     * @param startTime 初始时间戳
     * @param endTime   截止时间戳
     * @return 返回某段时间内访问城市TOP10
     */
    @Select("SELECT city AS name, COUNT(*) AS value FROM tb_page_view where " +
            "add_time between #{startTime} and #{endTime} GROUP BY name " +
            "ORDER BY value DESC limit 10")
    List<Map<String, Integer>> findCityTOP(@Param("startTime") Long startTime, @Param("endTime") Long endTime);


    /**
     * 通过访问人数进行省份排序降序输出
     *
     * @return 返回排序后数据
     */
    @Select("SELECT province as name, COUNT(*) AS value FROM tb_page_view GROUP BY name ORDER BY value DESC")
    List<Map<String, Integer>> descSortProvince();


    /**
     * 通过访问人数进行城市排序降序输出
     *
     * @param province 所要排序的省份
     * @return 返回排序后数据
     */
    @Select("\n" +
            "SELECT city AS name, COUNT(*) AS value FROM tb_page_view WHERE " +
            "province = #{province} GROUP BY name ORDER BY value DESC")
    List<Map<String, Integer>> DescSortCity(@Param("province") String province);


    /**
     * 查询所有网站的热门程度并倒序输出
     *
     * @return 返回所有网站的热门程度并倒序输出
     */
    @Select("Select page as name, Count(*) as value from tb_page_view group by name order by value desc")
    List<Map<String, Integer>> DescUri();



    /**
     * 防止一天内同一ip反复访问同一个界面
     *
     * @param uri           uri
     * @param ip            ip
     * @param zeroHour     当天时间的零点零刻
     * @param nextZeroHour  次日的零点零刻
     * @return 返回是否有访问数据
     */
    @Select("Select count(*) from tb_page_view where ip = #{ip} " +
            "and uri = #{uri} " +
            "and add_time between #{zeroHour} and #{nextZeroHour}  ")
    Integer findSame(@Param("uri") String uri,
                     @Param("ip") String ip,
                     @Param("zeroHour") Long zeroHour,
                     @Param("nextZeroHour") Long nextZeroHour);
}
