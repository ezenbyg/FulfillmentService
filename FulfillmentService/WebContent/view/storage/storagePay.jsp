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
<title>대금 청구/지급</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<i class="fa fa-angle-right"></i>
			<h3>청구 / 지급 조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (청구리스트)
						</h4>
						<hr>
						<ul class="nav nav-tabs">
							<li role="presentation"><a
								href="../storage/storageCharge.jsp">청구</a></li>
							<li role="presentation" class="active"><a href="#">지급</a></li>
						</ul>
						<div class="col-md-8"></div>
						<div class="col-md-offset-3">
							기간 조회 : <input id="monthpicker" type="text" /> 
							<input type="button" class="btn btn-primary" id="btn_monthpicker" value="조회" />
						</div>
						<hr>
						<table class="table">
							<thead>
							<tr>
									<th><select id="Payment" onChange="onloadPage(this);"
										style="border: 5px;">
											<option value="storagePay.jsp">구매처</option>
											<option value="storagePay_T.jsp">운송회사</option>
									</select></th>
									<th>송장ID</th>
									<th>물품명</th>
									<th>수량</th>
									<th>총가격</th>
									<th>날짜</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>
										<!--  style="font-size: 14px"><input type="date" id="datepicker1">&nbsp;&nbsp; -->
									</th>
								</tr>
							</tbody>
						</table>
						<div class="panel panel-danger" align="center">
							<table class="table table-striped" >
								<tr>
									<th>회사이름</th>
									<th>계좌</th>
									<th>총가격</th>
									<th></th>
								</tr>
								<tr>
									<th>#</th>
									<th>#</th>
									<th>#</th>
									<th><input type="submit" class="btn btn-primary"
										value="지급"></th>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
	<!-- ==================================================================== -->
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
	<!-- <script>
		$.datepicker.setDefaults({
			dateFormat : 'yy-mm-dd',
			prevText : '이전 달',
			nextText : '다음 달',
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ],
			monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ],
			dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			showMonthAfterYear : true,
			yearSuffix : '년'
		});
		$(function() {
			$("#datepicker1").datepicker();
		});
	</script> -->
</body>
</html>