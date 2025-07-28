package com.example.jwe.estudos_jwe.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityHeadersInterceptor implements HandlerInterceptor {
    
    private final JweService jweService;
    private static final String ENCRYPTED_HEADER = "X-Encrypted-Data";
    
    public SecurityHeadersInterceptor(JweService jweService) {
        this.jweService = jweService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String encryptedHeader = request.getHeader(ENCRYPTED_HEADER);
        if (encryptedHeader != null && !encryptedHeader.isEmpty()) {
            try {
            String decryptedHeader = jweService.decrypt(encryptedHeader);
            request.setAttribute("decryptedData", decryptedHeader);
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to decrypt header");
            return false;
        }
        return true;


    }
        return HandlerInterceptor.super.preHandle(request, response, handler);
}
}