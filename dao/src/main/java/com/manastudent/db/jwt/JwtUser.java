package com.manastudent.db.jwt;

import com.manastudent.db.domain.Role;
import com.manastudent.db.domain.User;
import com.manastudent.db.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shuang.kou
 * @description JWT用户对象
 */
@Component
public class JwtUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;
    private List<Role> roles;

    public JwtUser() {
    }

    /**
     * 通过 user 对象创建jwtUser
     */
    public JwtUser(User user, Collection<? extends GrantedAuthority> authorities, List<Role> roles) {
        this.id = user.getId();
        this.username = user.getName();
        this.password = user.getPassword();
        this.enabled = user.getEnabled() == null ? true : user.getEnabled();
        this.authorities = authorities;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return this.enabled;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
