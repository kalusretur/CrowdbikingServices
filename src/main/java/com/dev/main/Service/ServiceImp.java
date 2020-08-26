package com.dev.main.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dev.main.MySQLConexion;
import com.dev.main.Bean.Service;
import com.dev.main.Interface.Bike_Service;

@org.springframework.stereotype.Service
public class ServiceImp implements Bike_Service {

	@Override
	public int createBike_Service(Service service) {
		// TODO Auto-generated method stub
		int rs = 0;
		try(Connection con = MySQLConexion.getConexion()){
			String sql = "insert into crowdbiking.Bike_service (cel_phone,fec_solicitud,id_season_ini,estado) values (?,?,?,?);";
			try(PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, service.getCel_phone());
				pst.setString(2, service.getFec_solicitud());
				pst.setString(3, service.getId_season_ini());
				pst.setInt(4, service.getEstado_val());
				rs = pst.executeUpdate();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}

	@Override
	public Service getBike_Service(String cel_phone) {
		// TODO Auto-generated method stub
		Service service = new Service();
		try(Connection con = MySQLConexion.getConexion()){
			String sql = "select id_service,cel_phone,fec_solicitud,ini_service,fin_service,time_service,id_season_ini,id_season_fin,costo_service,estado, case estado when 0 then 'Pendiente' when 1 then 'Activo' when 2 then 'Concluido' when 5 then 'Cancelado' end as estadoString from crowdbiking.Bike_service where cel_phone=?  and estado in (0,1,2,3)";
			try(PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, cel_phone);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						service.setId_service(rs.getString(1));
						service.setCel_phone(rs.getString(2));
						service.setFec_solicitud(rs.getString(3));
						service.setIni_service(rs.getString(4));
						service.setFin_service(rs.getString(5));
						service.setTime_service(rs.getString(6));
						service.setId_season_ini(rs.getString(7));
						service.setId_season_fin(rs.getString(8));
						service.setCosto_service(rs.getDouble(9));
						service.setEstado_val(rs.getInt(10));
						service.setEstado_string(rs.getString(11));
						
					}
					else {
						service = new Service();
					}
									
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return service;
	}

	@Override
	public int activate_Service(Service service) {
		// TODO Auto-generated method stub
		int rs = 0;
		try(Connection con = MySQLConexion.getConexion()){
			String sql= "update crowdbiking.Bike_service set ini_service=?,ini_user_service=?, estado=? where id_service=?;";
			try(PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, service.getIni_service());
				pst.setString(2, service.getIni_user_service());
				pst.setInt(3, service.getEstado_val());
				pst.setString(4, service.getId_service());
				rs = pst.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public int finalizar_Service(Service service) {
		// TODO Auto-generated method stub
		int rs = 0;
		try(Connection con = MySQLConexion.getConexion()){
			//String sql= "update crowdbiking.Bike_service set fin_service=?,fin_user_service=?, estado=? where id_service=?;";
			String sql = "update crowdbiking.Bike_service set fin_service=?,fin_user_service=?, estado=?, time_service=(SELECT TIMEDIFF(?, ini_service)) where id_service=?;";
			try(PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, service.getFin_service());
				pst.setString(2, service.getFin_user_service());
				pst.setInt(3, service.getEstado_val());
				pst.setString(4, service.getFin_service());
				pst.setString(5, service.getId_service());
				rs = pst.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
