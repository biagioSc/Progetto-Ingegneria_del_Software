package com.example.dd24client.utils;

import com.example.dd24client.model.AstaribassoModel;

import java.util.List;

public class MessageResponseRibasso {
    private List<AstaribassoModel> aste;

    public MessageResponseRibasso(List<AstaribassoModel> aste) {
        this.aste = aste;
    }

    public List<AstaribassoModel> getAste() {
        return aste;
    }

    public void setAste(List<AstaribassoModel> aste) {
        this.aste = aste;
    }
}