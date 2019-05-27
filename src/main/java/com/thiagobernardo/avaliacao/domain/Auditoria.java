package com.thiagobernardo.avaliacao.domain;

import com.thiagobernardo.avaliacao.domain.enums.Operacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "auditoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataRegistro;
    private Integer clienteId;
    private Operacao operacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
