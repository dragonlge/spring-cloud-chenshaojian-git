package com.demo.merchant.restapi.controller;

import com.demo.merchant.domain.entity.Merchant;
import com.demo.merchant.domain.entity.Role;
import com.demo.merchant.domain.entity.User;
import com.demo.merchant.domain.service.UserService;
import com.demo.merchant.domain.util.CommonUtils;
import com.demo.merchant.domain.util.CopyUtil;
import com.demo.merchant.object.MerchantQo;
import com.demo.merchant.object.UserQo;
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
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public CompletableFuture<String> findById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> userService.findOne(id))
                .thenApply(user -> {
                    return new Gson().toJson(user);
                });
    }

    @RequestMapping("/names/{name}")
    public CompletableFuture<String> findByName(@PathVariable String name) {
        return CompletableFuture.supplyAsync(() -> userService.findByName(name))
                .thenApply(user -> {
                    return new Gson().toJson(user);
                });
    }

    @RequestMapping("/list")
    public CompletableFuture<String> findList() {
        return CompletableFuture.supplyAsync(() -> userService.findAll())
                .thenApply(users -> {
                    return new Gson().toJson(users);
                });
    }

    @RequestMapping(value = "/page")
    public CompletableFuture<String> findPage(Integer index, Integer size, String name, Long merchantId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserQo userQo = new UserQo();

                if (CommonUtils.isNotNull(index)) {
                    userQo.setPage(index);
                }
                if (CommonUtils.isNotNull(size)) {
                    userQo.setSize(size);
                }
                if (CommonUtils.isNotNull(name)) {
                    userQo.setName(name);
                }
                if (CommonUtils.isNotNull(merchantId)) {
                    MerchantQo merchantQo = new MerchantQo();
                    merchantQo.setId(merchantId);
                    userQo.setMerchant(merchantQo);
                }

                Page<User> users = userService.findAll(userQo);

                Map<String, Object> page = new HashMap<>();
                page.put("content", users.getContent());
                page.put("totalPages", users.getTotalPages());
                page.put("totalelements", users.getTotalElements());

                return new Gson().toJson(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CompletableFuture<String> save(@RequestBody UserQo userQo) {
        return CompletableFuture.supplyAsync(() -> {
            User user = CopyUtil.copy(userQo, User.class);

            List<Role> roleList = CopyUtil.copyList(userQo.getRoles(), Role.class);
            user.setRoles(roleList);
            user.setMerchant(CopyUtil.copy(userQo.getMerchant(), Merchant.class));

            userService.save(user);

            log.info("新增->ID=" + user.getId());
            return "1";
        });
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CompletableFuture<String> update(@RequestBody UserQo userQo) {
        return CompletableFuture.supplyAsync(() -> {
            User user = CopyUtil.copy(userQo, User.class);

            List<Role> roleList = CopyUtil.copyList(userQo.getRoles(), Role.class);
            user.setRoles(roleList);
            user.setMerchant(CopyUtil.copy(userQo.getMerchant(), Merchant.class));

            userService.save(user);

            log.info("修改->ID=" + user.getId());
            return "1";
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CompletableFuture<String> delete(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            userService.delete(id);
            log.info("删除->ID=" + id);
            return "1";
        });
    }
}
