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
				<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí hóa đơn điện</h1>
					
					
				</div>
				<!--end card-header-->

				<div class="card-body">
<h4 class="card-title">Danh sách hoá đơn điện</h4>
					<!-- id="datatable" -->
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã hoá đơn</th>
								<th>Nhân viên</th>
								<th>Ngày lập</th>
								<th>Số điện đầu</th>
								<th>Số điện cuối</th>
								<th>Giá điện</th>
								<th>Tổng tiền</th>
								<th></th>
								<th></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="hdd" items="${hoaDonDiens}">
								<tr>
									<td>${hdd.maHD}</td>
									<td>${hdd.nhanVien.maNV}</td>
									<fmt:parseDate value = "${hdd.ngayLap}" var = "ngaylap" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaylap}" pattern="dd-MM-yyyy" var="ngayLap" />
									<td>${ngayLap}</td>
									<td>${hdd.chiSoDienDau}</td>
									<td>${hdd.chiSoDienCuoi}</td>
									<td>${hdd.giaDien}</td>
									<td>${hdd.giaDien*(hdd.chiSoDienCuoi-hdd.chiSoDienDau)}</td>
									<td><a class="btnEdit"
										href="ktx/hoadondien/${id_NV}/${hdd.maHD}/${hdd.phong.idPhong}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
										
										<td><a class="btnDelete"
										href="ktx/hoadondien/${id_NV}/${hdd.maHD}/${hdd.phong.idPhong}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>	
											
									<%-- <td><a class="btnDelete"
										href="ktx/hoadondien/del/${id_NV}/${hdd.maHD}/${hdd.phong.idPhong}.htm"><i
											class="ti-trash"></i></a></td> --%>
											
											
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
			<div class="card" style="width: 40%; margin: 0 auto;">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Hoá đơn</h4>
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
					<form:form action="ktx/hoadondien/action/${id_NV}/${id_Phong}.htm" modelAttribute="hoaDonDien">
						
						
						<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã NV</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="nhanVien.maNV" class="form-control"
										type="text" value="${id_NV}" readonly="true" />
										
								</div>
						</div>
						<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã phòng</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="phong.idPhong" class="form-control"
										type="text" value="${id_Phong}" readonly="true" />
								</div>
						</div>
						<fmt:parseDate  value ="${hoaDonDien.ngayLap}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="ngaySinhFT" />	
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày lập</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ngayLap" value="${ngaySinhFT}"  class="form-control" type="date" />
								<form:errors path="ngayLap"/>
							</div>
						</div>
	
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Chỉ số điện đầu</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="chiSoDienDau" class="form-control" type="number" id="dd"/>
								<form:errors path="chiSoDienDau"/>
							</div>
						</div>
						<c:set var="dd" value="${hoaDonDien.chiSoDienDau}" scope="page" />
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Chỉ số điện cuối</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="chiSoDienCuoi" class="form-control" type="number" id="dc"/>
							<form:errors path="chiSoDienCuoi"/>
							</div>
						</div>
						<c:set var="dc" value="${hoaDonDien.chiSoDienCuoi}" scope="page" />
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Giá điện</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="giaDien" class="form-control" type="number" id="gd"/>
							<form:errors path="giaDien"/>
							</div>
						</div>
						
					
						
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
								<button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</button>
							</div>
						</div>
					</form:form>
				</div>
				<!--end card-body-->
			</div>
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
