package br.com.senai.controlechamados.service;

import br.com.senai.controlechamados.dto.CategoriaRequestDTO;
import br.com.senai.controlechamados.dto.CategoriaResponseDTO;
import br.com.senai.controlechamados.entity.Categoria;
import br.com.senai.controlechamados.repository.CategoriaRepository;
import br.com.senai.controlechamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ChamadoRepository chamadoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ChamadoRepository chamadoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.chamadoRepository = chamadoRepository;
    }

    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO busarPorId(Long id) {
        Categoria categoria = buscarCategoriaPorId(id);
        return converterParaResponse(categoria);
    }

    public CategoriaResponseDTO cadastrar(CategoriaRequestDTO dto) {
        validarDadosCategoria(dto);
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return this.converterParaResponse(categoriaSalva);

    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        validarDadosCategoria(dto);

        Categoria categoria = buscarCategoriaPorId(id);
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        Categoria catAtt = categoriaRepository.save(categoria);

        return this.converterParaResponse(catAtt);
    }

    public void excluir(Long id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (chamadoRepository.existsByCategoriaId(id)) {
            System.out.println("Não é possível excluir uma categoria vinculada a chamados.");             //Todo: Mudar
        }
        categoriaRepository.delete(categoria);
    }

    private Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElse(null);                                                                      //Todo: Mudar
    }

    private void validarDadosCategoria(CategoriaRequestDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            System.out.println("Não encontrada");                                                         //Todo: Mudar
        }
    }

    public CategoriaResponseDTO converterParaResponse(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}
