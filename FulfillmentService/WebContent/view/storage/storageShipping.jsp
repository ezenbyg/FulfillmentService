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
<title>송장처리</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>송장처리</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (송장처리)
						</h4>
						<hr>
						<ul class="nav nav-tabs">
							<li role="presentation" class="active"><a href="#">송장처리</a></li>
							<li role="presentation"><a href="../storage/storageStocktaking.jsp">재고조사</a></li>
						</ul>
						<div style="margin-left: 90%;">
							<a class="btn btn-primary" href ="/FulfillmentService/control/adminServlet?action=download" role="button">다운로드</a>
						</div>
						<table class="table table-striped table-advance table-hover">
							<thead>
								<tr>
									<th>송장번호</th>
									<th>이름</th>
									<th>전화번호</th>
									<th>주소</th>
									<th>날짜</th>
									<th>쇼핑몰</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="invoiceList" value="${requestScope.invoiceList}" />
									<c:forEach var="vDto" items="${invoiceList}">
										<tr>
											<th><a href="/FulfillmentService/control/adminServlet?action=invoiceDetail&vId=${vDto.vId}">${vDto.vId}</a></th>
											<th>${vDto.vName}</th>
											<th>${vDto.vTel}</th>
											<th>${vDto.vAddress}</th>
											<th>${vDto.vDate}</th>
											<th>${vDto.vShopName}</th>
											<th>${vDto.vState}</th>
										</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			<div>
			<c:set var="pageList" value="${requestScope.invoicePageList}" />
			<c:forEach var="pageNo" items="${pageList}">
				${pageNo}
			</c:forEach>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
</html>