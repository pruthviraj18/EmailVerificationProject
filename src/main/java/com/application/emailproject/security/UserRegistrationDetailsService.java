package com.application.emailproject.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.application.emailproject.user.UserRepository;
@Service
public class UserRegistrationDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email)
				.map(UserRegistrationDetails::new)
				.orElseThrow(()->new UsernameNotFoundException("User not found"));
	}
}