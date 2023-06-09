package ptithcm.entity;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;




import javax.persistence.FetchType;



@Entity
@Table(name = "NHANVIEN")
public class NhanVien {
	@Id
	@Column(name = "MANV")
	
	private String maNV;
	
	@Column(name="HOTEN")

	private String hoTen;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "MM/dd/yyyy")
//	@Column(name="NGAYSINH")
//	private Date ngaySinh;
	@Column(name="NGAYSINH")
	private String ngaySinh;
	
	@Column(name = "DIACHI")
	private String diaChi;

	@Column(name="SDT")
	private String soDT;
	
	@Column(name="MAILNV")
	private String email;
	
	@OneToOne(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TaiKhoan taiKhoan;
	
//	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
//	private Collection<Phong> phongs;
	
	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
	private Collection<HoaDonDien> hoaDonDiens;
	
	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
	private Collection<PhiKTX> phiKTXs;
	
	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
	private Collection<HopDong> hopDongs;


	
	public String getMaNV() {
		return maNV;
	}

	
	public Collection<PhiKTX> getPhiKTXs() {
		return phiKTXs;
	}


	public void setPhiKTXs(Collection<PhiKTX> phiKTXs) {
		this.phiKTXs = phiKTXs;
	}


	public void setMaNV(String maNV) {
		this.maNV = maNV;
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


	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

//	public Collection<Phong> getPhongs() {
//		return phongs;
//	}
//
//	public void setPhongs(Collection<Phong> phongs) {
//		this.phongs = phongs;
//	}

	public Collection<HoaDonDien> getHoaDonDiens() {
		return hoaDonDiens;
	}

	public void setHoaDonDiens(Collection<HoaDonDien> hoaDonDiens) {
		this.hoaDonDiens = hoaDonDiens;
	}

	public Collection<HopDong> getHopDongs() {
		return hopDongs;
	}

	public void setHopDongs(Collection<HopDong> hopDongs) {
		this.hopDongs = hopDongs;
	}



	
	
	
}
