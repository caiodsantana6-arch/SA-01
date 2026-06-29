package br.com.senai.controlechamados.service;

import br.com.senai.controlechamados.dto.TecnicoRequestDTO;
import br.com.senai.controlechamados.dto.TecnicoResponseDTO;
import br.com.senai.controlechamados.entity.Tecnico;
import br.com.senai.controlechamados.enums.Ativo;
import br.com.senai.controlechamados.exception.RecursoNaoEncontradoException;
import br.com.senai.controlechamados.exception.RegraNegocioException;
import br.com.senai.controlechamados.repository.ChamadoRepository;
import br.com.senai.controlechamados.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;
    private final ChamadoRepository chamadoRepository;

    public TecnicoService(ChamadoRepository chamadoRepository, TecnicoRepository tecnicoRepository) {
        this.chamadoRepository = chamadoRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    public List<TecnicoResponseDTO> listar() {
        return tecnicoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public TecnicoResponseDTO buscarPorId(Long id) {
        Tecnico tecnico = buscarTecnicoPorId(id);
        return converterParaResponse(tecnico);
    }

    public TecnicoResponseDTO cadastrar( TecnicoRequestDTO dto) {
        validarDados(dto);
        Tecnico tecnico = new Tecnico();
        tecnico.setNome(dto.getNome());
        tecnico.setEmail(dto.getEmail());
        tecnico.setEspecialidade(dto.getEspecialidade());
        tecnico.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : Ativo.ATIVO);
        Tecnico tecnicoSalvo = tecnicoRepository.save(tecnico);

        return converterParaResponse(tecnicoSalvo);
    }

    public TecnicoResponseDTO atualizar(Long id, TecnicoRequestDTO dto) {
        validarDados(dto);
        Tecnico tecnico = buscarTecnicoPorId(id);
        tecnico.setNome(dto.getNome());
        tecnico.setEmail(dto.getEmail());
        tecnico.setEspecialidade(dto.getEspecialidade());
        if (dto.getAtivo() != null) {
            tecnico.setAtivo(dto.getAtivo());
        }
        Tecnico tecAtt = tecnicoRepository.save(tecnico);

        return converterParaResponse(tecAtt);
    }

    public void excluir(Long id) {
        Tecnico tecnico = this.buscarTecnicoPorId(id);
        if (chamadoRepository.existsByTecnicosId(id)) {
            throw new RegraNegocioException("Não é possível excluir um técnico vinculado a chamados.");
        }

        tecnicoRepository.delete(tecnico);
    }

    private void validarDados(TecnicoRequestDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("O nome do técnico é obrigatório.");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("O e-mail do técnico é obrigatório.");
        }
    }

    public Tecnico buscarTecnicoPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Técnico não encontrado com o ID: " + id));
    }

    public TecnicoResponseDTO converterParaResponse(Tecnico tecnico) {
        return new TecnicoResponseDTO(
                tecnico.getId(),
                tecnico.getNome(),
                tecnico.getEmail(),
                tecnico.getEspecialidade(),
                tecnico.getAtivo()
        );
    }
}
