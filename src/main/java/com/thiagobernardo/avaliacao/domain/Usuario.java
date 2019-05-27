package com.thiagobernardo.avaliacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiagobernardo.avaliacao.domain.enums.Perfil;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String senha;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Auditoria> auditorias = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    public Usuario(Integer id, String login, String senha) {
        super();
        this.id = id;
        this.login = login;
        this.senha = senha;
        addPerfil(Perfil.COMUM);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCod());
    }
}
