<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- ==================================================================== -->
<title>운송내역조회</title>
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
							<i class="fa fa-angle-right"></i> 운송내역조회
						</h4>
							<form action="/FulfillmentService/control/transportServlet?action=transportHistory&page=1" class="form-horizontal" method="post">
								<input type="date" name="dateRelease" id="datepicker1">&nbsp;
								<input type="submit" class="btn btn-info btn-xs" value="조회">
							</form>
						<hr>
						<c:set var="rList" value="${requestScope.rList}" />
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
							<c:forEach var="rDto" items="${rList}">
								<tr>
									<th>${rDto.rId}</th>
									<th>${rDto.rInvoiceId}</th>
									<th>${rDto.rTransportName}</th>
									<th>${rDto.rDate}</th>
									<th>${rDto.rState}</th>		
									<th><a class="btn btn-primary btn-xs" href ="/FulfillmentService/control/transportServlet?action=startDelivery&rState=${rDto.rState}&rInvoiceId=${rDto.rInvoiceId}" role="button">배송실행</a></th>
									<th><a class="btn btn-primary btn-xs" href ="/FulfillmentService/control/transportServlet?action=requestConfirm&rState=${rDto.rState}&rInvoiceId=${rDto.rInvoiceId}" role="button">배송확인요청</a></th>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
			<div>
			<c:set var="pageList" value="${requestScope.transportHistoryPageList}" />
			<c:forEach var="pageNo" items="${pageList}">
				${pageNo}
			</c:forEach>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
</html>