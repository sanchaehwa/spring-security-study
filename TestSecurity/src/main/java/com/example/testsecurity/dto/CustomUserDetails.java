package com.example.testsecurity.dto;

import com.example.testsecurity.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class  CustomUserDetails implements UserDetails {

   private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    //권한을 주기위한 Role 값을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    //사용자 비밀번호 반환 (암호화된 상태여야함)
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    //사용자 이름 반환
    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    //@return 사용자 계정의 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //@return 사용자 계정의 잠김 상태 여부

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //@return 사용자 계정의 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //@return tk
    @Override
    public boolean isEnabled() {
        return true;
    }
}
