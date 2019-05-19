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
<title>창고 발주 / 출고 (출고)</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>발주</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (출고)	
						</h4>
						<div class="col-md-offset-9" style="margin-bottom: -15px; margin-top: 10px;">
							<a class="btn btn-primary btn-xs" href ="/FulfillmentService/control/adminServlet?action=release" role="button">출고</a>
							<form action="/FulfillmentService/control/adminServlet?action=invoiceDaily" class="form-horizontal" method="post">
								<input type="date" name="dateInvoice" id="datepicker1">&nbsp;
								<input type="submit" class="btn btn-info btn-xs" value="조회">
							</form>
						</div>
						<hr>
						<ul class="nav nav-tabs">
							<li role="presentation"><a
								href="../storage/storageOrder.jsp">발주</a></li>
							<li role="presentation" class="active"><a href="#">출고</a></li>
						</ul>
						<c:set var="vList" value="${requestScope.vList}" />
						<table class="table table-striped">
							<thead>
								<tr>
									<th>송장번호</th>
									<th>운송회사이름</th>
									<th>쇼핑몰이름</th>
									<th>이름</th>
									<th>전화번호</th>
									<th>주소</th>
									<th>날짜</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="vDto" items="${vList}">
								<tr>
									<th><a href="/FulfillmentService/control/adminServlet?action=releasePage&vId=${vDto.vId}&date=${vDto.vDate}">${vDto.vId}</a></th>
									<th>${vDto.vTransportName}</th>
									<th>${vDto.vShopName}</th>
									<th>${vDto.vName}</th>
									<th>${vDto.vTel}</th>
									<th>${vDto.vAddress}</th>
									<th>${vDto.vDate}</th>
									<th>${vDto.vState}</th>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<br><br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 송장상세내역	
						</h4>
						<hr>
						<c:set var="invList" value="${requestScope.invList}" />
						<table class="table table-striped">
							<thead>
								<tr>
								<td>송장번호</td>
								<td>제품명</td>
								<td>수량</td>
								<td>제품상태</td>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="invDto" items="${invList}">
								<tr>
								<td>${invDto.vId}</td>
								<td>${invDto.vProductName}</td>
								<td>${invDto.vQuantity}</td>
								<td>${invDto.vProductState}</td>
								</tr>
							</c:forEach>
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