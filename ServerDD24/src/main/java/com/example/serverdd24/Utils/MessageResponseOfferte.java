package com.example.serverdd24.Utils;

import com.example.serverdd24.Model.OffertaastasilenziosaModel;

import java.util.List;

public class MessageResponseOfferte {
    private List<OffertaastasilenziosaModel> aste;

    public MessageResponseOfferte(List<OffertaastasilenziosaModel> aste) {
        this.aste = aste;
    }

    public List<OffertaastasilenziosaModel> getAste() {
        return aste;
    }

    public void setAste(List<OffertaastasilenziosaModel> aste) {
        this.aste = aste;
    }
}