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

			<div class="card">
			
				<div class="card-header">
					<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí phí kí túc xá</h1>	
				</div>
				<!--end card-header-->
				<div class="card-body">

					<!-- id="datatable" -->
					<h4>Danh sách hợp đồng</h4>
					<table id="datatable"
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
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hd" items="${hopDongs}" varStatus="loop">
									
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
										
							 		<c:choose>
										<c:when test="${phiKTXs[loop.index] == null}">
											<td><span class="badge badge-soft-warning"
												style="font-weight: bold;">Chưa thanh toán</span></td>
											<td><a
												href="ktx/phiktx/${id_NV}/${hd.maHopDong}.htm?lnkAdd"
												style="color: #1761fd;">Thanh toán</a></td>
										</c:when>
										<c:otherwise>
										
										<td><span class="badge badge-soft-success"
												style="font-weight: bold;">Đã thanh toán</span></td>
											<td><a
												href="ktx/phiktx/${id_NV}/${hd.maHopDong}.htm?lnkShow"
												style="color: #1761fd;">Xem biên lai</a></td>	
										</c:otherwise>
									</c:choose>
								</tr>
								</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<c:if test="${show1 == 'show'}">
			<div class="card">
				<div class="card" style="width: 40%; margin:0 auto;">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Biên lai</h4>
							</div>
							<!--end col-->
						</div>
						<!--end row-->
					</div>
					<!--end card-header-->
					<div class="card-body">
								<!-- id="datatable" -->
								<div class="table dt-responsive" style="max-height: 200px;">
									<table id="datatable" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>Mã biên lai</th>
												<th>Số tháng</th>
												<th>Số tiền</th>
												<th>Ngày thu</th>
												<th>Nhân viên</th>
												
												<c:if test="${phanQuyen == 'admin'}">
												<th></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											
											
												<tr>
													<td>${bienLai.hopDong.maHopDong}</td>
													<td>${bienLai.soThang}</td>
													<td>${bienLai.soTien}</td>
													<td>${bienLai.ngayThu}</td>
													<td>${bienLai.nhanVien.maNV}</td>
													<c:if test="${phanQuyen == 'admin'}">
												<td><a class="btnDelete"
											href="ktx/phiktx/${id_NV}/${bienLai.maHopDong}.htm?lnkDel"><i
												class="ti-trash"></i></a></td>
												</c:if>
												</tr>
											
										</tbody>
									</table>
								</div>
							</div>
					<!--end card-body-->
				</div>
				<!--end card-->

				</div>	
			</c:if>
			
			
			<c:if test="${show == 'showFormAdd'}">
			<div class="card">
				<div class="card" style="width: 40%; margin:0 auto;">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Lập biên lai</h4>
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

						<form:form action="ktx/phiktx/action/${id_NV}/${maHopDong}.htm"
						modelAttribute="phiktx">
									
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã biên lai</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="hopDong.maHopDong" value="${hopDong.maHopDong}" class="form-control" type="text" readonly="true" />
									<form:errors path="hopDong.maHopDong"/>
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
							
							
							<fmt:parseDate value = "${hopDong.ngayBatDau}" var = "ngaybatdau" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaybatdau}" pattern="dd-MM-yyyy" var="ngayBatDau" />
									<fmt:parseDate value = "${hopDong.ngayKetThuc}" var = "ngayketthuc" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngayketthuc}" pattern="dd-MM-yyyy" var="ngayKetThuc" />
									
									<c:if test="${ngayketthuc.getMonth()-ngaybatdau.getMonth() > 0}">
										<c:set var="sothang" value="${ngayketthuc.getMonth()-ngaybatdau.getMonth()}" scope="page"/>
									</c:if>	
									
									<c:if test="${ngayketthuc.getMonth()-ngaybatdau.getMonth() < 0}">
										<c:set var="sothang" value="${ngayketthuc.getMonth()-ngaybatdau.getMonth() + 12}" scope="page"/>
									</c:if>
									
						 <div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số tháng thu</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="soThang" value="${sothang}" class="form-control" type="text" readonly="true" />
									
								</div>
							</div>
									<c:set var="giatheothang" value="${hopDong.phong.loaiPhong.gia}" scope="page" />
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số tiền</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="soTien" value="${giatheothang*sothang}" class="form-control" type="text"  readonly="true"/>
								</div>
							</div>
									
							<%-- <fmt:parseDate  value ="${phiktx.ngayThu}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd" var="ngaySinhFT" /> --%>
						
							<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="ngaySinhFT" />
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày thu</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayThu"  value="${ngaySinhFT}" class="form-control" type="date" />
									<form:errors path="ngayThu"/>
								</div>
							</div>  
							
							<div class="form-group row">
								<div class="col-lg-9 col-xl-8 offset-lg-3">
									<button name="${btnAction}" type="submit"
										class="btn btn-sm btn-outline-primary">Xác nhận</button>
										
									<form:button formaction="ktx/phiktx/${id_NV}.htm" type="submit"
										class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
								</div>
							</div>
						</form:form>
					</div>
					<!--end card-body-->
				</div>
				<!--end card-->

				</div>	
			</c:if>

			<c:if test="${show == 'showFormDel'}">
			<div class="card">
				<div class="card" style="width: 40%; margin:0 auto;">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Lập biên lai</h4>
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

						<form:form action="ktx/phiktx/action/${id_NV}/${maHopDong}.htm"
						modelAttribute="phiktx">
									
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã biên lai</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="hopDong.maHopDong" value="${hopDong.maHopDong}" class="form-control" type="text" readonly="true" />
									<form:errors path="hopDong.maHopDong"/>
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
							
							
							<fmt:parseDate value = "${hopDong.ngayBatDau}" var = "ngaybatdau" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaybatdau}" pattern="dd-MM-yyyy" var="ngayBatDau" />
									<fmt:parseDate value = "${hopDong.ngayKetThuc}" var = "ngayketthuc" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngayketthuc}" pattern="dd-MM-yyyy" var="ngayKetThuc" />
									
									<c:if test="${ngayketthuc.getMonth()-ngaybatdau.getMonth() > 0}">
										<c:set var="sothang" value="${ngayketthuc.getMonth()-ngaybatdau.getMonth()}" scope="page"/>
									</c:if>	
									
									<c:if test="${ngayketthuc.getMonth()-ngaybatdau.getMonth() < 0}">
										<c:set var="sothang" value="${ngayketthuc.getMonth()-ngaybatdau.getMonth() + 12}" scope="page"/>
									</c:if>
									
						 <div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số tháng thu</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="soThang" value="${sothang}" class="form-control" type="text" readonly="true" />
									
								</div>
							</div>
									<c:set var="giatheothang" value="${hopDong.phong.loaiPhong.gia}" scope="page" />
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số tiền</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="soTien" value="${giatheothang*sothang}" class="form-control" type="text"  readonly="true"/>
								</div>
							</div>
									
							<fmt:parseDate  value ="${phiktx.ngayThu}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd" var="ngaySinhFT" />
					
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày thu</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngayThu"  value="${ngaySinhFT}" class="form-control" type="date" readonly="true"/>
									<form:errors path="ngayThu"/>
								</div>
							</div>  
							
							<div class="form-group row">
								<div class="col-lg-9 col-xl-8 offset-lg-3">
									<button name="${btnAction}" type="submit"
										class="btn btn-sm btn-outline-primary">Xác nhận</button>
										
									<form:button formaction="ktx/phiktx/${id_NV}.htm" type="submit"
										class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
								</div>
							</div>
						</form:form>
					</div>
					<!--end card-body-->
				</div>
				<!--end card-->

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
