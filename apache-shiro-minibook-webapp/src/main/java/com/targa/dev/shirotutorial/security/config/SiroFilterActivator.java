package com.targa.dev.shirotutorial.security.config;

import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class SiroFilterActivator extends ShiroFilter {

    public SiroFilterActivator(){
        super();
    }
}
