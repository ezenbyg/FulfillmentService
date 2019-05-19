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
<title>운송내역조회(창고)</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<h3>운송내역조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (운송내역조회)
						</h4>
						<hr>
						<c:set var="vList" value="${requestScope.vList}" />
						<table class="table table-striped">
							<thead>
								<tr>
									<th>출고번호</th>
									<th>송장ID</th>
									<th>운송회사</th>
									<th>날짜</th>
									<th>배송상태</th>
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
									<th><a class="btn btn-primary btn-xs" href ="/FulfillmentService/control/adminServlet?action=completeDelivery" role="button">배송확정</a></th>
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