package com.demo.merchant.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class ModelQo extends PageQo implements java.io.Serializable{
    private Long id;
    private String name;
    private String host;
    private String icon;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    private List<ResourceQo> resources;
    private KindQo kind;

}
