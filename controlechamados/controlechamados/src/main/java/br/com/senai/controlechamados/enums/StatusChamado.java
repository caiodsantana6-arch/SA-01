package br.com.senai.controlechamados.enums;

public enum StatusChamado {
    ABERTO("Aberto"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private String descricao;

    StatusChamado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
