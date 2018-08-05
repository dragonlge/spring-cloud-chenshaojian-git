package com.demo.merchant.restapi.controller;

import com.demo.merchant.domain.entity.Resource;
import com.demo.merchant.domain.entity.Role;
import com.demo.merchant.domain.entity.User;
import com.demo.merchant.domain.service.RoleService;
import com.demo.merchant.domain.service.UserService;
import com.demo.merchant.domain.util.CommonUtils;
import com.demo.merchant.domain.util.CopyUtil;
import com.demo.merchant.object.RoleQo;
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
@RequestMapping("/role")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public CompletableFuture<String> findById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> roleService.findOne(id))
                .thenApply(role -> {
                    return new Gson().toJson(role);
                });
    }

    @RequestMapping("/list")
    public CompletableFuture<String> findList() {
        return CompletableFuture.supplyAsync(() -> roleService.findAll())
                .thenApply(roles -> {
                    return new Gson().toJson(roles);
                });
    }

    @RequestMapping(value = "/page")
    public CompletableFuture<String> findPage(Integer index, Integer size, String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                RoleQo roleQo = new RoleQo();

                if (CommonUtils.isNotNull(index)) {
                    roleQo.setPage(index);
                }
                if (CommonUtils.isNotNull(size)) {
                    roleQo.setSize(size);
                }
                if (CommonUtils.isNotNull(name)) {
                    roleQo.setName(name);
                }

                Page<Role> roles = roleService.findAll(roleQo);

                Map<String, Object> page = new HashMap<>();
                page.put("content", roles.getContent());
                page.put("totalPages", roles.getTotalPages());
                page.put("totalelements", roles.getTotalElements());

                return new Gson().toJson(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CompletableFuture<String> save(@RequestBody RoleQo roleQo) {
        return CompletableFuture.supplyAsync(() -> {
            Role role = CopyUtil.copy(roleQo, Role.class);

            List<Resource> resourceList = CopyUtil.copyList(roleQo.getResources(), Resource.class);
            role.setResources(resourceList);

            roleService.save(role);

            log.info("新增->ID=" + role.getId());
            return "1";
        });
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CompletableFuture<String> update(@RequestBody RoleQo roleQo) {
        return CompletableFuture.supplyAsync(() -> {
            Role role = CopyUtil.copy(roleQo, Role.class);

            List<Resource> resourceList = CopyUtil.copyList(roleQo.getResources(), Resource.class);
            role.setResources(resourceList);

            roleService.save(role);

            log.info("修改->ID=" + role.getId());
            return "1";
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CompletableFuture<String> delete(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            //让具有此角色的用户脱离关系
            List<User> userList = userService.findByRoleId(id);
            if (userList != null && userList.size() > 0) {
                for (User user : userList) {
                    for (Role role : user.getRoles()) {
                        if (role.getId().equals(id)) {
                            user.getRoles().remove(role);
                            userService.save(user);
                            break;
                        }
                    }
                }
            }
            //安全删除角色
            roleService.delete(id);
            log.info("删除->ID=" + id);
            return "1";
        });
    }
}
