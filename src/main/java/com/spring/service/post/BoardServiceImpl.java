package com.spring.service.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.repository.post.BoardRepository;

import com.spring.domain.Tour;
import java.util.List;


@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardRepository boardRepository;

	@Override
	public Map<String, Object> allBoard(int page) {
		return boardRepository.allBoard(page);
	}

	@Override
    public Map<String, Object> allBoardSearch(String type, String keyword, int page) {
        return boardRepository.allBoardSearch(type, keyword, page);
    }
	@Override
	public Map<String, Object> myBoard(String member, int page) {
		
		return boardRepository.myBoard(member,page);
	}

	@Override
	public Map<String, Object> myBoardSearch(String id,String keyword, int page) {
		return boardRepository.myBoardSearch(id,keyword, page);
	}

	@Override
	public Map<String, Object> hotBoard(int size, int page) {
		return boardRepository.hotBoard(size , page);
	}
	@Override
	public List<Tour> hotSpots(String type, int limit, int offset) {
		return boardRepository.hotSpots(type, limit, offset);
	}

	@Override
	public List<Map<String, Object>> getTourInfoByPostId(int p_unique) {
		return boardRepository.getTourInfoByPostId(p_unique);
	}
}
