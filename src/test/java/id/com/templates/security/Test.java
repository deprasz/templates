package id.com.templates.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("1"));
	}

}
