package com.demo.merchant.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author yangyueming
 */
@Slf4j
public class CsrfSecurityRequestMatcher implements RequestMatcher {
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    /**
     * 需要排除的url列表
     */
    private List<String> excludeUrlList;

    @Override
    public boolean matches(HttpServletRequest request) {

        if (excludeUrlList != null && excludeUrlList.size() > 0) {
            String servletPath = request.getServletPath();
            for (String url : excludeUrlList) {
                if (servletPath.contains(url)) {
                    log.info("++++" + servletPath);
                    return false;
                }
            }
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    public List<String> getExcludeUrlList() {
        return excludeUrlList;
    }

    public void setExcludeUrlList(List<String> excludeUrlList) {
        this.excludeUrlList = excludeUrlList;
    }
}