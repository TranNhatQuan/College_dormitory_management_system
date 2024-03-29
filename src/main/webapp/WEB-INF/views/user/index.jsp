<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>



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

			<div class="container-fluid">
				<!-- Page-Title -->
				<div class="row">
					<div class="col-sm-12">
						<div class="page-title-box">
							<div class="row">
								<div class="col">
									<h4 class="page-title">Thông tin</h4>
									<ol class="breadcrumb">
										<li class="breadcrumb-item active">Thông tin nhân viên</li>
									</ol>
								</div>
								<!--end col-->
								<!--end col-->
							</div>
							<!--end row-->
						</div>
						<!--end page-title-box-->
					</div>
					<!--end col-->
				</div>
				<!--end row-->
				<!-- end page title end breadcrumb -->

				<div class="row">
					<div class="col-12">
						<div class="card">
							<!--end card-body-->
							<div class="card-body">
								<div class="dastone-profile">
									<div class="row">
										<div class="col-lg-4 align-self-center mb-3 mb-lg-0">
											<div class="dastone-profile-main">
												<div class="dastone-profile-main-pic">
												
											
												<img src="resources/img-nv/avt.png"
														alt="" height="110" class="rounded-circle"> <span
														class="dastone-profile_main-pic-change"> <i
														class="fas fa-camera"></i>
													</span>
												
												
													
													
												</div>
												<div class="dastone-profile_user-detail">
													<h5 class="dastone-user-name">${hoTen}</h5>
													<p class="mb-0 dastone-user-name-post">${user.role.tenRole}</p>
												</div>
											</div>
										</div>
										<!--end col-->

										<div class="col-lg-4 ms-auto align-self-center">
											<ul class="list-unstyled personal-detail mb-0">
												<li class=""><i
													class="ti ti-mobile me-2 text-secondary font-16 align-middle"></i>
													<b> Phone </b> : ${user.nhanVien.soDT}</li>
												<li class="mt-2"><i
													class="ti ti-email text-secondary font-16 align-middle me-2"></i>
													<b> Email </b> : ${user.nhanVien.email}</li>
												
											</ul>
										</div>
										<!--end col-->
										<div class="col-lg-4 align-self-center">
											<!--end row-->
										</div>
										<!--end col-->
									</div>
									<!--end row-->
								</div>
								<!--end f_profile-->
							</div>
							<!--end card-body-->
						</div>
						<!--end card-->
					</div>
					<!--end col-->
				</div>
				<!--end row-->
				<div class="pb-4">
					<ul class="nav-border nav nav-pills mb-0" id="pills-tab"
						role="tablist">
						<li class="nav-item"><a class="nav-link active"
							id="Profile_Post_tab" data-bs-toggle="pill" href="#Profile_Post">Thông tin thành viên</a>
						</li>
						<li class="nav-item"><a class="nav-link"
							id="settings_detail_tab" href="user/setting/${id_NV}.htm">Chỉnh sửa thông tin</a></li>
						<li class="nav-item"><a class="nav-link"
							id="portfolio_detail_tab" href="user/changepassword/${id_NV}.htm">Đổi mật khẩu</a></li>
					</ul>
				</div>


				<!-- container -->
				<footer class="footer text-center text-sm-start">
					©
					<script>
						document.write(new Date().getFullYear())
					</script>
					2021 Nhom28 <span
						class="text-muted d-none d-sm-inline-block float-end">
					
					</span>
				</footer>
				<!--end footer-->
			</div>
			<!-- end page content -->
		</div>
		<!-- end page-wrapper -->
	</div>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
