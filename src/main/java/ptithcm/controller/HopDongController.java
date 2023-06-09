package ptithcm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
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

import ptithcm.entity.HopDong;
import ptithcm.entity.NhanVien;
import ptithcm.entity.Phong;
import ptithcm.entity.SinhVien;
import ptithcm.entity.SoTheoDoi;


@Transactional
@Controller
@RequestMapping("/ktx/")
public class HopDongController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "qlhopdong/{id_NV}.htm")
	public String showHopDong(ModelMap model, @PathVariable("id_NV") String maNV,@ModelAttribute("hopDong") HopDong hopDong)
	{
		model.addAttribute("show", "show1");
		model.addAttribute("show1", "showFormAdd");
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HopDong> listSinhVien = this.getHopDongs();
			model.addAttribute("hopDongs", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {

			List<HopDong> listSinhVien = this.getHopDongs();
			model.addAttribute("hopDongs", listSinhVien);
			model.addAttribute("phanQuyen", "admin");

		}
		//
		model.addAttribute("btnAction", "btnAdd");

		
		
		return "ktx/qlhopdong";
	}
	@RequestMapping(value = "qlhopdong/action/{id_NV}.htm", params = "btnAdd")
	public String addHopDongql(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("hopDong") HopDong hopDong, BindingResult erros) {
	
		//Bắt lỗi
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
	
		
		//Mã sv
		if(hopDong.getSinhVien().getMaSV().trim().length()==0) {
			erros.rejectValue("sinhVien.maSV","HopDong", "Vui lòng nhập mã sinh viên!");
		}
		else {
			if (this.kiemtraSV(hopDong.getSinhVien().getMaSV())==2) {
				erros.rejectValue("sinhVien.maSV", "HopDong", "Sinh viên này không tồn tại!");
			}
			else {
				if(this.kiemtraSV(hopDong.getSinhVien().getMaSV())==0) {
					erros.rejectValue("sinhVien.maSV", "HopDong", "Sinh viên hiện đang ở ktx, vui lòng chọn sinh viên chưa ở ktx!");
				}
			}
		}
		
		
	
		//Mã phòng
		
		if(hopDong.getPhong().getIdPhong().trim().length()==0) {
			erros.rejectValue("phong.idPhong","HopDong", "Vui lòng nhập mã phòng");
		}
		else {
			if(kiemtraPhong(hopDong.getPhong().getIdPhong())==0) {
				erros.rejectValue("phong.idPhong","HopDong", "Phòng không tồn tại!");
			}else {
				try {
					if(kiemTraPhongFull(hopDong.getPhong().getIdPhong())) {
						erros.rejectValue("phong.idPhong","HopDong", "Phòng này đã hết chỗ, chọn phòng khác!");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//Ngày lập
		if(hopDong.getNgayLap().trim().length()==0) {
			erros.rejectValue("ngayLap","HopDong", "Vui lòng nhập ngày lập!");
		}
		//Ngày bắt đầu
		if(hopDong.getNgayLap().trim().length()==0) {
			erros.rejectValue("ngayBatDau","HopDong", "Vui lòng nhập ngày bắt đầu!");
			
			
		}else {
			if(this.sosanhNgay(hopDong.getNgayLap(), hopDong.getNgayBatDau())==0) {
				erros.rejectValue("ngayBatDau","HopDong", "Ngày bắt đầu phải lớn hơn ngày lập!");
			}
		}
		//Ngày kết thúc
		if(hopDong.getNgayLap().trim().length()==0) {
			erros.rejectValue("ngayKetThuc","HopDong", "Vui lòng nhập ngày kết thúc!");
		}
		else {
			if(this.sosanhNgay(hopDong.getNgayBatDau(), hopDong.getNgayKetThuc())==0) {
				erros.rejectValue("ngayKetThuc","HopDong", "Ngày kết thúc phải lớn hơn ngày bắt đầu ở!");
			}
		}
		
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			
			Integer check = this.addHopDong(hopDong);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Thêm hợp đồng thành công");
			} else {
				model.addAttribute("message", "Thêm hợp đồng thất bại");
			}
		}	
		
//		Integer check = this.addHopDong(hopDong);
//		model.addAttribute("check", check);
//		if (check != 0) {
//			model.addAttribute("message", "Thêm mới hợp đồng thành công!");
//		} else {
//			model.addAttribute("message", "Thêm mới hợp đồng thất bại!");
//		}
		
		model.addAttribute("btnAction", "btnAdd");
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HopDong> listSinhVien = this.getHopDongs();
			model.addAttribute("hopDongs", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {

			List<HopDong> listSinhVien = this.getHopDongs();
			model.addAttribute("hopDongs", listSinhVien);
			model.addAttribute("phanQuyen", "admin");

		}
	
		model.addAttribute("show", "show1");
		model.addAttribute("show1", "showFormAdd");

		return "ktx/qlhopdong";
	}
	@RequestMapping(value = "hopdong/{id_NV}.htm")	
	public String showSinhVien(ModelMap model, @PathVariable("id_NV") String maNV,@ModelAttribute("sinhvien") SinhVien sinhVien) {
		
		model.addAttribute("show1", "show");
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//
		model.addAttribute("btnAction", "btnAdd");

		return "ktx/hopdong";
	}
	
	
	
	@RequestMapping(value = "hopdong/{id_NV}/{maSV}/{idPhong}.htm", params = "lnkAdd")
	public String showAddHopDong(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV, @PathVariable("idPhong") String idPhong, @ModelAttribute("hopdong") HopDong hopDong) {

		
		SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhVien", sinhVien);
		List<HopDong> listHopDong = this.getHopDongsByMaSV(maSV);
		model.addAttribute("hopDongs", listHopDong);
//		Phong listPhong = this.getPhongByIdPhong(idPhong);
//		model.addAttribute("phongs", listPhong);
		
		model.addAttribute("idPhong", idPhong);
		if(idPhong != "") {
		Phong phong=this.getPhongByIdPhong(idPhong);
		model.addAttribute("loaiPhongSelected", phong.getLoaiPhong().getTenLoaiPhong());
		}
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		List<Phong> listPhong = this.getPhongs();
		model.addAttribute("phongs", listPhong);

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show", "showFormAdd");
		return "ktx/hopdong";
	}
	
//	@RequestMapping(value = "hopdong/{id_NV}.htm")
//	public String showHopDong(ModelMap model, @PathVariable("id_NV") String maNV, @ModelAttribute("hopdong") HopDong hopDong) {
//		
//		NhanVien nhanVien = this.getNhanVienByID(maNV);
//		model.addAttribute("id_NV", maNV);
//		model.addAttribute("hoTen", nhanVien.getHoTen());
//		
//		String s ="QL";
//		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
//		{
//			List<HopDong> listHopDongs = this.getHopDongs();
//			model.addAttribute("hopDongs", listHopDongs);
//			model.addAttribute("phanQuyen", "admin");
//		} else {
//			List<HopDong> listHopDongs = this.getHopDongs();
//			model.addAttribute("hopDongs", listHopDongs);
//			model.addAttribute("phanQuyen", "nhanvien");
//
//		}
//		
//		 model.addAttribute("btnAction", "btnAdd");
//		return "ktx/hopdong";
//	}
	
	
	@RequestMapping(value = "hopdong/action/{id_NV}/{maSV}.htm", params = "btnAdd")
	public String addHopDong(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV,@ModelAttribute("hopdong") HopDong hopDong, BindingResult erros) {
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhVien", sinhVien);
		
		//Mã hop dong
	
		//Mã phòng
		if(hopDong.getPhong().getIdPhong().trim().length()==0) {
			erros.rejectValue("phong.idPhong","HopDong", "Vui lòng chọn phòng");
		}
		else 
		try {
			if(kiemTraPhongFull(hopDong.getPhong().getIdPhong())) {
				erros.rejectValue("phong.idPhong","HopDong", "Phòng này đã hết chỗ, chọn phòng khác!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Ngày lập
		if(hopDong.getNgayLap().trim().length()==0) {
			erros.rejectValue("ngayLap","HopDong", "Vui lòng nhập ngày lập!");
		}
		//Ngày bắt đầu
		if(hopDong.getNgayLap().trim().length()==0) {
			erros.rejectValue("ngayBatDau","HopDong", "Vui lòng nhập ngày bắt đầu!");
		}
		//Ngày kết thúc
		if(hopDong.getNgayLap().trim().length()==0) {
			erros.rejectValue("ngayKetThuc","HopDong", "Vui lòng nhập ngày kết thúc!");
		}
		
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.addHopDong(hopDong);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Thêm hợp đồng thành công");
			} else {
				model.addAttribute("message", "Thêm hợp đồng thất bại");
			}
		}	
		
//		Integer check = this.addHopDong(hopDong);
//		model.addAttribute("check", check);
//		if (check != 0) {
//			model.addAttribute("message", "Thêm mới hợp đồng thành công!");
//		} else {
//			model.addAttribute("message", "Thêm mới hợp đồng thất bại!");
//		}
		
		model.addAttribute("btnAction", "btnAdd");
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		List<Phong> listPhong = this.getPhongs();
		model.addAttribute("phongs", listPhong);
//		String s ="QL";
//		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
//		{
//			List<HopDong> listHopDongs = this.getHopDongs();
//			model.addAttribute("hopDongs", listHopDongs);
//			model.addAttribute("phanQuyen", "admin");
//		} else {
//			List<HopDong> listHopDongs = this.getHopDongs();
//			model.addAttribute("hopDongs", listHopDongs);
//			model.addAttribute("phanQuyen", "nhanvien");
//
//		}
		model.addAttribute("show", "showFormAdd");

		return "ktx/hopdong";
	}
	
	@RequestMapping(value = "hopdong/{id_NV}/{maHopDong}.htm", params = "lnkDel")
	public String showDelHopDong(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maHopDong") String maHopDong, @ModelAttribute("hopdong") HopDong hopDong) {

		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		HopDong chooseHD = this.getHopDongsByMaHD(maHopDong);
		model.addAttribute("hopdong", chooseHD);

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			
			List<HopDong> listHopDongs = this.getHopDongs();
			model.addAttribute("hopDongs", listHopDongs);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			
			List<HopDong> listHopDongs = this.getHopDongsByMaNV(maNV);
			model.addAttribute("hopDongs", listHopDongs);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		model.addAttribute("btnAction", "btnDel");

		model.addAttribute("show", "showFormDel");
		return "ktx/hopdong";
	}
	
	@RequestMapping(value = "hopdong/action/{id_NV}.htm", params = "btnDel")
	public String delHopDong(ModelMap model, @PathVariable("id_NV") String maNV,@ModelAttribute("hopdong") HopDong hopDong) {
		
		Integer check = this.delHopDong(hopDong);
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa hợp đồng thành công!");
		} else {
			model.addAttribute("message", "Xóa hợp đồng thất bại!");
		}
		
		model.addAttribute("btnAction", "btnAdd");
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			List<HopDong> listHopDongs = this.getHopDongs();
			model.addAttribute("hopDongs", listHopDongs);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SinhVien> listSinhVien = this.getsinhviens();
			List<HopDong> listHopDongs = this.getHopDongs();
			model.addAttribute("hopDongs", listHopDongs);
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		List<Phong> listPhong = this.getPhongs();
		model.addAttribute("phongs", listPhong);
		
		model.addAttribute("show", "showFormAdd");

		return "ktx/hopdong";
	}
	
	
	@RequestMapping(value = "hopdong/{id_NV}/{maSV}.htm", params = "lnkShow")
	public String showHopDong(ModelMap model, @PathVariable("id_NV") String maNV,@PathVariable("maSV") String maSV) {

		SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhVien", sinhVien);

//		Phong listPhong = this.getPhongByIdPhong(idPhong);
//		model.addAttribute("phongs", listPhong);
		

		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<HopDong> listHopDong = this.getHopDongsByMaSV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("hopDongs", listHopDong);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<HopDong> listHopDong = this.getHopDongsByMaSV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("hopDongs", listHopDong);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show1", "show");
		return "ktx/hopdong";
	}
	
	public Integer addHopDong(HopDong hopDong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(hopDong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	public Integer delHopDong(HopDong hopDong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(hopDong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
//	public int svDaLapHopDong(String maSV) {
//		Session session = factory.getCurrentSession();
//		String hql = "SELECT ngaybatdau FROM HopDong WHERE ";
//		Query query = session.createQuery(hql);
//
//		List<SinhVien> list = query.list();
//		
//		return 0;
//	}
	
	public List<SinhVien> getsinhviens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien";
		Query query = session.createQuery(hql);

		List<SinhVien> list = query.list();
		return list;
	}
//	public List<SinhVien> getSinhViensByMaNV(String maNV) {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM SinhVien WHERE nhanVien.maNV= :maNV";
//		Query query = session.createQuery(hql);
//		query.setParameter("maNV", maNV);
//
//		List<SinhVien> list = query.list();
//		return list;
//	}
	
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
	
	public HopDong getHopDongsByMaHD(String maHopDong) {
			Session session = factory.getCurrentSession();
			String hql = "FROM HopDong WHERE maHopDong= :maHopDong";
			Query query = session.createQuery(hql);
			query.setParameter("maHopDong", maHopDong);

			HopDong hopDong = (HopDong) query.list().get(0);
			return hopDong;
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
	//Hiá»‡n danh sÃ¡ch sinh viÃªn chÆ°a cÃ³ há»£p Ä‘á»“ng trong thá»�i gian hiá»‡n táº¡i
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
	
	public boolean kiemTraPhongFull(String idPhong) throws Exception {		
		Date date = new Date();  
		Phong phong = this.getPhongByIdPhong(idPhong);
		int count = 0;
		for(HopDong hd : phong.getHopDongs()) {
			Date  nkt =new SimpleDateFormat("yyyy-MM-dd").parse(hd.getNgayKetThuc());
			if (nkt.after(date)){
				count++;
			}
		}
			if(count == phong.getSoSV()) {
				return true;
			}
			return false;
	}
	
	public Phong getPhongByIdPhong(String idPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong WHERE idPhong= :idPhong";
		Query query = session.createQuery(hql);
		query.setParameter("idPhong", idPhong);

		Phong phong = (Phong) query.list().get(0);
		return phong;
	}
	
	public Integer kiemtraPhong(String id) {
		try {
			Phong phong= this.getPhongByIdPhong(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	public Integer kiemtraSV( String id) {
		try {
			SinhVien sinhVien= this.getSinhVienByMaSV(id);
		} catch (Exception e) {
			return 2;
		}
		
		List<HopDong> listHD = this.getHopDongsByMaSV(id);
		for(HopDong hd:listHD) {
			Date date1;
			try {
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(hd.getNgayKetThuc());
				ZonedDateTime zonedDateTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
				Date currentDate = Date.from(zonedDateTime.toInstant());
				if(date1.after(currentDate)) {
					return 0;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
			
		}
		
		
		return 1;
	}
	public Integer sosanhNgay(String a, String b) {
		
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(a);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(b);
			
			if(date1.after(date2)||date1 ==date2) {
				return 0;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}
}
