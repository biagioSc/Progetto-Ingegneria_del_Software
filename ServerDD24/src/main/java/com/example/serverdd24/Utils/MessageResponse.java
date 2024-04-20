package com.example.serverdd24.Utils;

import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstasilenziosaModel;

import java.util.List;

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