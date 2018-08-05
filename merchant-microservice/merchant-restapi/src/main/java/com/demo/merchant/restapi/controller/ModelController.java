package com.demo.merchant.restapi.controller;

import com.demo.merchant.domain.entity.Kind;
import com.demo.merchant.domain.entity.Model;
import com.demo.merchant.domain.service.ModelService;
import com.demo.merchant.domain.util.CommonUtils;
import com.demo.merchant.domain.util.CopyUtil;
import com.demo.merchant.object.ModelQo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author yangyueming
 */
@RestController
@RequestMapping("/model")
@Slf4j
public class ModelController {
    @Autowired
    private ModelService modelService;

    @RequestMapping("/{id}")
    public CompletableFuture<String> findById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> modelService.findOne(id))
                .thenApply(resource -> {
                    return new Gson().toJson(resource);
                });
    }

    @RequestMapping("/list")
    public CompletableFuture<String> getList() {
        return CompletableFuture.supplyAsync(() -> {
            List<Model> models = modelService.findAll();
            return new Gson().toJson(models);
        });
    }

    @RequestMapping(value = "/page")
    public CompletableFuture<String> findPage(Integer index, Integer size, String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ModelQo modelQo = new ModelQo();

                if (CommonUtils.isNotNull(index)) {
                    modelQo.setPage(index);
                }
                if (CommonUtils.isNotNull(size)) {
                    modelQo.setSize(size);
                }
                if (CommonUtils.isNotNull(name)) {
                    modelQo.setName(name);
                }

                Page<Model> modelVos = modelService.findAll(modelQo);

                Map<String, Object> page = new HashMap<>();
                page.put("content", modelVos.getContent());
                page.put("totalPages", modelVos.getTotalPages());
                page.put("totalelements", modelVos.getTotalElements());

                return new Gson().toJson(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CompletableFuture<String> save(@RequestBody ModelQo modelQo) {
        return CompletableFuture.supplyAsync(() -> {
            Model model = CopyUtil.copy(modelQo, Model.class);

            model.setKind(CopyUtil.copy(modelQo.getKind(), Kind.class));

            modelService.save(model);

            log.info("新增->ID=" + model.getId());
            return "1";
        });
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CompletableFuture<String> update(@RequestBody ModelQo modelQo) {
        return CompletableFuture.supplyAsync(() -> {
            Model model = CopyUtil.copy(modelQo, Model.class);

            model.setKind(CopyUtil.copy(modelQo.getKind(), Kind.class));

            modelService.save(model);

            log.info("修改->ID=" + model.getId());
            return "1";
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CompletableFuture<String> delete(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            modelService.delete(id);
            log.info("删除->ID=" + id);
            return "1";
        });
    }
}
