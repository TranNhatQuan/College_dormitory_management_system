package ptithcm.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.FetchType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "SINHVIEN")
public class SinhVien {

	@Id
	@Column(name = "MASV")
	private String maSV;
	
	@Column(name = "HOTEN")
	private String hoTen;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "MM/dd/yyyy")
//	@Column(name = "NGAYSINH")
//	private Date ngaySinh;
	
	@Column(name = "NGAYSINH")
	private String ngaySinh;
	
	@Column(name = "GIOITINH")
	private String gioiTinh;
	
	@Column(name = "CMND")
	private String cMND;
	
	@Column(name = "SDT")
	private String soDT;
	
	@Column(name = "LOP")
	private String lop;
	
	@Column(name = "SDTNGUOITHAN")
	private String soDTNguoiThan;
	
	@Column(name = "MAILSV")
	private String mail;
	
	@OneToMany(mappedBy = "sinhVien", fetch = FetchType.EAGER)
	private Collection<HopDong> hopDongs;
	
	
	@OneToMany(mappedBy = "sinhVien", fetch = FetchType.EAGER)
	private Collection<SoTheoDoi> soTheoDois;

	

	public Collection<HopDong> getHopDongs() {
		return hopDongs;
	}

	public void setHopDongs(Collection<HopDong> hopDongs) {
		this.hopDongs = hopDongs;
	}

	public Collection<SoTheoDoi> getSoTheoDois() {
		return soTheoDois;
	}

	public void setSoTheoDois(Collection<SoTheoDoi> soTheoDois) {
		this.soTheoDois = soTheoDois;
	}

	public String getMaSV() {
		return maSV;
	}

	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	
	
	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getcMND() {
		return cMND;
	}

	public void setcMND(String cMND) {
		this.cMND = cMND;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public String getSoDTNguoiThan() {
		return soDTNguoiThan;
	}

	public void setSoDTNguoiThan(String soDTNguoiThan) {
		this.soDTNguoiThan = soDTNguoiThan;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	
	public int getSo() {
		return 1;
	}
	
	
}
