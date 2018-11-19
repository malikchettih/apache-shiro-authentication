package com.targa.dev.shirotutorial.security.config;

import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.util.WebUtils;

public class ShiroConfiguration {


    public static WebSecurityManager getSecurityManager(){

        DefaultWebSecurityManager securityManager = null;

        if(securityManager == null){

            AuthorizingRealm authorizingRealm = new SecurityRealm();
            CredentialsMatcher credentialsMatcher = new SimpleCredentialsMatcher();
            authorizingRealm.setCredentialsMatcher(credentialsMatcher);

            securityManager = new DefaultWebSecurityManager(authorizingRealm);

            CacheManager cacheManager = new EhCacheManager();
            ((EhCacheManager) cacheManager).setCacheManagerConfigFile("classpath:ehcache.xml");
            securityManager.setCacheManager(cacheManager);

            byte[] cypherKey = String.format(
                    "0x%s", Hex.encodeToString(new AesCipherService().generateNewKey().getEncoded())).getBytes();
            RememberMeManager rememberMeManager = new CookieRememberMeManager();
            ((CookieRememberMeManager) rememberMeManager).setCipherKey(cypherKey);

            securityManager.setRememberMeManager(rememberMeManager);

        }

        return securityManager;
    }

    public static FilterChainResolver getFilterChainResolver(){

        FilterChainResolver filterChainResolver = null;

        if(filterChainResolver == null){

            FormAuthenticationFilter authc = new FormAuthenticationFilter();
            AnonymousFilter anon = new AnonymousFilter();
            UserFilter user = new UserFilter();
            LogoutFilter logout = new LogoutFilter();
            LicensedUserFilter licuser = new LicensedUserFilter();

            authc.setLoginUrl(WebPages.LOGIN_URL);
            user.setLoginUrl(WebPages.LOGIN_URL);

            FilterChainManager filterChainManager = new DefaultFilterChainManager();

            filterChainManager.addFilter("authc", authc);
            filterChainManager.addFilter("anon", anon);
            filterChainManager.addFilter("user", user);
            filterChainManager.addFilter("logout", logout);
            filterChainManager.addFilter("licuser", licuser);
            
            filterChainManager.createChain("/css/**", "anon");
            filterChainManager.createChain("/api/**", "anon");
            filterChainManager.createChain(WebPages.NO_LICENSE_URL, "anon");
            filterChainManager.createChain(WebPages.LOGIN_URL, "authc");
            filterChainManager.createChain("/**", "user,licuser");


            PathMatchingFilterChainResolver pathMatchingFilterChainResolver = new PathMatchingFilterChainResolver();
            pathMatchingFilterChainResolver.setFilterChainManager(filterChainManager);
            filterChainResolver = pathMatchingFilterChainResolver;
        }
        return filterChainResolver;
    }
}
