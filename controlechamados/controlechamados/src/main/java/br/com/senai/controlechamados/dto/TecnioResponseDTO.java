package br.com.senai.controlechamados.dto;

import br.com.senai.controlechamados.enums.Ativo;

public class TecnioResponseDTO {
    private Long id;
    private String email;
    private String especialidade;
    private Ativo ativo;

    public TecnioResponseDTO() {
    }

    public TecnioResponseDTO(Long id, String email, String especialidade, Ativo ativo) {
        this.id = id;
        this.email = email;
        this.especialidade = especialidade;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }
}
