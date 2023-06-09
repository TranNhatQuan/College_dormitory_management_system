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
					<h1 class="font-54 fw-bold mt-10 mb-4">Sổ theo dõi</h1>
					<%-- <fmt:formatDate value="${date}" pattern="MM-dd-yyyy" var="myDate" /> --%>
					
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
								
								
								<th>Số lần phạm lỗi</th>
								<th></th>
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
									
									
									<td>${sv.soTheoDois.size()}</td>
									<td><a
										href="ktx/sotheodoi/${id_NV}/${sv.maSV}.htm?lnkShow"
										style="color: #1761fd;">Sổ theo dõi</a> 
									</td>
									<td><a
										href="ktx/sotheodoi/${id_NV}/${sv.maSV}.htm?lnkAdd"
										style="color: #1761fd;">Lập biên bản</a> 
									</td>
									<c:choose>
													
										<c:when test="${sv.soTheoDois.size() >= 3}">
											<td><a
										href="ktx/sotheodoi/${id_NV}/${sv.maSV}.htm?lnkDel"
										style="color: red;">Chấm dứt hợp đồng</a> 
												</td>
										</c:when>
										<c:otherwise>
										<td></td>
										</c:otherwise>
										</c:choose>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
			
			<c:if test="${show == 'showFormAdd'}">
			<div class="card" style="width: 40%;margin: 0 auto; ">
				<div class="card" >
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Lập biên bản</h4>
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

						<form:form action="ktx/sotheodoi/action/${id_NV}/${sinhVien.maSV}.htm"
						modelAttribute="sotheodoi">
							
							 
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Nhân viên</label>
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
							
						<%-- <fmt:parseDate  value ="${sotheodoi.ngayGhiNhan}" var = "ngaysinh1" pattern = "yyyy-MM-dd"/> --%>
						
						<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="ngaySinhFT" />
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
								ghi nhận</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ngayGhiNhan" value="${ngaySinhFT}" class="form-control" type="date" />
							<form:errors path="ngayGhiNhan"/>
							</div>
						</div>
						
								<div class="form-group row" >
								
								<div class="col-lg-9 col-xl-10" style="margin: 0 auto; ">
							<div class="form-floating">
                                        <textarea class="form-control" placeholder="Leave a comment here" id="lyDo" name="lyDo" style="height: 109px;"></textarea>
                                        <label for="floatingTextarea2">Lý do</label>
  <form:errors path="lyDo"/>
                                      </div>
                                      	</div>
							</div>
							
							<div class="form-group row" >
								<div class="col-lg-9 col-xl-8 offset-lg-3">
									<button name="${btnAction}" type="submit"
										class="btn btn-sm btn-outline-primary">Xác nhận</button>
										
									<form:button formaction="user/index/${id_NV}.htm" type="submit"
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
			<c:if test="${show1 ==  'show'}">
			<div class="card">
			<!-- id="datatable" -->
					<table id="datatable1"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã KTKL</th>
								<th>Ngày ghi nhận</th>
								<th>Lý do</th>
								<th>Mã sinh viên</th>
								<th>Họ và tên</th>
								<th>Mã nhân viên</th>
								
								<th></th>
								
								

							</tr>
						</thead>
						<tbody>
							<c:forEach var="std" items="${soTheoDois}">
								<tr>
									<td>${std.makl}</td>
									<fmt:parseDate value = "${std.ngayGhiNhan}" var = "ngaysinh" pattern = "yyyy-MM-dd"/>
									<fmt:formatDate value="${ngaysinh}" pattern="dd-MM-yyyy" var="ngaySinh" />
										<td>${ngaySinh}</td>
									<td>${std.lyDo}</td>
									<td>${std.sinhVien.maSV}</td>
									<td>${std.sinhVien.hoTen}</td>
									
									<td>${std.nhanVien.maNV}</td>
									
									<td><a class="btnDelete"
										href="ktx/sotheodoi/delete/${id_NV}/${std.makl}/${std.sinhVien.maSV}.htm"><i
											class="ti-trash"></i></a></td>
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
