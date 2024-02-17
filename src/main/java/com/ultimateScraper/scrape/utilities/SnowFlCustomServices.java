package com.ultimateScraper.scrape.utilities;

import com.ultimateScraper.scrape.ServiceImpl.ExternalApiServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SnowFlCustomServices {
    private static final Logger logger = LogManager.getLogger(SnowFlCustomServices.class);

    private final GenericService genericService;

    public SnowFlCustomServices(GenericService genericService) {
        this.genericService = genericService;
    }

    public String fetchToken(String baseUrl, List<Map.Entry<String, String>> headers) {
        String html = genericService.fetchURL(baseUrl, headers);
        String scriptName = extractScriptName(html);
        String script = genericService.fetchURL(baseUrl + scriptName, headers);
        return extractToken(script);
    }

    private String extractScriptName(String html) {
        Pattern pattern = Pattern.compile("(b.min.js\\?v=\\w+?)\"");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            logger.error("Error at: {} - at function: {} - Script name not found", this.getClass().getSimpleName(), "extractScriptName");
            throw new RuntimeException("Script name not found");
        }
    }

    private String extractToken(String script) {
        Pattern pattern = Pattern.compile("url: *\"/\" *\\+ *(\\w+?) *\\+ *\"/newsfeed\"");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            String tokenVar = matcher.group(1);
            pattern = Pattern.compile(tokenVar + " *= *\"(\\w+?)\"");
            matcher = pattern.matcher(script);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        logger.error("Error at: {} - at function: {} - Token not found", this.getClass().getSimpleName(), "extractToken");
        throw new RuntimeException("Token not found");
    }

    public String generateQuery(String searchString, String token, String baseUrl) {
        String encodedSearch = URLEncoder.encode(searchString, StandardCharsets.UTF_8);
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return baseUrl + token + "/" + encodedSearch + "/" + randomString + "/0/SEED/NONE/1?=" + System.currentTimeMillis();
    }
}
