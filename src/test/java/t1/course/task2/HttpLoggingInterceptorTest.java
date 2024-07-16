package t1.course.task2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import t1.course.task2.config.HttpLoggingInterceptor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


public class HttpLoggingInterceptorTest {

    private HttpLoggingInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        interceptor = new HttpLoggingInterceptor();
    }

    @Test
    public void testPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/test");

        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerInterceptor handler = mock(HandlerInterceptor.class);

        boolean result = interceptor.preHandle(request, response, handler);

        assertTrue(result);
    }

    @Test
    public void testAfterCompletion() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerInterceptor handler = mock(HandlerInterceptor.class);

        interceptor.afterCompletion(request, response, handler, null);
    }
}