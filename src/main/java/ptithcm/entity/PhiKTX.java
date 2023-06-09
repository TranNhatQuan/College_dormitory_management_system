package ptithcm.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PHIKTX")
public class PhiKTX {

	
	@Id
    @Column(name = "MAHOPDONG")
    private String maHopDong;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "MAHOPDONG")
	private HopDong hopDong;
	
	@Column(name = "SOTHANGTHU")
	private Integer soThang;
	
	@Column(name = "SOTIEN")
	private Long soTien;
	
	@Column(name = "NGAYTHU")
	private String ngayThu;
	
	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;
	
	
	
//	@OneToOne(mappedBy = "phiKTX")
//	private HopDong hopDong;
	
	public Integer getSoThang() {
		return soThang;
	}

	public void setSoThang(Integer soThang) {
		this.soThang = soThang;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public String getNgayThu() {
		return ngayThu;
	}

	public void setNgayThu(String ngayThu) {
		this.ngayThu = ngayThu;
	}

	
	public Long getSoTien() {
		return soTien;
	}

	public void setSoTien(Long soTien) {
		this.soTien = soTien;
	}

	public HopDong getHopDong() {
		return hopDong;
	}

	public void setHopDong(HopDong hopDong) {
		this.hopDong = hopDong;
	}

	public String getMaHopDong() {
		return maHopDong;
	}

	public void setMaHopDong(String maHopDong) {
		this.maHopDong = maHopDong;
	}



	
	
	
	
}
