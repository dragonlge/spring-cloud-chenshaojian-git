package com.demo.goods.object;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GoodsQo extends PageQo {
    private Long id;
    private Long merchantid;
    private Long sortsid;
    private Long subsid;
    private String name;
    private String contents;
    private String photo;
    private float price;
    private Integer buynum;
    private Integer reserve;
    private String operator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;


}
