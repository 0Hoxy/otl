package com.otl.user.config;

import com.otl.user.constant.Role;
import com.otl.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Secutiry Session => Authentication => UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    //일반 로그인
    public PrincipalDetails(User user){
        this.user = user;
    }

    //OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    //UserDetails
    @Override //해당 User 권한을 리턴하는 곳
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole() == Role.ADMIN ? "ROLE_ADMIN" : "ROLE_USER";
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        // ex) 1년동안 로그인 안하면 휴먼계정
        return true;
    }

    //OAuth2User
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName(){
        return user.getName();
    }
}
