package com.demo.manage.web.controller;

import com.demo.manage.domain.entity.Department;
import com.demo.manage.domain.entity.Operator;
import com.demo.manage.domain.entity.Part;
import com.demo.manage.domain.service.DepartmentService;
import com.demo.manage.domain.service.OperatorService;
import com.demo.manage.domain.service.PartService;
import com.demo.manage.object.OperatorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author yangyueming
 */
@Controller
@RequestMapping("/operator")
@Slf4j
public class OperatorController {
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private PartService partService;
    @Autowired
    private DepartmentService departmentService;


    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) {
        model.addAttribute("user", user);
        return "operators/index";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Operator> getList(OperatorVo operatorVo) {
        try {
            return operatorService.findAll(operatorVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        Operator operator = operatorService.findOne(id);
        model.addAttribute("operators", operator);
        return "operators/show";
    }

    @RequestMapping("/new")
    public String create(ModelMap model, Operator operator) {

        List<Part> partList = partService.findAll();
        List<Department> departmentList = departmentService.findAll();

        model.addAttribute("parts", partList);
        model.addAttribute("departments", departmentList);
        model.addAttribute("operators", operator);
        return "operators/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public CompletableFuture<String> save(Operator operator) {
        return CompletableFuture.supplyAsync(() -> {
            BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
            operator.setPassword(bpe.encode(operator.getPassword()));
            log.info("新增->ID=" + operator.getId());
            operatorService.save(operator);
            return "1";
        });
    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        Operator operator = operatorService.findOne(id);

        List<Part> partList = partService.findAll();

        List<Department> departmentList = departmentService.findAll();

        List<Long> pids = new ArrayList<>();
        for (Part part : operator.getParts()) {
            pids.add(part.getId());
        }

        model.addAttribute("operators", operator);
        model.addAttribute("parts", partList);
        model.addAttribute("pids", pids);
        model.addAttribute("departments", departmentList);
        return "operators/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    public CompletableFuture<String> update(Operator operator) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("修改->ID=" + operator.getId());
            operatorService.save(operator);
            return "1";
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        operatorService.delete(id);
        log.info("删除->ID=" + id);
        return "1";
    }

}
