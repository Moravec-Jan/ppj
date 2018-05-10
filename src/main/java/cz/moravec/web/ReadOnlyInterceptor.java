package cz.moravec.web;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadOnlyInterceptor extends HandlerInterceptorAdapter {

    // in read only mode we allow only GET requests
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getMethod().equals("GET")){
            response.getWriter().write("Modifications are not allowed (disable read-only mode to enable modifications) ");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

      return true;
    }
}
