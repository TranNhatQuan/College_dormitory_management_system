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
				<!--PageListHolder  -->
			<%-- 	<jsp:useBean id="pagedListHolder" scope="request"
					type="org.springframework.beans.support.PagedListHolder" /> --%>
				<c:url value="user/list/${id_NV}.htm" var="pagedLink">
					<c:param name="p" value="~" />
				</c:url>
			
	
				<div class="card-header">
					<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí nhân viên</h1>
				</div>
				<!--end card-header-->
				<div class="card-body">
					<!-- id="datatable" -->
					<h4 >Danh sách nhân viên</h4>
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						
						<thead>
							<tr>
								<th >Mã nhân viên</th>
								<th>Họ tên</th>
								<th>Ngày Sinh</th>
								<th>Địa chỉ</th>
								<th>SĐT</th>
								<th>Email</th>
								<th>Quyền</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${nhanViens}">
								<tr>
									<td>${pd.maNV}</td>
									<td>${pd.hoTen}</td>
									
									<fmt:parseDate value = "${pd.ngaySinh}" var = "ngaysinh" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaysinh}" pattern="dd-MM-yyyy" var="ngaySinh1" />
									
									<td>${ngaySinh1}</td>
									<td>${pd.diaChi}</td>
									<td>${pd.soDT}</td>
									<td>${pd.email}</td>
									<c:if test="${pd.taiKhoan != null}">
									<td>${pd.taiKhoan.role.tenRole}</td>
									</c:if>
									<c:if test="${pd.taiKhoan == null}">
									<td><a
												href="user/createaccount/${id_NV}/${pd.maNV}.htm?lnkAdd"
												style="color: blue;">Tạo tài khoản</a>
										</td>
									</c:if>
									<td><a class="btnEdit"
										href="user/list/${id_NV}/${pd.maNV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
									<td><a class="btnDelete"
										href="user/list/${id_NV}/${pd.maNV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
								</tr>

							</c:forEach>

						</tbody>
					</table>

					<%-- <tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" /> --%>

				</div>
			</div>
<!-- ////////////////////// -->
			<div class="card" >
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Nhân Viên</h4>
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
<!-- /////////////////////////////////////////// -->
					<form:form action="user/list/action/${id_NV}.htm" 
						modelAttribute="user" >
						
						
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Họ và tên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="hoTen" class="form-control" type="text" />
								<form:errors path="hoTen"></form:errors>
							</div>
						</div>
						
						<fmt:parseDate  value ="${user.ngaySinh}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/>
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
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary" >Xác nhận</form:button>
									
								<button formaction="user/list/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</button>
									
								<input class="btn btn-sm btn-outline-primary" type="button" value="Làm mới" onclick="submitForm()" />
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
					function submitForm(){
       					
							document.getElementById('maNV').value='';
							document.getElementById('hoTen').value='';
							document.getElementById('ngaySinh').value='';
							document.getElementById('diaChi').value='';
							document.getElementById('soDT').value='';
							document.getElementById('email').value='';
							
       					
							}
				</script>
				2021 Nhom28
			</footer>
			<!--end footer-->
		</div>
		<!-- end page content -->
	</div>
	<!-- end page-wrapper -->
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
