package com.nectivo.service;
import com.nectivo.dto.AuthResponse;
import com.nectivo.dto.LoginRequest;
import com.nectivo.dto.RegisterRequest;
import com.nectivo.dto.ForgotPasswordRequest;
import com.nectivo.dto.VerifyOtpRequest;
import com.nectivo.dto.ResetPasswordRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

    void forgotPassword(ForgotPasswordRequest request);
    void verifyOtp(VerifyOtpRequest request);
    void resetPassword(ResetPasswordRequest request);
}