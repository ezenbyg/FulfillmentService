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
<title>쇼핑몰 청구 리스트</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_shopping_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<h3>
				<i class="fa fa-angle-right"></i>청구 리스트
			</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 청구리스트
						</h4>
						<hr>
						<div class="col-md-8"></div>
						<div class="col-md-offset-7">
						<form action="/FulfillmentService/control/shopServlet?action=chargeHistory&page=1" class="form-horizontal" method="post">
							<input id="monthpicker" name="monthCharge" type="text" /> 
							<input type="submit" class="btn btn-primary btn-xs" id="btn_monthpicker" value="조회" />
						</form>
						</div>
						<c:set var="gList" value="${requestScope.gList}" />
						<table class="table table-striped">
							<thead>
								<tr>
									<th>청구번호</th>
									<th>청구자</th>
									<th>계좌번호</th>
									<th>지불가격</th>
									<th>날짜</th>
									<th>청구 상태</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="gDto" items="${gList}">
								<tr>
									<th>${gDto.gId}</th>
									<th>${gDto.storageAdminName}</th>
									<th>${gDto.gBankId}</th>
									<th>${gDto.gTotalPrice}</th>
									<th>${gDto.gDate}</th>
									<th>${gDto.gState}</th>
									<th><a class="btn btn-primary btn-xs"
									 href ="/FulfillmentService/control/shopServlet?action=pay&gId=${gDto.gId}&gAdminId=${gDto.gAdminId}&gBankId=${gDto.gBankId}&gTotalPrice=${gDto.gTotalPrice}&gState=${gDto.gState}&gDate=${gDto.gDate}" role="button">지급</a></th>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
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
	<%@ include file="../common/_bottom.jspf"%>
</body>
</html>