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
<script type="text/javascript">
	function onloadPage(i) {
		location.href = i;
		//console.log(i.value);
	}
</script>
<!-- ==================================================================== -->
<title>운송회사 지급</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<i class="fa fa-angle-right"></i>
			<h3>지급 조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 운송회사 지급
						</h4>
						<hr>
						<div class="col-md-8"></div>
						<div class="col-md-offset-7">
						<form action="/FulfillmentService/control/adminServlet?action=transportPayPage&page=1" class="form-horizontal" method="post">
							<input id="monthpicker" name="monthPayTransport" type="text" /> 
							<input type="submit" class="btn btn-primary btn-xs" id="btn_monthpicker" value="조회" />
						</form>
						</div>
						<c:set var="spList" value="${requestScope.spList}" />
						<table class="table">
							<thead>
								<tr>
									<th>운송회사</th>
									<th>송장번호</th>
									<th>배송비</th>
									<th>날짜</th>
									<th>지급상태</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="spDto" items="${spList}">
									<tr>
										<td>${spDto.transportName}</td>
										<td>${spDto.soldInvId}</td>
										<td>${spDto.deliveryPrice}</td>
										<td>${spDto.soldDate}</td>
										<td>${spDto.transportState}</td>
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
							<i class="fa fa-angle-right"></i> 계산서	
						</h4>
						<hr>
						<c:set var="sList" value="${requestScope.sList}" />
						<table class="table table-striped">
							<thead>
								<tr>
								<td>업체명</td>
								<td>계좌</td>
								<td>지급금액</td>
								<td></td>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="sDto" items="${sList}">
								<tr>
								<td>${sDto.transportName}</td>
								<td>${sDto.soldBankId}</td>
								<td>${sDto.deliveryPrice}</td>
								<td><a class="btn btn-primary btn-xs" 
									href ="/FulfillmentService/control/adminServlet?action=payForTransport&soldTransportId=${sDto.soldTransportId}&soldBankId=${sDto.soldBankId}&total=${sDto.deliveryPrice}&soldDate=${sDto.soldDate}&soldInvId=${sDto.soldInvId}" role="button">지급</a></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</section>
	</section>
	<!-- ==================================================================== -->
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