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
<title>구매처 상품 출고</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>상품 출고</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 납품
						</h4>
						<hr>
						<div style="margin-left: 90%;">
							<a class="btn btn-primary" href ="/FulfillmentService/control/adminServlet?action=download" role="button">납품</a>
						</div>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>발주번호</th>
									<th>구매처</th>
									<th>제품명</th>
									<th>수량</th>
									<th>총 가격</th>
									<th>날짜</th>
									<th>발주상태</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th><a class="btn btn-primary" href ="/FulfillmentService/control/adminServlet?action=download" role="button">구매확인요청</a></th>
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