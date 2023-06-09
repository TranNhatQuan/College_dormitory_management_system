package ptithcm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.HoaDonDien;
import ptithcm.entity.HopDong;
import ptithcm.entity.NhanVien;
import ptithcm.entity.Phong;
import ptithcm.entity.SinhVien;
import ptithcm.entity.SoTheoDoi;



@Transactional
@Controller
@RequestMapping("/ktx/")
public class HoaDonDienController {
	@Autowired
	SessionFactory factory;
	
	
	@RequestMapping(value = "quanlidien/{id_NV}.htm")
	public String showPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("phong") Phong phong) {
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<Phong> listPhong= this.getPhongs();
			model.addAttribute("phongs", listPhong);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Phong> listPhong= this.getPhongs();
			model.addAttribute("phongs", listPhong);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//
		model.addAttribute("btnAction", "btnAdd");
		return "ktx/quanlidien";
	}
	
	/*@RequestMapping(value = "svtrongphong/{id_NV}/{idPhong}.htm", params = "lnkShow")
	public String showSVTrongPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("idPhong") String idPhong) {

		Phong phong = this.getPhongByIdPhong(idPhong);
		model.addAttribute("phong", phong);
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		List<Phong> listPhong= this.getPhongs();
		model.addAttribute("phongs", listPhong);
		
			List<HopDong> listHD= this.getHopDongsbyidPhong(idPhong);
			model.addAttribute("hopdongs", listHD);
			model.addAttribute("phanQuyen", "admin");
		
		//model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show1", "show");
		return "ktx/quanlidien";
	}*/
	
	@RequestMapping(value = "hoadondien/{id_NV}/{id_Phong}.htm", params = "lnkShow")
	public String showHoaDonDien(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("id_Phong") String id_Phong,@ModelAttribute("hoaDonDien") HoaDonDien hoaDonDien) {

		//SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		//model.addAttribute("sinhVien", sinhVien);

//		Phong listPhong = this.getPhongByIdPhong(idPhong);
//		model.addAttribute("phongs", listPhong);
		Phong phong = this.getPhongByIdPhong(id_Phong);
		model.addAttribute("phong", phong);	

		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		model.addAttribute("btnAction", "btnAdd");

		//model.addAttribute("show", "showFormAdd");
		return "ktx/hoadondien";
	}
	@RequestMapping(value = "hoadondien/{id_NV}/{maHD}/{id_Phong}.htm", params = "lnkDel")
	public String lnkDelHoaDonDien(ModelMap model, @PathVariable("maHD") String maHD, @PathVariable("id_NV") String id_NV, @PathVariable("id_Phong") String id_Phong) {

		
		model.addAttribute("message", "Xoá hoá đơn điện");
		
		HoaDonDien chooseHDD = this.getHoaDonDienbyMaHD(maHD);
		model.addAttribute("hoaDonDien", chooseHDD);
		
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}

		

		model.addAttribute("btnAction", "btnDelete");

		return "ktx/hoadondien";
	}

	@RequestMapping(value = "hoadondien/action/{id_NV}/{id_Phong}.htm", params = "btnDelete")
	public String delHoaDonDien(ModelMap model, @PathVariable("id_NV") String id_NV, @ModelAttribute("hoaDonDien") HoaDonDien hdd, @PathVariable("id_Phong") String id_Phong) {
		
		Integer check = this.deleteHoaDonDien(hdd);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa thành công!");
		} else {
			model.addAttribute("message", "Xóa thất bại!");
		}
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}


		

		model.addAttribute("btnAction", "btnAdd");

		return "ktx/hoadondien";
	}
