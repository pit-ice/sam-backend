package com.skgc.vrd.account.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import com.skgc.vrd.account.model.UserEntity;

@Mapper
public interface UserMapper {
	
	Optional<UserEntity> findByUsername(String userName);
	
	@Cacheable("users")
	List<UserEntity> findByAll();

}
