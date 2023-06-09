package ptithcm.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "HOPDONG")
public class HopDong {
	@Id
	@Column(name = "MAHOPDONG")
	private String maHopDong;
	
	@ManyToOne
    @JoinColumn(name = "MASV")
	private SinhVien sinhVien;
    
	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;
	
	@ManyToOne
	@JoinColumn(name = "SOPHONG")
	private Phong phong;
	
	@OneToOne(mappedBy = "hopDong", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PhiKTX phiktx;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	private PhiKTX phiKTX;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "MM/dd/yyyy")
//	@Column(name = "NGAYLAP")
//	private Date ngayLap;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "MM/dd/yyyy")
//	@Column(name = "NGAYBATDAUHD")
//	private Date ngayBatDau;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "MM/dd/yyyy")
//	@Column(name = "NGAYKETTHUCHD")
//	private Date ngayKetThuc;
	
	@Column(name = "NGAYLAP")
	private String ngayLap;
	
	@Column(name = "NGAYBATDAUHD")
	private String ngayBatDau;

	@Column(name = "NGAYKETTHUCHD")
	private String ngayKetThuc;
	
//	public PhiKTX getPhiKTX() {
//		return phiKTX;
//	}
//
//	public void setPhiKTX(PhiKTX phiKTX) {
//		this.phiKTX = phiKTX;
//	}

	public String getMaHopDong() {
		return maHopDong;
	}

	public void setMaHopDong(String maHopDong) {
		this.maHopDong = maHopDong;
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

	public Phong getPhong() {
		return phong;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}

//	public Date getNgayLap() {
//		return ngayLap;
//	}
//
//	public void setNgayLap(Date ngayLap) {
//		this.ngayLap = ngayLap;
//	}
	
	

//	public Date getNgayBatDau() {
//		return ngayBatDau;
//	}

	public String getNgayLap() {
		return ngayLap;
	}

	public String getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public void setNgayLap(String ngayLap) {
		this.ngayLap = ngayLap;
	}

	public String getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(String ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	 public HopDong() {
		// TODO Auto-generated constructor stub
 
		LocalDateTime now = LocalDateTime.now(); 
		String year = (now.getYear()+"").substring(2,4);
		String dayth = now.getDayOfYear()+"";
		String time = now.getHour()*60*60+now.getMinute()*60+now.getSecond()+"";
		while(time.length()<5){
			time="0"+time;
		}
		String key = year+dayth+time;
		this.maHopDong="HD"+key;
	}
//	public void setNgayBatDau(Date ngayBatDau) {
//		this.ngayBatDau = ngayBatDau;
//	}

//	public Date getNgayKetThuc() {
//		return ngayKetThuc;
//	}
//
//	public void setNgayKetThuc(Date ngayKetThuc) {
//		this.ngayKetThuc = ngayKetThuc;
//	}

	

}
