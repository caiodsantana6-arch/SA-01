package br.com.senai.controlechamados.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private List<Chamado> chamados;

    public Categoria() {
    }

    public Categoria(Long id, String nome, String descricao, List<Chamado> chamados) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.chamados = chamados;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Chamado> getChamado() {
        return chamados;
    }

    public void setChamado(List<Chamado> chamado) {
        this.chamados = chamado;
    }
}
