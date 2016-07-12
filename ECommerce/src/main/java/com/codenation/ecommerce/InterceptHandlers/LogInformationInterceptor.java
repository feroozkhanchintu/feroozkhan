package com.codenation.ecommerce.InterceptHandlers;

import com.codenation.ecommerce.Helpers.AWSMessagingService;
import com.codenation.ecommerce.Pojos.LogInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ferooz on 11/07/16.
 */
@Component
public class LogInformationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    AWSMessagingService awsMessagingService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {
        LogInformation logInformation = new LogInformation();
        int responseCode = response.getStatus();
        String url = request.getRequestURI();
        String ipAddress = request.getRemoteAddr();
        long startTime = (Long)request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        String parameters = "";
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        parameters = map.toString();

        logInformation.setRequestType(request.getMethod());
        logInformation.setStartTime(new Date());
        logInformation.setExecTime(executeTime);
        logInformation.setIpAddress(ipAddress);
        logInformation.setURL(url);
        logInformation.setParameters(parameters);
        logInformation.setResponseCode(responseCode);

        awsMessagingService.sendMessageToQueue(logInformation);

    }
}
