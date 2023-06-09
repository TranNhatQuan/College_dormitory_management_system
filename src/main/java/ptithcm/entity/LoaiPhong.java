package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;

@Entity
@Table(name = "LOAIPHONG")
public class LoaiPhong {
	@Id
	@Column(name = "MALOAIPHONG")
	private String maLoaiPhong;
	
	@Column(name = "TENLOAIPHONG")
	private String tenLoaiPhong;
	
	@Column(name = "GIA")
	private Long gia;
	
	
	@OneToMany(mappedBy = "loaiPhong", fetch = FetchType.EAGER)
	private Collection<Phong> phongs;
	
	
	
	
	public Long getGia() {
		return gia;
	}
	public void setGia(Long gia) {
		this.gia = gia;
	}
	public String getMaLoaiPhong() {
		return maLoaiPhong;
	}
	public void setMaLoaiPhong(String maLoaiPhong) {
		this.maLoaiPhong = maLoaiPhong;
	}
	public String getTenLoaiPhong() {
		return tenLoaiPhong;
	}
	public void setTenLoaiPhong(String tenLoaiPhong) {
		this.tenLoaiPhong = tenLoaiPhong;
	}
	public Collection<Phong> getPhongs() {
		return phongs;
	}
	public void setPhongs(Collection<Phong> phongs) {
		this.phongs = phongs;
	}
	
}
