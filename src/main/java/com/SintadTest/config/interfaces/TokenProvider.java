package com.SintadTest.config.interfaces;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenProvider {
    String getToken(HttpServletRequest request);
}
