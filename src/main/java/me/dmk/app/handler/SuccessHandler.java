package me.dmk.app.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

/**
 * Created by DMK on 18.04.2023
 */

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
         FlashMap flashMap = new FlashMap();
         flashMap.put("success", "Zalogowano.");

         FlashMapManager flashMapManager = new SessionFlashMapManager();
         flashMapManager.saveOutputFlashMap(flashMap, request, response);

         response.sendRedirect(
                 request.getHeader("referer")
         );
    }
}
