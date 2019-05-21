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
<title>발주내역 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>발주내역 조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (발주내역 조회)
						</h4>
						<div class="col-md-offset-9" style="margin-bottom: -15px; margin-top: 10px;">
							<form action="/FulfillmentService/control/adminServlet?action=orderHistory&page=1" class="form-horizontal" method="post">
							<div class="form-group">
								<input type="month" name="monthOrder" id="monthpicker">&nbsp;&nbsp;
								<input type="submit" class="btn btn-primary btn-xs" value="조회">
							</div>
							</form>
						</div>
						<hr>
						<c:set var="oList" value="${requestScope.orderList}"/>
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
									 href ="/FulfillmentService/control/adminServlet?action=purchaseConfirm&oState=${oDto.oState}&oId=${oDto.oId}&oDate=${oDto.oDate}&oProductId=${oDto.oProductId}&oQuantity=${oDto.oQuantity}" role="button">구매확정</a></th>
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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/jquery.mtz.monthpicker.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/jquery.mtz.monthpicker.js"></script>
	<script>
	    /* MonthPicker 옵션 */
	    var currentYear = (new Date()).getFullYear();
	    var startYear = currentYear-5;
	    var options = {
	            startYear: startYear,
	            finalYear: currentYear,
	            pattern: 'yyyy-mm',
	            monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	    };
		/* MonthPicker Set */
		$('#monthpicker').monthpicker(options);
		/* 버튼 클릭시 MonthPicker Show */
		$('#btn_monthpicker').bind('click', function () {
			$('#monthpicker').monthpicker('show');
		});
	</script>
</body>
</html>