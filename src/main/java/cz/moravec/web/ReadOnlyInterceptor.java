package cz.moravec.web;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadOnlyInterceptor extends HandlerInterceptorAdapter {

    /**
     * Active only when read-only mode is active.
     * Allows only GET requests.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getMethod().equals("GET")){
            response.getWriter().write("Modifications are not allowed (disable read-only mode to enable modifications)");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }

      return true;
    }
}
