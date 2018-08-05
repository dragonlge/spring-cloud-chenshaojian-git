package com.demo.goods.object;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PictureQo extends PageQo {
    private Long id;
    private String pathInfo;
    private String fileName;
    private int width;
    private int height;
    private String flag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    private Long merchantid;


}
