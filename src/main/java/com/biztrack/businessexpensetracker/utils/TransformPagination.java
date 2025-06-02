package com.biztrack.businessexpensetracker.utils;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class TransformPagination {

    private String sortByColumn;
    private String sort;

    String [] sortArr = new String [2];

    public Map<String,Object> transformPagination(
            List<?> ls,
            Page<?> page,
            String column,
            String value
    ){
        Iterator<Sort.Order> orderIterator = page.getSort().stream().iterator();
        Sort.Order s = orderIterator.hasNext() ? orderIterator.next() : null;
        sortByColumn = s == null ? "id" : s.getProperty();
        sort = s == null ? "ASC" : s.getDirection().name();

        Map<String,Object> pageData = new HashMap<>();
        pageData.put("content", ls);
        pageData.put("total-data", page.getTotalElements());
        pageData.put("total-pages", page.getTotalPages());
        pageData.put("current-page", page.getNumber());
        pageData.put("size-per-page", page.getSize());
        pageData.put("sort-by", sortByColumn);
        pageData.put("sort", sort.trim().toLowerCase());
        pageData.put("column-name", column);
        pageData.put("value", value==null ? "" : value);
        return pageData;
    }

}