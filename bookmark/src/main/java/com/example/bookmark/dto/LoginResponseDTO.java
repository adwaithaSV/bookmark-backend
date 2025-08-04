package com.example.bookmark.dto;

public class LoginResponseDTO {

    private String username;
    private String message;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
               "username='" + username + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
