package com.dev.main.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Service;

import com.dev.main.MySQLConexion;
import com.dev.main.Interface.Logs_smsService;

@Service
public class Logs_smsImp implements Logs_smsService {

	@Override
	public int createLogs_sms(com.dev.main.Bean.Logs_sms log_be) {
		// TODO Auto-generated method stub
		int rs = 0;
		try(Connection con = MySQLConexion.getConexion()){
			String sql = "insert into crowdbiking.Logs_sms(cel_phone,sms_code,sns_messageId,fec_send_sms) values (?,?,?,?);";
			try(PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, log_be.getCel_phone());
				pst.setInt(2, log_be.getSms_code());
				pst.setString(3, log_be.getSns_messageId());
				pst.setString(4, log_be.getFec_send_sms());
				rs = pst.executeUpdate();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
