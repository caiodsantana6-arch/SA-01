package br.com.senai.controlechamados.dto;

import java.util.List;

public class VincularTecnicosDTO {
    private List<Long> tecnicosIds;

    public VincularTecnicosDTO(){}

    public List<Long> getTenicosIds() {
        return tecnicosIds;
    }

    public void setTenicosIds(List<Long> tenicosIds) {
        this.tecnicosIds = tenicosIds;
    }
}
