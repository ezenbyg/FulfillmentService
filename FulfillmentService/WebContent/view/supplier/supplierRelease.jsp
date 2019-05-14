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
	<%@ include file="../common/_main_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>상품 출고</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 구매처 관리 (상품 출고)
						</h4>
						<hr>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>구매처ID</th>
									<th>제품ID</th>
									<th>수량</th>
									<th>물품 가격</th>
									<th>총 가격</th>
									<th>날짜</th>
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
									<th><input type="button" class="btn btn-success"
										value="출고"></th>
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