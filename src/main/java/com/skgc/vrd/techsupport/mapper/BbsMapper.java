package com.skgc.vrd.techsupport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.skgc.vrd.techsupport.model.BbsVO;

@Mapper
public interface BbsMapper {
	List<BbsVO> findAllBbs();
}
