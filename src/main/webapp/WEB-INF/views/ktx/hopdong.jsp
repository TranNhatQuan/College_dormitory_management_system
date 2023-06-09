<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<style>
.btnDelete, .btnEdit {
	font-size: 15px;
}

.btnDelete {
	color: red;
}

.btnEdit {
	color: #1761fd;
}
*[id$=errors]{
color:red;
font-style: italic;
}

.table th{
font-size: medium;
font-weight: 1000;
}
h1{
 text-align: center;
}
h4{
 font-weight: 1000;
}

</style>
</head>
<body class="">
<jsp:useBean id="date" class="java.util.Date"/>

	<!-- Left Sidenav -->
	<%@ include file="/WEB-INF/views/include/left-sidenav.jsp"%>
	<!-- end left-sidenav-->
	<!-- container -->
	<div class="page-wrapper">
		<!-- Top Bar Start -->
		<%@ include file="/WEB-INF/views/include/top-bar.jsp"%>
		<!-- Top Bar End -->
		<!-- Page Content-->
		<div class="page-content">

			<c:if test="${show == 'showFormAdd'}">
			
			
			<div class="card">
				<div class="card-header">
				<h1 class="font-54 fw-bold mt-10 mb-4">Lập hợp đồng</h1>
					
				</div>
				<!--end card-header-->
				<div class="card-body">
					<!-- id="datatable" -->
					<h4>Danh sách phòng</h4>
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
												<th>Mã Phòng</th>
												<th>Tình trạng</th>
												<th>Loại phòng</th>
												<th>Số lượng tối đa</th>
												<th>Số lượng hiện có</th>
												<th></th>
												
											</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${phongs}">
												<tr>
													<td>${p.idPhong}</td>
													<td>${p.tinhTrang}</td>
													<td>${p.loaiPhong.tenLoaiPhong}</td>
													<td>${p.soSV}</td>
										<c:choose>
													
										<c:when test="${p.hopDongs.size() == 0}">
											<td>0</td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sinhVien.maSV}/${p.idPhong}.htm?lnkAdd"
												style="color: #1761fd;">Chọn phòng</a>
												</td>
										</c:when>
										
										<c:otherwise>
						
										
										<c:set var="count" value="0" scope="page" />
										
										<c:forEach items="${p.hopDongs}" var="hds">
								<fmt:parseDate value = "${hds.ngayKetThuc}" var = "ngayhethan" pattern = "yyyy-MM-dd"/>
										<c:if test="${ngayhethan > date}">
										
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:if>	
										
										</c:forEach>
										
										<td>${count} </td>
										
										<c:if test="${count < p.soSV}">
										<td><a
												href="ktx/hopdong/${id_NV}/${sinhVien.maSV}/${p.idPhong}.htm?lnkAdd"
												style="color: #1761fd;">Chọn phòng</a>
												</td>
										</c:if>

										<c:if test="${count == p.soSV}">
										<td>Phòng đầy</td>
										</c:if>	
										
										</c:otherwise>
									</c:choose>
													
													
												</tr>
											</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="card">
			<!-- id="datatable" -->
					<h4>Danh sách hợp đồng</h4>
					<table id="datatable1"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã hợp đồng</th>
								<th>Tên sinh viên</th>
								<th>Tên nv lập hợp đồng</th>
								<th>Số phòng</th>
								<th>Ngày lập</th>
								<th>Ngày bắt đầu</th>
								<th>Ngày kết thúc</th>
								<c:if test="${phanQuyen == 'admin'}">
								<th></th>
								</c:if>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="hd" items="${hopDongs}">
								<tr>
									<td>${hd.maHopDong}</td>
									<td>${hd.sinhVien.hoTen}</td>
									<td>${hd.nhanVien.hoTen}</td>
									<td>${hd.phong.idPhong}</td>
									<fmt:parseDate value = "${hd.ngayLap}" var = "ngaylap" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaylap}" pattern="dd-MM-yyyy" var="ngayLap" />
									<td>${ngayLap}</td>
									<fmt:parseDate value = "${hd.ngayBatDau}" var = "ngaybatdau" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaybatdau}" pattern="dd-MM-yyyy" var="ngayBatDau" />
									<td>${ngayBatDau}</td>
									<fmt:parseDate value = "${hd.ngayKetThuc}" var = "ngayketthuc" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngayketthuc}" pattern="dd-MM-yyyy" var="ngayKetThuc" />
									<td>${ngayKetThuc}</td>	
									
									<c:if test="${phanQuyen == 'admin'}">
									<td><a class="btnDelete" href="ktx/hopdong/${id_NV}/${hd.maHopDong}.htm?lnkDel"><i
												class="ti-trash"></i></a></td>
										</c:if>		
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				<div class="card">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Lập hợp đồng</h4>
							</div>
							<!--end col-->
						</div>
						<!--end row-->
					</div>
					<!--end card-header-->
					<div class="card-body">
						<!-- Thông báo -->
						<c:choose>
							<c:when test="${check==null}">
							</c:when>
							<c:when test="${check == 1}">
								<div class="alert alert-success border-0" role="alert">
									<strong>Thông báo!</strong> ${message}.
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-danger border-0" role="alert">
									<strong>Thông báo!</strong> ${message}.
								</div>
							</c:otherwise>
						</c:choose>

						<form:form action="ktx/hopdong/action/${id_NV}/${sinhVien.maSV}.htm"
						modelAttribute="hopdong">
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã hợp đồng</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="maHopDong" class="form-control" type="text" readonly="true" />
									<form:errors path="maHopDong"/>
								</div>
							</div>
							 
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã nhân viên</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="nhanVien.maNV" class="form-control"
										type="text" value="${id_NV}" readonly="true" />
								</div>
							</div>
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã sinh viên</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="sinhVien.maSV" class="form-control"
										type="text" value="${sinhVien.maSV}" readonly="true" />
								</div>
							</div>						
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Phòng</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="phong.idPhong" class="form-control"
										type="text" value="${idPhong}" readonly="true"/>
										<form:errors path="phong.idPhong"/>
								</div>
							</div>
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center"> Loại phòng</label>
								<div class="col-lg-9 col-xl-8">
									<input class="form-control"
										type="text" value="${loaiPhongSelected}" readonly/>
										<form:errors path="phong.idPhong"/>
								</div>
							</div>
							
							<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="datenow" />
							 <div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày lập</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayLap" value="${datenow}"  class="form-control" type="date"/>
									<form:errors path="ngayLap"/>
								</div>
							</div> 
											
							<div class="form-group row">
								<label	
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày bắt đầu</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayBatDau" class="form-control" type="date"/>
									<form:errors path="ngayBatDau"/>
								</div>
							</div>
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày kết thúc</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayKetThuc" class="form-control" type="date"/>
									<form:errors path="ngayKetThuc"/>
								</div>
							</div> 
							
							<div class="form-group row">
								<div class="col-lg-9 col-xl-8 offset-lg-3">
									<button name="${btnAction}" type="submit"
										class="btn btn-sm btn-outline-primary">Xác nhận</button>
										
									<form:button formaction="ktx/hopdong/${id_NV}.htm" type="submit"
										class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
								</div>
							</div>
						</form:form>
					</div>
					
				</div>
	
			</c:if>
			
			
			
			
			<c:if test="${show1 ==  'show'}">
			<div class="card">

				<div class="card-header">
					<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí hợp đồng</h1>
					
				</div>
				<!--end card-header-->

				<div class="card-body">

					<!-- id="datatable" -->
					<h4>Danh sách sinh viên</h4>
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã sinh viên</th>
								<th>Họ và tên</th>
								
								<th>Giới tính</th>
								
								<th>Số điện thoại</th>
								<th>Lớp</th>
								
								<th>Email</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="sv" items="${sinhViens}">
								<tr>
									<td>${sv.maSV}</td>
									<td>${sv.hoTen}</td>
									
									<td>${sv.gioiTinh}</td>
									
									<td>${sv.soDT}</td>
									<td>${sv.lop}</td>
									
									<td>${sv.mail}</td>


									<c:choose>
										<c:when test="${sv.soTheoDois.size() >= 3}">
											<td><span class="badge badge-soft-danger"
												style="font-weight: bold;">Vi phạm quá nhiều</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="color: #1761fd;">Xem hợp đồng</a> 
											</td>
										</c:when>
										<c:when test="${sv.hopDongs.size() == 0}">
											<td><span class="badge badge-soft-danger"
												style="font-weight: bold;">Chưa có hợp đồng </span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}/.htm?lnkAdd"
												style="color: #1761fd;">Lập hợp đồng</a></td>
										</c:when>
										<c:otherwise>
										<c:set var="count" value="0" scope="page" />
										
										<c:forEach items="${sv.hopDongs}" var="hds">
								<fmt:parseDate value = "${hds.ngayKetThuc}" var = "ngayhethan" pattern = "yyyy-MM-dd"/>
										<c:if test="${ngayhethan > date}">
										
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:if>	
										</c:forEach>

										<c:if test="${count >0}">
										<td><span class="badge badge-soft-success"
												style="font-weight: bold;">Đã có hợp đồng</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="color: #1761fd;">Xem hợp đồng</a> 
											</td>
										</c:if>
										<c:if test="${count == 0}">
										<td><span class="badge badge-soft-warning"
												style="font-weight: bold;">Hợp đồng hết hạn</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}/${sv.hopDongs.get(sv.hopDongs.size()-1).phong.idPhong}.htm?lnkAdd"
												style="color: #1761fd;">Lập hợp đồng mới</a> 
											</td>
										</c:if>	
											
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
			<div class="card">
			<!-- id="datatable" -->
					<table id="datatable1"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã hợp đồng</th>
								<th>Tên sinh viên</th>
								<th>Tên nv lập hợp đồng</th>
								<th>Số phòng</th>
								<th>Ngày lập</th>
								<th>Ngày bắt đầu</th>
								<th>Ngày kết thúc</th>
								<c:if test="${phanQuyen == 'admin'}">
								<th></th>
								</c:if>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="hd" items="${hopDongs}">
								<tr>
									<td>${hd.maHopDong}</td>
									<td>${hd.sinhVien.hoTen}</td>
									<td>${hd.nhanVien.hoTen}</td>
									<td>${hd.phong.idPhong}</td>
									<fmt:parseDate value = "${hd.ngayLap}" var = "ngaylap" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaylap}" pattern="dd-MM-yyyy" var="ngayLap" />
									<td>${ngayLap}</td>
									<fmt:parseDate value = "${hd.ngayBatDau}" var = "ngaybatdau" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaybatdau}" pattern="dd-MM-yyyy" var="ngayBatDau" />
									<td>${ngayBatDau}</td>
									<fmt:parseDate value = "${hd.ngayKetThuc}" var = "ngayketthuc" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngayketthuc}" pattern="dd-MM-yyyy" var="ngayKetThuc" />
									<td>${ngayKetThuc}</td>	
									
									<c:if test="${phanQuyen == 'admin'}">
									<td><a class="btnDelete" href="ktx/hopdong/${id_NV}/${hd.maHopDong}.htm?lnkDel"><i
												class="ti-trash"></i></a></td>
										</c:if>		
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				
				
			</c:if>


			<c:if test="${show ==  'showFormDel'}">
			
			<div class="card">

				<div class="card-header">
				<h1 class="font-54 fw-bold mt-10 mb-4">Xóa hợp đồng</h1>
					
					
				</div>
				<!--end card-header-->

				<div class="card-body">
						<h4>Danh sách sinh viên</h4>
					<!-- id="datatable" -->
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã sinh viên</th>
								<th>Họ và tên</th>
							
								<th>Giới tính</th>
								
								<th>Số điện thoại</th>
								<th>Lớp</th>
								
								<th>Email</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="sv" items="${sinhViens}">
								<tr>
									<td>${sv.maSV}</td>
									<td>${sv.hoTen}</td>
									
									<td>${sv.gioiTinh}</td>
									
									<td>${sv.soDT}</td>
									<td>${sv.lop}</td>
									
									<td>${sv.mail}</td>


									<c:choose>
										<c:when test="${sv.soTheoDois.size() >= 3}">
											<td><span class="badge badge-soft-danger"
												style="font-weight: bold;">Vi phạm quá nhiều</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="color: #1761fd;">Xem hợp đồng</a> 
											</td>
										</c:when>
										<c:when test="${sv.hopDongs.size() == 0}">
											<td><span class="badge badge-soft-danger"
												style="font-weight: bold;">Chưa có hợp đồng </span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}/.htm?lnkAdd"
												style="color: #1761fd;">Lập hợp đồng</a></td>
										</c:when>
										<c:otherwise>
										<c:set var="count" value="0" scope="page" />
										
										<c:forEach items="${sv.hopDongs}" var="hds">
										
								<fmt:parseDate value = "${hds.ngayKetThuc}" var = "ngayhethan" pattern = "yyyy-MM-dd"/>
										<c:if test="${ngayhethan > date}">
										
											<c:set var="count" value="${count + 1}" scope="page"/>
										</c:if>	
										</c:forEach>

										<c:if test="${count >0}">
										<td><span class="badge badge-soft-success"
												style="font-weight: bold;">Đã có hợp đồng</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="color: #1761fd;">Xem hợp đồng</a> 
											</td>
										</c:if>
										<c:if test="${count == 0}">
										<td><span class="badge badge-soft-warning"
												style="font-weight: bold;">Hợp đồng hết hạn</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}/${sv.hopDongs.get(sv.hopDongs.size()-1).phong.idPhong}.htm?lnkAdd"
												style="color: #1761fd;">Lập hợp đồng mới</a> 
											</td>
										</c:if>	
										
										
										
										<%-- <fmt:parseDate value = "${sv.hopDongs.get(sv.hopDongs.size()-1).ngayKetThuc }" var = "ngayhethan" pattern = "yyyy-MM-dd"/>
										<c:if test="${ngayhethan < date}">
										
										<td><span class="badge badge-soft-warning"
												style="font-weight: bold;">Hợp đồng hết hạn</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}/${sv.hopDongs.get(sv.hopDongs.size()-1).phong.idPhong}.htm?lnkAdd"
												style="color: #1761fd;">Lập hợp đồng mới</a> 
											</td>
											
										</c:if>
										
										<c:if test="${ngayhethan > date}">
											<td><span class="badge badge-soft-success"
												style="font-weight: bold;">Đã có hợp đồng</span></td>
											<td><a
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="color: #1761fd;">Xem hợp đồng</a> 
												Get hợp dồng gần nhát
												${sv.hopDongs.get(sv.hopDongs.size()-1).maHopDong}
												Kiểm tra hợp đồng gần nhất hết hạn
											${sv.hopDongs.get(sv.hopDongs.size()-1).ngayKetThuc > date} còn hạn
											</td>
											</c:if>	 --%>
											
											
											
											
											
											
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
			
			<div class="card">
			<!-- id="datatable" -->
					<table id="datatable1"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã hợp đồng</th>
								<th>Tên sinh viên</th>
								<th>Tên nv lập hợp đồng</th>
								<th>Số phòng</th>
								<th>Ngày lập</th>
								<th>Ngày bắt đầu</th>
								<th>Ngày kết thúc</th>
								<c:if test="${phanQuyen == 'admin'}">
								<th></th>
								</c:if>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="hd" items="${hopDongs}">
								<tr>
									<td>${hd.maHopDong}</td>
									<td>${hd.sinhVien.hoTen}</td>
									<td>${hd.nhanVien.hoTen}</td>
									<td>${hd.phong.idPhong}</td>
									<fmt:parseDate value = "${hd.ngayLap}" var = "ngaylap" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaylap}" pattern="dd-MM-yyyy" var="ngayLap" />
									<td>${ngayLap}</td>
									<fmt:parseDate value = "${hd.ngayBatDau}" var = "ngaybatdau" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaybatdau}" pattern="dd-MM-yyyy" var="ngayBatDau" />
									<td>${ngayBatDau}</td>
									<fmt:parseDate value = "${hd.ngayKetThuc}" var = "ngayketthuc" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngayketthuc}" pattern="dd-MM-yyyy" var="ngayKetThuc" />
									<td>${ngayKetThuc}</td>	
									
									<c:if test="${phanQuyen == 'admin'}">
									<td><a class="btnDelete" href="ktx/hopdong/${id_NV}/${hd.maHopDong}.htm?lnkDel"><i
												class="ti-trash"></i></a></td>
										</c:if>	

								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				
				<div class="card" >
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Xóa hợp đồng</h4>
							</div>
							<!--end col-->
						</div>
						<!--end row-->
					</div>
					<!--end card-header-->
					<div class="card-body">
						<!-- Thông báo -->
						<c:choose>
							<c:when test="${check==null}">
							</c:when>
							<c:when test="${check == 1}">
								<div class="alert alert-success border-0" role="alert">
									<strong>Thông báo!</strong> ${message}.
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-danger border-0" role="alert">
									<strong>Thông báo!</strong> ${message}.
								</div>
							</c:otherwise>
						</c:choose>

						<form:form action="ktx/hopdong/action/${id_NV}.htm"
						modelAttribute="hopdong">
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã hợp đồng</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="maHopDong" class="form-control" type="text" readonly="true"/>
								</div>
							</div>
							 
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã nhân viên</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="nhanVien.maNV" class="form-control"
										type="text" value="${id_NV}" readonly="true" />
								</div>
							</div>
							
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã sinh viên</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="sinhVien.maSV" class="form-control"
										type="text" value="${sinhVien.maSV}" readonly="true" />
								</div>
							</div>						
	
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Phòng</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="phong.idPhong" class="form-control"
										type="text" value="${idPhong}" readonly="true"/>
								</div>
							</div>
							
							<fmt:parseDate  value ="${hopdong.ngayLap}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd" var="ngaySinhFT" />
							 <div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày lập</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayLap" value="${ngaySinhFT}" class="form-control" type="date"
										  />
								</div>
							</div> 	
								
								<fmt:parseDate  value ="${hopdong.ngayBatDau}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd" var="ngaySinhFT" />			
							<div class="form-group row">
								<label	
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày bắt đầu</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayBatDau" value="${ngaySinhFT}" class="form-control" type="date"
										 />
								</div>
							</div>
							
							<fmt:parseDate  value ="${hopdong.ngayKetThuc}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd" var="ngaySinhFT" />
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày kết thúc</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayKetThuc" value="${ngaySinhFT}" class="form-control" type="date"
										 />
								</div>
							</div> 
							
							<div class="form-group row">
								<div class="col-lg-9 col-xl-8 offset-lg-3">
									<button name="${btnAction}" type="submit"
										class="btn btn-sm btn-outline-primary">Xác nhận</button>
										
									<form:button formaction="ktx/hopdong/${id_NV}.htm" type="submit"
										class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
								</div>
							</div>
						</form:form>
					</div>
					<!--end card-body-->
				</div>
				<!--end card-->
			</c:if>
	<!-- container -->
	<footer class="footer text-center text-sm-start">
		©
		<script>
			document.write(new Date().getFullYear())
		</script>
		 Dastone <span
			class="text-muted d-none d-sm-inline-block float-end">Crafted
			with <i class="mdi mdi-heart text-danger"></i> by Mannatthemes
		</span>
	</footer>
	<!--end footer-->
	</div>
	<!-- end page content -->
	</div>
	<!-- end page-wrapper -->

	<%@include file="/WEB-INF/views/include/footer.jsp"%>

</body>
</html>
