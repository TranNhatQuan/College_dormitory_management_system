package ptithcm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "HOADONDIEN")
public class HoaDonDien {
	@Id
	@GeneratedValue
	@Column(name = "MAHD")
	private String maHD;
	
	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;
	
	@ManyToOne
	@JoinColumn(name = "SOPHONG")
	private Phong phong;

	@Column(name = "NGAYLAP")
	private String ngayLap;
	
	
	@Column(name = "CHISODIENDAU")
	private Integer chiSoDienDau;
	
	@Column(name = "CHISODIENCUOI")
	private Integer chiSoDienCuoi;
	
	@Column(name = "GIADIEN")
	private Long giaDien;
	
	
	
	
	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Phong getPhong() {
		return phong;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}


	public String getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(String ngayLap) {
		this.ngayLap = ngayLap;
	}

	

	

	public Long getGiaDien() {
		return giaDien;
	}

	public void setGiaDien(Long giaDien) {
		this.giaDien = giaDien;
	}

	public Integer getChiSoDienDau() {
		return chiSoDienDau;
	}

	public void setChiSoDienDau(Integer chiSoDienDau) {
		this.chiSoDienDau = chiSoDienDau;
	}

	public Integer getChiSoDienCuoi() {
		return chiSoDienCuoi;
	}

	public void setChiSoDienCuoi(Integer chiSoDienCuoi) {
		this.chiSoDienCuoi = chiSoDienCuoi;
	}




	
}
