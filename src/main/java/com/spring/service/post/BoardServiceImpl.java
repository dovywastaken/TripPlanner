package com.spring.service.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.repository.post.BoardRepository;


@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardRepository boardRepository;

	@Override
	public Map<String, Object> AllboardRead(int page) {
		Map<String, Object> result=boardRepository.AllboardRead(page);
		return result;
	}

	@Override
    public Map<String, Object> searchPosts(String type, String keyword, int page) {
        return boardRepository.searchPosts(type, keyword, page);
    }
	

}