//	@RequestMapping(value = "hoadondien/del/{id_NV}/{maHD}/{id_Phong}.htm")
//	public String delHoaDonDien(ModelMap model, @PathVariable("id_NV") String id_NV,@PathVariable("maHD") String maHD,@ModelAttribute("hoaDonDien") HoaDonDien hoaDonDien, @PathVariable("id_Phong") String id_Phong) {
//		HoaDonDien hdd = this.getHoaDonDienbyMaHD(maHD);
//		
//		Integer check = this.deleteHoaDonDien(hdd);
//		
//		model.addAttribute("check", check);
//		if (check != 0) {
//			model.addAttribute("message", "Xóa thành công!");
//		} else {
//			model.addAttribute("message", "Xóa thất bại!");
//		}
//		NhanVien nhanVien = this.getNhanVienByID(id_NV);
//		model.addAttribute("id_NV", id_NV);
//		model.addAttribute("hoTen", nhanVien.getHoTen());
//
//		String s ="QL";
//		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
//		{
//			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
//			model.addAttribute("hoaDonDiens", listHoaDonDiens);
//			//List<SinhVien> listSinhVien = this.getsinhviens();
//			//model.addAttribute("sinhViens", listSinhVien);
//			model.addAttribute("phanQuyen", "admin");
//		} else {
//			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
//			model.addAttribute("hoaDonDiens", listHoaDonDiens);
//			//List<SinhVien> listSinhVien = this.getsinhviens();
//			//model.addAttribute("sinhViens", listSinhVien);
//			model.addAttribute("phanQuyen", "nhanvien");
//
//		}
//
//
//		
//
//		model.addAttribute("btnAction", "btnAdd");
//
//		return "ktx/hoadondien";
//	}
	
	@RequestMapping(value = "hoadondien/{id_NV}/{maHD}/{id_Phong}.htm", params = "lnkEdit")
	public String lnkEditHoaDonDien(ModelMap model, @PathVariable("maHD") String maHD, @PathVariable("id_NV") String id_NV, @PathVariable("id_Phong") String id_Phong) {

		
		model.addAttribute("message", "Sửa hoá đơn điện");
		
		HoaDonDien chooseHDD = this.getHoaDonDienbyMaHD(maHD);
		model.addAttribute("hoaDonDien", chooseHDD);
		
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}

		

		model.addAttribute("btnAction", "btnUpdate");

		return "ktx/hoadondien";
	}

	@RequestMapping(value = "hoadondien/action/{id_NV}/{id_Phong}.htm", params = "btnUpdate")
	public String updateHoaDonDien(ModelMap model, @PathVariable("id_NV") String id_NV, @ModelAttribute("hoaDonDien") HoaDonDien hdd, @PathVariable("id_Phong") String id_Phong, BindingResult erros) {
		
		
		//Bắt lỗi
				if(hdd.getMaHD().trim().length()==0) {
					erros.rejectValue("maHD","HoaDonDien", "Vui lòng nhập hoá đơn điện!");
				}else {
					if(hdd.getMaHD().trim().length()>=10) {
						erros.rejectValue("maHD","HoaDonDien", "Mã hoá đơn điện không được dài hơn 10 ký tự!");
					}
				}
				//Ngày lập
				if(hdd.getNgayLap().trim().length()==0) {
					erros.rejectValue("ngayLap","HoaDonDien", "Không được bỏ trống ngày lập!");
				}
				//Chỉ số điện đầu
				if(hdd.getChiSoDienDau()==null) {
					erros.rejectValue("chiSoDienDau","HoaDonDien", "Vui lòng nhập chỉ số điện ban đầu!");
				}else {
					if (hdd.getChiSoDienDau()<=0) {
						erros.rejectValue("chiSoDienDau","HoaDonDien", "Chỉ số điện ban đầu phải lớn hơn 0!");
					}
				}
				//Chỉ số điện cuối>Chỉ số điện đầu
				if(hdd.getChiSoDienCuoi()==null) {
					erros.rejectValue("chiSoDienCuoi","HoaDonDien", "Vui lòng nhập chỉ số điện mới đo!");
				}else {
					if(hdd.getChiSoDienCuoi()<=hdd.getChiSoDienDau()) {
						erros.rejectValue("chiSoDienDau","HoaDonDien", "Chỉ số điện mới đo được phải lớn hơn hoặc bằng chỉ số điện ban đầu!");
					}
				}
				//Tổng tiền
				if (hdd.getGiaDien()==null) {
					erros.rejectValue("giaDien","HoaDonDien", "Vui lòng nhập giá điện!");
				}
				else {
					if(hdd.getGiaDien()<1000) {
						erros.rejectValue("giaDien","HoaDonDien", "Giá điện phải lớn hơn 1000!");
					}
				}
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
				}
				else {
					//System.out.print("1");
					model.addAttribute("message", "Bạn đã nhập đúng!");
					Integer check = this.updateHoaDonDien(hdd);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Sửa hoá đơn điện thành công, chuyển sang form nhập hoá đơn điện");
						model.addAttribute("btnAction", "btnAdd");
					} else {
						model.addAttribute("message", "Sửa hoá đơn điện thất bại");
						model.addAttribute("btnAction", "btnUpdate");
					}
				}
		
		
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}


		

		

		return "ktx/hoadondien";
	}
	@RequestMapping(value = "hoadondien/action/{id_NV}/{id_Phong}.htm", params = "btnAdd")
	public String addHoaDonDien(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("id_Phong") String id_Phong,@ModelAttribute("hoaDonDien") HoaDonDien hoaDonDien, BindingResult erros) {
		
		Phong phong = this.getPhongByIdPhong(id_Phong);
		model.addAttribute("phong", phong);	
		
		//Bắt lỗi
				if(hoaDonDien.getMaHD().trim().length()==0) {
					erros.rejectValue("maHD","HoaDonDien", "Vui lòng nhập hoá đơn điện!");
				}else {
					if(hoaDonDien.getMaHD().trim().length()>=10) {
						erros.rejectValue("maHD","HoaDonDien", "Mã hoá đơn điện không được dài hơn 10 ký tự!");
					}
				}
				//Ngày lập
				if(hoaDonDien.getNgayLap().trim().length()==0) {
					erros.rejectValue("ngayLap","HoaDonDien", "Không được bỏ trống ngày lập!");
				}
				//Chỉ số điện đầu
				if(hoaDonDien.getChiSoDienDau()==null) {
					erros.rejectValue("chiSoDienDau","HoaDonDien", "Vui lòng nhập chỉ số điện ban đầu!");
				}
				else {
					if(hoaDonDien.getChiSoDienDau()<=0) {
						erros.rejectValue("chiSoDienDau","HoaDonDien", "Chỉ số điện ban đầu phải lớn hơn 0!");
					}
				}
				//Chỉ số điện cuối>Chỉ số điện đầu
				if(hoaDonDien.getChiSoDienCuoi()==null) {
					erros.rejectValue("chiSoDienCuoi","HoaDonDien", "Vui lòng nhập chỉ số điện mới đo!");
				}else {
					if(hoaDonDien.getChiSoDienCuoi()<=hoaDonDien.getChiSoDienDau()) {
						erros.rejectValue("chiSoDienCuoi","HoaDonDien", "Chỉ số điện mới đo được phải lớn hơn hoặc bằng chỉ số điện ban đầu!");
					}
				}
				//Tổng tiền
				if (hoaDonDien.getGiaDien()==null) {
					erros.rejectValue("giaDien","HoaDonDien", "Vui lòng nhập giá điện!");
				}
				else {
					if(hoaDonDien.getGiaDien()<1000) {
						erros.rejectValue("giaDien","HoaDonDien", "Giá điện phải lớn hơn 1000!");
					}
				}
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
				}
				else {
					//System.out.print("1");
					model.addAttribute("message", "Bạn đã nhập đúng!");
					Integer check = this.addHoaDonDien(hoaDonDien);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Thêm hoá đơn điện thành công");
					} else {
						model.addAttribute("message", "Thêm hoá đơn điện thất bại");
					}
				}
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		//
		
		model.addAttribute("btnAction", "btnAdd");
