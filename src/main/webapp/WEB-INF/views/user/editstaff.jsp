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
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Thông tin nhân viên</h4>
						</div>
						<!--end col-->
					</div>
					<!--end row-->
				</div>
				<!--end card-header-->
				<div class="card-body">


					<form:form action="user/edit.htm" modelAttribute="NhanVien" enctype="multipart/form-data">
						<c:choose>
							<c:when test="${check==null}">
								<div class="alert alert-light border-0" role="alert">
									<strong>Vui lòng!</strong> Điền thông tin cần chỉnh sửa.
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
						<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Hình
									ảnh minh họa(nếu có)</label>
								<div class="col-lg-9 col-xl-8">
									<input name="phoToVT" type="file" id="input-file-now"
										class="dropify">
								</div>
							</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
								NV</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maNV" class="form-control" type="text" readonly="true" />
								<form:errors path="maNV"></form:errors>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Họ và tên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="hoTen" class="form-control" type="text" />
								<form:errors path="hoTen"></form:errors>
							</div>
						</div>
						
						<fmt:parseDate  value ="${NhanVien.ngaySinh}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd" var="ngaySinhFT" />
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
								sinh</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ngaySinh" value="${ngaySinhFT}" class="form-control" type="date" />
								<form:errors path="ngaySinh"></form:errors>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Địa
								chỉ</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="diaChi" class="form-control" type="text" />
								<form:errors path="diaChi"></form:errors>
							</div>
						</div>
						
                       
                       
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số
								điện thoại</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-phone"></i></span>
									<form:input path="soDT" type="number" class="form-control"
										placeholder="Phone" aria-describedby="basic-addon1" />
									
								</div>
								<form:errors path="soDT"></form:errors>
							</div>
						</div>
						
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Email</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-at"></i></span>
									<form:input path="email" type="email" class="form-control"
										placeholder="Email" aria-describedby="basic-addon1" />
								</div>
								<form:errors path="email"></form:errors>
							</div>
						</div>

						
							
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<button name="btnEdit" type="submit"
									class="btn btn-sm btn-outline-primary">Thay đổi</button>
								<button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</button>
							</div>
						</div>
					</form:form>
				</div>
				<!--end card-body-->
			</div>
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
