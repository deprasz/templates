package id.com.templates.model;


import org.springframework.security.core.GrantedAuthority;

public class NamingGrantedAuthority implements GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6311462798677011923L;
	private String authority;
    private String name;

    public NamingGrantedAuthority(String authority,String name){
        this.name = name;
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
