package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role {
	@Id
	@Column(name = "MAROLE")
	private String maRole;
	
	@Column(name = "TENROLE")
	private String tenRole;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private Collection<TaiKhoan> taiKhoan;

	

	public String getMaRole() {
		return maRole;
	}

	public void setMaRole(String maRole) {
		this.maRole = maRole;
	}

	public String getTenRole() {
		return tenRole;
	}

	public void setTenRole(String tenRole) {
		this.tenRole = tenRole;
	}

	public Collection<TaiKhoan> getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(Collection<TaiKhoan> taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

}
