package com.suny.configuration;

import com.suny.interceptor.LoginRequiredInterceptor;
import com.suny.interceptor.PassportInterceptor;
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

    private final PassportInterceptor passportInterceptor;

    private final LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    public WendaWebConfiguration(LoginRequiredInterceptor loginRequiredInterceptor, PassportInterceptor passportInterceptor) {
        this.loginRequiredInterceptor = loginRequiredInterceptor;
        this.passportInterceptor = passportInterceptor;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginRequiredInterceptor);
//        registry.addInterceptor(passportInterceptor);
        super.addInterceptors(registry);
    }
}
