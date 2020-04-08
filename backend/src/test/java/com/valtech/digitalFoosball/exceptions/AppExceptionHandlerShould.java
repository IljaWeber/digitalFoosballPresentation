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

public class AppExceptionHandlerShould {

    private AppExceptionHandler appExceptionHandler;

    @BeforeEach
    void setUp() {
        appExceptionHandler = new AppExceptionHandler();
    }

    @Test
    public void use_the_name_from_the_exception_to_create_the_error_message() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new NameDuplicateException("One"), new WebRequestDummy());

        ErrorMessage body = (ErrorMessage) response.getBody();
        String errorMessage = body.getErrorMessage();
        assertThat(errorMessage).isEqualTo("One is used more than once");
    }

    @Test
    public void answer_with_HTTP_status_500() {
        ResponseEntity<Object> response = appExceptionHandler.handleDuplicateException(new NameDuplicateException(""), new WebRequestDummy());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
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
