package com.skgc.vrd.vrd.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository;
import com.skgc.vrd.vrd.model.InputData;

 

@Repository
@Qualifier("dataDesignRepository")
public interface DataDesignRepository extends MongoRepository<InputData, String> {
 
//	Flux<InputData> findByData(String data);
}
