package ptithcm.controller;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.entity.TaiKhoan;
import ptithcm.bean.UploadFile;
import ptithcm.entity.NhanVien;


@Transactional
@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	SessionFactory factory;

	@Autowired
	UploadFile baseUpLoadFilePhoto;
	
	@RequestMapping(value = "index/{id_NV}.htm", method = RequestMethod.GET)
	public String indexGET(ModelMap model, @PathVariable("id_NV") String maNV) {
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		TaiKhoan dangNhap = this.getDangNhapByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", dangNhap.getNhanVien().getHoTen());
		model.addAttribute("user", dangNhap);
		
		String s ="QL";
		if (dangNhap.getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(dangNhap.getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(dangNhap.getRole().getTenRole());
		}
		return "user/index";
	}

	@RequestMapping(value = "index/{id_NV}.htm", method = RequestMethod.POST)
	public String indexPOST(ModelMap model, @PathVariable("id_NV") String maNV) {
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		TaiKhoan dangNhap = this.getDangNhapByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", dangNhap.getNhanVien().getHoTen());
		model.addAttribute("user", dangNhap);
		String s ="QL";
		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(dangNhap.getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(dangNhap.getRole().getTenRole());
		}
		return "user/index";
	}

	@RequestMapping(value = "changepassword/{id_NV}.htm", method = RequestMethod.GET)
	public String changepassGet(ModelMap model, @PathVariable("id_NV") String maNV) {
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		TaiKhoan dangNhap = this.getDangNhapByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", dangNhap.getNhanVien().getHoTen());
		model.addAttribute("user", dangNhap);
		
		String s ="QL";
		if (dangNhap.getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(dangNhap.getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(dangNhap.getRole().getTenRole());
		}
		return "user/changepassword";
	}

	@RequestMapping(value = "changepass/{id_NV}.htm", method = RequestMethod.POST)
	public String changepassPost(ModelMap model, @PathVariable("id_NV") String maNV,
			@RequestParam("currentpw") String currentpw, @RequestParam("newpw") String newpw,
			@RequestParam("checkpw") String checkpw) {
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		TaiKhoan dangNhapCu = this.getDangNhapByID(maNV);
		model.addAttribute("check", 0); // kiểm tra thông báo
		String s ="QL";
		if (currentpw.equals(dangNhapCu.getMatKhau()) == false) {
			model.addAttribute("message1", "Mật khẩu hiện tại không đúng!");
			model.addAttribute("id_NV", maNV);
			model.addAttribute("hoTen", dangNhapCu.getNhanVien().getHoTen());
			model.addAttribute("user", dangNhapCu);
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhapCu.getRole().getMaRole().equals(s)) 
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhapCu.getRole().getTenRole());
			} 
			else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhapCu.getRole().getTenRole());
			}

			return "user/changepassword";
		}
		if (checkpw.equals(newpw) == false) {
			model.addAttribute("message2", "Xác nhận mật khẩu không trùng nhau! Vui lòng thử lại!!");
			model.addAttribute("id_NV", maNV);
			model.addAttribute("hoTen", dangNhapCu.getNhanVien().getHoTen());
			model.addAttribute("user", dangNhapCu);
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhapCu.getRole().getMaRole().equals(s)) 
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhapCu.getRole().getTenRole());
			} 
			else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhapCu.getRole().getTenRole());
			}
			return "user/changepassword";
		} else {
			model.addAttribute("check", 1); // kiểm tra thông báo

			// Đổi mật khẩu mới rồi update lại
			TaiKhoan dangNhapmoi = dangNhapCu;
			dangNhapmoi.setMatKhau(newpw);
			model.addAttribute("check", 1); // kiểm tra thông báo
			// Trả về họ tên và id_NV cho left-sidenav và top-bar
			model.addAttribute("id_NV", maNV);
			model.addAttribute("hoTen", dangNhapmoi.getNhanVien().getHoTen());
			model.addAttribute("user", dangNhapmoi);
			model.addAttribute("notify", "Thay đổi mật khẩu thành công!");
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhapmoi.getRole().getMaRole().equals(s)) 
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhapmoi.getRole().getTenRole());
			} 
			else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhapmoi.getRole().getTenRole());
			}
			return "user/changepassword";
		}
	}

	@RequestMapping(value = "setting/{id_NV}.htm", method = RequestMethod.GET)
	public String settingUser(ModelMap model, @PathVariable("id_NV") String id) {
		NhanVien nhanVien = this.getNhanVienByID(id);
		TaiKhoan dangNhap = this.getDangNhapByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		model.addAttribute("user", dangNhap);
		// Nếu admin thì cho chọn chi nhánh
		

		// Kiểm tra đăng nhập theo quyền nào
		String s ="QL";
		if (dangNhap.getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(dangNhap.getRole().getTenRole());
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(dangNhap.getRole().getTenRole());
		}
		

		model.addAttribute("NhanVien", nhanVien);
		return "user/editstaff";
	}

	@RequestMapping(value = "edit", params = "btnEdit", method = RequestMethod.POST)
	public String editInfo(ModelMap model, @ModelAttribute("NhanVien") NhanVien nv, BindingResult erros) {
		//Integer check = this.updateNhanVien(nv);
		
		TaiKhoan dangNhap = this.getDangNhapByID(nv.getMaNV());
		if(nv.getHoTen().trim().length()==0) {
			erros.rejectValue("hoTen","NhanVien", "Vui lòng nhập họ tên!");
		}else {
			if(nv.getHoTen().trim().length()>=50) {
				erros.rejectValue("hoTen","NhanVien", "Họ tên không được dài hơn 50 ký tự!");
			}
		}
		//MÃ£ nhÃ¢n viÃªn
		if(nv.getMaNV().trim().length()==0) {
			erros.rejectValue("maNV","NhanVien", "Vui lòng nhập mã nhân viên!");
		}else {
			if(nv.getMaNV().trim().length()>=20) {
				erros.rejectValue("maNV","NhanVien", "Mã nhânn viên không được dài hơn 20 kí tự!");
			}
		}
		//NgÃ y sinh
		if(nv.getNgaySinh().trim().length()==0) {
			erros.rejectValue("ngaySinh","NhanVien", "Vui lòng nhập ngày sinh!");
		}
		//Ä�á»‹a chá»‰
		if(nv.getDiaChi().trim().length()==0) {
			erros.rejectValue("diaChi","NhanVien", "Vui lòng nhập địa chỉ!");
		}else {
			if(nv.getDiaChi().trim().length()>=50) {
				erros.rejectValue("diaChi","NhanVien", "Địa chỉ không được dài hơn 50 ký tự!");
			}
		}
		//Sá»‘ Ä‘iá»‡n thoáº¡i
		if(nv.getSoDT().trim().length()==0) {
			erros.rejectValue("soDT","NhanVien", "Vui lòng nhập số điện thoại!");
		}else {
			if(!nv.getSoDT().trim().matches("[0-9]+[\\.]?[0-9]*")) {
				erros.rejectValue("soDT","NhanVien", "Số điện thoại phải là số!");
			}else {
				if(nv.getSoDT().trim().length()>=12) {
					erros.rejectValue("soDT","NhanVien", "Số điện thoại không được dà hơn 12 ký tự!");
				}
			}
		}
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.updateNhanVien(nv);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Cập nhật nhân viên thành công");
				
			} else {
				model.addAttribute("message", "Cập nhật nhân viên thất bại!");
				
			}
		}

		// Kiểm tra phân quyền và cho phép chọn chi nhánh
		String s ="QL";
		if (dangNhap.getRole().getMaRole().equals(s)) 
		{
			model.addAttribute("phanQuyen", "admin");
			
		} 
		else {
			model.addAttribute("phanQuyen", "nhanvien");
			
		}

		// truyền id cho các thông tin để xử lý profile và lock form
		model.addAttribute("id_NV", nv.getMaNV());
		// truyền tên cho top-bar
		model.addAttribute("hoTen", nv.getHoTen());
		return "user/editstaff";
	}
	
	
