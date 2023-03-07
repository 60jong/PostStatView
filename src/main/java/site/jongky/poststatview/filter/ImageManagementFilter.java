package site.jongky.poststatview.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ImageManagementFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // response control
        setCacheControl(response);

        filterChain.doFilter(request, response);
    }

    private void setCacheControl(HttpServletResponse response) {
        response.addHeader("Cache-Control", "max-age=3600");
    }
}
