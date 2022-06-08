package application;

public class Admin {
	static int numberOfAdmin= 0;
	String adminID;
	String username;
	String password;
	
	Admin (String adminID, String username, String password){
		this.adminID = adminID;
		this.username = username;
		this.password = password;
		numberOfAdmin += 1;
	}
	
	void getUsername() {
		System.out.println(username);
	}
}
