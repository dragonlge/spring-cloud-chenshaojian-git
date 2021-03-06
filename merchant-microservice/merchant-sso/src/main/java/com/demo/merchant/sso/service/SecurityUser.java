package com.demo.merchant.sso.service;

import com.demo.merchant.object.RoleQo;
import com.demo.merchant.object.UserQo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yangyueming
 */
public class SecurityUser extends UserQo implements UserDetails {
    private static final long serialVersionUID = 1L;

    public SecurityUser(UserQo user) {
        if (user != null) {
            this.setId(user.getId());
            this.setName(user.getName());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setSex(user.getSex());
            this.setCreated(user.getCreated());
            this.setRoles(user.getRoles());
            this.setMerchant(user.getMerchant());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<RoleQo> roleQoList = this.getRoles();
        if (roleQoList != null) {
            for (RoleQo roleQo : roleQoList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleQo.getName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
