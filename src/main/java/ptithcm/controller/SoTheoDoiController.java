package ptithcm.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.HoaDonDien;
import ptithcm.entity.HopDong;
import ptithcm.entity.NhanVien;
import ptithcm.entity.Phong;
import ptithcm.entity.SinhVien;
import ptithcm.entity.SoTheoDoi;



@Transactional
@Controller
@RequestMapping("/ktx/")
public class SoTheoDoiController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "sotheodoi/{id_NV}.htm")
	public String showSinhVien(ModelMap model, @PathVariable("id_NV") String maNV,@ModelAttribute("sinhvien") SinhVien sinhVien) {
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

		return "ktx/sotheodoi";
	}
	
	@RequestMapping(value = "sotheodoi/{id_NV}/{maSV}.htm", params = "lnkAdd")
	public String showAddSoTheoDoi(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV, @ModelAttribute("sotheodoi") SoTheoDoi soTheoDoi) {

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
		return "ktx/sotheodoi";
	}
	
	@RequestMapping(value = "sotheodoi/{id_NV}/{maSV}.htm", params = "lnkShow")
	public String showSoTheoDoi(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV) {

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
			List<SoTheoDoi> listSoTheoDoi = this.getSoTheoDoisbyMASV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("soTheoDois", listSoTheoDoi);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SoTheoDoi> listSoTheoDoi = this.getSoTheoDoisbyMASV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("soTheoDois", listSoTheoDoi);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show1", "show");
		return "ktx/sotheodoi";
	}
	
	
	@RequestMapping(value = "sotheodoi/action/{id_NV}/{maSV}.htm", params = "btnAdd")
	public String addSoTheoDoi(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV,@ModelAttribute("sotheodoi") SoTheoDoi soTheoDoi, BindingResult erros) {
		
		//Bắt lỗi
				
				//Lý do
				if(soTheoDoi.getLyDo().trim().length()==0) {
					erros.rejectValue("lyDo","SoTheoDoi", "Vui lòng nhập lý do!");
				}
				else {
					if(soTheoDoi.getLyDo().trim().length()>=500) {
						erros.rejectValue("lyDo","SoTheoDoi", "Lý do không được dài hơn 500 ký tự!");
					}
				}
				if(soTheoDoi.getNgayGhiNhan().trim().length()==0) {
					erros.rejectValue("ngayGhiNhan","SoTheoDoi", "Vui lòng nhập ngày ghi nhận!");
				}
				
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
				}
				else {
					//System.out.print("1");
					model.addAttribute("message", "Bạn đã nhập đúng!");
					Integer check = this.addSoTheoDoi(soTheoDoi);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Thêm sổ theo dõi thành công");
					} else {
						model.addAttribute("message", "Thêm sổ theo dõi thất bại");
					}
				}
		
		
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhVien", sinhVien);
		
		
		
		
//		try {
//			Integer check2 =this.ketthuchopdong(maSV);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
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

		return "ktx/sotheodoi";
	}
	@RequestMapping(value = "sotheodoi/delete/{id_NV}/{makl}/{maSV}")
	public String deleteBienBan(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV, @PathVariable("makl") String makl ) {
		
		SoTheoDoi bienBan = this.getSoTheoDoibymakl(makl);
		
		Integer check = this.deleteBienBan(bienBan);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa thành công!");
		} else {
			model.addAttribute("message", "Xóa thất bại!");
		}
		
