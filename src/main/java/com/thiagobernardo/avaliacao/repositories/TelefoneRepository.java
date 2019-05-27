package com.thiagobernardo.avaliacao.repositories;

import com.thiagobernardo.avaliacao.domain.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {

}

