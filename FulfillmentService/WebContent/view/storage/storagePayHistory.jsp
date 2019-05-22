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

<title>창고 청구/지급 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<h3>청구 / 지급 조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (청구리스트)
						</h4>
						<hr>
						<ul class="nav nav-tabs">
							<li role="presentation"><a
								href="/FulfillmentService/view/storage/storageCharge.jsp">청구</a></li>
							<li role="presentation" class="active"><a href="#">지급</a></li>
						</ul>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>출고번호</th>
									<th>운송회사ID</th>
									<th>송장ID</th>
									<th>이름</th>
									<th>번호</th>
									<th>주소</th>
									<th>제품명</th>
									<th>수량</th>
									<th>날짜</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>(출고번호)</th>
									<th>(운송회사ID)</th>
									<th>(송장ID)</th>
									<th>(이름)</th>
									<th>(번호)</th>
									<th>(주소)</th>
									<th>(제품명)</th>
									<th>(수량)</th>
									<th>(날짜)</th>
								</tr>
							</tbody>
						</table>
						<div class="col-md-3"></div>
					</div>
				</div>
			</div>
		</section>
	</section>
	<!-- ==================================================================== -->
</body>
</html>