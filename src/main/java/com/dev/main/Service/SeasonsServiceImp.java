package com.dev.main.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.main.MySQLConexion;
import com.dev.main.Bean.Seasons;
import com.dev.main.Interface.SeasonsService;

@Service
public class SeasonsServiceImp implements SeasonsService{
	
	

	@Override
	public List<Seasons> finAllSeasons() {
		List<Seasons> seasons = new ArrayList<Seasons>();
		try(Connection con = MySQLConexion.getConexion()){
			try(Statement st = con.createStatement()){
				String sql = "select * from crowdbiking.Seasons";
				try(ResultSet rs = st.executeQuery(sql)){
					while(rs.next()) {
						Seasons season = new Seasons();
						season.setId(rs.getString(1));
						season.setName(rs.getString(2));
						season.setDireccion(rs.getString(3));
						season.setLatitud(rs.getString(4));
						season.setLongitud(rs.getString(5));
						season.setStock(rs.getInt(6));
						seasons.add(season);
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return seasons;
	}

}
