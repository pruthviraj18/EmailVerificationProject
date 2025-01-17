package com.application.emailproject.user;

import java.util.List;
import java.util.Optional;

import com.application.emailproject.registration.RegistrationRequest;
import com.application.emailproject.registration.token.VerificationToken;

public interface IUserService {
	
	List<User> getUsers();
	User registerUser(RegistrationRequest request);
	Optional<User> findByEmail(String email);
	void saveUserVerificationToken(User theUser, String token);
	String validateToken(String theToken);
	VerificationToken generateNewVerificationToken(String oldToken);
	

}
