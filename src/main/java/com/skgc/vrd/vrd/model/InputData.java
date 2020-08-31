package com.skgc.vrd.vrd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("datadesign")
@Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class InputData {
	@Id
    private String _id;

    private String name;
    
    private String data;
}
