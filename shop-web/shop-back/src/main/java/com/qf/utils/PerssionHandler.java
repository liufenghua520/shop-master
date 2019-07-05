package com.qf.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/4 19:09
 */
@Component
public class PerssionHandler {
    /**
     * 权限验证
     */
    public boolean hasPerssion(HttpServletRequest request, Authentication authentication){

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails){
            String requestURI = request.getRequestURI();

            UserDetails userDetails = (UserDetails) principal;

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                if (antPathMatcher.match(requestURI,authority.getAuthority())){
                    //权限验证通过
                    return true;
                }
            }
        }

        return false;
    }
}
