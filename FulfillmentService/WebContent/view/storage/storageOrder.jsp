<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- ==================================================================== -->
<title>창고 발주 / 출고 (발주)</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<h3>발주</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (청구리스트)
						</h4>
						<hr>
						<ul class="nav nav-tabs">
							<li role="presentation" class="active"><a href="#">발주</a></li>
							<li role="presentation"><a href="/main/storageShipping.jsp">출고</a></li>
						</ul>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>날짜</th>
									<th>구매처ID</th>
									<th>제품ID</th>
									<th>수량</th>
									<th>물품가격</th>
									<th>총가격</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>#</th>
									<th>#</th>
									<th>#</th>
									<th>#</th>
									<th>#</th>
									<th>#</th>
									<th>
									<th class="col-md-3"><input class="btn btn-primary btn-sm"
										type="button" value="발주하기"> &nbsp;&nbsp;<input
										class="btn btn-primary btn-sm" type="reset" value="취소">
									</th>
								</tr>

							</tbody>
						</table>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
	<!-- ==================================================================== -->
</body>
</html>