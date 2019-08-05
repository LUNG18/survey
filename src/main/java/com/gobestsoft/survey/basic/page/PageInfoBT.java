package com.gobestsoft.survey.basic.page;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页结果的封装
 */
@Data
public class PageInfoBT<T> {

    private List<T> data;
    private long count;

    public PageInfoBT(Page<T> page) {
        this.data = page.getRecords();
        this.count = page.getTotal();
    }

}
