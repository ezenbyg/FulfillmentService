<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">


<title>쇼핑몰 송장 내역 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<header>
	<%@ include file="../common/_admin_top.jspf" %>
	</header>
	<nav>
	<%@ include file="../common/_shopping_nav.jspf"%>
	</nav>
	<section>
	<div class="container">
		<div class="row" style="margin-top: 80px">
			<div class="col-md-offset-1 col-md-6">
				<h3>
					<i class="glyphicon glyphicon-list-alt"></i>&nbsp;송장 내역 조회
				</h3>
			</div>
			<div class="col-md-5">
			</div>
			<div class="col-md-12">
				<hr>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="panel panel-primary">
					<table class="table table-striped table-condensed">
						<thead>
						<tr>
							<th class="col-md-2" style="text-align: center;">고객명</th>
							<th class="col-md-2" style="text-align: center;">전화번호</th>
							<th class="col-md-2" style="text-align: center;">주소</th>
							<th class="col-md-2" style="text-align: center;">제품코드</th>
							<th class="col-md-2" style="text-align: center;">제품명</th>
							<th class="col-md-2" style="text-align: center;">수량</th>
						</tr>
						</thead>
						<%-- <tbody>
					    <c:set var="dsList" value="${requestScope.dailySalesList}"/>
						<c:forEach var="dsDto" items="${dsList}">
						<tr>
							<td align="center">${}</td>
							<td align="center">${}</td>
							<td align="center">${}</td>
							<td align="center">${}</td>
							<td align="center">${}</td>
							<td align="center">${}</td>
						</tr>
						</c:forEach>
						</tbody> --%>
					</table>
				</div>
				<!-- 페이지 번호  -->
				<div align="center">
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#">back</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">next</a></li>
					</ul>
				</div>
				<div class="col-md-1"></div>
			</div>
		</div>
	</div>
	</section>
	<footer>
	<%@ include file="../common/_bottom.jspf"%>
	</footer>
	<!-- ==================================================================== -->
</body>
</html>