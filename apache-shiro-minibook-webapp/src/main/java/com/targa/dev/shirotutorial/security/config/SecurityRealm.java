package com.targa.dev.shirotutorial.security.config;

import com.targa.dev.shirotutorial.security.control.UserService;
import com.targa.dev.shirotutorial.security.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class SecurityRealm extends AuthorizingRealm  {

    private final static Logger logger = LoggerFactory.getLogger(SecurityRealm.class);

    private UserService userService;

    public SecurityRealm(){
        super();
        this.userService = new UserService();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        Set<String> roleNames = new HashSet<>();
        roleNames.add(this.userService.findByUsername(username).getRole().getName());

        AuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();

        if(username == null){
            logger.warn("Username is null.");
            return null;
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            logger.warn("No account found for user [" + username + "]");
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
