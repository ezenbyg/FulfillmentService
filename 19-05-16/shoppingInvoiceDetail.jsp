<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- ==================================================================== -->
<title>쇼핑몰 송장 내역 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_shopping_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<h3>
				<i class="glyphicon glyphicon-list-alt"></i>&nbsp;송장 내역 조회
			</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
			<h4>
				<i class="fa fa-angle-right"></i> 송장 내역 조회
			</h4>
			<hr>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>제품ID</th>
						<th>건 수</th>
						<th>수수료</th>
						<th>총가격</th>
						<th>날짜</th>
						<th>청구 상태</th>
						<th></th>
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
						<th><input class="btn btn-round btn-primary" type="button" value="청구확정"></th>
					</tr>
				</tbody>
			</table>
			</div>
			</div>
			</div>
		</section>
	<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
</html>