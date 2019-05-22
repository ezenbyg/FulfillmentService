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
<title>창고 발주 / 출고 (발주)</title>
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
			<h3>발주</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (발주)
						</h4>
						<div class="col-md-offset-10">
						<form name="move" method="post">
							<select id="menu" onChange="onloadPage(this.value);"
								style="border: 5px;">
								<option
									value="/FulfillmentService/control/adminServlet?action=orderPage&page=1&state=P">P</option>
								<option
									value="/FulfillmentService/control/adminServlet?action=orderPage&page=1&state=재고부족">재고부족</option>
								<option
									value="/FulfillmentService/control/adminServlet?action=orderPage&page=1&state=재고부족예상">재고부족예상</option>
							</select>
						</form>
						</div>
						<hr>
						<c:set var="pList" value="${requestScope.pList}" />
						<table class="table table-striped">
							<thead>
								<tr>
									<th>구매처</th>
									<th>제품명</th>
									<th>보유수량</th>
									<th>제품가격</th>
									<th>제품상태</th>
									<th>발주수량</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="pDto" items="${pList}">
								<tr>
									<th>${pDto.pSupplierName}</th>
									<th>${pDto.pName}</th>
									<th>${pDto.pQuantity}</th>
									<th>${pDto.pPrice}</th>
									<th>${pDto.pState}</th>
									<th class="col-md-3">
									<form action="/FulfillmentService/control/adminServlet?action=order&pId=${pDto.pId}&pSupplierId=${pDto.pSupplierId}&pState=${pDto.pState}&pPrice=${pDto.pPrice}" 
									class="form-horizontal" method="post">
									<input type="text" name="oQuantity" size="15">&nbsp;&nbsp;&nbsp;
									<input type="submit" value="발주" name="W1">
									</form>
									</th>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
			<div>
			<c:set var="pageList" value="${requestScope.orderPageList}" />
			<c:forEach var="pageNo" items="${pageList}">
				${pageNo}
			</c:forEach>
			</div>
		</section>
	</section>
	<!-- ==================================================================== -->
</body>
</html>