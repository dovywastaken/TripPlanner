package com.spring.service.post;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Tour;
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
	@Override
	public Map<String, Object> getMyboard(String member, int page) {
		
		return boardRepository.getMyboard(member,page);
	}

	@Override
	public Map<String, Object> mysearchPosts(String id,String keyword, int page) {
		return boardRepository.mysearchPosts(id,keyword, page);
	}

	@Override
	public List<Tour> hotSpots(int limit, int offset) {
		System.out.println("서비스에서 limit,offset값을 들고 갑니다" + limit + offset);
		return boardRepository.hotSpots(limit, offset);
	}
}
