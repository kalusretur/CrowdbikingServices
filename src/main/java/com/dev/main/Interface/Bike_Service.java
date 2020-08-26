package com.dev.main.Interface;

import com.dev.main.Bean.Service;

public interface Bike_Service {
	
	int createBike_Service(Service service);
	Service getBike_Service(String cel_phone);
	int activate_Service(Service service);
	int finalizar_Service(Service service);
	
	

}
