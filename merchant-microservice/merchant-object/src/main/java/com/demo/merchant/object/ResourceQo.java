package com.demo.merchant.object;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResourceQo extends PageQo implements Serializable {
    private Long id;
    private String name;
    private String url;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    private ModelQo model;

}
