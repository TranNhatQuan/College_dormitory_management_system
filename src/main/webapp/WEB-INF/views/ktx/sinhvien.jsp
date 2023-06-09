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

*[id$=errors] {
	color: red;
	font-style: italic;
}

.modal {
	display: none;
	position: fixed;
	z-index: 1;
	padding-top: 100px;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
	background-color: #fefefe;
	margin: auto;
	border-radius: 4px;
	width: 20%;
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
	<jsp:useBean id="date" class="java.util.Date" />
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
					<h1 class="font-54 fw-bold mt-10 mb-4">Quản lí sinh viên</h1>
 	
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
								
								
								<th>Lớp</th>
							
								
								<th>Đang ở</th>
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
									
									
									<td>${sv.lop}</td>
									
									

									<c:choose>
										<c:when test="${sv.hopDongs.size() == 0}">
											<td>Chưa có phòng</td>
										</c:when>

										<c:otherwise>
											<c:set var="count" value="0" scope="page" />
											<c:forEach items="${sv.hopDongs}" var="hds">
												<fmt:parseDate value="${hds.ngayKetThuc}" var="ngayhethan"
													pattern="yyyy-MM-dd" />
												<c:if test="${ngayhethan > date}">
													<c:set var="count" value="${count + 1}" scope="page" />
													<c:set var="sophong" value="${hds.phong.idPhong}"
														scope="page" />
												</c:if>
											</c:forEach>
											<c:if test="${count >0}">
												<td>${sophong}</td>
											</c:if>
											<c:if test="${count == 0}">
												<td>Chưa đăng kí lại</td>

											</c:if>
											<%-- <fmt:parseDate value = "${sv.hopDongs.get(sv.hopDongs.size()-1).ngayKetThuc}" var = "ngayhethan" pattern = "yyyy-MM-dd"/>
									<c:if test="${ngayhethan > date}"><!--Còn hợp đồng  -->		
												<td>${sv.hopDongs.get(sv.hopDongs.size()-1).phong.idPhong}</td>			
										</c:if>	
										<c:if test="${ngayhethan < date}"><!--Hết hợp đồng  -->		
												<td>Chưa đăng kí lại</td>			
										</c:if>	 --%>
										</c:otherwise>
									</c:choose>
									<td><a class="btnEdit"
										href="ktx/sinhvien/${id_NV}/${sv.maSV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>

									<td><a class="btnDelete"
										href="ktx/sinhvien/${id_NV}/${sv.maSV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
											
										<c:choose>
										<c:when test="${sv.soTheoDois.size() >= 3}">
											<td><a class=" btn btn-sm btn-soft-danger"
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="font-weight: bold;">Vi phạm quá nhiều</a> 
											</td>
											
										</c:when>
										<c:when test="${sv.hopDongs.size() == 0}">
											<td>
												<a class=" btn btn-sm btn-soft-danger"
												href="ktx/hopdong/${id_NV}/${sv.maSV}/.htm?lnkAdd"
												style="font-weight: bold;"> Chưa có hợp đồng</a>
												</td>
											
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
										<td><a class=" btn btn-sm btn-soft-success"
												href="ktx/hopdong/${id_NV}/${sv.maSV}.htm?lnkShow"
												style="font-weight: bold;">Đã có hợp đồng</a> 
											</td>
											
										</c:if>
										<c:if test="${count == 0}">
										<td><a class=" btn btn-sm btn-soft-warning"
												href="ktx/hopdong/${id_NV}/${sv.maSV}/${sv.hopDongs.get(sv.hopDongs.size()-1).phong.idPhong}.htm?lnkAdd"
												style="font-weight: bold;">Hợp đồng hết hạn</a> 
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
			
			
				
			<!-- ////////////////////// -->
	<c:if test="${show == 'showFormAdd'}">		
			<div class="card">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Sinh viên</h4>
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
					<form:form action="ktx/sinhvien/action/${id_NV}.htm" name="geek"
						modelAttribute="sinhvien">

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
								SV</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maSV"  class="form-control" type="text" />
								<form:errors path="maSV" />
							</div>
						</div>
						
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Lớp</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="lop" class="form-control" type="text" />
								<form:errors path="lop" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Họ
								và tên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="hoTen" class="form-control" type="text" />
								<form:errors path="hoTen" />
							</div>
						</div>


						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Giới
								tính</label>
							<div class="col-lg-9 col-xl-8">
								<form:select path="gioiTinh" class="form-select">
									<form:option value="Nam" label="Nam" />
									<form:option value="Nữ" label="Nữ" />
								</form:select>
							</div>
						</div>

						<fmt:parseDate value="${sinhvien.ngaySinh}" var="ngaysinh1"
							pattern="yyyy-MM-dd" />
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd"
							var="ngaySinhFT" />
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
								sinh</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ngaySinh" value="${ngaySinhFT}"
									class="form-control" type="date" />
								<form:errors path="ngaySinh" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">CMND</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="cMND" class="form-control" type="number" />
								<form:errors path="cMND" />
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
								<form:errors path="soDT" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Sdt
								người thân</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-phone"></i></span>
									<form:input path="soDTNguoiThan" type="number"
										class="form-control" placeholder="Phone"
										aria-describedby="basic-addon1" />
								</div>
								<form:errors path="soDTNguoiThan" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Email</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-at"></i></span>
									<form:input path="mail" type="email" class="form-control"
										placeholder="Email" aria-describedby="basic-addon1" />
								</div>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">

								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary" >Xác nhận</form:button>
									
								<button formaction="ktx/sinhvien/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</button>
									
									<input class="btn btn-sm btn-outline-primary" type="button" value="Làm mới" onclick="submitForm()" />
               
               <c:if test="${check == 1}">
               <script> submitForm()</script>
               </c:if>
               
               
               </div>
						</div>
               
								</form:form>
								
								<%-- <input type="submit" value="submit" id="submit">
								<script>
								$(window).load(function() {
									$('form').children('input:not(#submit)').val('')
									}

								</script> --%>

				</div>
				<!--end card-body-->
			</div>
			<!--end card-->
			
</c:if>

<c:if test="${show == 'showFormDel'}">		
			<div class="card">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Xác nhận xóa sinh viên</h4>
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
					<form:form action="ktx/sinhvien/action/${id_NV}.htm"
						modelAttribute="sinhvien">

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
								SV</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maSV" class="form-control" type="text" readonly="true" />
								<form:errors path="maSV" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Lớp</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="lop" class="form-control" type="text" readonly="true"  />
								<form:errors path="lop" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Họ
								và tên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="hoTen" class="form-control" type="text" readonly="true"  />
								<form:errors path="hoTen" />
							</div>
						</div>


						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Giới
								tính</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="gioiTinh" class="form-control" type="text" readonly="true"  />
							</div>
						</div>

						<fmt:parseDate value="${sinhvien.ngaySinh}" var="ngaysinh1"
							pattern="yyyy-MM-dd" />
						<fmt:formatDate value="${ngaysinh1}" pattern="yyyy-MM-dd"
							var="ngaySinhFT" />
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
								sinh</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ngaySinh" value="${ngaySinhFT}"
									class="form-control" type="date" readonly="true" />
								<form:errors path="ngaySinh" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">CMND</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="cMND" class="form-control" type="number" readonly="true" />
								<form:errors path="cMND" />
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
										placeholder="Phone" aria-describedby="basic-addon1" readonly="true" />
									<form:errors path="soDT" />
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Sdt
								người thân</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-phone"></i></span>
									<form:input path="soDTNguoiThan" type="number"
										class="form-control" placeholder="Phone"
										aria-describedby="basic-addon1" readonly="true" />
									<form:errors path="soDTNguoiThan" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Email</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-at"></i></span>
									<form:input path="mail" type="email" class="form-control"
										placeholder="Email" aria-describedby="basic-addon1" readonly="true"  />
								</div>
							</div>
						</div>
						<div id="myModal" class="modal">
						
									
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title" id="exampleModalFullscreenMdLabel">Thông báo</h1>
					</div>
					<span class="close"></span>
					<div class="modal-body">                                                    
                      <h2>Xác nhận xóa</h2>
                  </div>
					<div class="modal-footer">                                                    
                      <form:button name="${btnAction}" type="submit" class="btn btn-sm btn-outline-primary" >Xác nhận</form:button>
                      <button formaction="ktx/sinhvien/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger" >Hủy bỏ</button>
                  </div>

				</div>
				
			</div>
			
						</form:form>
						
						
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
							
								<%-- <form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button> --%>
									
									<button id="myBtn" class="btn btn-sm btn-outline-primary">Xóa</button>
									
								<button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger" >Hủy bỏ</button>
									
							</div>
						</div>
					
				</div>
				<!--end card-body-->
			</div>
			<!--end card-->
			
</c:if>


			


			<!-- container -->
			<footer class="footer text-center text-sm-start">
				©
				<script>
					document.write(new Date().getFullYear());
					
       				function submitForm(){
       					
							document.getElementById('maSV').value='';
							document.getElementById('hoTen').value='';
							document.getElementById('ngaySinh').value='';
							document.getElementById('cMND').value='';
							document.getElementById('soDT').value='';
							document.getElementById('lop').value='';
							document.getElementById('soDTNguoiThan').value='';
							document.getElementById('mail').value='';
       					
							}

					
					var modal = document.getElementById("myModal");
					var btn = document.getElementById("myBtn");
					var span = document.getElementsByClassName("close")[0];
					btn.onclick = function() {
						modal.style.display = "block";
					}

					span.onclick = function() {
						modal.style.display = "none";
					}

					window.onclick = function(event) {
						if (event.target == modal) {
							modal.style.display = "none";
						}
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
