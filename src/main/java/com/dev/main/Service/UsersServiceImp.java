package com.dev.main.Service;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.dev.main.MySQLConexion;
import com.dev.main.Bean.Users;
import com.dev.main.Interface.UsersService;

@Service
public class UsersServiceImp implements UsersService{

	@Override
	public int createUser(Users users) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pst = null;
		int rs = 0;
		
		try {
			con = MySQLConexion.getConexion();
			//String sql = "INSERT INTO PRUEBA VALUE (?)";
			String sql = "insert into crowdbiking.Users values (?,?,?,?,?,?,?,?,?,default);";
			pst = con.prepareStatement(sql);
			pst.setInt(1, users.getCel_phone());
			pst.setString(2, getMD5(users.getPassword()));
			pst.setString(3, users.getName());
			pst.setString(4, users.getLast_name());
			pst.setString(5, users.getMail());
			pst.setString(6, users.getSex());
			pst.setInt(7, users.getStatus());
			pst.setInt(8, users.getRol());
			pst.setString(9, users.getFec_creacion());
			/*java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			System.err.println(date);
			pst.setTimestamp(9, date);
			*/
			
			rs = pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en la sentencia: " + e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if (pst!= null) pst.close();
				if (con!= null) con.close();
			} catch (Exception e) {
				System.out.println("Error al cerrar: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return rs;
	}
	
	
	@Override
	public Users getUser(String cel_phone, String password) {
		try(Connection con = MySQLConexion.getConexion()) {
			String sql = "select * from crowdbiking.Users where cel_phone=? and password =?";
			try(PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, cel_phone);
				pst.setString(2, getMD5(password));
				try(ResultSet rs = pst.executeQuery()) {					
					if(rs.next()) {
						Users usr = new Users();
						usr.setCel_phone(rs.getInt(1));
						usr.setPassword(rs.getString(2));
						usr.setName(rs.getString(3));
						usr.setLast_name(rs.getString(4));
						usr.setMail(rs.getString(5));
						usr.setSex(rs.getString(6));
						usr.setStatus(rs.getInt(7));
						usr.setRol(rs.getInt(8));
						usr.setFec_creacion(rs.getString(9));
						usr.setFec_activacion(rs.getString(10));
						return usr;
					}					
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public PublishResult sendSMS(String phone_number) {
		// TODO Auto-generated method stub
		BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials("**","**");
		AmazonSNS snsClient = AmazonSNSClient
		                      .builder()
		                      .withRegion(Regions.US_WEST_2)
		                      .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
		                      .build();
		
		String message = "Crowdbiking \nCodigo de validación :\n "+generateRandomSMS()+"";
		String phoneNumber = phone_number;
		Map<String, MessageAttributeValue> smsAttributes = 
		 		new HashMap<String, MessageAttributeValue>();
		//<set SMS attributes>
		return sendSMSMessage((AmazonSNSClient) snsClient, message, phoneNumber, smsAttributes);		
	}

	
	/**
	 * Other Metho
	 */
	
	public String getMD5(String cadena) throws Exception {		 
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(cadena.getBytes());
        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
 
            int u = b[i] & 255;
 
            if (u < 16)
            {
                h.append("0").append(Integer.toHexString(u));
            }
            else
            {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }
	
	
	/**
	 * Method send SMS with AWS
	 */
	public PublishResult sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
	        PublishResult result = snsClient.publish(new PublishRequest()
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	        System.out.println(result); // Prints the message ID.
	        return result;
	}
	
	public String generateRandomSMS() {
		Random rand = new Random();
		String data = String.format("%05d%n", rand.nextInt(100000));
		return data;
	}


	
	
	
	
}
