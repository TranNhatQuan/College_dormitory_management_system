package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name = "TAIKHOAN")
public class TaiKhoan {

	@Id
    @Column(name = "MANV")
    private String maNV;
	
	@Column(name = "MATKHAU")
	private String matKhau;
	
	@Column(name = "TAIKHOAN")
	private String taiKhoan;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;
	
	@ManyToOne
	@JoinColumn(name = "MAROLE")
	private Role role;

	

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

	
	
}
