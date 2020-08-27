package com.skgc.vrd.account.repository;

 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skgc.vrd.account.model.ERole;
import com.skgc.vrd.account.model.UserRoleEntity;

 

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
	Optional<UserRoleEntity> findByName(ERole name);
}