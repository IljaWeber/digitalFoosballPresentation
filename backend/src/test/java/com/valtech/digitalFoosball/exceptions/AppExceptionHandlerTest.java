package com.valtech.digitalFoosball.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppExceptionHandlerTest {

    private AppExceptionHandler appExceptionHandler;

    @BeforeEach
    void setUp() {
        appExceptionHandler = new AppExceptionHandler();
    }

    @Test
    public void handlePlayerDuplicateException_whenCalled_thenReturnResponseEntityWithStatus500() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new PlayerDuplicateException(""), new WebRequestDummy());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void handlePlayerDuplicateException_whenCalled_thenReturnErrorMessageForPlayerGivenInTheException() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new PlayerDuplicateException("One"), new WebRequestDummy());

        ErrorMessage body = (ErrorMessage) response.getBody();
        assertThat(body.getErrorMessage()).isEqualTo("One is used more than once");
    }

    @Test
    void handlePlayerDuplicateException_whenCalledWithoutMessage_thenReturnPlayerDuplicateExceptionAsErrorMessage() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new PlayerDuplicateException(null), new WebRequestDummy());

        ErrorMessage body = (ErrorMessage) response.getBody();
        assertThat(body.getErrorMessage()).isEqualTo("com.valtech.digitalFoosball.exceptions.PlayerDuplicateException");
    }

    @Test
    public void handleTeamDuplicateException_whenCalled_thenReturnResponseEntityWithStatus500() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new TeamDuplicateException(""), new WebRequestDummy());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void handleTeamDuplicateException_whenCalled_thenReturnErrorMessageForTeamGivenInTheException() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new TeamDuplicateException("One"), new WebRequestDummy());

        ErrorMessage body = (ErrorMessage) response.getBody();
        assertThat(body.getErrorMessage()).isEqualTo("One is used more than once");
    }

    @Test
    void handleTeamDuplicateException_whenCalledWithoutMessage_thenReturnTeamDuplicateExceptionAsErrorMessage() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new TeamDuplicateException(null), new WebRequestDummy());

        ErrorMessage body = (ErrorMessage) response.getBody();
        assertThat(body.getErrorMessage()).isEqualTo("com.valtech.digitalFoosball.exceptions.TeamDuplicateException");
    }

    private class WebRequestDummy implements WebRequest{
    @Override
    public String getHeader(String headerName) {
        return null;
    }

    @Override
    public String[] getHeaderValues(String headerName) {
        return new String[0];
    }

    @Override
    public Iterator<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getParameter(String paramName) {
        return null;
    }

    @Override
    public String[] getParameterValues(String paramName) {
        return new String[0];
    }

    @Override
    public Iterator<String> getParameterNames() {
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public boolean checkNotModified(long lastModifiedTimestamp) {
        return false;
    }

    @Override
    public boolean checkNotModified(String etag) {
        return false;
    }

    @Override
    public boolean checkNotModified(String etag, long lastModifiedTimestamp) {
        return false;
    }

    @Override
    public String getDescription(boolean includeClientInfo) {
        return null;
    }

    @Override
    public Object getAttribute(String name, int scope) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {

    }

    @Override
    public void removeAttribute(String name, int scope) {

    }

    @Override
    public String[] getAttributeNames(int scope) {
        return new String[0];
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {

    }

    @Override
    public Object resolveReference(String key) {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }
}
}
