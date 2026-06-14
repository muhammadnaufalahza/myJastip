package myjastip.users;

public abstract class User {
	protected String userId;
	protected String name;
	protected String email;
	protected String password;
	protected String phoneNumber;
	protected double balance;

	public User(String userId, String name, String email, String password, String phoneNumber, double balance) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.balance = balance;

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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return password;
	}
}
