package t1.course.task2.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import t1.course.task2.annotation.TrackAsyncTime;
import t1.course.task2.annotation.TrackTime;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.stream.Collectors;

public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);

    private static final String TRACK_TIME = "trackTime";
    private static final String TRACK_ASYNC_TIME = "trackAsyncTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (method.isAnnotationPresent(TrackTime.class) || method.isAnnotationPresent(TrackAsyncTime.class)) {
                long startTime = System.currentTimeMillis();
                request.setAttribute(TRACK_TIME, startTime);

                if (method.isAnnotationPresent(TrackAsyncTime.class)) {
                    request.setAttribute(TRACK_ASYNC_TIME, startTime);
                }
            }
        }

        logger.info("Incoming request: method={}, uri={}, headers={}", request.getMethod(), request.getRequestURI(), getHeadersAsString(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getAttribute(TRACK_TIME) != null) {
            long startTime = (Long) request.getAttribute(TRACK_TIME);
            long duration = System.currentTimeMillis() - startTime;

            logger.info("Outgoing response: status={}, headers={}, duration={}ms", response.getStatus(), getHeadersAsString(response), duration);
        } else {
            logger.info("Outgoing response: status={}, headers={}", response.getStatus(), getHeadersAsString(response));
        }
    }

    private String getHeadersAsString(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .map(headerName -> headerName + "=" + request.getHeader(headerName))
                .collect(Collectors.joining(", "));
    }

    private String getHeadersAsString(HttpServletResponse response) {
        return response.getHeaderNames()
                .stream()
                .map(headerName -> headerName + "=" + response.getHeader(headerName))
                .collect(Collectors.joining(", "));
    }
}
