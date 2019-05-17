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
<title>창고 상세 내역 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>
				<i class="glyphicon glyphicon-list-alt"></i>&nbsp;상세 내역 조회
			</h3>
			<c:set var="ivto" value="${requestScope.ivto}" />
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
			<h4>
				<a href="#"><i class="fa fa-angle-right"></i>  송장번호 :  ${ivto.vId}</a>
			</h4>
			<hr>
			<ul class="nav nav-tabs">
						
						</ul>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>송장번호</th>
						<th>주문처</th>
						<th>주문자</th>
						<th>운송처</th>
						<th>상태</th>
						<th>수량</th>
						<th>총액</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>${ivto.vId}</th>
						<th>${ivto.vShopName}</th>
						<th>${ivto.vName}</th>
						<th>${ivto.vTransportName}</th>
						<th>${ivto.vState}</th>
						<th>${ivto.vQuantity}</th>
						<th>${ivto.vPrice}</th>
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