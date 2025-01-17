package com.application.emailproject.registration.token;
import java.util.Calendar;
import java.util.Date;
import com.application.emailproject.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
public class VerificationToken {
	
	public VerificationToken() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Date expirationTime;
	private static final int EXPIRATION_TIME = 1;
	
	public VerificationToken(String token,User user) {
		super();
		this.token = token;
		this.user = user;
		this.expirationTime = this.getTokenExpirationTime();
	}
	
	
	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationTime = this.getTokenExpirationTime();
	}
	public Date getTokenExpirationTime() {
	    // Create an instance of the Calendar initialized to the current date and time
	    Calendar calendar = Calendar.getInstance();
	    
	    // Set the calendar time to the current time in milliseconds
	    calendar.setTimeInMillis(new Date().getTime());
	    
	    // Add the expiration time (in minutes) to the current time
	    calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
	    
	    // Return the new expiration time as a Date object
	    // calendar.getTime() returns the updated Date (current time + expiration time)
	    // new Date(calendar.getTime().getTime()) creates a new Date object using the updated milliseconds
	    return new Date(calendar.getTime().getTime());
	}
	
	
	//Since we need to bind a token for the particular user we are creating the object of the User
	@OneToOne    //SIncce the user and token are unique and one user can have a one verification token
	@JoinColumn(name="user_id")
	private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}