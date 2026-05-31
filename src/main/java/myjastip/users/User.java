package myjastip.users;

import myjastip.payment.Payable;

public abstract class User {
	private String userId;
	private String name;
	private String email;
	private String password;
	private String phoneNumber;

	public User(String userId, String name, String email, String password, String phoneNumber) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public void login() {
		
	}
	
	public void register() {
		
	}
	
	public void logout() {
		
	}
	
}
