package com.targa.dev.shirotutorial.security.config;

import com.targa.dev.shirotutorial.security.control.UserService;
import com.targa.dev.shirotutorial.security.entity.DataBase;
import com.targa.dev.shirotutorial.security.entity.User;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

public class LicensedUserFilter extends AccessControlFilter {
    
    protected List<User> licensedUsers = DataBase.getLicensedUsers();
    protected UserService userService = new UserService();
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if(subject != null && subject.getPrincipal() != null){
            User user = userService.findByUsername((String)subject.getPrincipal());
            if(licensedUsers.contains(user)){
                return true;
            }            
        }

        WebUtils.issueRedirect(request, response, WebPages.NO_LICENSE_URL);
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }
}