//		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
//		model.addAttribute("KhoByMaCN", listKho);
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("hoaDonDiens", listHoaDonDiens);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
//		model.addAttribute("clickaction", "showtablekho");

		return "ktx/hoadondien";
	}


	
	
	public Integer addHoaDonDien(HoaDonDien hoaDonDien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			
			session.save(hoaDonDien);
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	

	
	public List<SinhVien> getsinhviens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien";
		Query query = session.createQuery(hql);

		List<SinhVien> list = query.list();
		return list;
	}
	
	public List<HoaDonDien> getHoaDonDiens(String id_Phong){
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonDien Where phong.idPhong='"+id_Phong+"'";
		Query query = session.createQuery(hql);

		List<HoaDonDien> list = query.list();
		
		
		return list;
	}
	
	public List<SoTheoDoi> getSoTheoDoisbyMASV(String maSV){
		Session session = factory.getCurrentSession();
		String hql = "FROM SoTheoDoi WHERE sinhVien.maSV='"+maSV+"'";
		Query query = session.createQuery(hql);

		List<SoTheoDoi> list = query.list();
		return list;
	}
	
	public NhanVien getNhanVienByID(String maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien n WHERE n.maNV= :maNV";
		Query quey = session.createQuery(hql);
		quey.setParameter("maNV", maNV);

		NhanVien nhanVien = (NhanVien) quey.list().get(0);
		return nhanVien;
	}
	
	public List<HopDong> getHopDongsByMaSV(String maSV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong WHERE sinhVien.maSV= :maSV";
		Query query = session.createQuery(hql);
		query.setParameter("maSV", maSV);	
		List<HopDong> list = query.list();
		return list;
	}
	
	public List<HopDong> getHopDongsByMaNV(String maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong WHERE nhanVien.maNV= :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);	
		List<HopDong> list = query.list();
		return list;
	}
	
	public List<HopDong> getHopDongs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong";
		Query query = session.createQuery(hql);

		List<HopDong> list = query.list();
		return list;
	}
	//Hiện danh sách sinh viên chưa có hợp đồng trong thời gian hiện tại
