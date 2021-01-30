package com.digital.realty.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Value("#{${User.Map}}")
	private Map<String, String> userInfoMap;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (null != userInfoMap.get(username)){
			String encodePassword = bCryptPasswordEncoder.encode(userInfoMap.get(username));
			return new User(username, encodePassword,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}