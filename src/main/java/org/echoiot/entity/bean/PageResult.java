package org.echoiot.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Alfalfa99
 * @version 1.0
 * @date 2020/10/22 14:34
 * 分页返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult <T> {
    private long pages;
    private long total;
    private List<T> rows;
}
