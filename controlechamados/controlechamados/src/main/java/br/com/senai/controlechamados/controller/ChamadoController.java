package br.com.senai.controlechamados.controller;

import br.com.senai.controlechamados.dto.ChamadoRequestDTO;
import br.com.senai.controlechamados.dto.ChamadoResponseDTO;
import br.com.senai.controlechamados.enums.StatusChamado;
import br.com.senai.controlechamados.service.ChamadoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChamadoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ChamadoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponseDTO salvar(@RequestBody ChamadoRequestDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public ChamadoResponseDTO atualizar(@PathVariable Long id,
                                        @RequestBody ChamadoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @PatchMapping("/{id}/status")
    public ChamadoResponseDTO alterarStatus(@PathVariable Long id,
                                            @RequestParam StatusChamado status) {
        return service.alterarStatus(id, status);
    }

    @PatchMapping("/{id}/tecnico")
    public ChamadoResponseDTO vincularTecnico(@PathVariable Long id,
                                              @RequestParam Long tecnicoId) {
        return service.vincularTecnico(id, tecnicoId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
