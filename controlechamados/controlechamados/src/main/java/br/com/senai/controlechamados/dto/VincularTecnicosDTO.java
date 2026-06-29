package br.com.senai.controlechamados.dto;

import java.util.List;

public class VincularTecnicosDTO {
    private List<Long> tecnicosIds;

    public VincularTecnicosDTO(){}

    public List<Long> getTecnicosIds() {
        return tecnicosIds;
    }

    public void setTecnicosIds(List<Long> tenicosIds) {
        this.tecnicosIds = tenicosIds;
    }
}
