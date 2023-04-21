package me.dmk.app.handler;

import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

/**
 * Created by DMK on 18.04.2023
 */

@Component
public class FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        FlashMap flashMap = new FlashMap();

        //Whether the error is database server dependent
        if (exception.getCause() instanceof MongoException || exception.getCause() instanceof MongoTimeoutException) {
            flashMap.put("error", "Wystąpił błąd z serwerem.");
        } else if (exception instanceof BadCredentialsException) {
            flashMap.put("error", "Nieprawidłowe dane logowania.");
        } else {
            flashMap.put("error", exception.getMessage());
        }

        FlashMapManager flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        response.sendRedirect(request.getHeader("referer"));
    }
}
