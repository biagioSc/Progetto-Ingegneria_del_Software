package com.example.dd24client.utils;

import com.example.dd24client.model.AstasilenziosaModel;

import java.util.List;

public class MessageResponseSilenziosa {
    private List<AstasilenziosaModel> aste;

    public MessageResponseSilenziosa(List<AstasilenziosaModel> aste) {
        this.aste = aste;
    }

    public List<AstasilenziosaModel> getAste() {
        return aste;
    }

    public void setAste(List<AstasilenziosaModel> aste) {
        this.aste = aste;
    }
}