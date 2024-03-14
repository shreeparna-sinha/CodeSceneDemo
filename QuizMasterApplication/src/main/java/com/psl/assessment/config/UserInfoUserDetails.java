package com.psl.assessment.config;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.psl.assessment.model.User;

/**
* Custom UserDetails implementation for Spring Security.
*/
public class UserInfoUserDetails implements UserDetails{
    private String email;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();
    
    
    /**
    * Constructs a UserInfoUserDetails object based on the provided User model.
    *
    * @param user The User model from which to extract user details.
    */
    public UserInfoUserDetails(User user) {
        email=user.getEmail();
        password=user.getPassword();
//        authorities= Arrays.stream(user.getRole().toString())
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString().toUpperCase()));
        System.out.println(password);
        for(GrantedAuthority g:authorities) {
        	System.out.println(g.toString());
        }
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
        return email;
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
