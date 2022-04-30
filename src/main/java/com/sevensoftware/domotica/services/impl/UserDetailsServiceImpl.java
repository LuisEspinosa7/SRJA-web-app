/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

/**
 * @author LUIS
 *
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevensoftware.domotica.dao.PerfilDao;
import com.sevensoftware.domotica.dao.UsuarioDao;
import com.sevensoftware.domotica.dao.impl.UsuarioDaoImpl;
import com.sevensoftware.domotica.entities.CustomUserDetail;
import com.sevensoftware.domotica.entities.Perfil;
import com.sevensoftware.domotica.entities.Usuario;

/**
 * @author LUIS
 *
 */
@Service("userDetail")
public class UserDetailsServiceImpl implements UserDetailsService{
		
	private Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	PerfilDao perfilDao;
	
	
	@Override
	public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("********* Buscando por en loadUserByUsername .... ");
		logger.debug("********* usuario .... ");
		Usuario usuario = usuarioDao.buscarPorNombre(username);
		
		if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
		
		logger.debug("********* PERFILES .... ");
		Set<Perfil> perfiles = perfilDao.buscarPerfilesPorUsername(username);	
		logger.debug("********* configurando perfiles y usuario .... ");
		Set<GrantedAuthority> authorities = buildUserAuthority(perfiles);		
		
		logger.debug("********* Construyendo usuario .... ");
		
		CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUser(usuario);
        customUserDetail.setAuthorities(authorities);
		
        return customUserDetail;		
		//return buildUserForAuthentication(usuario, authorities);
	}
	
		
	private Set<GrantedAuthority> buildUserAuthority(Set<Perfil> userPerfiles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (Perfil userPerfil : userPerfiles) {
			setAuths.add(new SimpleGrantedAuthority(userPerfil.getPerfil()));
		}				
		return setAuths;
	}
	
}