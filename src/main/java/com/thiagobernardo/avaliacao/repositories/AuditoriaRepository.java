package com.thiagobernardo.avaliacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagobernardo.avaliacao.domain.Auditoria;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {

}
