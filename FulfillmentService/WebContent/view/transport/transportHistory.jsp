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
<title>운송내역조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_transport_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<h3>운송내역조회</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="content-panel">
						<h4>
							<i class="fa fa-angle-right"></i> 운송회사 관리 (운송내역조회)
						</h4>
						<hr>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>출고번호</th>
									<th>송장ID</th>
									<th>운송회사</th>
									<th>날짜</th>
									<th>배송상태</th>
								</tr>
							</thead>
							<!-- Modal -->
							<tbody>
								<tr>
									<th onclick="modal();">출고번호</th>
									<th>송장ID</th>
									<th>이름</th>
									<th>번호</th>
									<th>주소</th>
									<th>날짜</th>
									<td style="margin-right: 10px"><select
										style="text-align: center;">
											<option value="출고">출고</option>
											<option value="배송전">배송전</option>
											<option value="배송중">배송중</option>
											<option value="배송완료">배송완료</option>
									</select></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- 여기에 글 쓰씨면 됩니다. -->
				<div class="row" id="dialog" style="width: auto; height: auto; display: none;">
					<div class="col-md-12">
						<table class="table table-bordered table-striped table-condensed">
							<tr>
								<th>출고번호</th>
								<th>송장</th>
								<th>이름</th>
								<th>번호</th>
								<th>배송상태</th>
							</tr>
							<tr>
								<th>#</th>
								<th>#</th>
								<th><input type="text" size="5px"></th>
								<th>#</th>
								<th>#</th>
							</tr>
							<tr>
								<th colspan="5">
									<button type="button" class="btn btn-primary">배송</button>
									<button type="button" class="btn btn-primary">배송확인요청</button>
								</th>
							</tr>
						</table>
					</div>
				</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
	<!-- ==================================================================== -->
</body>
</html>