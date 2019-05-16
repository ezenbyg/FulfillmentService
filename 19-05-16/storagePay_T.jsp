<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- ==================================================================== -->
	<title>대금 청구/지급</title>
	<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf" %>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>청구 / 지급 조회 (창고)</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (청구리스트)
						</h4>
						<hr>
				<ul class="nav nav-tabs">
						  <li role="presentation"><a href="/FulfillmentService/view/storage/storageCharge.jsp">청구</a></li>
						  <li role="presentation" class="active"><a href="#">지급</a></li>
						</ul>
					<div class="col-md-8"></div>
						<div class="col-md-offset-10" style="margin-bottom: -15px; margin-top: 10px;">
							<input type="date" id="datepicker1">&nbsp;
							<input type="button" class="btn btn-info btn-xs" value="조회">
						</div>
						<hr>
					<table class="table">
					<thead>
						<tr>
						<th>
						<form name="move" method="post">
											<select id="Payment" onChange="onloadPage(this.value);" style="border: 5px;">
												<option value="/FulfillmentService/control/payServlet?action=payList&page=1&firstAdminId=4">운송회사</option>
												<option value="/FulfillmentService/control/payServlet?action=payList&page=1&firstAdminId=3">구매처</option>
											</select>
										</form>
						</th>
						<th>관리자 ID</th>
						<th>이름</th>
						<th>지급 가격</th>
						<th>지급 상태</th>
						<th></th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<th>?</th>
							<th>?</th>
							<th>?</th>
							<th style="font-size: 14px"><input type="date" id="datepicker1">&nbsp;&nbsp;</th>
						</tr>
						</tbody>
					</table>
					<hr>
					<div align="center">
						<table class="table table-striped">
							<tr>
								<th>ID</th>
								<th>이름</th>
								<th>건 수</th>
								<th>지급 상태</th>
								<th></th>
							</tr>
							<tr>
								<th>ID</th>
								<th>이름</th>
								<th>건 수</th>
								<th>지급 상태</th>
								<th><input type="submit" class="btn btn-primary" value="지급"></th>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</section>
	<%@ include file="../common/_bottom.jspf"%>
	<script>
	    $.datepicker.setDefaults({
	        dateFormat: 'yy-mm-dd',
	        prevText: '이전 달',
	        nextText: '다음 달',
	        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	        showMonthAfterYear: true,
	        yearSuffix: '년'
	    });
	    $(function() {
	        $("#datepicker1").datepicker();
	    });
	</script>
	<script type="text/javascript">
		function onloadPage(i){
			location.href=i.value;
			//console.log(i.value);
		}
   </script>
   <script>
		/* MonthPicker 옵션 */
		options = {
			pattern : 'yyyy-mm', // Default is 'mm/yyyy' and separator char is not mandatory
			selectedYear : 2014,
			startYear : 2008,
			finalYear : 2018,
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