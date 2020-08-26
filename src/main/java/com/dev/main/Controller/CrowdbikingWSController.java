package com.dev.main.Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.sns.model.PublishResult;
import com.dev.main.MySQLConexion;
import com.dev.main.Bean.Logs_sms;
import com.dev.main.Bean.Seasons;
import com.dev.main.Bean.Service;
import com.dev.main.Bean.Users;
import com.dev.main.Interface.Bike_Service;
import com.dev.main.Interface.SeasonsService;
import com.dev.main.Interface.UsersService;
import com.dev.main.Interface.Logs_smsService;

@RestController
public class CrowdbikingWSController {
	
	@Autowired
	UsersService usersserice;
	@Autowired
	SeasonsService seasonservice;
	@Autowired
	Bike_Service bikeservice;
	@Autowired
	Logs_smsService logsservice;
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<Integer> createUser2(@RequestBody Users users){
		int rs = usersserice.createUser(users);
		
		if(rs==1){
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
        
	}
	
	@RequestMapping(value = "/seasonsAllBK", method = RequestMethod.GET)
    public ArrayList<Seasons> listAllSeasons() throws SQLException {
		ArrayList<Seasons> lista= new ArrayList<Seasons>();
		Connection con = null;
		
		try {
			con = MySQLConexion.getConexion();
			Statement consult = con.createStatement();
			String sql = "select * from crowdbiking.Seasons";
			ResultSet rs = consult.executeQuery(sql);
			while(rs.next()){
				Seasons season = new Seasons();
				season.setId(rs.getString(1));
				season.setName(rs.getString(2));
				season.setDireccion(rs.getString(3));
				season.setLatitud(rs.getString(4));
				season.setLongitud(rs.getString(5));
				season.setStock(rs.getInt(6));
				lista.add(season);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (con!= null) con.close();
		}
		
		return lista;
    }
	
	@RequestMapping(value = "/seasonsAll", method = RequestMethod.GET)
    public ResponseEntity<List<Seasons>> listAllUsers() {
        List<Seasons> seasons = seasonservice.finAllSeasons();
        if(seasons.isEmpty()){
            return new ResponseEntity<List<Seasons>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Seasons>>(seasons, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/userGet", method = RequestMethod.GET)
    public ResponseEntity<Users> getUser(@RequestParam(value="cel_phone", required=true) String cel_phone,
    									 @RequestParam(value="password", required=true) String password) {
        
        Users users = usersserice.getUser(cel_phone, password);
        if (users == null) {
            System.out.println("User with not found");
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Users>(users, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/createService", method = RequestMethod.POST)
	public ResponseEntity<Integer> createService(@RequestBody Service service){
		int rs = bikeservice.createBike_Service(service);		
		if(rs==1){
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
        
	}
	
	@RequestMapping(value = "/BikeServiceGet", method = RequestMethod.GET)
    public ResponseEntity<Service> getBikeService(@RequestParam(value="cel_phone", required=true) String cel_phone) {
                
        Service service = bikeservice.getBike_Service(cel_phone);
        if (service == null) {
            System.out.println("Bikes_Service with not found");
            return new ResponseEntity<Service>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Service>(service, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/activateService", method = RequestMethod.PUT)
	public ResponseEntity<Integer> activateService(@RequestBody Service service){
		int rs = bikeservice.activate_Service(service);		
		if(rs==1){
			return new ResponseEntity<Integer>(HttpStatus.ACCEPTED);
		}else{
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
        
	}
	
	@RequestMapping(value = "/finalizarService", method = RequestMethod.PUT)
	public ResponseEntity<Integer> finalizarService(@RequestBody Service service){
		int rs = bikeservice.finalizar_Service(service);		
		if(rs==1){
			return new ResponseEntity<Integer>(HttpStatus.ACCEPTED);
		}else{
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
        
	}
	
	@RequestMapping(value="/BikeSendSMS", method = RequestMethod.GET)
	public ResponseEntity<PublishResult> sendSMS(@RequestParam(value="cel_phone", required=true) String cel_phone){
		PublishResult resul = usersserice.sendSMS(cel_phone);		
		if (resul == null) {
            System.out.println("Send SMS not found");
            return new ResponseEntity<PublishResult>(HttpStatus.NOT_FOUND);
        }		
        return new ResponseEntity<PublishResult>(resul, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/Logs_sms/create", method = RequestMethod.POST)
	public ResponseEntity<Integer> createLogs_service(@RequestBody Logs_sms logs_sms){
		int rs = 1;
		HttpStatus statuscode = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/BikeSendSMS?cel_phone=+51922987941",HttpMethod.GET, null, String.class);
			System.err.println("Code" + response.getStatusCode());
			System.out.print(response.getBody());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(rs==1){
			statuscode = HttpStatus.CREATED;
		}else{
			statuscode = HttpStatus.BAD_REQUEST;
		}
        
		return new ResponseEntity<Integer>(statuscode);
	}
	
	

}
