package com.thiagobernardo.avaliacao.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiagobernardo.avaliacao.domain.Auditoria;
import com.thiagobernardo.avaliacao.domain.Cliente;
import com.thiagobernardo.avaliacao.domain.Endereco;
import com.thiagobernardo.avaliacao.domain.Telefone;
import com.thiagobernardo.avaliacao.domain.Usuario;
import com.thiagobernardo.avaliacao.domain.enums.Operacao;
import com.thiagobernardo.avaliacao.repositories.AuditoriaRepository;
import com.thiagobernardo.avaliacao.repositories.ClienteRepository;
import com.thiagobernardo.avaliacao.repositories.EnderecoRepository;
import com.thiagobernardo.avaliacao.repositories.TelefoneRepository;
import com.thiagobernardo.avaliacao.repositories.UsuarioRepository;
import com.thiagobernardo.avaliacao.security.UserSS;
import com.thiagobernardo.avaliacao.services.exceptions.AuthorizationException;
import com.thiagobernardo.avaliacao.services.exceptions.DataIntegrityException;
import com.thiagobernardo.avaliacao.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuditoriaRepository auditoriaRepository;

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		
		if (user == null || !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repository.findById(id);
		obj.get().setUsuarioId(user.getId());
		insertAuditoria(user.getId(), id, Operacao.RETRIEVE);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato ! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		Cliente newObj = repository.save(obj);
		obj.getEnderecos().forEach(e -> {e.setCliente(newObj);});
		enderecoRepository.saveAll(obj.getEnderecos());
		telefoneRepository.saveAll(obj.getTelefones());
		UserSS user = UserService.authenticated();
		insertAuditoria(user.getId(), newObj.getId(), Operacao.CREATE);
		return newObj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		newObj.setNome(obj.getNome());
		newObj.setCpf(obj.getCpf());
		
		for (Endereco e : obj.getEnderecos()) {
			e.setCliente(newObj);
			newObj.getEnderecos().add(e);
		}
		
		for (Telefone t : obj.getTelefones()) {
			t.setCliente(newObj);
			newObj.getTelefones().add(t);
		}
		newObj.setTelefones(obj.getTelefones());
		newObj = repository.save(newObj);
		
		enderecoRepository.saveAll(newObj.getEnderecos());
		telefoneRepository.saveAll(newObj.getTelefones());
		UserSS user = UserService.authenticated(); 
		insertAuditoria(user.getId(), newObj.getId(), Operacao.UPDATE);
		return newObj;
	}

	public void delete(Integer id) {
		Cliente newObj = find(id);
		insertAuditoria(newObj.getId(), newObj.getUsuarioId(), Operacao.UPDATE);
		try {
			repository.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, 
				Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public void insertAuditoria(Integer usuarioId, Integer clienteId, Operacao operacao) {
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		Usuario retorno = usuario.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado " + Cliente.class.getName()));
		Auditoria auditoria = new Auditoria(null, LocalDate.now(), clienteId, operacao, retorno);
		auditoriaRepository.save(auditoria);
	}
}
