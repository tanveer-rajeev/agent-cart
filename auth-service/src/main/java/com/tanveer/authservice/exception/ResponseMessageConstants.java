package com.tanveer.authservice.exception;

public class ResponseMessageConstants {
    public static final String UNAUTHORIZED = "Access Denied: Not Authorized";
    public static final String UNAUTHENTICATED = "Access Denied: Authentication failure. Email or password not match";
    public static final String INVALID_JWT_SIGNATURE = "Access Denied: JWT Signature not valid";
    public static final String EXPIRED_JWT_SIGNATURE = "Access Denied: JWT Signature expired";
}
