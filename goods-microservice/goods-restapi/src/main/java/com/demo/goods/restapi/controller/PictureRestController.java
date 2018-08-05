package com.demo.goods.restapi.controller;

import com.demo.goods.domain.entity.Picture;
import com.demo.goods.domain.service.PictureService;
import com.demo.goods.domain.util.CommonUtils;
import com.demo.goods.object.PictureQo;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author yangyueming
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureRestController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/{id}")
    public CompletableFuture<String> fnidById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Picture picture = pictureService.findOne(id);
            return new Gson().toJson(picture);
        });
    }


    @GetMapping
    public CompletableFuture<String> findAll(Integer index, Integer size, Long merchantid, String created) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PictureQo pictureQo = new PictureQo();

                if (CommonUtils.isNotNull(index)) {
                    pictureQo.setPage(index);
                }
                if (CommonUtils.isNotNull(size)) {
                    pictureQo.setSize(size);
                }
                if (CommonUtils.isNotNull(merchantid)) {
                    pictureQo.setMerchantid(merchantid);
                }
                if (CommonUtils.isNotNull(created)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    pictureQo.setCreated(sdf.parse(created));
                }

                Page<Picture> pictures = pictureService.findAll(pictureQo);

                Map<String, Object> page = new HashMap<>();
                page.put("content", pictures.getContent());
                page.put("totalPages", pictures.getTotalPages());
                page.put("totalelements", pictures.getTotalElements());

                return new Gson().toJson(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @PostMapping
    public CompletableFuture<String> save(@RequestBody PictureQo pictureQo) {
        return CompletableFuture.supplyAsync(() -> {
            Picture picture = new Picture();
            BeanUtils.copyProperties(pictureQo, picture);

            pictureService.save(picture);

            log.info("新增->ID=" + picture.getId());
            return picture.getId().toString();
        });
    }

    @PutMapping
    public CompletableFuture<String> update(@RequestBody PictureQo pictureQo) {
        return CompletableFuture.supplyAsync(() -> {
            Picture picture = new Picture();
            BeanUtils.copyProperties(pictureQo, picture);


            pictureService.save(picture);

            log.info("修改->ID=" + picture.getId());
            return picture.getId().toString();
        });
    }

    @DeleteMapping(value = "/{id}")
    public CompletableFuture<String> delete(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            pictureService.delete(id);

            log.info("删除->ID=" + id);
            return id.toString();
        });
    }

    @DeleteMapping(value = "/deleteByFileName/{fileName}")
    public CompletableFuture<String> deleteByFileName(@PathVariable String fileName) {
        return CompletableFuture.supplyAsync(() -> {
            pictureService.deleteByFileName(fileName);

            log.info("删除->ID=" + fileName);
            return "1";
        });
    }
}