//	@RequestMapping(value = "changepassword/{id_NV}.htm", method = RequestMethod.GET)
//	public String changepassGet(ModelMap model, @PathVariable("id_NV") Integer id) {
//		DangNhap dangNhap = this.getDangNhapByID(id);
//		model.addAttribute("hoTen", dangNhap.getNhanVien().getHo() + " " + dangNhap.getNhanVien().getTen());
//		model.addAttribute("id_NV", id);
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (dangNhap.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/changepassword";
//	}
//
//	@RequestMapping(value = "changepass/{id_NV}.htm", method = RequestMethod.POST)
//	public String changepassPost(ModelMap model, @PathVariable("id_NV") Integer id,
//			@RequestParam("currentpw") String currentpw, @RequestParam("newpw") String newpw,
//			@RequestParam("checkpw") String checkpw) {
//		DangNhap dangNhapCu = this.getDangNhapByID(id);
//		model.addAttribute("check", 0); // kiÃ¡Â»Æ’m tra thÃƒÂ´ng bÃƒÂ¡o
//		if (currentpw.equals(dangNhapCu.getmaNVhau()) == false) {
//			model.addAttribute("message1", "MÃ¡ÂºÂ­t khÃ¡ÂºÂ©u hiÃ¡Â»â€¡n tÃ¡ÂºÂ¡i khÃƒÂ´ng Ã„â€˜ÃƒÂºng!");
//			model.addAttribute("id_NV", dangNhapCu.getNhanVien().getMaNV());
//			model.addAttribute("hoTen", dangNhapCu.getNhanVien().getHo() + " " + dangNhapCu.getNhanVien().getTen());
//			// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//			if (dangNhapCu.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//			{
//				model.addAttribute("phanQuyen", "admin");
//				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//			} else {
//				model.addAttribute("phanQuyen", "nhanvien");
//				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//			}
//
//			return "user/changepassword";
//		}
//		if (checkpw.equals(newpw) == false) {
//			model.addAttribute("message2", "XÃƒÂ¡c nhÃ¡ÂºÂ­n mÃ¡ÂºÂ­t khÃ¡ÂºÂ©u khÃƒÂ´ng trÃƒÂ¹ng nhau! Vui lÃƒÂ²ng thÃ¡Â»Â­ lÃ¡ÂºÂ¡i!!");
//			model.addAttribute("id_NV", dangNhapCu.getNhanVien().getMaNV());
//			model.addAttribute("hoTen", dangNhapCu.getNhanVien().getHo() + " " + dangNhapCu.getNhanVien().getTen());
//			// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//			if (dangNhapCu.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//			{
//				model.addAttribute("phanQuyen", "admin");
//				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//			} else {
//				model.addAttribute("phanQuyen", "nhanvien");
//				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//			}
//			return "user/changepassword";
//		} else {
//			model.addAttribute("check", 1); // kiÃ¡Â»Æ’m tra thÃƒÂ´ng bÃƒÂ¡o
//
//			// Ã„ï¿½Ã¡Â»â€¢i mÃ¡ÂºÂ­t khÃ¡ÂºÂ©u mÃ¡Â»â€ºi rÃ¡Â»â€œi update lÃ¡ÂºÂ¡i
//			DangNhap dangNhapmoi = dangNhapCu;
//			dangNhapmoi.setmaNVhau(newpw);
//			model.addAttribute("check", 1); // kiÃ¡Â»Æ’m tra thÃƒÂ´ng bÃƒÂ¡o
//			// TrÃ¡ÂºÂ£ vÃ¡Â»ï¿½ hÃ¡Â»ï¿½ tÃƒÂªn vÃƒÂ  id_NV cho left-sidenav vÃƒÂ  top-bar
//			model.addAttribute("id_NV", dangNhapmoi.getNhanVien().getMaNV());
//			model.addAttribute("hoTen", dangNhapmoi.getNhanVien().getHo() + " " + dangNhapmoi.getNhanVien().getTen());
//			model.addAttribute("notify", "Thay Ã„â€˜Ã¡Â»â€¢i mÃ¡ÂºÂ­t khÃ¡ÂºÂ©u thÃƒÂ nh cÃƒÂ´ng!");
//			// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//			if (dangNhapCu.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//			{
//				model.addAttribute("phanQuyen", "admin");
//				System.out.println(dangNhapmoi.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//			} else {
//				model.addAttribute("phanQuyen", "nhanvien");
//				System.out.println(dangNhapmoi.getNhanVien().getPhanQuyen().getTenPhanQuyen());
//			}
//			return "user/changepassword";
//		}
//	}
///

	@RequestMapping(value = "list/{id_NV}.htm")
	public String listUser(ModelMap model, @PathVariable("id_NV") String maNV, @ModelAttribute("user") NhanVien form) {
		
		model.addAttribute("message", "Thêm nhân viên.");
		
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		//
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
		return "user/listuser";
	}
///////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnAdd")
	public String addNhanVien(ModelMap model, @ModelAttribute("user") NhanVien newNhanVien,
			@PathVariable("id_NV") String maNV, BindingResult erros) {
		model.addAttribute("message", "Thêm nhân viên.");
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

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
		//Há»� tÃªn
		if(newNhanVien.getHoTen().trim().length()==0) {
			erros.rejectValue("hoTen","NhanVien", "Vui lòng nhập họ tên!");
		}else {
			if(newNhanVien.getHoTen().trim().length()>=50) {
				erros.rejectValue("hoTen","NhanVien", "Họ tên không được dài hơn 50 ký tự!");
			}
		}
		
		//NgÃ y sinh
		if(newNhanVien.getNgaySinh().trim().length()==0) {
			erros.rejectValue("ngaySinh","NhanVien", "Vui lòng nhập ngày sinh!");
		}
		//Ä�á»‹a chá»‰
		if(newNhanVien.getDiaChi().trim().length()==0) {
			erros.rejectValue("diaChi","NhanVien", "Vui lòng nhập địa chỉ!");
		}else {
			if(newNhanVien.getDiaChi().trim().length()>=50) {
				erros.rejectValue("diaChi","NhanVien", "Địa chỉ không được dài hơn 50 ký tự!");
			}
		}
		//Sá»‘ Ä‘iá»‡n thoáº¡i
		if(newNhanVien.getSoDT().trim().length()==0) {
			erros.rejectValue("soDT","NhanVien", "Vui lòng nhập số điện thoại!");
		}else {
			if(!newNhanVien.getSoDT().trim().matches("[0-9]+[\\.]?[0-9]*")) {
				erros.rejectValue("soDT","NhanVien", "Số điện thoại phải là số!");
			}else {
				if(newNhanVien.getSoDT().trim().length()<9 || newNhanVien.getSoDT().trim().length()>=12 ) {
					erros.rejectValue("soDT","NhanVien", "Số điện thoại không đúng định dạng!");
				}
			}
		}
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			newNhanVien.setMaNV(this.setmaNV());
			Integer check = this.addNhanVien(newNhanVien);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Thêm nhân viên thành công");
			} else {
				model.addAttribute("message", "Thêm nhân viên thất bại");
			}
		}
	
		//Integer check = this.addNhanVien(newNhanVien);
		//model.addAttribute("check", check);

		/*if (check != 0) {
			model.addAttribute("message", "ThÃªm nhÃ¢n viÃªn thÃ nh cÃ´ng");
		} else {
			model.addAttribute("message", "ThÃªm nhÃ¢n viÃªn tháº¥t báº¡i");
		}*/
		model.addAttribute("btnAction", "btnAdd");
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);
		return "user/listuser";
	}
