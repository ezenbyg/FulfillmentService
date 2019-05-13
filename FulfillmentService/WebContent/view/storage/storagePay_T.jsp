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
	<header>
	<%@ include file="../common/_admin_top.jspf" %>
	</header>
	<nav>
	<%@ include file="../common/_storage_nav.jspf"%>
	</nav>
	<section>
	<div class="container">
		<div class="row" style="margin-top: 70px">
			<div class="col-md-offset-1 col-md-11"><h3>청구 / 지급 (운송회사)</h3></div>
			<div class="col-md-12"><hr></div>
			<div class="col-md-1"></div>
			<div class="col-md-8">
				<div class="panel panel-primary">
				<ul class="nav nav-tabs">
						  <li role="presentation"><a href="/FulfillmentService/view/storage/storageCharge.jsp">청구</a></li>
						  <li role="presentation" class="active"><a href="#">지급</a></li>
						</ul>
					
					<table class="table table-striped">
						<%-- <%@ include file="../common/_storage.jspf" %> --%>
						<tr>
						<th><select id="Payment" onChange="onloadPage(this);" style="border: 5px;">
							<option value="storagePay_T.jsp">운송회사</option>
							<option value="storagePay.jsp">구매처</option>
							</select>
						</th>
						<th>관리자 ID</th>
						<th>이름</th>
						<th>지급 가격</th>
						<th>지급 상태</th>
						<th></th>
						</tr>
						<tr>
							<th>?</th>
							<th>?</th>
							<th>?</th>
							<th style="font-size: 14px"><input type="date" id="datepicker1">&nbsp;&nbsp;</th>
						</tr>
					</table>
					<hr>
					<div class="panel panel-primary">
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
	</div>
	</section>
	<footer>
	<%@ include file="../common/_bottom.jspf"%>
	</footer>
	<!-- ==================================================================== -->
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
</body>
</html>