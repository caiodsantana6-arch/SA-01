package br.com.senai.controlechamados.controller;

import br.com.senai.controlechamados.dto.TecnicoRequestDTO;
import br.com.senai.controlechamados.dto.TecnicoResponseDTO;
import br.com.senai.controlechamados.service.TecnicoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TecnicoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TecnicoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TecnicoResponseDTO salvar(@RequestBody TecnicoRequestDTO dto) {
        return service.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public TecnicoResponseDTO atualizar(@PathVariable Long id,
                                        @RequestBody TecnicoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
