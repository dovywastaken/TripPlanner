package com.spring.repository.post;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.domain.Tour;

public class TourMapper implements RowMapper<Tour>
{
	public Tour mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Tour tour = new Tour();
		tour.setP_unique(rs.getInt(1));
		tour.setId(rs.getInt(2));
		tour.setContentid(rs.getString(3));
		tour.setContenttypeid(rs.getString(4));
		tour.setTitle(rs.getString(5));
		tour.setFirstimage(rs.getString(6));
		tour.setAddr1(rs.getString(7));
		tour.setCat2(rs.getString(8));
		tour.setCat3(rs.getString(9));
		tour.setMapx(rs.getLong(10));
		tour.setMapy(rs.getLong(11));
		tour.setCreated_at(rs.getTimestamp(12));

		return tour;
	}
}
