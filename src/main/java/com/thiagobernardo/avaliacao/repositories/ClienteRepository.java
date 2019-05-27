package com.thiagobernardo.avaliacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobernardo.avaliacao.domain.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
