package com.biztrack.businessexpensetracker.utils;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransformPagination {

    private String sortByColumn;// disortir berdasarkan kolom apa?
    private String sort;// disortir berdasarkan apa ? Desc atau Asc

    String [] sortArr = new String [2];

    public Map<String,Object> transformPagination(
            List ls,
            Page page,
            String column,
            String value
    ){
        Sort s = page.getSort();
        sortArr = s.toString().split(":");
        sortByColumn = sortArr[0];
        Boolean isSorted = sortByColumn.equals("UNSORTED");
        sortByColumn = isSorted ? "id" : sortByColumn;
        sort = isSorted ? "asc" : sortArr[1];

        Map<String,Object> pageData = new HashMap<>();
        pageData.put("content",ls);
        pageData.put("total-data",page.getTotalElements());
        pageData.put("total-pages",page.getTotalPages());
        pageData.put("current-page",page.getNumber());
        pageData.put("size-per-page",page.getSize());
        pageData.put("sort-by",sortByColumn);
        pageData.put("sort",sort.trim().toLowerCase());
        pageData.put("column-name",column);
        pageData.put("value",value==null?"":value);
        return pageData;
    }

}