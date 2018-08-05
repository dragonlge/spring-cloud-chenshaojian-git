package com.demo.manage.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private PathMatcher pathMatcher = new AntPathMatcher();

    private String urlroles;

    public CustomSecurityMetadataSource(String urlroles) {
        super();
        this.urlroles = urlroles;
        resourceMap = loadResourceMatchAuthority();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {

        Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();

        if (urlroles != null && !urlroles.isEmpty()) {
            String[] resouces = urlroles.split(";");
            for (String resource : resouces) {
                String[] urls = resource.split("=");
                String[] roles = urls[1].split(",");
                Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
                for (String role : roles) {
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
                //key：url, value：roles
                map.put(urls[0].trim(), list);
            }
        } else {
            log.error("'securityconfig.urlroles' must be set");
        }

        log.info("Loaded UrlRoles Resources.");
        return map;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        log.debug("request url is  " + url);

        if (resourceMap == null)
            resourceMap = loadResourceMatchAuthority();

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (pathMatcher.match(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}