///////////////////////////////////////////////////////////////////////

//	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnAdd")
//	public String addNhanVien(ModelMap model, @ModelAttribute("user") NhanVien newNhanVien,
//			@PathVariable("id_NV") String maNV) {
//
//		NhanVien nhanVien = this.getNhanVienByID(maNV);
//		model.addAttribute("id_NV", maNV);
//		model.addAttribute("hoTen", nhanVien.getHoTen());
//		
//		String s ="QL";
//		if (nhanVien.getTaiKhoan().getRole().getMaRole().equals(s)) 
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
//		} 
//		else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getTaiKhoan().getRole().getTenRole());
//		}
//
//		
//		
//		Integer check = this.addNhanVien(newNhanVien);
//		model.addAttribute("check", check);
//
//		if (check != 0) {
//			model.addAttribute("message", "ThÃªm nhÃ¢n viÃªn thÃ nh cÃ´ng");
//		} else {
//			model.addAttribute("message", "ThÃªm nhÃ¢n viÃªn tháº¥t báº¡i");
//		}
//
//		// chÃ¡ÂºÂ¡y lÃ¡ÂºÂ¡i list nhÃƒÂ¢n viÃƒÂªn Ã„â€˜Ã¡Â»Æ’ lÃƒÂ m mÃ¡Â»â€ºi
//		List<NhanVien> nhanViens = this.getNhanViens();
//		model.addAttribute("nhanViens", nhanViens);
//		return "user/listuser";
//	}

	@RequestMapping(value = "list/{id_NV}/{maNV}.htm", params = "lnkEdit")
	public String editNhanVien(ModelMap model, @PathVariable("maNV") String maNV, @PathVariable("id_NV") String id_NV) {
		
		model.addAttribute("message", "Sửa thông tin nhân viên");
		
		NhanVien chooseNV = this.getNhanVienByID(maNV);
		model.addAttribute("user", chooseNV);

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

		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		model.addAttribute("btnAction", "btnUpdate");

		return "user/listuser";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnUpdate")
	public String updateNhanVien(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("user") NhanVien nv, BindingResult erros) {
		
		//Báº¯t lá»—i
		//Há»� tÃªn
				if(nv.getHoTen().trim().length()==0) {
					erros.rejectValue("hoTen","NhanVien", "Vui lòng nhập họ tên!");
				}else {
					if(nv.getHoTen().trim().length()>=50) {
						erros.rejectValue("hoTen","NhanVien", "Họ tên không được dài hơn 50 ký tự!");
					}
				}
				//MÃ£ nhÃ¢n viÃªn
				if(nv.getMaNV().trim().length()==0) {
					erros.rejectValue("maNV","NhanVien", "Vui lòng nhập mã nhân viên!");
				}else {
					if(nv.getMaNV().trim().length()>=20) {
						erros.rejectValue("maNV","NhanVien", "Mã nhânn viên không được dài hơn 20 kí tự!");
					}
				}
				//NgÃ y sinh
				if(nv.getNgaySinh().trim().length()==0) {
					erros.rejectValue("ngaySinh","NhanVien", "Vui lòng nhập ngày sinh!");
				}
				//Ä�á»‹a chá»‰
				if(nv.getDiaChi().trim().length()==0) {
					erros.rejectValue("diaChi","NhanVien", "Vui lòng nhập địa chỉ!");
				}else {
					if(nv.getDiaChi().trim().length()>=50) {
						erros.rejectValue("diaChi","NhanVien", "Địa chỉ không được dài hơn 50 ký tự!");
					}
				}
				//Sá»‘ Ä‘iá»‡n thoáº¡i
				if(nv.getSoDT().trim().length()==0) {
					erros.rejectValue("soDT","NhanVien", "Vui lòng nhập số điện thoại!");
				}else {
					if(!nv.getSoDT().trim().matches("[0-9]+[\\.]?[0-9]*")) {
						erros.rejectValue("soDT","NhanVien", "Số điện thoại phải là số!");
					}else {
						if(nv.getSoDT().trim().length()<9 || nv.getSoDT().trim().length()>=12) {
							erros.rejectValue("soDT","NhanVien", "Số điện thoại không đúng định dạng!");
						}
					}
				}
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("check", "1");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
					model.addAttribute("btnAction", "btnUpdate");
				}
				else {
					//System.out.print("1");
					model.addAttribute("message", "Bạn đã nhập đúng!");
					
					Integer check = this.updateNhanVien(nv);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Cập nhật nhân viên thành công. Tiếp tục sửa");
						model.addAttribute("btnAction", "btnUpdate");
					} else {
						model.addAttribute("message", "Cập nhật nhân viên thất bại!");
						model.addAttribute("btnAction", "btnUpdate");
					}
				}
		/*Integer check = this.updateNhanVien(nv);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cáº­p nháº­t nhÃ¢n viÃªn thÃ nh cÃ´ng!");
		} else {
			model.addAttribute("message", "Cáº­p nháº­t nhÃ¢n viÃªn tháº¥t báº¡i!");
		}
		// LÃ¡ÂºÂ¥y dÃ¡Â»Â¯ liÃ¡Â»â€¡u cÃ¡Â»Â§a NhÃƒÂ¢n viÃƒÂªn Ã„â€˜Ã¡Â»Æ’ Ã„â€˜Ã¡ÂºÂ©y lÃƒÂªn thÃƒÂ´ng tin cÃ¡Â»Â§a left-sidenav vÃƒÂ  topbar
		*/
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

		// chÃ¡ÂºÂ¡y lÃ¡ÂºÂ¡i list nhÃƒÂ¢n viÃƒÂªn Ã„â€˜Ã¡Â»Æ’ lÃƒÂ m mÃ¡Â»â€ºi
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		// ChuyÃ¡Â»Æ’n vÃ¡Â»ï¿½ lÃ¡ÂºÂ¡i btnAdd Ã„â€˜Ã¡Â»Æ’ cÃƒÂ³ thÃ¡Â»Æ’ tÃ¡ÂºÂ¡o phiÃ¡ÂºÂ¿u tiÃ¡ÂºÂ¿p nÃ¡ÂºÂ¿u cÃƒÂ³
		

		return "user/listuser";
	}

	@RequestMapping(value = "list/{id_NV}/{maNV}.htm", params = "lnkDel")
	public String lnkDelNhanVien(ModelMap model,  @PathVariable("maNV") String maNV, @PathVariable("id_NV") String id_NV) {

		
		model.addAttribute("message", "Bạn có chắc muốn xóa nhân viên này");
		
		NhanVien chooseNV = this.getNhanVienByID(maNV);
		model.addAttribute("user", chooseNV);
		
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

		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		model.addAttribute("btnAction", "btnDelete");

		return "user/listuser";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnDelete")
	public String delNhanVien(ModelMap model, @PathVariable("id_NV") String id_NV, @ModelAttribute("user") NhanVien nv) {
		
		Integer check = this.deleteNhanVien(nv);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa nhân viên thành công!");
		} else {
			model.addAttribute("message", "Xóa nhân viên thất bại!");
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


		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		model.addAttribute("btnAction", "btnAdd");

		return "user/listuser";
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//Táº¡o tÃ i khoáº£n cho nhÃ¢n viÃªn
	@RequestMapping(value = "createaccount/{id_NV}.htm", method = RequestMethod.GET)
	public String showFormCreateAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("dangnhap") TaiKhoan taiKhoan) {
		
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		//
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
		
		return "user/createaccount";
	}
	
	@RequestMapping(value = "createaccount/{id_NV}/{maNV}.htm", params = "lnkAdd")
	public String showFormCreateAccount(ModelMap model, @PathVariable("id_NV") String id_NV,@PathVariable("maNV") String maNV,
			@ModelAttribute("dangnhap") TaiKhoan taiKhoan) {
		
		model.addAttribute("show", "showFormAdd");
		model.addAttribute("message", "Tạo tài khoản");
		
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		//
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
		
		return "user/createaccount";
	}

	
	@RequestMapping(value = "createaccount/action/{id_NV}.htm", params = "btnAdd")
	public String createAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("dangnhap") TaiKhoan dangNhap, BindingResult erros) {
		model.addAttribute("show", "showFormAdd");
		
		//Báº¯t lá»—i
		
				//TÃ i Khoáº£n
				if(dangNhap.getTaiKhoan().trim().length()==0) {
					erros.rejectValue("taiKhoan","TaiKhoan", "Vui lòng nhập tài khoản!");
				}else {
					if(dangNhap.getTaiKhoan().trim().length()>=20) {
						erros.rejectValue("taiKhoan","TaiKhoan", "Tài khoản không được dài hơn 20 ký tự!");
					}
				}
				//Máº­t kháº©u
				if(dangNhap.getMatKhau().trim().length()==0) {
					erros.rejectValue("matKhau","TaiKhoan", "Vui lòng nhập mật khẩu!");
				}else {
					if(dangNhap.getMatKhau().trim().length()>=50) {
						erros.rejectValue("matKhau","TaiKhoan", "Mật khẩu không được dài hơn 50 ký tự!");
					}
				}
				
				if(erros.hasErrors()) {
					//System.out.print("2");
					model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
				}
				else {
					//System.out.print("1");
					model.addAttribute("message", "Bạn đã nhập đúng!");
					Integer check = this.addDangNhap(dangNhap);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Thêm tài khoản thành công");
					} else {
						model.addAttribute("message", "Thêm tài khoản thất bại");
					}
				}
		
		/*Integer check = this.addDangNhap(dangNhap);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Táº¡o tÃ i khoáº£n thÃ nh cÃ´ng!");
		} else {
			model.addAttribute("message", "TÃ i khoáº£n Ä‘Ã£ tá»“n táº¡i vui lÃ²ng kiá»ƒm tra láº¡i");
		}
		*/
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		//
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
		
		return "user/createaccount";
	}

	@RequestMapping(value = "createaccount/{id_NV}/{edit_maNV}.htm", params = "lnkEdit")
	public String editAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@PathVariable("edit_maNV") String edit_maNV) {
		model.addAttribute("show", "showEditDel");
		model.addAttribute("message", "Sửa tài khoản");
		
		TaiKhoan chooseTK = this.getDangNhapByID(edit_maNV);
		model.addAttribute("dangnhap", chooseTK);
		
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		model.addAttribute("btnAction", "btnUpdate");

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
		
		return "user/createaccount";
	}

	@RequestMapping(value = "createaccount/action/{id_NV}.htm", params = "btnUpdate")
	public String updateAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("dangnhap") TaiKhoan dangNhap, BindingResult erros) {
		model.addAttribute("show", "showEditDel");
		
		//TÃ i Khoáº£n
		if(dangNhap.getTaiKhoan().trim().length()==0) {
			erros.rejectValue("taiKhoan","TaiKhoan", "Vui lòng nhập tài khoản!");
		}else {
			if(dangNhap.getTaiKhoan().trim().length()>=20) {
				erros.rejectValue("taiKhoan","TaiKhoan", "Tài khoản không được dài hơn 20 ký tự!");
			}
		}
		//Máº­t kháº©u
		if(dangNhap.getMatKhau().trim().length()==0) {
			erros.rejectValue("matKhau","TaiKhoan", "Vui lòng nhập mật khẩu!");
		}else {
			if(dangNhap.getMatKhau().trim().length()>=50) {
				erros.rejectValue("matKhau","TaiKhoan", "Mật khẩu không được dài hơn 50 ký tự!");
			}
		}
		
		if(erros.hasErrors()) {
			//System.out.print("2");
			model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
		}
		else {
			//System.out.print("1");
			model.addAttribute("message", "Bạn đã nhập đúng!");
			Integer check = this.updateDangNhap(dangNhap);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Sửa tài khoản thành công");
				
			} else {
				model.addAttribute("message", "Sửa tài khoản thất bại");
			}
		}
		/*Integer check = this.updateDangNhap(dangNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Sá»­a tÃ i khoáº£n thÃ nh cÃ´ng!");
		} else {
			model.addAttribute("message", "Sá»­a tÃ i khoáº£n tháº¥t báº¡i!");
		}*/
		
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id_NV);
		model.addAttribute("id_NV", id_NV);
		model.addAttribute("hoTen", nhanVien.getHoTen());

		//
		model.addAttribute("btnAction", "btnUpdate");

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
		
		return "user/createaccount";
	}
	
	@RequestMapping(value = "createaccount/{id_NV}/{edit_maNV}.htm", params = "lnkDel")
	public String delAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@PathVariable("edit_maNV") String editMaNV ,@ModelAttribute("dangnhap") TaiKhoan dangNhap) {
		model.addAttribute("show", "showEditDel");
		model.addAttribute("message", "Xóa tài khoản");
		model.addAttribute("dangnhap", this.getDangNhapByID(editMaNV));
		
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

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
		return "user/createaccount";
	}
	
	@RequestMapping(value = "createaccount/action/{id_NV}.htm", params = "btnDelete")
	public String deleteAccount(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("dangnhap") TaiKhoan dangNhap) {
		model.addAttribute("show", "showEditDel");
		Integer check = this.deleteDangNhap(dangNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa tài khoản thành công!");
		} else {
			model.addAttribute("message", "Xóa tài khoản thất bại!");
		}
		
		List<TaiKhoan> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

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
		return "user/createaccount";
	}
