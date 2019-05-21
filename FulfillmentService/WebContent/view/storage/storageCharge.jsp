<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>대금 청구/지급 조회</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
      <section class="wrapper">
			<i class="fa fa-angle-right"></i><h3>청구 조회</h3>
			 <div class="row">
          <div class="col-md-12">
            <div class="content-panel">
              <h4><i class="fa fa-angle-right"></i> 청구
						</h4>
						<hr>
						<div class="col-md-8"></div>
						<div class="col-md-offset-10" style="margin-bottom: -15px; margin-top: 10px;">
							<input type="date" id="datepicker1">&nbsp;
							<input type="button" class="btn btn-info btn-xs" value="조회">
						</div>
						<hr>
						 <table class="table">
              			  <thead>
								<tr>
									<th><select id="Payment" onChange="onloadPage(this);" style="border: 5px;">
											<option value="storageCharge.jsp">JH쇼핑몰</option>
											<option value="storageCharge_SW.jsp">SW쇼핑몰</option>
											<option value="storageCharge_GJ.jsp">GJ쇼핑몰</option>
										</select>
									</th>
									<th>송장ID</th>
									<th>물품명</th>
									<th>수량</th>
									<th>총가격</th>
									<th>날짜</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>#</td>
									<td>#</td>
									<td>#</td>
									<td>#</td>
									<td>#</td>
									<td>#</td>
									<td><input type="button" class="btn btn-round btn-primary" value="청구"></td>
								</tr>

							</tbody>
						</table>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
		</section>
	<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
</html>