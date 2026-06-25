package br.com.senai.controlechamados.repository;

import br.com.senai.controlechamados.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    boolean existsByCategoriaId(Long categoriaId);
    boolean existsByTecnicosId(Long tecnicoId);
}