//	
//	@RequestMapping(value = "chinhanh/{id_NV}.htm", method = RequestMethod.GET)
//	public String showChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
//			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {
//
//		NhanVien nhanVien = this.getNhanVienByID(id);
//		model.addAttribute("id_NV", id);
//		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
//
//		//
//		model.addAttribute("btnAction", "btnAdd");
//
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/chinhanh";
//	}
//
//	@RequestMapping(value = "chinhanh/action/{id_NV}.htm", params = "btnAdd")
//	public String addchiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
//			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {
//
//		Integer check = this.addChiNhanh(chiNhanh);
//		model.addAttribute("check", check);
//		if (check != 0) {
//			model.addAttribute("message", "ThÃƒÂªm chi nhÃƒÂ¡nh thÃƒÂ nh cÃƒÂ´ng!");
//		} else {
//			model.addAttribute("message", "Ã„ï¿½ÃƒÂ£ cÃƒÂ³ lÃ¡Â»â€”i vui lÃƒÂ²ng kiÃ¡Â»Æ’m tra lÃ¡ÂºÂ¡i!");
//		}
//		
//		model.addAttribute("chiNhanhs", this.getChiNhanhs());
//		
//		NhanVien nhanVien = this.getNhanVienByID(id);
//		model.addAttribute("id_NV", id);
//		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
//		
//		//
//		model.addAttribute("btnAction", "btnAdd");
//
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/chinhanh";
//	}
//
//	@RequestMapping(value = "chinhanh/{maCN}/{id_NV}.htm", params = "lnkEdit")
//	public String editChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
//			@PathVariable("maCN") String maCN) {
//
//		model.addAttribute("chinhanh", this.getChiNhanhByMaCN(maCN));
//		
//		NhanVien nhanVien = this.getNhanVienByID(id);
//		model.addAttribute("id_NV", id);
//		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
//
//		//
//		model.addAttribute("btnAction", "btnUpdate");
//
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/chinhanh";
//	}
//	
//	@RequestMapping(value = "chinhanh/action/{id_NV}.htm", params = "btnUpdate")
//	public String updateChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
//			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {
//
//		Integer check = this.updateChiNhanh(chiNhanh);
//		model.addAttribute("check", check);
//		if (check != 0) {
//			model.addAttribute("message", "CÃ¡ÂºÂ­p nhÃ¡ÂºÂ­t chi nhÃƒÂ¡nh thÃƒÂ nh cÃƒÂ´ng!");
//		} else {
//			model.addAttribute("message", "Ã„ï¿½ÃƒÂ£ cÃƒÂ³ lÃ¡Â»â€”i vui lÃƒÂ²ng kiÃ¡Â»Æ’m tra lÃ¡ÂºÂ¡i!");
//		}
//	
//		model.addAttribute("chiNhanhs", this.getChiNhanhs());
//		
//		NhanVien nhanVien = this.getNhanVienByID(id);
//		model.addAttribute("id_NV", id);
//		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
//
//		//
//		model.addAttribute("btnAction", "btnAdd");
//
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/chinhanh";
//	}
//	
//	@RequestMapping(value = "chinhanh/{maCN}/{id_NV}.htm", params = "lnkDel")
//	public String delChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
//			@PathVariable("maCN") String maCN) {
//
//		model.addAttribute("chinhanh", this.getChiNhanhByMaCN(maCN));
//		
//		NhanVien nhanVien = this.getNhanVienByID(id);
//		model.addAttribute("id_NV", id);
//		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
//
//		//
//		model.addAttribute("btnAction", "btnDelete");
//
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/chinhanh";
//	}
//	
//	@RequestMapping(value = "chinhanh/action/{id_NV}.htm", params = "btnDelete")
//	public String deleteChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
//			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {
//
//		Integer check = this.deleteChiNhanh(chiNhanh);
//		model.addAttribute("check", check);
//		if (check != 0) {
//			model.addAttribute("message", "XÃƒÂ³a chi nhÃƒÂ¡nh thÃƒÂ nh cÃƒÂ´ng!");
//		} else {
//			model.addAttribute("message", "Ã„ï¿½ÃƒÂ£ cÃƒÂ³ lÃ¡Â»â€”i vui lÃƒÂ²ng kiÃ¡Â»Æ’m tra lÃ¡ÂºÂ¡i!");
//		}
//
//		model.addAttribute("chiNhanhs", this.getChiNhanhs());
//		
//		NhanVien nhanVien = this.getNhanVienByID(id);
//		model.addAttribute("id_NV", id);
//		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
//
//		//
//		model.addAttribute("btnAction", "btnAdd");
//
//		// KiÃ¡Â»Æ’m tra Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p theo quyÃ¡Â»ï¿½n nÃƒÂ o
//		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 lÃƒÂ  quyÃ¡Â»ï¿½n Admin
//		{
//			model.addAttribute("phanQuyen", "admin");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		} else {
//			model.addAttribute("phanQuyen", "nhanvien");
//			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
//		}
//		return "user/chinhanh";
//	}
//	
//	@ModelAttribute("chiNhanhs")
//	public List<ChiNhanh> getChiNhanhs() {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM ChiNhanh";
//		Query query = session.createQuery(hql);
//		List<ChiNhanh> list = query.list();
//		return list;
//	}
//	
//	public ChiNhanh getChiNhanhByMaCN(String maCN) {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM ChiNhanh WHERE maCN= :maCN";
//		Query quey = session.createQuery(hql);
//		quey.setParameter("maCN", maCN);
//
//		ChiNhanh chiNhanh = (ChiNhanh) quey.list().get(0);
//		return chiNhanh;
//	}
//	
//	
	@ModelAttribute("phanQuyens")
	public List<Role> getPhanQuyens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Role";
		Query query = session.createQuery(hql);
		List<Role> list = query.list();
		return list;
	}

	public List<NhanVien> getNhanViens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien";
		Query query = session.createQuery(hql);
		List<NhanVien> list = query.list();
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

	public TaiKhoan getDangNhapByID(String maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TaiKhoan WHERE nhanVien.maNV= :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);
		TaiKhoan dangNhap = (TaiKhoan) query.list().get(0);
		return dangNhap;
	}

	public List<TaiKhoan> getDangNhaps() {
		Session session = factory.getCurrentSession();
		String hql = "FROM TaiKhoan";
		Query query = session.createQuery(hql);
		List<TaiKhoan> list = query.list();
		return list;
	}

	public Integer updateDangNhap(TaiKhoan dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(dangNhap);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return 1;
	}

	public String setmaNV() {
		Session	session = factory.openSession();
		String hql = "FROM NhanVien Order By maNV desc";
		Query query = session.createQuery(hql);
		NhanVien list =(NhanVien) query.list().get(0);
		
		
			String s = list.getMaNV().substring(2);
			Integer a = Integer.parseInt(s);
			a = a+1;
			if(a<10) {
				s= String.valueOf(a);
				s = "NV0"+s;
			}
			else {
			s= String.valueOf(a);
			
			s = "NV"+s;
			
			}
		
		return s;
	}
	public Integer addNhanVien(NhanVien nhanVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(nhanVien);
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

	public Integer updateNhanVien(NhanVien nhanVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(nhanVien);
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
//
	public Integer deleteNhanVien(NhanVien nhanVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(nhanVien);
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

	public Integer addDangNhap(TaiKhoan dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(dangNhap);
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

	public Integer deleteDangNhap(TaiKhoan dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(dangNhap);
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
//	
//	public Integer addChiNhanh(ChiNhanh chiNhanh) {
//		Session session = factory.openSession();
//		Transaction t = session.beginTransaction();
//		try {
//			session.save(chiNhanh);
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			e.printStackTrace();
//			return 0;
//		} finally {
//			session.close();
//		}
//		return 1;
//	}
//
//	public Integer updateChiNhanh(ChiNhanh chiNhanh) {
//		Session session = factory.openSession();
//		Transaction t = session.beginTransaction();
//		try {
//			session.update(chiNhanh);
//			t.commit();
//
//		} catch (Exception e) {
//			t.rollback();
//			return 0;
//		} finally {
//			// TODO: handle finally clause
//			session.close();
//		}
//		return 1;
//	}
//
//	public Integer deleteChiNhanh(ChiNhanh chiNhanh) {
//		Session session = factory.openSession();
//		Transaction t = session.beginTransaction();
//		try {
//			session.delete(chiNhanh);
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			e.printStackTrace();
//			return 0;
//		} finally {
//			session.close();
//		}
//		return 1;
//	}

}
