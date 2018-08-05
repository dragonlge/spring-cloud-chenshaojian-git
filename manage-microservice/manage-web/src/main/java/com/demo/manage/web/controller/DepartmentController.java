package com.demo.manage.web.controller;

import com.demo.manage.domain.entity.Department;
import com.demo.manage.domain.service.DepartmentService;
import com.demo.manage.object.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;

/**
 * @author yangyueming
 */
@Controller
@RequestMapping("/department")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) {
        model.addAttribute("user", user);
        return "department/index";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Department> getList(DepartmentVo departmentVo) {
        try {
            return departmentService.findAll(departmentVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        Department department = departmentService.findOne(id);
        model.addAttribute("department", department);
        return "department/show";
    }

    @RequestMapping("/new")
    public String create() {
        return "department/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public CompletableFuture<String> save(Department department) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("新增->ID=" + department.getId());
            departmentService.save(department);
            return "1";
        });
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        Department department = departmentService.findOne(id);

        model.addAttribute("department", department);
        return "department/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    public CompletableFuture<String> update(Department department) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("修改->ID=" + department.getId());
            departmentService.save(department);
            return "1";
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        departmentService.delete(id);
        log.info("删除->ID=" + id);
        return "1";
    }

}
