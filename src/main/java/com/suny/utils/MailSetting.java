package com.suny.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 孙建荣 on 17-9-8.上午9:45
 */
@ConfigurationProperties(prefix = "mail", locations = "classpath:mailSetting.properties")
@Component
public class MailSetting {

    private String username;
    private String password;
    private String host;
    private int port;
    private String protocol;
    private String defaultEncoding;

    public MailSetting() {
    }

    public MailSetting(String username, String password, String host, int port, String protocol, String defaultEncoding) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.defaultEncoding = defaultEncoding;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }
}
