package com.ims.exception;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@ControllerAdvice
public class GlobalSessionCheck {

    @ModelAttribute
    public void checkSession(HttpSession session, HttpServletResponse response) throws IOException {
        String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRequestURI();

        if (!requestURI.startsWith("/login") && !requestURI.startsWith("/register")) {
            if (session == null || session.getAttribute("loggedInUser") == null) {
                response.sendRedirect("/login?sessionExpired=true");
            }
        }
    }
}
