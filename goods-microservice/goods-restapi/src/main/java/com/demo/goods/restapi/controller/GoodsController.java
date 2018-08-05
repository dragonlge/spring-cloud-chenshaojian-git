package com.demo.goods.restapi.controller;


import com.demo.goods.domain.entity.Goods;
import com.demo.goods.domain.service.GoodsService;
import com.demo.goods.domain.util.CommonUtils;
import com.demo.goods.object.GoodsQo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author yangyueming
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value="/{id}")
    public CompletableFuture<String> fnidById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Goods goods = goodsService.findOne(id);
            return new Gson().toJson(goods);
        });
    }


    @GetMapping
    public CompletableFuture<String> findAll(Integer index, Integer size, Long merchantid,
                                             String name, Long sortsid, Long subsid, String created) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GoodsQo goodsQo = new GoodsQo();

                if(CommonUtils.isNotNull(index)){
                    goodsQo.setPage(index);
                }
                if(CommonUtils.isNotNull(size)){
                    goodsQo.setSize(size);
                }
                if(CommonUtils.isNotNull(merchantid)){
                    goodsQo.setMerchantid(merchantid);
                }
                if(CommonUtils.isNotNull(name)){
                    goodsQo.setName(name);
                }
                if(CommonUtils.isNotNull(sortsid)){
                    goodsQo.setSortsid(sortsid);
                }
                if(CommonUtils.isNotNull(subsid)){
                    goodsQo.setSubsid(subsid);
                }
                if(CommonUtils.isNotNull(created)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    goodsQo.setCreated(sdf.parse(created));
                }

                Page<Goods> orderses = goodsService.findAll(goodsQo);

                Map<String, Object> page = new HashMap<>();
                page.put("content", orderses.getContent());
                page.put("totalPages", orderses.getTotalPages());
                page.put("totalelements", orderses.getTotalElements());

                return new Gson().toJson(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @PostMapping
    public CompletableFuture<String> save(@RequestBody GoodsQo goodsQo) {
        return CompletableFuture.supplyAsync(() -> {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsQo, goods);
            goods.setCreated(new Date());

            goodsService.save(goods);

            log.info("新增->ID=" + goods.getId());
            return goods.getId().toString();
        });
    }

    @PutMapping
    public CompletableFuture<String> update(@RequestBody GoodsQo goodsQo) {
        return CompletableFuture.supplyAsync(() -> {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsQo, goods);


            goodsService.save(goods);

            log.info("修改->ID=" + goods.getId());
            return goods.getId().toString();
        });
    }

    @DeleteMapping(value="/{id}")
    public CompletableFuture<String> delete(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            goodsService.delete(id);

            log.info("删除->ID=" + id);
            return id.toString();
        });
    }

}
