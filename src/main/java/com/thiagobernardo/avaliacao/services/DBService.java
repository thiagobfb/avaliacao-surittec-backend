package com.thiagobernardo.avaliacao.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiagobernardo.avaliacao.domain.Cliente;
import com.thiagobernardo.avaliacao.domain.Endereco;
import com.thiagobernardo.avaliacao.domain.Telefone;
import com.thiagobernardo.avaliacao.domain.Usuario;
import com.thiagobernardo.avaliacao.domain.enums.Perfil;
import com.thiagobernardo.avaliacao.domain.enums.TipoTelefone;
import com.thiagobernardo.avaliacao.repositories.ClienteRepository;
import com.thiagobernardo.avaliacao.repositories.EnderecoRepository;
import com.thiagobernardo.avaliacao.repositories.TelefoneRepository;
import com.thiagobernardo.avaliacao.repositories.UsuarioRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	
	public void instantiateTestDatabase() throws ParseException {

		Usuario u1 = new Usuario(null, "admin", encoder.encode("123456"));
		u1.addPerfil(Perfil.ADMIN);

		Usuario u2 = new Usuario(null, "comum", encoder.encode("123456"));
		u2.addPerfil(Perfil.COMUM);

		usuarioRepository.saveAll(Arrays.asList(u1, u2));


		Cliente cli1 = new Cliente(null, "Thiago Bernardo", "10118163779");
		cli1.getEmails().addAll(Arrays.asList("madguitar777@gmail.com", "thiagobf.barbosa@gmail.com"));

		Cliente cli2 = new Cliente(null, "Ana Costa", "42977727604");
		cli2.getEmails().addAll(Arrays.asList("anacosta@email.com"));

		Telefone t1 = new Telefone(null, 6135488076L, TipoTelefone.RESIDENCIAL, cli1);
		Telefone t2 = new Telefone(null, 61982010760L, TipoTelefone.CELULAR, cli1);
		Telefone t3 = new Telefone(null, 2127832730L, TipoTelefone.RESIDENCIAL, cli2);
		Telefone t4 = new Telefone(null, 21999334578L, TipoTelefone.CELULAR, cli2);
		Telefone t5 = new Telefone(null, 2125487412L, TipoTelefone.COMERCIAL, cli2);

		cli1.getTelefones().addAll(Arrays.asList(t1, t2));
		cli2.getTelefones().addAll(Arrays.asList(t3, t4, t5));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, "Belo Horizonte", "MG");
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, "São Paulo", "SP");
		Endereco e3 = new Endereco(null, "Avenida Floriano", "2106", null, "Centro", "38172010", cli2, "Curitiba", "PR");
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		telefoneRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
		
	}
}
