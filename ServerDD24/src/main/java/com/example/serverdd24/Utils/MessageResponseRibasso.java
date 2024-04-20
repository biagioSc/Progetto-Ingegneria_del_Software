package com.example.serverdd24.Utils;


import com.example.serverdd24.Model.AstaribassoModel;

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
