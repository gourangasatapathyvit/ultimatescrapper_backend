package com.ultimateScraper.scrape.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class RateLimitFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(RateLimitFilter.class);
    private final GenericService rateLimiterService;
    @Autowired
    public RateLimitFilter(GenericService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String ipAddress = httpRequest.getRemoteAddr(); // Get client's IP address
        logger.info("IP ADDRESS at: {} {}", "doFilter", ipAddress);
        // Perform rate limiting logic here
        if (rateLimiterService.exceedsRateLimit(ipAddress)) {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Rate limit exceeded");
            return;
        }
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Initialization logic
    }

    @Override
    public void destroy() {
        // Cleanup logic
    }
}
