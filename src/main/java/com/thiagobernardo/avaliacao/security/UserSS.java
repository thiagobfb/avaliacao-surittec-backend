package com.thiagobernardo.avaliacao.security;

import com.thiagobernardo.avaliacao.domain.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = -9159887693013225654L;
	
	private Integer id;
	private String login;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS() {	
	}
	
	public UserSS(Integer id, String login, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.authorities = perfis.stream().map(p -> new SimpleGrantedAuthority(p.getDescricao())).collect(Collectors.toList());
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
