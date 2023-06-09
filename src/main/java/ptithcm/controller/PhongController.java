package ptithcm.controller;

import java.util.List;

import javax.management.relation.Role;
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
import ptithcm.entity.LoaiPhong;
import ptithcm.entity.NhanVien;
import ptithcm.entity.Phong;
import ptithcm.entity.SinhVien;
import ptithcm.entity.TaiKhoan;

@Transactional
@Controller
@RequestMapping("/user/")
public class PhongController {
	@Autowired
	SessionFactory factory;

	
	@RequestMapping(value = "phong/{id_NV}.htm")
	public String showPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("phong") Phong phong) {
		model.addAttribute("message", "Thêm phòng");
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
		return "ktx/phong";
	}
	
	@RequestMapping(value = "loaiphong/{id_NV}.htm")
	public String showLoaiPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("loaiphong") LoaiPhong loaiphong) {
		model.addAttribute("message", "Thêm loại phòng");
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<LoaiPhong> listlPhong= this.getloaiPhongs();
			model.addAttribute("lphongs", listlPhong);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<LoaiPhong> listlPhong= this.getloaiPhongs();
			model.addAttribute("lphongs", listlPhong);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//
		model.addAttribute("btnAction", "btnAdd");
		return "ktx/loaiphong";
	}
	
	@RequestMapping(value = "svtrongphong/{id_NV}/{idPhong}.htm", params = "lnkShow")
	public String showSVTrongPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@PathVariable("idPhong") String idPhong) {

		Phong phong = this.getPhongByMaPhong(idPhong);
		model.addAttribute("phong", phong);
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			
			model.addAttribute("phanQuyen", "admin");
		} else {
		
			model.addAttribute("phanQuyen", "nhanvien");

		}
		
			List<HopDong> listHD= this.getHopDongsbyidPhong(idPhong);
			model.addAttribute("hopdongs", listHD);
			
			
		//model.addAttribute("btnAction", "btnAdd");
			List<Phong> listPhong= this.getPhongs();
			model.addAttribute("phongs", listPhong);
		
		return "ktx/sinhvientrongphong";
	}
	
	@RequestMapping(value = "loaiphong/action/{id_NV}.htm", params = "btnAdd")
	public String addLoaiPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("loaiphong") LoaiPhong loaiphong, BindingResult erros) {
		
		//Bắt lỗi
		
		//Mã loại phòng
		if(loaiphong.getMaLoaiPhong().trim().length()==0) {
			erros.rejectValue("maLoaiPhong","LoaiPhong", "Vui lòng nhập mã loại phòng!");
		}else {
			if(loaiphong.getMaLoaiPhong().trim().length()>=10) {
				erros.rejectValue("maLoaiPhong","LoaiPhong", "Mã loại phòng không được dài hơn 10 kí tự!");
			}
		}
		//Tên loại phòng
		if(loaiphong.getTenLoaiPhong().trim().length()==0) {
			erros.rejectValue("tenLoaiPhong","LoaiPhong", "Vui lòng nhập tên loại phòng!");
		}else {
			if(loaiphong.getTenLoaiPhong().trim().length()>=50) {
				erros.rejectValue("tenLoaiPhong","LoaiPhong", "Tên loại phòng không được dài hơn 50 kí tự!");
			}
		}
		//Giá
		if(loaiphong.getGia()==null) {
			erros.rejectValue("gia","LoaiPhong", "Vui lòng nhập giá phòng!");
		}else {
			if (loaiphong.getGia()<1000) {
				erros.rejectValue("gia","LoaiPhong", "Giá phòng không thể nhỏ hơn 1000 đồng!");
			}
		}
		

		
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.addLoaiPhong(loaiphong);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Thêm loại phòng thành công");
			} else {
				model.addAttribute("message", "Thêm loại phòng thất bại");
			}
		}
		
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		
		model.addAttribute("btnAction", "btnAdd");

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			List<LoaiPhong> listlPhong= this.getloaiPhongs();
			model.addAttribute("lphongs", listlPhong);
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<LoaiPhong> listlPhong= this.getloaiPhongs();
			model.addAttribute("lphongs", listlPhong);
			model.addAttribute("phanQuyen", "nhanvien");

		}

		return "ktx/loaiphong";
	}
	
	@RequestMapping(value = "loaiphong/{id_NV}/{maLoaiPhong}.htm", params = "lnkEdit")
	public String editLoaiPhong(ModelMap model, @PathVariable("id_NV") String maNV, @PathVariable("maLoaiPhong") String maLoaiPhong) {
		
		model.addAttribute("message", "Sửa thông tin loại phòng");
		
		LoaiPhong chooseLP = this.getLoaiPhongbyMaPhong(maLoaiPhong);
		model.addAttribute("loaiphong", chooseLP);

		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
		}

		List<LoaiPhong> listLPhong= this.getloaiPhongs();
		model.addAttribute("lphongs", listLPhong);

		model.addAttribute("btnAction", "btnUpdate");

		return "ktx/loaiphong";
	}

	@RequestMapping(value = "loaiphong/action/{id_NV}.htm", params = "btnUpdate")
	public String updateLoaiPhong(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("loaiphong") LoaiPhong loaiphong, BindingResult erros) {
		
		//Bắt lỗi
		
				//Mã phòng
		if(loaiphong.getMaLoaiPhong().trim().length()==0) {
			erros.rejectValue("maLoaiPhong","LoaiPhong", "Vui lòng nhập mã loại phòng!");
		}else {
			if(loaiphong.getMaLoaiPhong().trim().length()>=10) {
				erros.rejectValue("maLoaiPhong","LoaiPhong", "Mã loại phòng không được dài hơn 10 kí tự!");
			}
		}
		//Tên loại phòng
		if(loaiphong.getTenLoaiPhong().trim().length()==0) {
			erros.rejectValue("tenLoaiPhong","LoaiPhong", "Vui lòng nhập tên loại phòng!");
		}else {
			if(loaiphong.getTenLoaiPhong().trim().length()>=50) {
				erros.rejectValue("tenLoaiPhong","LoaiPhong", "Tên loại phòng không được dài hơn 50 kí tự!");
			}
		}
		//Giá
		if(loaiphong.getGia()==null) {
			erros.rejectValue("gia","LoaiPhong", "Vui lòng nhập giá phòng!");
		}else {
			if (loaiphong.getGia()<1000) {
				erros.rejectValue("gia","LoaiPhong", "Giá phòng không thể nhỏ hơn 1000 đồng!");
			}
		}
		

		
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.updateLoaiPhong(loaiphong);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Cập nhật loại phòng thành công, chuyển về form nhập loại phòng!");
				model.addAttribute("btnAction", "btnAdd");
			} else {
				model.addAttribute("message", "Cập nhật loại phòng thất bại!");
				model.addAttribute("btnAction", "btnUpdate");
			}
		}		
		/*Integer check = this.updatePhong(phong);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cáº­p nháº­t phÃ²ng thÃ nh cÃ´ng!");
		} else {
			model.addAttribute("message", "Cáº­p nháº­t phÃ²ng tháº¥t báº¡i!");
		}	*/	
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
		}

		List<LoaiPhong> listLPhong= this.getloaiPhongs();
		model.addAttribute("lphongs", listLPhong);

		

		return "ktx/loaiphong";
	}
	
	@RequestMapping(value = "loaiphong/{id_NV}/{maLoaiPhong}.htm", params = "lnkDel")
	public String delLoaiPhong(ModelMap model, @PathVariable("id_NV") String id_NV, @PathVariable("maLoaiPhong") String maLoaiPhong) {
		
		model.addAttribute("message", "Xóa loại phòng");
		LoaiPhong chooselP = this.getLoaiPhongbyMaPhong(maLoaiPhong);
		model.addAttribute("loaiphong", chooselP);
		
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		model.addAttribute("btnAction", "btnDelete");

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		}
		List<LoaiPhong> listLPhong= this.getloaiPhongs();
		model.addAttribute("lphongs", listLPhong);
		return "ktx/loaiphong";	
		}
	
	@RequestMapping(value = "loaiphong/action/{id_NV}.htm", params = "btnDelete")
	public String deleteLoaiPhong(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("loaiphong") LoaiPhong loaiphong, BindingResult erros) {
		
		Integer check = this.deleteLoaiPhong(loaiphong);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa loại phòng thành công, chuyển về form nhập loại phòng!");
			
		} else {
			model.addAttribute("message", "Xóa loại phòng thất bại, chuyển về form nhập loại phòng!");
			
		}

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		model.addAttribute("btnAction", "btnAdd");

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		}
		List<LoaiPhong> listLPhong= this.getloaiPhongs();
		model.addAttribute("lphongs", listLPhong);
		return "ktx/loaiphong";
	}
	
	@RequestMapping(value = "phong/action/{id_NV}.htm", params = "btnAdd")
	public String addPhong(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("phong") Phong phong, BindingResult erros) {
		
		//Bắt lỗi
		model.addAttribute("message", "Sửa thông tin phòng");
		//Mã phòng
		if(phong.getIdPhong().trim().length()==0) {
			erros.rejectValue("idPhong","Phong", "Vui lòng nhập mã phòng!");
		}else {
			if(phong.getIdPhong().trim().length()>=10) {
				erros.rejectValue("idPhong","Phong", "Mã phòng không được dài hơn 10 kí tự!");
			}
		}
		//Số sinh viên tối đa
		if(phong.getSoSV()==null) {
			erros.rejectValue("soSV","Phong", "Vui lòng nhập số sinh viên tối đa!");
		}else {
			if (phong.getSoSV()<0||phong.getSoSV()>30) {
				erros.rejectValue("soSV","Phong", "Số sinh viên tối đa không thể nhỏ hơn 0 hoặc lớn hơn 30!");
			}
		}
		

		
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.addPhong(phong);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Thêm phòng thành công");
			} else {
				model.addAttribute("message", "Thêm phòng thất bại");
			}
		}
		
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		
		
		model.addAttribute("btnAction", "btnAdd");

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

		return "ktx/phong";
	}
	
	@RequestMapping(value = "phong/{id_NV}/{idPhong}.htm", params = "lnkEdit")
	public String editPhong(ModelMap model, @PathVariable("id_NV") String maNV, @PathVariable("idPhong") String idPhong) {
		
		model.addAttribute("message", "Sửa thông tin phòng");
		
		Phong chooseP = this.getPhongByMaPhong(idPhong);
		model.addAttribute("phong", chooseP);

		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
		}

		List<Phong> listPhong= this.getPhongs();
		model.addAttribute("phongs", listPhong);

		model.addAttribute("btnAction", "btnUpdate");

		return "ktx/phong";
	}

	@RequestMapping(value = "phong/action/{id_NV}.htm", params = "btnUpdate")
	public String updatePhong(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("phong") Phong phong, BindingResult erros) {
		
		//Bắt lỗi
		
				//Mã phòng
				if(phong.getIdPhong().trim().length()==0) {
					erros.rejectValue("idPhong","Phong", "Vui lòng nhập mã phòng!");
				}else {
					if(phong.getIdPhong().trim().length()>=10) {
						erros.rejectValue("idPhong","Phong", "Mã phòng không được dài hơn 10 kí tự!");
					}
				}
				//Số sinh viên tối đa
				if(phong.getSoSV()==null) {
					erros.rejectValue("soSV","Phong", "Vui lòng nhập số sinh viên tối đa!");
				}else {
					if (phong.getSoSV()<0||phong.getSoSV()>30) {
						erros.rejectValue("soSV","Phong", "Số sinh viên tối đa không thể nhỏ hơn 0 hoặc lớn hơn 30!");
					}
				}
				
	
				
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
				}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.updatePhong(phong);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Sửa phòng thành công");
			} else {
				model.addAttribute("message", "Sửa phòng thất bại");
			}
		}		
	
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
		}

		List<Phong> listPhong= this.getPhongs();
		model.addAttribute("phongs", listPhong);

		model.addAttribute("btnAction", "btnUpdate");

		return "ktx/phong";
	}
	
	
	@RequestMapping(value = "phong/{id_NV}/{idPhong}.htm", params = "lnkDel")
	public String delAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@PathVariable("idPhong") String idPhong ,@ModelAttribute("phong") Phong phong) {
		
		model.addAttribute("message", "Xóa phòng");
		Phong chooseP = this.getPhongByMaPhong(idPhong);
		model.addAttribute("phong", chooseP);
		
		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		model.addAttribute("btnAction", "btnDelete");

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		}
		List<Phong> listPhong= this.getPhongs();
		model.addAttribute("phongs", listPhong);
		return "ktx/phong";	
		}
	
	@RequestMapping(value = "phong/action/{id_NV}.htm", params = "btnDelete")
	public String deletePhong(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("phong") Phong phong) {
		
		Integer check = this.deletePhong(phong);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa phòng thành công!");
		} else {
			model.addAttribute("message", "Xóa phòng thất bại!");
		}

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		model.addAttribute("btnAction", "btnAdd");

		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
		}
		List<Phong> listPhong= this.getPhongs();
		model.addAttribute("phongs", listPhong);
		return "ktx/phong";
	}
	
	
	public Integer deletePhong(Phong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(phong);
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
	
	public Integer deleteLoaiPhong(LoaiPhong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(phong);
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
	
	public Integer updatePhong(Phong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(phong);
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
	public Integer updateLoaiPhong(LoaiPhong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(phong);
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
	@ModelAttribute("loaiPhongs")
	public List<LoaiPhong> getPhanQuyens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiPhong";
		Query query = session.createQuery(hql);
		List<LoaiPhong> list = query.list();
		return list;
	}
	
	public Integer addPhong(Phong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	public Integer addLoaiPhong(LoaiPhong phong) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phong);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	public List<Phong> getPhongs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong";
		Query query = session.createQuery(hql);

		List<Phong> list = query.list();
		return list;
	}
	
	public List<LoaiPhong> getloaiPhongs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiPhong";
		Query query = session.createQuery(hql);

		List<LoaiPhong> list = query.list();
		return list;
	}
	
	public Phong getPhongByMaPhong(String idPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phong WHERE idPhong= :idPhong";
		Query query = session.createQuery(hql);
		query.setParameter("idPhong", idPhong);

		Phong phong = (Phong) query.list().get(0);
		return phong;
	}
	
	public LoaiPhong getLoaiPhongbyMaPhong(String maLoaiPhong) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiPhong WHERE maLoaiPhong='"+maLoaiPhong+"'";
		Query query = session.createQuery(hql);
		

		LoaiPhong hoaDonDien = (LoaiPhong) query.list().get(0);
		return hoaDonDien;
	}
	
	public List<HopDong> getHopDongsbyidPhong(String idPhong){
		Session session = factory.getCurrentSession();
		String hql = "FROM HopDong WHERE phong.idPhong='"+idPhong+"'";
		Query query = session.createQuery(hql);

		List<HopDong> list = query.list();
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
}
