package com.demo.merchant.object;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangyueming
 */
@Data
public class UserQo extends PageQo implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Integer sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    private List<RoleQo> roles = new ArrayList<>();

    private MerchantQo merchant;

    public void addRole(RoleQo roleQo) {
        this.roles.add(roleQo);
    }

}
