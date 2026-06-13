package myjastip.users;

public abstract class User {
	protected String userId;
	protected String name;
	protected String email;
	protected String password;
	protected String phoneNumber;

	public User() {
	}
	public User(String userId, String name, String email, String password, String phoneNumber) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public boolean isNull() {
		return userId != null;
	}

	public void login() {

	}
	
	public void logout() {
		
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
