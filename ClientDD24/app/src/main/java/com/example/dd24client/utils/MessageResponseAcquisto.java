package com.example.dd24client.utils;

import com.example.dd24client.model.acquistaastaribassoModel;

import java.util.List;

public class messageResponseAcquisto {

    private List<acquistaastaribassoModel> aste;

    public messageResponseAcquisto(List<acquistaastaribassoModel> aste) {
        this.aste = aste;
    }

    public List<acquistaastaribassoModel> getAste() {
        return aste;
    }

    public void setAste(List<acquistaastaribassoModel> aste) {
        this.aste = aste;
    }
}

