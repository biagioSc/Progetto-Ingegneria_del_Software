package com.example.dd24client.utils;
public class MessageResponse {
    private String message;

    public MessageResponse(String message){
        setMessage(message);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

