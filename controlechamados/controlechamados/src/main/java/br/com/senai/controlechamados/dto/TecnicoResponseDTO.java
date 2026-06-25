package br.com.senai.controlechamados.dto;

import br.com.senai.controlechamados.enums.Ativo;

public class TecnicoResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String especialidade;
    private Ativo ativo;

    public TecnicoResponseDTO() {
    }

    public TecnicoResponseDTO(Ativo ativo, String email, String especialidade, Long id, String nome) {
        this.ativo = ativo;
        this.email = email;
        this.especialidade = especialidade;
        this.id = id;
        this.nome = nome;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
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
}
