package com.crisansa4.demo.retail.infrastructure.controller;

import com.crisansa4.demo.retail.infrastructure.controller.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void handleResourceNotFoundException_returns404() {
        when(request.getRequestURI()).thenReturn("/api/test");
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex, request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        // Asegura que response.getBody() no es null antes de acceder a sus métodos
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Not Found", body.getError());
        assertEquals("Not found", body.getMessage());
        assertEquals("/api/test", body.getPath());
    }

    @Test
    void handleTypeMismatch_returns400() {
        when(request.getRequestURI()).thenReturn("/api/test");
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("param");
        when(ex.getValue()).thenReturn("badValue");
        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Bad Request", body.getError());
        assertTrue(body.getMessage().contains("param"));
        assertEquals("/api/test", body.getPath());
    }

//    @Test
//     void handleMissingParam_returns400_withMockedException() {
//         when(request.getRequestURI()).thenReturn("/api/test");
//         MissingServletRequestParameterException ex = mock(MissingServletRequestParameterException.class);
//         when(ex.getParameterName()).thenReturn("param");
//         ResponseEntity<ErrorResponse> response = handler.handleMissingParam(ex, request);
//         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//         assertNotNull(response.getBody());
//         ErrorResponse body = response.getBody();
//         assertEquals("Bad Request", body.getError());
//         assertEquals("Missing parameter: param", body.getMessage());
//         assertEquals("/api/test", body.getPath());
//     }

    @Test
    void handleMissingParam_returns400_withRealException() {
        when(request.getRequestURI()).thenReturn("/api/test");
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "String");
        ResponseEntity<ErrorResponse> response = handler.handleMissingParam(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertEquals("Bad Request", body.getError());
        assertTrue(body.getMessage().contains("param"));
        assertEquals("/api/test", body.getPath());
    }

    @Test
    void handleTypeMismatch_returns400_fullMessageCheck() {
        when(request.getRequestURI()).thenReturn("/api/test");
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("param");
        when(ex.getValue()).thenReturn("badValue");
        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertEquals("Bad Request", body.getError());
        assertEquals("Invalid parameter: param", body.getMessage());
        assertEquals("/api/test", body.getPath());
    }

    @Test
    void handleGeneric_returns500_fullMessageCheck() {
        when(request.getRequestURI()).thenReturn("/api/test");
        Exception ex = new Exception("Internal error");
        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex, request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertEquals("Internal Server Error", body.getError());
        assertEquals("Internal error", body.getMessage());
        assertEquals("/api/test", body.getPath());
    }

    @Test
    void handleGeneric_returns500_nullMessage() {
        when(request.getRequestURI()).thenReturn("/api/test");
        Exception ex = new Exception();
        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex, request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertEquals("Internal Server Error", body.getError());
        assertNull(body.getMessage());
        assertEquals("/api/test", body.getPath());
    }
}
