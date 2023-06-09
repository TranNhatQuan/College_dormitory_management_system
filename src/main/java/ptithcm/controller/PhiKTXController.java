package ptithcm.controller;

import java.util.ArrayList;
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
import ptithcm.entity.PhiKTX;
import ptithcm.entity.Phong;
import ptithcm.entity.SinhVien;

@Transactional
@Controller
@RequestMapping("/ktx/")
public class PhiKTXController {

	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "phiktx/{id_NV}.htm")
	public String showPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("hopdong") Phong phong) {
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		List<HopDong> listHopDong = this.getHopDongs();
		model.addAttribute("hopDongs", listHopDong);
		
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
		
			model.addAttribute("phanQuyen", "admin");
		} else {
			
			model.addAttribute("phanQuyen", "nhanvien");

		}
		
		return "ktx/phiktx";
	}	
	
	public PhiKTX getphiKTXbymaHopDong(String maHopDong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhiKTX WHERE maHopDong ='"+maHopDong+"'";
		Query query = session.createQuery(hql);
		PhiKTX phiKTX = (PhiKTX) query.list().get(0);
		return phiKTX;
	}
	
	
	@RequestMapping(value = "phiktx/{id_NV}/{maHopDong}.htm", params = "lnkAdd")
	public String showAddPhiKTX(ModelMap model, @PathVariable("id_NV") String maNV,
		 @PathVariable("maHopDong") String maHopDong, @ModelAttribute("phiktx") PhiKTX phiktx) {
		
		HopDong hopDong = this.getHopDongsByMaHopDong(maHopDong);
		model.addAttribute("hopDong", hopDong);
		
//		Phong listPhong = this.getPhongByIdPhong(idPhong);
//		model.addAttribute("phongs", listPhong);
				
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		List<HopDong> listHopDong = this.getHopDongs();
		model.addAttribute("hopDongs", listHopDong);
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			
			model.addAttribute("phanQuyen", "admin");
		} else {
			
			model.addAttribute("phanQuyen", "nhanvien");

		}
		model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show", "showFormAdd");
		return "ktx/phiktx";
	}
	
	
	
	@RequestMapping(value = "phiktx/action/{id_NV}/{maHopDong}.htm", params = "btnAdd")
	public String addPhiKTX(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maHopDong") String maHopDong,@ModelAttribute("phiktx") PhiKTX phiktx, BindingResult erros) {
		
		
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		HopDong hopDong = this.getHopDongsByMaHopDong(maHopDong);
		model.addAttribute("hopDong", hopDong);	
		
		//Bắt lỗi
				//Ngày thu
				if(phiktx.getNgayThu().trim().length()==0) {
					erros.rejectValue("ngayThu","PhiKTX", "Ngày thu không được để trống!");
				}
				
				
				
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
				}
				else {
					//System.out.print("1");
					model.addAttribute("message", "Bạn đã nhập đúng!");
					Integer check = this.addPhiKTX(phiktx);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Thêm biên lai thành công");
					} else {
						model.addAttribute("message", "Thêm sổ biên lai thất bại");
					}
				}
		
		
		
		List<HopDong> listHopDong = this.getHopDongs();
		model.addAttribute("hopDongs", listHopDong);
		
		model.addAttribute("btnAction", "btnAdd");
		
		model.addAttribute("show", "showFormAdd");

		return "ktx/phiktx";
	}
	
	
	@RequestMapping(value = "phiktx/{id_NV}/{maHopDong}.htm", params = "lnkDel")
	public String showDelPhiKTX(ModelMap model, @PathVariable("id_NV") String maNV,
			 @PathVariable("maHopDong") String maHopDong) {

		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		List<HopDong> listHopDong = this.getHopDongs();
		model.addAttribute("hopDongs", listHopDong);
		
		PhiKTX phiktx = this.getPhiKTXsByMaHopDong(maHopDong);
		model.addAttribute("phiktx", phiktx);	

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			
			model.addAttribute("phanQuyen", "admin");
		} else {
			
			model.addAttribute("phanQuyen", "nhanvien");

		}
		model.addAttribute("btnAction", "btnDel");

		model.addAttribute("show", "showFormDel");
		return "ktx/phiktx";
	}
	
	@RequestMapping(value = "phiktx/action/{id_NV}/{maHopDong}.htm", params = "btnDel")
	public String delPhiKTX(ModelMap model, @PathVariable("id_NV") String maNV,@PathVariable("maHopDong") String maHopDong,@ModelAttribute("phiktx") PhiKTX phiktx) {
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		

		Integer check = this.delPhiKTX(phiktx);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa biên lai thành công!");
		} else {
			model.addAttribute("message", "Xóa biên lai thất bại!");
		}
		
		model.addAttribute("btnAction", "btnAdd");
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			
			model.addAttribute("phanQuyen", "admin");
		} else {
			
			model.addAttribute("phanQuyen", "nhanvien");

		}
		
		List<HopDong> listHopDong = this.getHopDongs();
		model.addAttribute("hopDongs", listHopDong);
		
		model.addAttribute("show", "showFormAdd");

		return "ktx/phiktx";
	}
	
	public Integer addPhiKTX(PhiKTX phiKTX) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phiKTX);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	public Integer delPhiKTX(PhiKTX phiKTX) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(phiKTX);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	@RequestMapping(value = "phiktx/{id_NV}/{maHopDong}.htm", params = "lnkShow")
	public String showBienLai(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("maHopDong") String maHopDong) {

		//SinhVien sinhVien = this.getSinhVienByMaSV(maSV);
		//model.addAttribute("sinhVien", sinhVien);

//		Phong listPhong = this.getPhongByIdPhong(idPhong);
//		model.addAttribute("phongs", listPhong);
		//Phong phong = this.getPhongByIdPhong(id_Phong);
		//model.addAttribute("phong", phong);	
		PhiKTX bienLai = this.getphiKTXbymaHopDong(maHopDong);
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			//List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("bienLai", bienLai);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "admin");
		} else {
			//List<HoaDonDien> listHoaDonDiens = this.getHoaDonDiens(id_Phong);
			model.addAttribute("bienLai", bienLai);
			//List<SinhVien> listSinhVien = this.getsinhviens();
			//model.addAttribute("sinhViens", listSinhVien);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		List<HopDong> listHopDong = this.getHopDongs();
		model.addAttribute("hopDongs", listHopDong);
		//model.addAttribute("btnAction", "btnAdd");

		model.addAttribute("show1", "show");
		return "ktx/phiktx";
	}
	
	public NhanVien getNhanVienByID(String maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien n WHERE n.maNV= :maNV";
		Query quey = session.createQuery(hql);
		quey.setParameter("maNV", maNV);

		NhanVien nhanVien = (NhanVien) quey.list().get(0);
		return nhanVien;
	}
	
	public List<HopDong> getHopDongs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong";
		Query query = session.createQuery(hql);

		List<HopDong> list = query.list();
		return list;
	}
	
	@ModelAttribute("phiKTXs")
	public List<PhiKTX> getphiKTXs(){
		Session session =  factory.getCurrentSession();
		String hqlString="FROM HopDong";
		Query query = session.createQuery(hqlString);
		List<HopDong> listhd =  query.list();
		List<PhiKTX> list = new ArrayList<PhiKTX>();
		for(HopDong hd:listhd) {
			session =  factory.openSession();
			hqlString="FROM PhiKTX WHERE maHopDong = '"+hd.getMaHopDong()+"'";
			Query query2 = session.createQuery(hqlString);
			if(query2.list().size()>0) {
				PhiKTX phi = (PhiKTX) query2.list().get(0);
				list.add(phi);
			}
			else {
				list.add(null);
			}
		}
		for(PhiKTX p:list) {
			System.out.println(p);
		}
		return list;
	}
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
	public List<HopDong> getHopDongsByMaNV(String maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong WHERE nhanVien.maNV= :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);	
		List<HopDong> list = query.list();
		return list;
	}
	
	public HopDong getHopDongsByMaHopDong(String maHopDong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong WHERE maHopDong= :maHopDong";
		Query query = session.createQuery(hql);
		query.setParameter("maHopDong", maHopDong);

		HopDong hopDong = (HopDong) query.list().get(0);
		return hopDong;
	}
	
	public PhiKTX getPhiKTXsByMaHopDong(String maHopDong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhiKTX WHERE maHopDong= :maHopDong";
		Query query = session.createQuery(hql);
		query.setParameter("maHopDong", maHopDong);

		PhiKTX phiKTX = (PhiKTX) query.list().get(0);
		return phiKTX;
	}
}
