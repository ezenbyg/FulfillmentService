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
<title>송장처리</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>

<section id="main-content">
	<section class="wrapper">
		<h3>송장처리</h3>
		<div class="row">
			<div class="col-md-12">
				<div class="content-panel">
					<h4>
						<i class="fa fa-angle-right"></i> 창고 관리 (송장처리)
					</h4>
					<hr>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>송장번호</th>
								<th>이름</th>
								<th>전화번호</th>
								<th>주소</th>
								<th>날짜</th>
								<th>쇼핑몰 이름</th>
								<th>상태</th>
								<th>수량</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th onclick="modal();">#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
								<th>#</th>
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
						<table class="table table-bordered table-striped table-condensed" style="text-align: center;">
							<tr>
								<td>운송회사이름</td>
								<td></td>
							</tr>
							<tr>	
								<td>쇼핑몰이름</td>
								<td></td>
							</tr>
							<tr>
								<td>송장번호</td>
								<td></td>
							</tr>
							<tr>
								<td>이름</td>
								<td></td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td></td>
							</tr>
							<tr>
								<td>주소</td>
								<td></td>
							</tr>	
							<tr>
								<td>제품명</td>
								<td></td>
							</tr>	
							<tr>
								<td>제품상태</td>
								<td></td>
							</tr>	
							<tr>
								<td>수량</td>
								<td></td>
							</tr>	
							<tr>
								<td>가격</td>
								<td></td>
							</tr>	
							<tr>
							<td colspan="4" align="center">
							<button type="button" class="btn btn-primary">출고</button>&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-primary">발주</button>&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-primary">보류</button>
							</td>
							</tr>	
						</table>
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
function modal(){
	$("#dialog").dialog({
		  dialogClass: "alert",
		});
}
</script>
</html>