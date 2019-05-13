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
<title>운송내역조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<header>
	<%@ include file="../common/_main_top.jspf" %>
	</header>
	<nav>
	<%@ include file="../common/_transport_nav.jspf"%>
	</nav>
	<section>
	<div class="container">
		<div class="row" style="margin-top: 70px">
			<div class="col-md-offset-1 col-md-11"><h3>운송내역조회</h3></div>
			<div class="col-md-1"></div>
			<div class="col-md-8">
				<div class="panel panel-primary">
					<table class="table table-striped" >
						<thead>
							<tr>
								<th>출고번호</th>
								<th>운송회사ID</th>
								<th>송장ID</th>
								<th>이름</th>
								<th>번호</th>
								<th>주소</th>
								<th>제품명</th>
								<th>수량</th>
								<th>날짜</th>
								<th>배송상태</th>
								<th><input type="button" value="조회"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>출고번호</th>
								<th>운송회사ID</th>
								<th>송장ID</th>
								<th>이름</th>
								<th>번호</th>
								<th>주소</th>
								<th>제품명</th>
								<th>수량</th>
								<th>날짜</th>
								<td style="margin-right: 10px">
									<select style="text-align: center;">        
							            <option value="출고">출고</option>
							            <option value="배송전">배송전</option>
							            <option value="배송중">배송중</option>
							            <option value="배송완료">배송완료</option>
								     </select>
								</td>
								<th></th>
						</tbody>
					</table>
				</div>
				<div class="col-md-3"></div>
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