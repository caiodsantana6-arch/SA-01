package br.com.senai.controlechamados.dto;

import br.com.senai.controlechamados.entity.Categoria;
import br.com.senai.controlechamados.entity.Tecnico;
import br.com.senai.controlechamados.enums.StatusChamado;

public class ChamadoRequestDTO {
    private String titulo;
    private String descricao;
    private String solicitante;
    private String local;
    private String prioridade;
    private StatusChamado status;
    private String dataAbertura;
    private String dataFinalizacao;
    private Categoria categoria;
    private Tecnico tecnicos;

    public ChamadoRequestDTO() {
    }

    public ChamadoRequestDTO(String titulo, String descricao, String solicitante, String local, String prioridade, StatusChamado status, String dataAbertura, String dataFinalizacao, Categoria categoria, Tecnico tecnicos) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.solicitante = solicitante;
        this.local = local;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFinalizacao = dataFinalizacao;
        this.categoria = categoria;
        this.tecnicos = tecnicos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(String dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tecnico getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(Tecnico tecnicos) {
        this.tecnicos = tecnicos;
    }
}
