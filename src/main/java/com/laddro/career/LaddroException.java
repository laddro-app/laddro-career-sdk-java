package com.laddro.career;

public class LaddroException extends Exception {

    private final int status;
    private final String code;

    public LaddroException(String message, int status, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public boolean isAuthError() {
        return status == 401;
    }

    public boolean isUsageLimitError() {
        return status == 402;
    }

    public boolean isNotFound() {
        return status == 404;
    }
}
