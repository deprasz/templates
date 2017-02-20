package id.com.templates.common;

public class Regex {
	public static boolean regexUsername(String username){
		return username.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{5,}$");
	}
	
	public static boolean regexPassword(String pasword){
		return pasword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
	}
	
	public static boolean regexEmail(String email){
		return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}
}
