package com.skgc.vrd.vrd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.skgc.vrd.vrd.model.InputData;

@Service
public class DataDesignService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public InputData getInputData(String _id) {
		InputData inputData = mongoTemplate.findById(_id, InputData.class);
		return Optional.ofNullable(inputData).orElseThrow(() -> new RuntimeException( "Not found event"));
	}
	
	public List<InputData> getInputDataList(String title) {
		Query query = new Query().addCriteria(Criteria.where("title").is(title));
		return mongoTemplate.find(query, InputData.class);
	}
	
	public InputData insertInputData(InputData body) { 
		return mongoTemplate.insert(body);
	}
}
