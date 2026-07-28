package com.nectivo.service;

import com.nectivo.dto.AuthResponse;
import com.nectivo.dto.LoginRequest;
import com.nectivo.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
