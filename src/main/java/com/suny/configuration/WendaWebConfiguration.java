package com.suny.configuration;

import com.suny.interceptor.LoginRequiredInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 * Created by 孙建荣 on 17-9-2.下午9:12
 */
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {

    private final LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    public WendaWebConfiguration(LoginRequiredInterceptor loginRequiredInterceptor) {
        this.loginRequiredInterceptor = loginRequiredInterceptor;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor);
        super.addInterceptors(registry);
    }
}
