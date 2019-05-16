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
			<div class="container">
				<h3>송장처리</h3>
				<div class="row">
					<div class="col-md-12">
						<div class="content-panel">
							<h4>
								<i class="fa fa-angle-right"></i> 창고 관리 (송장처리)
							</h4>
							<hr>
							<div style="margin-left: 90%;">
								<a class="btn btn-primary" href ="/FulfillmentService/control/invoiceServlet?action=download" role="button">다운로드
								</a>
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
											<th>수량</th>
											<th>상태</th>
										</tr>
									</thead>
									<tbody>
									<c:set var="invoiceList" value="${requestScope.invoiceList}" />
									<c:forEach var="vDto" items="${invoiceList}">
										<tr>
											<th onclick="modal1();">${vDto.vId}</th>
											<th>${vDto.vName}</th>
											<th>${vDto.vTel}</th>
											<th>${vDto.vAddress}</th>
											<th>${vDto.vDate}</th>
											<th>${vDto.vShopName}</th>
											<th>${vDto.vQuantity}</th>
											<th onclick="modal2();">${vDto.vState}</th>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<!-- 여기에 글 쓰씨면 됩니다. -->
						<!-- 송장번호 Modal -->
						<div class="row" id="dialog1" style="display: none;">
							<div class="col-md-12">
								<table
									class="table table-bordered table-striped table-condensed"
									style="text-align: center;">
									<tr>
										<td>운송회사이름</td>
										<td>#</td>
									</tr>
									<tr>
										<td>쇼핑몰이름</td>
										<td>#</td>
									</tr>
									<tr>
										<td>송장번호</td>
										<td>#</td>
									</tr>
									<tr>
										<td>이름</td>
										<td>#</td>
									</tr>
									<tr>
										<td>전화번호</td>
										<td>#</td>
									</tr>
									<tr>
										<td>주소</td>
										<td>#</td>
									</tr>
									<tr>
										<td>제품명</td>
										<td>#</td>
									</tr>
									<tr>
										<td>제품상태</td>
										<td>#</td>
									</tr>
									<tr>
										<td>수량</td>
										<td>#</td>
									</tr>
									<tr>
										<td>가격</td>
										<td>#</td>
									</tr>
								</table>
								<div class="col-md-3"></div>
							</div>
							</div>
						</div>
						<!-- 상태 Modal  -->
						<div class="row" id="dialog2" style="display: none;">
							<div class="col-md-12">
								<table
									class="table table-bordered table-striped table-condensed"
									style="text-align: center;">
									<tr>
										<td>이름</td>
										<td>#</td>
									</tr>
									<tr>
										<td>전화번호</td>
										<td>#</td>
									</tr>
									<tr>
										<td>제품명</td>
										<td>#</td>
									</tr>
									<tr>
										<td>수량</td>
										<td>#</td>
									</tr>
									<tr>
										<td>날짜</td>
										<td>#</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
			<c:set var="pageList" value="${requestScope.pageList}" />
			<c:forEach var="pageNo" items="${pageList}">
				${pageNo}
				</c:forEach>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
<script>
	//데이터 가져올때 여기서 가져오세요
	// https://api.jqueryui.com/1.12/dialog/
	function modal1() {
		$("#dialog1").dialog({
			dialogClass : "alert",
			title : "송정정보 상세정보"
		});
	}
	function modal2() {
		$("#dialog2").dialog({
			dialogClass : "alert",
			title : "송장처리 상태 상세정보"
		});
	}
</script>
</html>