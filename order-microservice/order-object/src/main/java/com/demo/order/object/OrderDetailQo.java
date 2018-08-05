package com.demo.order.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Data
public class OrderDetailQo {
    private Long id;
    private Long goodsid;
    private String goodsname;
    private String photo;
    private Integer nums;
    private float price;
    private double money;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;


}
