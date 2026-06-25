package br.com.senai.controlechamados.repository;

import br.com.senai.controlechamados.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
}
