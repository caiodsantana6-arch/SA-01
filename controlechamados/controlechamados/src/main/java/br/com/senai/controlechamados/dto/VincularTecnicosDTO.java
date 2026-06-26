package br.com.senai.controlechamados.dto;

import java.util.List;

public class VincularTecnicosDTO {
    private List<Long> tenicosIds;

    public VincularTecnicosDTO(){}

    public List<Long> getTenicosIds() {
        return tenicosIds;
    }

    public void setTenicosIds(List<Long> tenicosIds) {
        this.tenicosIds = tenicosIds;
    }
}