//		try {
//			Integer check2 =this.ketthuchopdong(maSV);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		e.printStackTrace();
//		}
		
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
			List<SoTheoDoi> listSoTheoDoi = this.getSoTheoDoisbyMASV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("soTheoDois", listSoTheoDoi);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SoTheoDoi> listSoTheoDoi = this.getSoTheoDoisbyMASV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("soTheoDois", listSoTheoDoi);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show1", "show");
		return "ktx/sotheodoi";
	}
	
	@RequestMapping(value = "sotheodoi/{id_NV}/{maSV}.htm", params = "lnkDel")
	public String chamDutHopDong(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maSV") String maSV) {

		SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhVien", sinhVien);

//		Phong listPhong = this.getPhongByIdPhong(idPhong);
//		model.addAttribute("phongs", listPhong);
		
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		try {
			Integer check2 =this.ketthuchopdong(maSV);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<SoTheoDoi> listSoTheoDoi = this.getSoTheoDoisbyMASV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("soTheoDois", listSoTheoDoi);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<SoTheoDoi> listSoTheoDoi = this.getSoTheoDoisbyMASV(maSV);
			List<SinhVien> listSinhVien = this.getsinhviens();
			model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("soTheoDois", listSoTheoDoi);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show", "showFormQues");
		return "ktx/sotheodoi";
	}
	
	public Integer addSoTheoDoi(SoTheoDoi soTheoDoi) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(soTheoDoi);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	//Kiểm tra số biên bản của một sinh viên nếu trên 3 biên bản thì kết thúc ngay lập tức
	public Integer ketthuchopdong(String maSV) throws Exception {
		Session session = factory.getCurrentSession();
		String hql = "FROM SoTheoDoi WHERE sinhVien.maSV='"+maSV+"'";
		Query query = session.createQuery(hql);
		SinhVien sv = this.getSinhVienByMaSV(maSV);
		List<SoTheoDoi> list = query.list();
		if(list.size()>=3) {
			List<HopDong> listHD = sv.getHopDongs().stream().toList();
			for(HopDong hd:listHD) {
			
				//System.out.print(hd.getNgayKetThuc());
				Date date1 =  new SimpleDateFormat("yyyy-MM-dd").parse(hd.getNgayKetThuc());
				ZonedDateTime zonedDateTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
				Date currentDate = Date.from(zonedDateTime.toInstant());
				if(date1.after(currentDate)) {
					Date  nl = new SimpleDateFormat("yyyy-MM-dd").parse(hd.getNgayBatDau());
					Calendar calendar= Calendar.getInstance();
					/*
					 * calendar.setTime(nl); calendar.add(Calendar.DATE, 2); nl =
					 * calendar.getTime();
					 */
					System.out.print(hd.getNgayBatDau());
					System.out.print(hd.getNgayKetThuc());
					System.out.print(new SimpleDateFormat("yyyy-MM-dd").format(nl));
					Date date2;
					if(nl.after(currentDate)) {
						calendar.setTime(nl);
						calendar.add(Calendar.DATE, 2);
						nl = calendar.getTime();
					}
					else {
						calendar.setTime(currentDate);
						calendar.add(Calendar.DATE, 2);
						nl=calendar.getTime();
						
						
					}
					date2 = nl;
					//Date date2=nl;
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String sdate2 = dateFormat.format(date2);
					hd.setNgayKetThuc(new SimpleDateFormat("yyyy-MM-dd").format(nl));
					//Integer checkhdInteger = this.updateHopDong(hd);
				}
			}
			return 1;
		}
		else {
			return 0;
		}
	}
	public Integer updateHopDong(HopDong sinhVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(sinhVien);
			t.commit();

		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			// TODO: handle finally clause
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
	
	public Phong getPhongByIdPhong(String idPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong WHERE idPhong= :idPhong";
		Query query = session.createQuery(hql);
		query.setParameter("idPhong", idPhong);

		Phong phong = (Phong) query.list().get(0);
		return phong;
	}
	
	public SoTheoDoi getSoTheoDoibymakl(String makl) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SoTheoDoi WHERE makl='"+makl+"'";
		Query query = session.createQuery(hql);
		

		SoTheoDoi soTheoDoi = (SoTheoDoi) query.list().get(0);
		return soTheoDoi;
	}
	
	public Integer deleteBienBan(SoTheoDoi soTheoDoi) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(soTheoDoi);
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
}
