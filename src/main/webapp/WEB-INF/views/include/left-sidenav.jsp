<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="left-sidenav">
	<!-- LOGO -->
	<div class="brand">
		<a href="user/index/${id_NV}.htm" class="logo"> <span> <img
				src="resources/dashtone/assets/images/logo-ptithcm.png" alt="logo-small"
				class="logo-sm">
				
		 </span>
		
		</a>
	</div>
	<!--end logo-->
	<div class="menu-content h-100" data-simplebar>
		<ul class="metismenu left-sidenav-menu">
			<li class="menu-label mt-0">Hệ thống</li>

			<li><a href="javascript: void(0);"><i data-feather="lock"
					class="align-self-center menu-icon"></i><span>Tài khoản</span><span
					class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
				<ul class="nav-second-level" aria-expanded="false">
					<li class="nav-item"><a class="nav-link"
						href="user/index/${id_NV}.htm"><i class="ti-control-record"></i>Trang
							chủ</a></li>
					<%-- <li class="nav-item"><a class="nav-link"
						href="login/lock/${id_NV}.htm"><i class="ti-control-record"></i>Khóa
							màn hình</a></li> --%>
				</ul></li>
			<c:if test="${phanQuyen == 'admin'}">
				<li><a href="javascript: void(0);"><i data-feather="grid"
						class="align-self-center menu-icon"></i><span>Quản lý</span><span
						class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
					<ul class="nav-second-level" aria-expanded="false">

						<li class="nav-item"><a class="nav-link"
							href="user/createaccount/${id_NV}.htm"><i
								class="ti-control-record"></i>Tài khoản nhân viên</a></li>
						<li class="nav-item"><a class="nav-link"
							href="user/list/${id_NV}.htm"><i class="ti-control-record"></i>Quản lí nhân viên</a></li>
								
						

					</ul></li>
			</c:if>
			<hr class="hr-dashed hr-menu">
			<li class="menu-label my-2">Kí túc xá</li>
			<li><a href="javascript: void(0);"> <i data-feather="home"
					class="align-self-center menu-icon"></i><span>Quản lí kí túc xá</span><span
					class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
				<ul class="nav-second-level" aria-expanded="false">
				
					<li class="nav-item"><a class="nav-link"
						href="ktx/sinhvien/${id_NV}.htm"><i
							class="ti-control-record"></i>Quản lí sinh viên</a></li>
							
					<li class="nav-item"><a class="nav-link"
						href="ktx/hopdong/${id_NV}.htm"><i
							class="ti-control-record"></i>Lập hợp đồng</a></li>
					<li class="nav-item"><a class="nav-link"
						href="ktx/qlhopdong/${id_NV}.htm"><i
							class="ti-control-record"></i>Quản lý hợp đồng</a></li>
							
					<li class="nav-item"><a class="nav-link"
						href="ktx/phiktx/${id_NV}.htm">
						<i class="ti-control-record"></i>Phí kí túc xá</a></li>		
						
					<li class="nav-item"><a class="nav-link"
						href="ktx/sotheodoi/${id_NV}.htm"><i
							class="ti-control-record"></i>Sổ theo dõi</a></li>
							
					<li class="nav-item"><a class="nav-link"
						href="ktx/quanlidien/${id_NV}.htm">
						<i class="ti-control-record"></i>Quản lí tiền điện</a></li>
							
					
						
						<li class="nav-item"><a class="nav-link"
							href="user/phong/${id_NV}.htm"><i
								class="ti-control-record"></i>Phòng</a></li>
					<li class="nav-item"><a class="nav-link"
						href="user/loaiphong/${id_NV}.htm">
						<i class="ti-control-record"></i>Loại phòng</a></li>
				</ul></li>
				
		</ul>
	</div>
</div>
