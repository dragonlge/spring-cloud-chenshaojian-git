package com.demo.merchant.object;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class KindQo extends PageQo implements java.io.Serializable {
    private Long id;
    private String name;
    private String link;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;


}
