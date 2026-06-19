package br.com.senai.controlechamados.entity;

import br.com.senai.controlechamados.enums.StatusChamado;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String local;
    private String prioridade;
    private StatusChamado status;
    private String dataAbertura;
    private String dataFinalizacao;

    @ManyToOne
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
            name = "chamado_tecnico",
            joinColumns = @JoinColumn(name = "chamado_id"),
            inverseJoinColumns = @JoinColumn(name = "tecnico_id")
    )
    private List<Tecnico> tecnicos;

    public Chamado() {
    }

    public Chamado(Long id, String titulo, String descricao, String local,
                   String prioridade, StatusChamado status, String dataAbertura,
                   String dataFinalizacao, Categoria categoria, List<Tecnico> tecnicos) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.local = local;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFinalizacao = dataFinalizacao;
        this.categoria = categoria;
        this.tecnicos = tecnicos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }
}
