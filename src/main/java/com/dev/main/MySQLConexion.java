package com.dev.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConexion {
	public static Connection getConexion(){
		
		Connection con = null;
		
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://crowdbikingdb.cupuv5hpldgr.us-east-1.rds.amazonaws.com";
			String usr = "root";
			String psw = "12341234";
			/*String url = "jdbc:mysql://192.168.3.55/itmscomp_digitalizacion";
			String usr = "root";
			String psw = "itms.1110";*/
			con = DriverManager.getConnection(url,usr,psw);
		} catch (ClassNotFoundException ex) {
			System.out.println("Error >> Driver no Instalado!!");
		} catch (SQLException ex) {
			System.out.println("Error >> de conexi�n con la BD");
		}
		
		return con;
	}
}
