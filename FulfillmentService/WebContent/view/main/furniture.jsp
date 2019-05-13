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
	<title>가구</title>
	<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<header>
	<%@ include file="../common/_main_top.jspf" %>
	</header>
	<nav>
	<%@ include file="../common/_main_nav.jspf" %>
	</nav>
	<div class="container">
		<div class="row" style="margin-top: 70px">
			<div class="col-md-offset-1 col-md-11"><h3>가구</h3></div>
			<div class="col-md-12"><hr></div>
			<c:set var="productList" value="${requestScope.productList}"/>
			<%	int count = 0; %>
			<c:forEach var="pDto" items="${productList}">
			<%	if (count % 5 == 0) {	%>
				<div class="col-md-1"></div>
			<%	} %>
				<div class="col-md-2">
					<div class="thumbnail">
						<a data-target="#modal<%=count%>" data-toggle="modal">
								<img src="../img/기능성티.png" alt="${pDto.pName}"></a>
						<div class="caption" style="text-align: center;">
							<h4>${pDto.pName}</h4>
							<p>가격: ${pDto.pUnitPrice}원<p>
						</div>
					</div>
				</div>
			<%	if (count++ % 5 == 4) { %>
				<div class="col-md-1"></div>
				<div class="col-md-12"><hr></div>
			<%	} %>
			</c:forEach>
		</div>
	</div>
	<footer>
	<%@ include file="../common/_bottom.jspf" %>
	</footer>
	
	<div class="row">
	<c:set var="productList" value="${requestScope.productList}"/>
	<% count = 0; %>
	<c:forEach var="pDto" items="${productList}">
		<div class="modal" id="modal<%=count%>" tabindex="-1">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">${pDto.pName}</h4>
					</div>
					<div class="modal-body" style="text-align:center;">
					<form action="../control/shoppingControl.jsp?action=buy" class="form-horizontal" method="POST">
						<input type="hidden" name="pId" value="${pDto.pId}">
						<input type="hidden" name="pName" value="${pDto.pName}">
						<input type="hidden" name="pUnitPrice" value="${pDto.pUnitPrice}">
						<table class="table table-default">
							<tr>
								<td class="col-md-3" rowspan="3"><img src="../img/${pDto.pImgName}" alt="${pDto.pName}"></td>
								<td class="col-md-5">${pDto.pDescription}</td></tr>
							<tr><td>가격:&nbsp;&nbsp;&nbsp;${pDto.pUnitPrice}원</td></tr>
							<tr><td>
								주문 수량:&nbsp;&nbsp;&nbsp;<select name="quantity">
									<option value="0">&nbsp;&nbsp;0&nbsp;</option>
									<option value="1" selected>&nbsp;&nbsp;1&nbsp;</option>
									<option value="2">&nbsp;&nbsp;2&nbsp;</option>
									<option value="3">&nbsp;&nbsp;3&nbsp;</option>
									<option value="4">&nbsp;&nbsp;4&nbsp;</option>
									<option value="5">&nbsp;&nbsp;5&nbsp;</option>
								</select>
							</td></tr>
							<tr align="center">
								<td colspan="2">
									<input class="btn btn-primary" type="submit" value="주문">&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</form>
					</div>
				</div>
			</div>
		</div>
		<% count++; %>
		</c:forEach>
	</div>
	<!-- ==================================================================== -->
	<!-- ==================================================================== -->
</body>
</html>