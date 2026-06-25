package br.com.senai.controlechamados.service;

import br.com.senai.controlechamados.dto.TecnicoRequestDTO;
import br.com.senai.controlechamados.dto.TecnicoResponseDTO;
import br.com.senai.controlechamados.entity.Tecnico;
import br.com.senai.controlechamados.enums.Ativo;
import br.com.senai.controlechamados.repository.CategoriaRepository;
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

    public TecnicoResponseDTO cadastrar(Long id, TecnicoRequestDTO dto) {
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

    public void deletar(Long id) {
        Tecnico tecnico = this.buscarTecnicoPorId(id);
        if (chamadoRepository.existsByTecnicosId(id)) {
            System.out.println("Não é possível excluir um técnico vinculado a chamados."); //Todo: Mudar
        }

        tecnicoRepository.delete(tecnico);
    }

    private void validarDados(TecnicoRequestDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            System.out.println("O nome do técnico é obrigatório.");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            System.out.println("O e-mail do técnico é obrigatório.");
        }
    }

    private Tecnico buscarTecnicoPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElse(null); //Todo: Mudar
    }

    private TecnicoResponseDTO converterParaResponse(Tecnico tecnico) {
        return new TecnicoResponseDTO(
                tecnico.getId(),
                tecnico.getNome(),
                tecnico.getEmail(),
                tecnico.getEspecialidade(),
                tecnico.getAtivo()
        );
    }
}
