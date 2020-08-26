package com.dev.main.Interface;

import com.amazonaws.services.sns.model.PublishResult;
import com.dev.main.Bean.Users;

public interface UsersService {
	
	int createUser(Users users);
	Users getUser(String cel_phone, String password);
	PublishResult sendSMS(String phone_number);

}
