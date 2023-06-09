package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "SOTHEODOI")
public class SoTheoDoi {

	@Id 
	@GeneratedValue
	@Column(name = "MAKL")
	private int makl;
	
	@Column(name = "LYDO")
	private String lyDo;
	
	@Column(name = "NGAYGHINHAN")
	private String ngayGhiNhan;
	
	
	@ManyToOne
	@JoinColumn(name = "MASV")
	private SinhVien sinhVien;
	
	
	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;
	
	
	
	
	public int getMakl() {
		return makl;
	}
	public void setMakl(int makl) {
		this.makl = makl;
	}
	public String getLyDo() {
		return lyDo;
	}
	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}
	public SinhVien getSinhVien() {
		return sinhVien;
	}
	public void setSinhVien(SinhVien sinhVien) {
		this.sinhVien = sinhVien;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public String getNgayGhiNhan() {
		return ngayGhiNhan;
	}
	public void setNgayGhiNhan(String ngayGhiNhan) {
		this.ngayGhiNhan = ngayGhiNhan;
	}
	
	
}
