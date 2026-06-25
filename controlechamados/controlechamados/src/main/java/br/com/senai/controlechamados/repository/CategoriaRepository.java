package br.com.senai.controlechamados.repository;

import br.com.senai.controlechamados.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
