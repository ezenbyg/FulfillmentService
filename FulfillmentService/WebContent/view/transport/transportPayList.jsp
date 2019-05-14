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
<title>운송회사 대금지급리스트</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_main_top.jspf" %>
	<%@ include file="../common/_transport_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>대금지급리스트</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 운송회사 관리 (대금지급리스트)
						</h4>
						<hr>
						<table class="table table-striped">
							<thead>
								<tr>
								<th>지급번호</th>
								<th>구매처/운송사번호</th>
								<th>지급가격</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
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
</body>
</html>