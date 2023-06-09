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
					<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí loại phòng</h1>
				</div>
				<!--end card-header-->
				<div class="card-body">
				<h4>Danh sách loại phòng</h4>
					<!-- id="datatable" -->
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã loại phòng</th>
								<th>Tên loại phòng</th>
								<th>Giá phòng</th>
								<th>Số phòng hiện có</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="lp" items="${lphongs}">
								<tr>
									<td>${lp.maLoaiPhong}</td>
									<td>${lp.tenLoaiPhong}</td>
									<td>${lp.gia}</td>
									<td>${lp.phongs.size()}</td>
									
									<td><a class="btnEdit"
										href="user/loaiphong/${id_NV}/${lp.maLoaiPhong}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
											
									<td><a class="btnDelete"
										href="user/loaiphong/${id_NV}/${lp.maLoaiPhong}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
											
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="card">
				<div class="card-header">
					<h4 class="card-title">Loại Phòng</h4>
				</div>
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
					
					<form:form action="user/loaiphong/action/${id_NV}.htm"
						modelAttribute="loaiphong">
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã loại phòng</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maLoaiPhong"  class="form-control" type="text" />
								<form:errors path="maLoaiPhong"/>
							</div>
						</div>
					
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên loại phòng</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="tenLoaiPhong" class="form-control" type="text" />
								<form:errors path="tenLoaiPhong"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Giá</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="gia" class="form-control" type="number" />
								<form:errors path="gia"/>
							</div>
						</div>
						
						
						
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
								<form:button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
								<input class="btn btn-sm btn-outline-primary" type="button" value="Làm mới" onclick="submitForm()" />
									
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
					function submitForm(){
       					
							document.getElementById('maLoaiPhong').value='';
							document.getElementById('gia').value='';
							document.getElementById('tenLoaiPhong').value='';
							}
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
