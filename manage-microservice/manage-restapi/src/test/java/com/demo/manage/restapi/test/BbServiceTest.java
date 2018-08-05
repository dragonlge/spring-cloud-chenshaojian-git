package com.demo.manage.restapi.test;

import com.demo.manage.domain.config.JpaConfiguration;
import com.demo.manage.domain.entity.Department;
import com.demo.manage.domain.entity.Operator;
import com.demo.manage.domain.entity.Part;
import com.demo.manage.domain.service.DepartmentService;
import com.demo.manage.domain.service.OperatorService;
import com.demo.manage.domain.service.PartService;
import com.demo.manage.restapi.ManageRestApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class, ManageRestApiApplication.class})
@SpringBootTest
@Slf4j
public class BbServiceTest {
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private PartService partService;
    @Autowired
    private DepartmentService departmentService;

    @Test
    public void insertData() {
        Part part = new Part();
        part.setName("admins");

        partService.save(part);

        Department department = new Department();
        department.setName("技术部");
        departmentService.save(department);

        Operator operator = new Operator();
        operator.setName("admin");
        operator.setSex(1);

        operator.setDepartment(department);

        List<Part> partList = operator.getParts();
        partList.add(part);
        operator.setParts(partList);

        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        operator.setPassword(bc.encode("123456"));

        operatorService.save(operator);
        assert operator.getId() > 0 : "create error";
    }

    //@Test
    public void getData() {
        Operator operator = operatorService.findByName("admin");
        assert operator != null : "not find";
        log.info("=================operator name={}, part name={}, department name={}",
                operator.getName(), operator.getParts().get(0).getName(), operator.getDepartment().getName());
    }
}
