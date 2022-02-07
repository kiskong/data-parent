package com.cingk.datameta.filter;


import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.cingk.datameta.model.dto.ResponseDto;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求耗时统计拦截器，对所有web请求进行拦截，增加请求到响应结束的耗时统计
 */
@Component
@WebFilter
public class RequestCostFilter implements Filter {

    private static final String API_KEY = "/api/";

    /**
     * 时间单位，毫秒(ms)
     */
    private static final String TIME_UNIT_MS = "ms";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if (StrUtil.contains(path, API_KEY)) {
            apiFilter(request, response, chain);
        } else {
            chain.doFilter(request, response);
        }

    }

    private void apiFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);
        stopWatch.stop();
        overwriteResponse(response, responseWrapper, stopWatch);
    }

    /**
     * 重写响应内容
     *
     * @param response
     * @param responseWrapper
     * @param stopWatch
     * @throws IOException
     * @throws ServletException
     */
    private void overwriteResponse(ServletResponse response, ResponseWrapper responseWrapper, StopWatch stopWatch) throws IOException {
        byte[] content = responseWrapper.getContent();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
        ResponseDto responseDto = jsonObject.toJavaObject(ResponseDto.class);
        responseDto.setCostTime(stopWatch.getTotalTimeMillis() + TIME_UNIT_MS);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(JSONObject.toJSONBytes(responseDto));
        servletOutputStream.flush();
    }
}
