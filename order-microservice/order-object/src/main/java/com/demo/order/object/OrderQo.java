package com.demo.order.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class OrderQo extends PageQo{
    private Long id;
    private String orderNo;
    private Long userid;
    private Long merchantid;
    private double amount;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    private String operator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify;

    private List<OrderDetailQo> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetailQo orderDetailQo){
        orderDetails.add(orderDetailQo);
    }

}
