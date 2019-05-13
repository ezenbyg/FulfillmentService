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
	<title>의류</title>
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
			<div class="col-md-offset-1 col-md-11"><h3>${requestScope.title}</h3></div>
			<div class="col-md-12"><hr></div>
			<c:set var="productList" value="${requestScope.storageList}"/>
			<c:set var="count" value="0" />
			<c:forEach var="pDto" items="${productList}">
			<c:if test="${count % 5 == 0}">
				<div class="col-md-1"></div>
			</c:if>
				<div class="col-md-2">
					<div class="thumbnail">
								<img src="${pDto.pImgName}" alt="${pDto.pName}">
						<div class="caption" style="text-align: center;">
							<h4>${pDto.pName}</h4>
							<p>가격: ${pDto.pPrice}원<p>
							<p>수량: ${pDto.pQuantity}개<p>
						</div>
					</div>
				</div>
			<c:if test="${(count+1) % 5 == 4}">
				<div class="col-md-1"></div>
				<div class="col-md-12"><hr></div>
			</c:if>	
			</c:forEach>
		</div>
	</div>
	<center>
	<c:set var="pageList" value="${requestScope.pageList}"/>
	<c:forEach var="pageNo" items="${pageList}">
		${pageNo}
	</c:forEach>
	<footer>
	<footer>
	<%@ include file="../common/_bottom.jspf" %>
	</footer>
</body>
</html>