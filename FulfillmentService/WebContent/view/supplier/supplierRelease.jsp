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
	<%@ include file="../common/_supplier_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>상품 출고</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 납품
						</h4>
						<div class="col-md-offset-9" style="margin-bottom: -15px; margin-top: 10px;">
							<a class="btn btn-primary btn-xs" href ="/FulfillmentService/control/supplierServlet?action=release" role="button">납품</a>
							<form action="/FulfillmentService/control/supplierServlet?action=releasePage&page=1" class="form-horizontal" method="post">
								<input type="date" name="dateRelease" id="datepicker1">&nbsp;
								<input type="submit" class="btn btn-info btn-xs" value="조회">
							</form>
						</div>
						<hr>
						<c:set var="oList" value="${requestScope.oList}"/>
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
							<c:forEach var="oDto" items="${oList}">
								<tr>
									<th>${oDto.oId}</th>
									<th>${oDto.oAdminName}</th>
									<th>${oDto.oProductName}</th>
									<th>${oDto.oQuantity}</th>
									<th>${oDto.oDate}</th>
									<th>${oDto.oState}</th>
									<th><a class="btn btn-primary btn-xs"
									 href ="/FulfillmentService/control/supplierServlet?action=requestConfirm&oState=${oDto.oState}&oId=${oDto.oId}&oDate=${oDto.oDate}" role="button">구매확인요청</a></th>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
			<div>
			<c:set var="pageList" value="${requestScope.supplierReleasePageList}" />
			<c:forEach var="pageNo" items="${pageList}">
				${pageNo}
			</c:forEach>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
	<!-- ==================================================================== -->
</body>
</html>