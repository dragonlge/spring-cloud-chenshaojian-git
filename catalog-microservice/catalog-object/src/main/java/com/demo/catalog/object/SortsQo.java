package com.demo.catalog.object;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SortsQo extends PageQo {
    private Long id;
    private String name;
    private String operator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    private List<SubsortsQo> subsortses = new ArrayList<>();


    public void addSubsorts(SubsortsQo subsortsQo) {
        subsortses.add(subsortsQo);
    }


}
