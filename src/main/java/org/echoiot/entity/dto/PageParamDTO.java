package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Author Alfalfa99
 * @Date 2020/9/18 13:33
 * @Version 1.0
 * 分页查询DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParamDTO {
    /**
     * cp: 当前页数
     * ps: 每一页的数据条数
     * order: 需要排序的字段-排序的方式(asc:升序,desc：降序)
     */
    @NotNull(message = "当前页数不能为空")
    private Integer cp;
    @NotNull(message = "每页条数不能为空")
    private Integer ps;
    private Map<String,String> order;
}
