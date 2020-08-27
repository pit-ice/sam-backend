package com.skgc.vrd.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skgc.vrd.account.mapper.UserMapper;
 
 
import com.skgc.vrd.account.model.UserDetailsImpl;
import com.skgc.vrd.account.model.UserEntity;
import com.skgc.vrd.account.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	UserRepository userRepository;
	
//	public CustomUserDetailsService(UserMapper userMapper) {
//		this.userMapper = userMapper;
//	}
//	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> user = userRepository.findByUsername(username);
//		user.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
//		 
////		return user.map(CustomUserDetails::new).get();
		
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}
	
	public List<UserEntity> findByAll() {
		return userMapper.findByAll();
	}
	


}
