package com.skgc.vrd.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skgc.vrd.account.model.UserEntity;

 
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	
	Optional<UserEntity> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}