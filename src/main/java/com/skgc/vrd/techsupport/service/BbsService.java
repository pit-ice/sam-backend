package com.skgc.vrd.techsupport.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skgc.vrd.techsupport.mapper.BbsMapper;
import com.skgc.vrd.techsupport.model.BbsVO;

@Service
public class BbsService {

	private BbsMapper bbsMapper;
	
	public BbsService(BbsMapper bbsMapper){
		this.bbsMapper = bbsMapper;
	}
	
	public List<BbsVO> findAllBbs(){
		return bbsMapper.findAllBbs();
	}
}
