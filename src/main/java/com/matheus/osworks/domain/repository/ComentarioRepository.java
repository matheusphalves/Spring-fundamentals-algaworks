package com.matheus.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheus.osworks.domain.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
