package com.thiagobernardo.avaliacao.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Cliente implements Serializable {

	private static final long serialVersionUID = 4099237830842609237L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Size(min = 3, max = 100)
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@NotEmpty
	@Column(name = "cpf", nullable = false)
	@CPF(message = "Insira um CPF valido")
	private String cpf;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Telefone> telefones = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "EMAIL")
	private Set<String> emails = new HashSet<>();
	
	@Transient
	private Integer usuarioId;
	
	public Cliente(Integer id, String nome, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
}
