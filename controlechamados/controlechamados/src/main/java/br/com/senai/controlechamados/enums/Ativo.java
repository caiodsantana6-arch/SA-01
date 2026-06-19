package br.com.senai.controlechamados.enums;

public enum Ativo {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private String descricao;

    Ativo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
