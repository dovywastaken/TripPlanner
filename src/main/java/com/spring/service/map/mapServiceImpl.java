package com.spring.service.map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.repository.map.mapRepository;


@Service
public class mapServiceImpl implements mapService {
	@Autowired
	mapRepository mapRepository;
	

}
