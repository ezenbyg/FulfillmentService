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
						<div class="col-md-8"></div>
						<div class="col-md-offset-7">
						<form action="/FulfillmentService/control/adminServlet?action=transportHistory&page=1" class="form-horizontal" method="post">
							<input id="monthpicker" name="monthRelease" type="text" /> 
							<input type="submit" class="btn btn-primary btn-xs" id="btn_monthpicker" value="조회" />
						</form>
						</div>
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
									<th><a class="btn btn-primary btn-xs" href ="/FulfillmentService/control/adminServlet?action=completeDelivery&rState=${rDto.rState}&rInvoiceId=${rDto.rInvoiceId}" role="button">배송확정</a></th>
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
	</section>
	<script>
		/* MonthPicker 옵션 */
		options = {
			pattern : 'yyyy-mm', // Default is 'mm/yyyy' and separator char is not mandatory
			selectedYear : 2014,
			startYear : 2008,
			finalYear : 2019,
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ]
		};

		/* MonthPicker Set */
		$('#monthpicker').monthpicker(options);

		/* 버튼 클릭시 MonthPicker Show */
		$('#btn_monthpicker').bind('click', function() {
			$('#monthpicker').monthpicker('show');
		});

		/* MonthPicker 선택 이벤트 */
		$('#monthpicker').monthpicker().bind('monthpicker-click-month',
				function(e, month) {
					alert("선택하신 월은 : " + month + "월");
				});
	</script>
</body>
</html>