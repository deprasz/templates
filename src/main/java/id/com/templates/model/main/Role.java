package id.com.templates.model.main;

import id.com.templates.model.AbstractAuditingEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends AbstractAuditingEntity implements Serializable{

	private static final long serialVersionUID = 7063716663387942925L;

	@Id
	@Column(name="role_id")
	private String roleId;

	@Column(name="role_name")
	private String roleName;

	public String getRoleId() {

		return roleId;
	}

	public void setRoleId(String roleId) {

		this.roleId = roleId;
	}

	public String getRoleName()	{
		return roleName;
	}

	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}


}
