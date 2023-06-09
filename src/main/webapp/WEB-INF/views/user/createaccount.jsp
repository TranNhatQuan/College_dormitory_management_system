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
					<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí tài khoản nhân viên</h1>
				</div>
				<!--end card-header-->
				<div class="card-body">
					<!-- id="datatable" -->
					<h4>Danh sách tài khoản nhân viên</h4>
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Họ tên nhân viên</th>
								<th>Mã nhân viên</th>
								<th>Tài khoản</th>
								<th>Mật khẩu</th>
								<th>Phân quyền</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${DangNhaps}">
								<tr>
									<td>${pd.nhanVien.hoTen}</td>
									<td>${pd.nhanVien.maNV}</td>
									<td>${pd.taiKhoan}</td>
									<td>${pd.matKhau}</td>
									<td>${pd.role.tenRole}</td>
									<td><a class="btnEdit"
										href="user/createaccount/${id_NV}/${pd.nhanVien.maNV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
									<td><a class="btnDelete"
										href="user/createaccount/${id_NV}/${pd.nhanVien.maNV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
<c:if test="${show == 'showFormAdd'}">	
			<div class="card" style="width: 40%; margin: 0 auto;">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Tạo tài khoản</h4>
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
						<div class="alert alert-success border-0" role="alert">
								<strong>Thông báo!</strong> ${message}.
							</div>
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


					<form:form action="user/createaccount/action/${id_NV}.htm"
						modelAttribute="dangnhap">						
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã nhân viên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="nhanVien.maNV" value="${maNV}" class="form-control" type="text" readonly="true" />
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tài khoản</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="taiKhoan" class="form-control" type="text" />
								<form:errors path="taiKhoan"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mật
								khẩu</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="matKhau" class="form-control" type="text" />
								<form:errors path="matKhau"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Phân
								Quyền</label>
							<div class="col-lg-9 col-xl-8">
								<form:select path="role.maRole" class="form-select"
									items="${phanQuyens}" itemLabel="tenRole"
									itemValue="maRole" />
							</div>
						</div>
						
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
								<form:button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
							</div>
						</div>
					</form:form>
				</div>
				<!--end card-body-->
			</div>
				</c:if>
				
	<c:if test="${show == 'showEditDel'}">	
			<div class="card" style="width: 40%;">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Sửa xóa tài khoản</h4>
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
						<div class="alert alert-success border-0" role="alert">
								<strong>Thông báo!</strong> ${message}.
							</div>
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


					<form:form action="user/createaccount/action/${id_NV}.htm"
						modelAttribute="dangnhap">						
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên nhân viên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maNV" class="form-control" readonly="true"
											/>
							</div>
						</div>
						
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tài khoản</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="taiKhoan" class="form-control" type="text" />
								<form:errors path="taiKhoan"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mật
								khẩu</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="matKhau" class="form-control" type="text" />
								<form:errors path="matKhau"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Phân
								Quyền</label>
							<div class="col-lg-9 col-xl-8">
								<form:select path="role.maRole" class="form-select"
									items="${phanQuyens}" itemLabel="tenRole"
									itemValue="maRole" />
							</div>
						</div>
						
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
								<form:button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
							</div>
						</div>
					</form:form>
				</div>
				<!--end card-body-->
			</div>
				</c:if>			
				
				
				
				
				
			<!--end card-->
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
