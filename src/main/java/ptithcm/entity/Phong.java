package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;

@Entity
@Table(name = "PHONG")
public class Phong {
	@Id
	@Column(name = "SOPHONG")
	private String idPhong;
	
//	@ManyToOne
//	@JoinColumn(name = "MANV")
//	private NhanVien nhanVien;
	
	@Column(name = "SOLUONGSVTOIDA")
	private Integer soSV;
	
	@Column(name = "TINHTRANGPHONG")
	private String  tinhTrang;
	
	@ManyToOne
	@JoinColumn(name = "MALOAIPHONG")
	private LoaiPhong loaiPhong;
	
	@OneToMany(mappedBy = "phong", fetch = FetchType.EAGER)
	private Collection<HoaDonDien> hoaDonDiens;
	
	@OneToMany(mappedBy = "phong", fetch = FetchType.EAGER)
	private Collection<HopDong> hopDongs;
	

	public String getIdPhong() {
		return idPhong;
	}

	public void setIdPhong(String idPhong) {
		this.idPhong = idPhong;
	}

//	public NhanVien getNhanVien() {
//		return nhanVien;
//	}
//
//	public void setNhanVien(NhanVien nhanVien) {
//		this.nhanVien = nhanVien;
//	}

	public Integer getSoSV() {
		return soSV;
	}

	public void setSoSV(Integer soSV) {
		this.soSV = soSV;
	}

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

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
