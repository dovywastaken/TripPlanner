package com.spring.repository.map;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class mapRepositoryImpl implements mapRepository{
	
	@Autowired
	JdbcTemplate template;
	
	
	}


