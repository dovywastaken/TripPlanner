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
		tour.setTel(rs.getString(5));
		tour.setHomepage(rs.getString(6));
		tour.setFirstimage(rs.getString(7));
		tour.setAreacode(rs.getString(8));
		tour.setSigungucode(rs.getString(9));
		tour.setAddr1(rs.getString(10));
		tour.setAddr2(rs.getString(11));
		tour.setZipcode(rs.getString(12));
		tour.setOverview(rs.getString(13) != null ? rs.getString(13) : "");
		tour.setMapx(rs.getLong(14));
		tour.setMapy(rs.getLong(15));
		tour.setCitation_count(rs.getInt(16));

		return tour;
	}
}
