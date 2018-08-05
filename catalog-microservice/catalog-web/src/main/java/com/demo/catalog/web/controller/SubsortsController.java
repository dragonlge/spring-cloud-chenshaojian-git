package com.demo.catalog.web.controller;


import com.demo.catalog.client.service.SubsortsFuture;
import com.demo.catalog.client.service.SubsortsRestService;
import com.demo.catalog.client.util.TreeMapConvert;
import com.demo.catalog.object.SubsortsQo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author yangyueming
 */
@RestController
@RequestMapping("/subsorts")
@Slf4j
public class SubsortsController {
    @Autowired
    private SubsortsFuture subsortsFuture;

    @Autowired
    private SubsortsRestService subsortsService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("/subsorts/index");
    }

    @RequestMapping(value = "/{id}")
    public CompletableFuture<ModelAndView> findByName(@PathVariable Long id) {
        return subsortsFuture.findById(id).thenApply(subsorts ->
                new ModelAndView("subsorts/show", "subsorts", new Gson().fromJson(subsorts, SubsortsQo.class)));
    }

    @RequestMapping(value = "/list")
    public CompletableFuture<Page<Map<String, Object>>> findAll(SubsortsQo subsortsQo) {
        return subsortsFuture.findPage(subsortsQo.getPage(), subsortsQo.getSize(), subsortsQo.getName()).thenApply(json -> {
            Gson gson = TreeMapConvert.getGson();

            TreeMap<String, Object> page = gson.fromJson(json, new TypeToken<TreeMap<String, Object>>() {
            }.getType());

            Pageable pageable = new PageRequest(subsortsQo.getPage(), subsortsQo.getSize(), null);
            List<SubsortsQo> list = new ArrayList<>();

            if (page.get("content") != null)
                list = gson.fromJson(page.get("content").toString(), new TypeToken<List<SubsortsQo>>() {
                }.getType());
            String count = page.get("totalelements").toString();

            return new PageImpl(list, pageable, new Long(count));
        });
    }

    @RequestMapping("/new")
    public CompletableFuture<ModelAndView> create() {
        return CompletableFuture.supplyAsync(() -> {
            return new ModelAndView("subsorts/new");
        });
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CompletableFuture<String> save(SubsortsQo subsortsQo) {
        return subsortsFuture.create(subsortsQo).thenApply(sid -> {
            log.info("新增->ID=" + sid);
            return sid;
        });
    }

    @RequestMapping("/edit/{id}")
    public CompletableFuture<ModelAndView> update(@PathVariable Long id, ModelMap model) {
        return CompletableFuture.supplyAsync(() -> {
            String subs = subsortsService.findById(id);
            SubsortsQo subsorts = new Gson().fromJson(subs, SubsortsQo.class);

            model.addAttribute("subsorts", subsorts);
            return new ModelAndView("subsorts/edit");
        });
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CompletableFuture<String> update(SubsortsQo subsortsQo) {
        return subsortsFuture.update(subsortsQo).thenApply(sid -> {
            log.info("修改->ID=" + sid);
            return sid;
        });
    }

    @RequestMapping(value = "/delete/{id}")
    public CompletableFuture<String> delete(@PathVariable Long id) {
        return subsortsFuture.delete(id).thenApply(sid -> {
            log.info("删除->ID=" + sid);
            return sid;
        });
    }

}
