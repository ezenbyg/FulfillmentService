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
						<hr>
						<ul class="nav nav-tabs">
							<li role="presentation"><a
								href="../storage/storageOrder.jsp">발주</a></li>
							<li role="presentation" class="active"><a href="#">출고</a></li>
						</ul>
						<c:set var="vList" value="${requestScope.vList}" />
						<c:forEach var="vDto" items="${vList}">
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
								</tr>
							</thead>
							<tbody>
								<tr>
									<th><a data-target="#modal${vDto.vId}" data-toggle="modal">${vDto.vId}</a></th>
									<th>${vDto.vTransportName}</th>
									<th>${vDto.vShopName}</th>
									<th>${vDto.vName}</th>
									<th>${vDto.vTel}</th>
									<th>${vDto.vAddress}</th>
									<th>${vDto.vDate}</th>
							</tbody>
						</table>
						</c:forEach>
					</div>
				</div>
				<!-- modal -->
		<div class="row">
		<c:forEach var="vDto" items="${vList}">
			<div class="modal" id="modal${vDto.vId}" tabindex="-1">
				<div class="modal-dialog modal-md">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title">출고</h4>
						</div>
						<div class="modal-body" style="text-align:center;">
							<table class="table table-default">
							<tr>
								<td>송장번호</td>
								<td>제품명</td>
								<td>수량</td>
								<td>제품상태</td>
							</tr>
							<tr>
								<td>${vDto.vId}</td>
								<td>${vDto.vProductName}</td>
								<td>${vDto.vQuantity}</td>
								<td>${vDto.vProductState}</td>
							</tr>
							<tr>
								<td colspan="4">
								<input type="button" class="btn btn-round btn-primary btn-sm" value="출고">&nbsp;&nbsp;
								<input type="button" class="btn btn-round btn-success btn-sm" value="발주">&nbsp;&nbsp;
								<input type="button" class="btn btn-round btn-warning btn-sm" value="보류">
								</td>
							</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
</html>