package br.com.senai.controlechamados.enums;

public enum Prioridade {
    BAIXA("Baixa"),
    MEDIA("Media"),
    ALTA("Alta");

    private String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
