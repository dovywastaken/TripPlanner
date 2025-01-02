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
		tour.setId(rs.getInt(1));
		tour.setContentid(rs.getString(2));
		tour.setContenttypeid(rs.getString(3));
		tour.setTitle(rs.getString(4));
		tour.setFirstimage(rs.getString(5));
		tour.setAddr1(rs.getString(6));
		tour.setCat2(rs.getString(7));
		tour.setCat3(rs.getString(8));
		tour.setMapx(rs.getLong(9));
		tour.setMapy(rs.getLong(10));
		tour.setCitation_count(rs.getInt(11));

		return tour;
	}
}
