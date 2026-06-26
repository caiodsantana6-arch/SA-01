package br.com.senai.controlechamados.dto;

import br.com.senai.controlechamados.enums.StatusChamado;

public class AtualizarStatusDTO {
    private StatusChamado status;

    public AtualizarStatusDTO() {}

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }
}
