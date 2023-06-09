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
		
			<div class="card">
				<div class="card-header">
									<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí hóa đơn điện</h1>
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
								<th>Số phòng</th>
								<th>Tình trạng</th>
								<th>Loại phòng</th>
								<th>Số lượng sinh viên tối đa</th>
								<th>Số lượng sinh viên đang ở</th>
								
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
										
										
										</c:otherwise>
									</c:choose>
										<td><a
									href="ktx/hoadondien/${id_NV}/${p.idPhong}.htm?lnkShow"
									style="color: #1761fd;">Xem hoá đơn điện</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			
			
			
			
			
			
			
			<c:if test="${show1 ==  'show'}">
			<div class="card">
			<!-- id="datatable" -->
					<table id="datatable1"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã sinh viên</th>
								<th>Họ và tên</th>
								<th>Ngày sinh</th>
								<th>Giới tính</th>
								<th>CMND</th>
								<th>Số điện thoại</th>
								<th>Lớp</th>
								<th>Sdt người thân</th>
								<th>Email</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hd" items="${hopdongs}">
								<tr>
									<fmt:parseDate value = "${hd.ngayKetThuc}" var = "ngayhethan" pattern = "yyyy-MM-dd"/>
											<c:if test="${ngayhethan > date}">
											<td>${hd.sinhVien.maSV}</td>
											<td>${hd.sinhVien.hoTen}</td>
										<td>${hd.sinhVien.ngaySinh}</td>
										<td>${hd.sinhVien.gioiTinh}</td>
										<td>${hd.sinhVien.cMND}</td>
										<td>${hd.sinhVien.soDT}</td>
										<td>${hd.sinhVien.lop}</td>
										<td>${hd.sinhVien.soDTNguoiThan}</td>
										<td>${hd.sinhVien.mail}</td>
											</c:if>	
											
											
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</c:if>

			<!-- container -->
			<footer class="footer text-center text-sm-start">
				©
				<script>
					document.write(new Date().getFullYear())
				</script>
				2021 Dastone <span
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
