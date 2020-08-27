package com.skgc.vrd.account.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.skgc.vrd.account.model.UserEntity;

@Mapper
public interface UserMapper {
	
	Optional<UserEntity> findByUsername(String userName);
	
	List<UserEntity> findByAll();

}
