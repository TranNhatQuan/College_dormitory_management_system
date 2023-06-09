package ptithcm.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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

import ptithcm.entity.NhanVien;
import ptithcm.entity.Phong;
import ptithcm.entity.SinhVien;
import ptithcm.entity.TaiKhoan;


@Transactional
@Controller
@RequestMapping("/ktx/")
public class SinhVienController {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "sinhvien/{id_NV}.htm")
	public String showSinhVien(ModelMap model, @PathVariable("id_NV") String maNV,@ModelAttribute("sinhvien") SinhVien sinhVien) {
		model.addAttribute("message", "Thêm thông tin sinh viên");
		model.addAttribute("show", "showFormAdd");
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

		return "ktx/sinhvien";
	}
	

	@RequestMapping(value = "sinhvien/action/{id_NV}.htm", params = "btnAdd")
	public String addSinhVien(ModelMap model, @PathVariable("id_NV") String maNV,
			@ModelAttribute("sinhvien") SinhVien sinhVien, BindingResult erros) {
	
		//Bắt lỗi
		
				//Họ tên
				if(sinhVien.getHoTen().trim().length()==0) {
					erros.rejectValue("hoTen","SinhVien", "Vui lòng nhập họ tên!");
				}else {
					if(sinhVien.getHoTen().trim().length()>=50) {
						erros.rejectValue("hoTen","SinhVien", "Họ tên không được dài hơn 50 ký tự!");
					}
				}
				//Mã sinh vien
				if(sinhVien.getMaSV().trim().length()==0) {
					erros.rejectValue("maSV","SinhVien", "Vui lòng nhập mã sinh viên!");
				}else {
					if(sinhVien.getMaSV().trim().length()>=20) {
						erros.rejectValue("maNV","SinhVien", "Mã sinh viên không được dài hơn 20 ký tự!");
					}
				}
				//Ngày sinh
				if(sinhVien.getNgaySinh().trim().length()==0) {
					erros.rejectValue("ngaySinh","SinhVien", "Vui lòng nhập ngày sinh!");
				}
				//Lớp
				if(sinhVien.getLop().trim().length()==0) {
					erros.rejectValue("lop","SinhVien", "Vui lòng nhập lớp!");
				}else {
					if(sinhVien.getLop().trim().length()>=15) {
						erros.rejectValue("lop","SinhVien", "Tên lớp không được dài hơn 15 ký tự!");
					}
				}
				//CMND
				if(sinhVien.getcMND().trim().length()!=9&&sinhVien.getcMND().trim().length()!=12) {
					erros.rejectValue("cMND","SinhVien", "Số chứng minh nhân dân phải là 9 số hoặc 12 số!");
				
				}else {
					if (!sinhVien.getcMND().trim().matches("[0-9]+[\\.]?[0-9]*")) {
						erros.rejectValue("cMND","SinhVien", "Số chứng minh nhân dân phải là số!");
					}
				}
				//Số điện thoại
				if(sinhVien.getSoDT().trim().length()==0) {
					erros.rejectValue("soDT","SinhVien", "Vui lòng nhập số điện thoại!");
				}else {
					if(!sinhVien.getSoDT().trim().matches("[0-9]+[\\.]?[0-9]*")) {
						erros.rejectValue("soDT","SinhVien", "Số điện thoại phải là số!");
					}else {
						if(sinhVien.getSoDT().trim().length()>=12) {
							erros.rejectValue("soDT","SinhVien", "Số điện thoại không được dà hơn 12 ký tự!");
						}
					}
				}
				//Sdt người thân
				if(sinhVien.getSoDTNguoiThan().trim().length()==0) {
					erros.rejectValue("soDTNguoiThan","SinhVien", "Vui lòng nhập số điện thoại!");
				}else {
					if(!sinhVien.getSoDTNguoiThan().trim().matches("[0-9]+[\\.]?[0-9]*")) {
						erros.rejectValue("soDTNguoiThan","SinhVien", "Số điện thoại phải là số!");
					}else {
						if(sinhVien.getSoDTNguoiThan().trim().length()>=12) {
							erros.rejectValue("soDTNguoiThan","SinhVien", "Số điện thoại không được dà hơn 12 ký tự!");
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
					Integer check = this.addSinhVien(sinhVien);
					model.addAttribute("check", check);
					if (check != 0) {
						model.addAttribute("message", "Thêm sinh viên thành công!");

					} else {
						model.addAttribute("message", "Thêm sinh viên thất bại!");
					}
				}

		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
		model.addAttribute("hoTen", nhanVien.getHoTen());
		//
		
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
		
		model.addAttribute("show", "showFormAdd");
//		model.addAttribute("clickaction", "showtablekho");
		
		return "ktx/sinhvien";
	}
	

	@RequestMapping(value = "sinhvien/{id_NV}/{maSV}.htm", params = "lnkEdit")
	public String editSinhVien(ModelMap model, @PathVariable("id_NV") String maNV, @PathVariable("maSV") String maSV) {
		model.addAttribute("show", "showFormAdd");
		model.addAttribute("message", "Sửa thông tin sinh viên");
		
		SinhVien chooseSV = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhvien", chooseSV);

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

		List<SinhVien> listSinhVien = this.getsinhviens();
		model.addAttribute("sinhViens", listSinhVien);

		model.addAttribute("btnAction", "btnUpdate");
		
		return "ktx/sinhvien";
	}

	@RequestMapping(value = "sinhvien/action/{id_NV}.htm", params = "btnUpdate")
	public String updateSinhVien(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("sinhvien") SinhVien sinhVien, BindingResult erros) {
		if(sinhVien.getHoTen().trim().length()==0) {
			erros.rejectValue("hoTen","SinhVien", "Vui lòng nhập họ tên!");
		}else {
			if(sinhVien.getHoTen().trim().length()>=50) {
				erros.rejectValue("hoTen","SinhVien", "Họ tên không được dài hơn 50 ký tự!");
			}
		}
		//Mã sinh vien
		if(sinhVien.getMaSV().trim().length()==0) {
			erros.rejectValue("maSV","SinhVien", "Vui lòng nhập mã sinh viên!");
		}else {
			if(sinhVien.getMaSV().trim().length()>=20) {
				erros.rejectValue("maNV","SinhVien", "Mã sinh viên không được dài hơn 20 ký tự!");
			}
		}
		//Ngày sinh
		if(sinhVien.getNgaySinh().trim().length()==0) {
			erros.rejectValue("ngaySinh","SinhVien", "Vui lòng nhập ngày sinh!");
		}
		//Lớp
		if(sinhVien.getLop().trim().length()==0) {
			erros.rejectValue("lop","SinhVien", "Vui lòng nhập lớp!");
		}else {
			if(sinhVien.getLop().trim().length()>=15) {
				erros.rejectValue("lop","SinhVien", "Tên lớp không được dài hơn 15 ký tự!");
			}
		}
		//CMND
		if(sinhVien.getcMND().trim().length()!=9&&sinhVien.getcMND().trim().length()!=12) {
			erros.rejectValue("cMND","SinhVien", "Số chứng minh nhân dân phải là 9 số hoặc 12 số!");
		
		}else {
			if (!sinhVien.getcMND().trim().matches("[0-9]+[\\.]?[0-9]*")) {
				erros.rejectValue("cMND","SinhVien", "Số chứng minh nhân dân phải là số!");
			}
		}
		//Số điện thoại
		if(sinhVien.getSoDT().trim().length()==0) {
			erros.rejectValue("soDT","SinhVien", "Vui lòng nhập số điện thoại!");
		}else {
			if(!sinhVien.getSoDT().trim().matches("[0-9]+[\\.]?[0-9]*")) {
				erros.rejectValue("soDT","SinhVien", "Số điện thoại phải là số!");
			}else {
				if(sinhVien.getSoDT().trim().length()>=12) {
					erros.rejectValue("soDT","SinhVien", "Số điện thoại không được dà hơn 12 ký tự!");
				}
			}
		}
		//Sdt người thân
		if(sinhVien.getSoDTNguoiThan().trim().length()==0) {
			erros.rejectValue("soDTNguoiThan","SinhVien", "Vui lòng nhập số điện thoại!");
		}else {
			if(!sinhVien.getSoDTNguoiThan().trim().matches("[0-9]+[\\.]?[0-9]*")) {
				erros.rejectValue("soDTNguoiThan","SinhVien", "Số điện thoại phải là số!");
			}else {
				if(sinhVien.getSoDTNguoiThan().trim().length()>=12) {
					erros.rejectValue("soDTNguoiThan","SinhVien", "Số điện thoại không được dà hơn 12 ký tự!");
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
			Integer check = this.updateSinhVien(sinhVien);
			model.addAttribute("check", check);
			if (check != 0) {
				model.addAttribute("message", "Cập nhật sinh viên thành công. Tiếp tục sửa!");
				model.addAttribute("btnAction", "btnUpdate");
			} else {
				model.addAttribute("message", "Cập nhật sinh viên thất bại!");
				model.addAttribute("btnAction", "btnUpdate");
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

		List<SinhVien> listSinhVien = this.getsinhviens();
		model.addAttribute("sinhViens", listSinhVien);
		model.addAttribute("show", "showFormAdd");
		

		return "ktx/sinhvien";
	}

	@RequestMapping(value = "sinhvien/{id_NV}/{maSV}.htm", params = "lnkDel")
	public String delSinhVien(ModelMap model, @PathVariable("id_NV") String maNV, @PathVariable("maSV") String maSV) {
		model.addAttribute("show", "showFormDel");
		model.addAttribute("message", "Đây có phải là thông tin sinh viên bạn muốn xóa");
		
		SinhVien chooseSV = this.getSinhVienByMaSV(maSV);
		model.addAttribute("sinhvien", chooseSV);
		
		NhanVien nhanVien = this.getNhanVienByID(maNV);
		model.addAttribute("id_NV", maNV);
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
		
		List<SinhVien> listSinhVien = this.getsinhviens();
		model.addAttribute("sinhViens", listSinhVien);
		
		return "ktx/sinhvien";	
		}
	
	
	
	@RequestMapping(value = "sinhvien/action/{id_NV}.htm", params = "btnDelete")
	public String deleteSinhVien(ModelMap model, @PathVariable("id_NV") String id_NV,
			@ModelAttribute("sinhvien") SinhVien sinhVien) {
		
		Integer check = this.deleteSinhVien(sinhVien);
		
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa sinh viên thành công!");
		} else {
			model.addAttribute("message", "Xóa sinh viên  thất bại!");
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
		List<SinhVien> listSinhVien = this.getsinhviens();
		model.addAttribute("sinhViens", listSinhVien);
		model.addAttribute("show", "showFormAdd");
		return "ktx/sinhvien";
	}
	
//	public NhanVien getNhanVienByID(Integer id) {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM NhanVien n WHERE n.maNV= :id";
//		Query query = session.createQuery(hql);
//		query.setParameter("id", id);
//
//		NhanVien nhanVien = (NhanVien) query.list().get(0);
//		return nhanVien;
//	}
//	
	/*
	 * // public String setmasv(String nganh) { LocalDateTime now =
	 * LocalDateTime.now(); Session session = factory.openSession(); String year =
	 * "N"+(now.getYear()+"").substring(2,4);
	 * 
	 * String hqlString = "FROM SinhVien WHERE maSV LIKE '"+year+"DC";
	 * 
	 * return maSV; }
	 */
	
	public Integer addSinhVien(SinhVien sinhVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {/* sinhVien.setMaSV(this.setmasv(sinhVien.getLop())); */
		session.save(sinhVien);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	public Integer deleteSinhVien(SinhVien sinhVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(sinhVien);
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
	
	public Integer updateSinhVien(SinhVien sinhVien) {
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
//
//	public Integer updateDatHang(DatHang datHang) {
//		Session session = factory.openSession();
//		Transaction t = session.beginTransaction();
//		try {
//			session.update(datHang);
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			return 0;
//		} finally {
//			session.close();
//		}
//		return 1;
//	}
//
//	public Integer deleteDatHang(DatHang datHang) {
//		Session session = factory.openSession();
//		Transaction t = session.beginTransaction();
//		try {
//			session.delete(datHang);
//			t.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			t.rollback();
//			return 0;
//		} finally {
//
//			session.close();
//		}
//		return 1;
//	}
//
//	public DatHang getDatHangByMasoDDH(String masoDDH) {
//		Session session = factory.getCurrentSession();
//		String hql = "FROM DatHang WHERE masoDDH= :masoDDH";
//		Query query = session.createQuery(hql);
//		query.setParameter("masoDDH", masoDDH);
//
//		DatHang datHang = (DatHang) query.list().get(0);
//		return datHang;
//	}
////////////////////////////////////////////////////////////////////////////
	public List<SinhVien> getsinhviens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien";
		Query query = session.createQuery(hql);

		List<SinhVien> list = query.list();
		return list;
	}
///////////////////////////////////////////////////////////////////////////
	public List<SinhVien> getSinhViensByMaNV(String maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien WHERE nhanVien.maNV= :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);

		List<SinhVien> list = query.list();
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
	
	public SinhVien getSinhVienByMaSV(String maSV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SinhVien n WHERE n.maSV= :maSV";
		Query quey = session.createQuery(hql);
		quey.setParameter("maSV", maSV);

		SinhVien sinhVien = (SinhVien) quey.list().get(0);
		return sinhVien;
	}
	
//	public String taoMaSV() {
//		String prefix = "EMP";
//		Session session = factory.getCurrentSession();
//		String hql = "SELECT sv.maSV FROM SinhVien sv";
//		Query query = session.createQuery(hql);
//		
//	    return prefix ;
//	  
//	}
	
}
