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
<title>발주내역 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_main_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>발주내역 조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 창고 관리 (발주내역 조회)
						</h4>
						<hr>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>발주번호</th>
									<th>제품코드</th>
									<th>제품명</th>
									<th>발주수량</th>
									<th>총 가격</th>
									<th>날짜</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th onclick="modal();">#</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
									<th>?</th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
		<!-- 여기에 글 쓰씨면 됩니다. -->
				<div class="row" id="dialog" style="display:none;">
					<div class="col-md-12">
						<table class="table table-bordered table-striped table-condensed">
							<tr>
								<td>구매처이름</td>
								<td></td>
							</tr>
							<tr>	
								<td>제품코드</td>
								<td></td>
							</tr>
							<tr>
								<td>제품명</td>
								<td></td>
							</tr>
							<tr>
								<td>발주수량</td>
								<td><input type="text"></td>
							</tr>
							<tr>
								<td>물품가격</td>
								<td></td>
							</tr>
							<tr>
								<td>총가격</td>
								<td></td>
							</tr>	
							<tr>
							<td colspan="2" align="center">
							<button type="button1" class="btn btn-primary">구매확정</button>
							<button type="button1" class="btn btn-primary">취소</button>
							</td>
							</tr>	
						</table>
					</div>
				</div>
		</section>
	</section>
	<%@ include file="../common/_bottom.jspf"%>
	<!-- ==================================================================== -->
</body>

<script>
//데이터 가져올때 여기서 가져오세요
// https://api.jqueryui.com/1.12/dialog/
function modal(){
	$("#dialog").dialog({
		  dialogClass: "alert",
		});
}
</script>
</html>