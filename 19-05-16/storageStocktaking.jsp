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
<title>재고조사 및 발주</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
<script type="text/javascript">
	function onloadPage(i) {
		location.href = i;
		//console.log(i.value);
	}
</script>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

	<section id="main-content">
		<section class="wrapper">
			<div class="container">
				<h3>재고조사 및 발주</h3>
				<div class="row">
					<div class="col-md-12">
						<div class="content-panel">
							<h4>
								<i class="fa fa-angle-right"></i> 창고 관리 (재고조사 / 발주)
							</h4>
							<hr>
							<div style="margin-left: 50%;">
							<div class="col-md-6">
							<input type="text" placeholder="검색" style="text-align: center;">
							<input type="button" value="검색" class="btn btn-info btn-xs"></div>
								<div class="col-md-6">
								<form name="move" method="post">
									<select id="menu" onChange="onloadPage(this.value);"
										style="border: 5px;">
										<option
											value="/FulfillmentService/control/productServlet?action=category&pathNum=2&page=1&categoryNum=30001&name=supplier">의류</option>
										<option
											value="/FulfillmentService/control/productServlet?action=category&pathNum=2&page=1&categoryNum=30002&name=supplier">식품</option>
										<option
											value="/FulfillmentService/control/productServlet?action=category&pathNum=2&page=1&categoryNum=30003&name=supplier">스포츠</option>
										<option
											value="/FulfillmentService/control/productServlet?action=category&pathNum=2&page=1&categoryNum=30004&name=supplier">가구</option>
										<option
											value="/FulfillmentService/control/productServlet?action=category&pathNum=2&page=1&categoryNum=30005&name=supplier">가전제품</option>
									</select>
								</form>
								</div>
							</div>
							<table class="table table-striped">
								<thead>
									<tr>
										<th>제품코드</th>
										<th>제품명</th>
										<th>가격</th>
										<th>수량</th>
										<th>제품상태</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="stockList" value="${requestScope.stockList}" />
									<c:forEach var="pDto" items="${stockList}">
										<tr>
											<td onclick="modal();" ><a href="/FulfillmentService/control/productServlet?action=modal&pId=${pDto.pId}" >${pDto.pId}</a></td>
											<td>${pDto.pName}</td>
											<td>${pDto.pPrice}</td>
											<td>${pDto.pQuantity}</td>
											<td>${pDto.pState}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="col-md-3"></div>
					</div>
				</div>

				<!-- 여기에 글 쓰씨면 됩니다. -->
				<div class="row" id="dialog" style="display: none;">
					<div class="col-md-12">
						<table class="table table-bordered table-striped table-condensed">
						<c:set var="modal" value="${requestScope.modal}"/>
							<tr>
								<td>구매처이름</td>
								<td>${ProductProc.title}</td>
							</tr>
							<tr>
								<td>제품코드</td>
								<td>${modal.pId}</td>
							</tr>
							<tr>
								<td>제품명</td>
								<td></td>
							</tr>
							<tr>
								<td>발주수량</td>
								<td><input type="text" id="quantity"></td>
							</tr>
							<tr>
								<td>물품가격</td>
								<td>${modal.pPrice}</td>
							</tr>
							<tr>
								<td>총가격</td>
								<td>${modal.pPrice}*${param.quantity}</td>
							</tr>

							<tr>
								<td colspan="2" align="center">
									<button type="button" class="btn btn-primary">발주</button>
									<button type="button" class="btn btn-primary">닫기</button>
								</td>
							</tr>

						</table>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
	<!-- ==================================================================== -->
</body>

<script>
	//데이터 가져올때 여기서 가져오세요
	// https://api.jqueryui.com/1.12/dialog/
	function modal() {
		$("#dialog").dialog({
			dialogClass : "alert",
			$(this).show();
		});
	}
</script>
</html>