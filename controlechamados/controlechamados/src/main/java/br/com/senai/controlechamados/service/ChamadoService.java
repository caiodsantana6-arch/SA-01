package br.com.senai.controlechamados.service;

import br.com.senai.controlechamados.dto.*;
import br.com.senai.controlechamados.entity.Categoria;
import br.com.senai.controlechamados.entity.Chamado;
import br.com.senai.controlechamados.entity.Tecnico;
import br.com.senai.controlechamados.enums.Ativo;
import br.com.senai.controlechamados.enums.StatusChamado;
import br.com.senai.controlechamados.exception.RecursoNaoEncontradoException;
import br.com.senai.controlechamados.exception.RegraNegocioException;
import br.com.senai.controlechamados.repository.ChamadoRepository;
import br.com.senai.controlechamados.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final CategoriaService categoriaService;
    private final TecnicoService tecnicoService;
    private final TecnicoRepository tecnicoRepository;

    public ChamadoService(CategoriaService categoriaService, ChamadoRepository chamadoRepository, TecnicoService tecnicoService, TecnicoRepository tecnicoRepository) {
        this.categoriaService = categoriaService;
        this.chamadoRepository = chamadoRepository;
        this.tecnicoService = tecnicoService;
        this.tecnicoRepository = tecnicoRepository;
    }

    public List<ChamadoResponseDTO> listar() {
        return chamadoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public ChamadoResponseDTO buscarPorId(Long id) {
        Chamado chamado = buscarChamadoPorId(id);
        return converterParaResponse(chamado);
    }

    public ChamadoResponseDTO cadastrar(ChamadoRequestDTO dto) {
        validarDadosChamado(dto);

        Categoria categoria = categoriaService.buscarCategoriaPorId(dto.getCategoriaId());
        List<Tecnico> tecnicos = buscarTecnicos(dto.getTecnicosIds());
        validarTecnicosAtivos(tecnicos);

        Chamado chamado = new Chamado();
        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        chamado.setSolicitante(dto.getSolicitante());
        chamado.setLocal(dto.getLocal());
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setCategoria(categoria);
        chamado.setTecnicos(tecnicos);
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setDataAbertura(LocalDate.now());

        return converterParaResponse(chamadoRepository.save(chamado));

    }

    public ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO dto) {
        validarDadosChamado(dto);
        Chamado chamado = buscarChamadoPorId(id);

        if (chamado.getStatus() == StatusChamado.FINALIZADO) {
            throw new RegraNegocioException("Não é possível alterar um chamado finalizado.");
        }

        Categoria categoria = categoriaService.buscarCategoriaPorId(dto.getCategoriaId());
        List<Tecnico> tecnicos = buscarTecnicos(dto.getTecnicosIds());
        validarTecnicosAtivos(tecnicos);

        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        chamado.setSolicitante(dto.getSolicitante());
        chamado.setLocal(dto.getLocal());
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setCategoria(categoria);
        chamado.setTecnicos(tecnicos);
        return converterParaResponse(chamadoRepository.save(chamado));
    }

    public ChamadoResponseDTO atualizarStatus(Long id, AtualizarStatusDTO dto) {
        Chamado chamado = buscarChamadoPorId(id);

        if (chamado.getStatus() == StatusChamado.FINALIZADO && dto.getStatus() == StatusChamado.ABERTO) {
            throw new RegraNegocioException("Um chamado finalizado não poderá voltar para aberto.");
        }
        if (dto.getStatus() == StatusChamado.EM_ANDAMENTO && chamado.getTecnicos() == null ||
        chamado.getTecnicos().isEmpty()) {
            throw new RegraNegocioException("Um chamado só poderá ser alterado para EM_ANDAMENTO se possuir pelo menos um técnico vinculado.");
        }

        chamado.setStatus(dto.getStatus());
        if (dto.getStatus() == StatusChamado.FINALIZADO) {
            chamado.setDataFinalizacao(LocalDate.now());
        } else {
            chamado.setDataFinalizacao(null);
        }

        return converterParaResponse(chamadoRepository.save(chamado));
    }

    public ChamadoResponseDTO vincularTecnico(Long id, VincularTecnicosDTO dto) {
        Chamado chamado = buscarChamadoPorId(id);

        if (chamado.getStatus() == StatusChamado.FINALIZADO) {
            throw new RegraNegocioException("Um chamado FINALIZADO não poderá receber novos técnicos.");
        }
        List<Tecnico> tecnicos = buscarTecnicos(dto.getTenicosIds());
        validarTecnicosAtivos(tecnicos);

        chamado.setTecnicos(tecnicos);
        return converterParaResponse(chamadoRepository.save(chamado));
    }

    public void excluir(Long id) {
        Chamado chamado = buscarChamadoPorId(id);
        if (chamado.getStatus() == StatusChamado.FINALIZADO) {
            throw new RegraNegocioException("Um chamado finalizado não poderá ser excluído.");
        }

        chamadoRepository.delete(chamado);
    }

    private void validarDadosChamado(ChamadoRequestDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RegraNegocioException("O título do chamado é obrigatório.");
        }
        if (dto.getCategoriaId() == null) {
            throw new RegraNegocioException("A categoria do chamado é obrigatória.");
        }
        if (dto.getPrioridade() == null) {
            throw new RegraNegocioException("A prioridade do chamado é obrigatória.");
        }
    }

    private List<Tecnico> buscarTecnicos(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return List.of();
        List<Tecnico> tecnicos = tecnicoRepository.findAllById(ids);
        if (tecnicos.size() != ids.size()) {
            throw new RegraNegocioException("Um ou mais técnicos informados não foram encontrados.");
        }
        return tecnicos;
    }

    private void validarTecnicosAtivos(List<Tecnico> tecnicos) {
        for (Tecnico t : tecnicos) {
            if (t.getAtivo() == Ativo.ATIVO) {
                throw new RegraNegocioException("O técnico " + t.getNome() + " está inativo e não pode ser vinculado.");
            }
        }
    }

    private Chamado buscarChamadoPorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado não encontrado com o ID: " + id));
    }

    public ChamadoResponseDTO converterParaResponse(Chamado chamado) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.converterParaResponse(chamado.getCategoria());
        List<TecnicoResponseDTO> tecnicosDTO = chamado.getTecnicos()
                .stream()
                .map(tecnico -> new TecnicoResponseDTO(
                        tecnico.getId(),
                        tecnico.getNome(),
                        tecnico.getEmail(),
                        tecnico.getEspecialidade(),
                        tecnico.getAtivo()
                ))
                .collect(Collectors.toList());

        return new ChamadoResponseDTO(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getSolicitante(),
                chamado.getLocal(),
                chamado.getPrioridade(),
                chamado.getStatus(),
                chamado.getDataAbertura(),
                chamado.getDataFinalizacao(),
                categoriaResponseDTO,
                tecnicosDTO
        );
    }
}