//	@ModelAttribute("sinhViens")
//	public List<SinhVien> getSinhViens(){
//		Session session = factory.getCurrentSession();
//		//String hqlString = "FROM SinhVien";
//		String hqlString = "FROM SinhVien";
//		Query query = session.createQuery(hqlString);
//		List<SinhVien> list = query.list();
//		for(SinhVien sv:list) {
//			List<HopDong> listHD = sv.getHopDongs().stream().toList();
//			for(HopDong hd:listHD) {
//				ZonedDateTime zonedDateTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
//				Date currentDate = Date.from(zonedDateTime.toInstant());
//				System.out.print("now"+currentDate);
//				if(hd.getNgayKetThuc().after(currentDate)) {
//					list.remove(sv);
//				}
//			}
//		}
//		return list;
//	}
	public List<HopDong> getHopDongsbyidPhong(String idPhong){
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong WHERE phong.idPhong='"+idPhong+"'";
		Query query = session.createQuery(hql);

		List<HopDong> list = query.list();
		return list;
	}
	
	public List<Phong> getPhongs(){
		Session session = factory.getCurrentSession();
		String hqlString = "FROM Phong";
		Query query = session.createQuery(hqlString);
		List<Phong> list = query.list();
		return list;
	}
	
	public SinhVien getSinhVienByMaSV(String maSV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien WHERE maSV= :maSV";
		Query query = session.createQuery(hql);
		query.setParameter("maSV", maSV);

		SinhVien sinhVien = (SinhVien) query.list().get(0);
		return sinhVien;
	}
	public HoaDonDien getHoaDonDienbyMaHD(String maHD) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonDien WHERE maHD='"+maHD+"'";
		Query query = session.createQuery(hql);
		

		HoaDonDien hoaDonDien = (HoaDonDien) query.list().get(0);
		return hoaDonDien;
	}
	public Integer deleteHoaDonDien(HoaDonDien hoaDonDien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(hoaDonDien);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}	
		return 1;
	}
		
	public Integer updateHoaDonDien(HoaDonDien hoaDonDien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(hoaDonDien);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}	
		return 1;
	}
	public Phong getPhongByIdPhong(String idPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong WHERE idPhong= :idPhong";
		Query query = session.createQuery(hql);
		query.setParameter("idPhong", idPhong);

		Phong phong = (Phong) query.list().get(0);
		return phong;
	}
}
