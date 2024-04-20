package com.example.serverdd24.Utils;

import com.example.serverdd24.Model.AstasilenziosaModel;

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