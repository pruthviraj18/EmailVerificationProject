package com.application.emailproject.security;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.application.emailproject.user.User;
import lombok.Data;
@Data
public class UserRegistrationDetails implements UserDetails{
	
	private String userName;
	private String userPassword;
	private boolean isEnabled;
	private List<GrantedAuthority> authorities;
	
	
	public UserRegistrationDetails(User user) {
		super();
		this.userName = user.getEmail();
		this.userPassword = user.getPassword();	
		this.isEnabled = user.isEnabled();
		this.authorities = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	 @Override
	    public String getPassword() {
	        return userPassword;
	    }
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}