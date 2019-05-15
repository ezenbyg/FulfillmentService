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
<title>관리자 모드</title>
<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_admin_top.jspf"%>
	<%@ include file="../common/_storage_nav.jspf"%>
	<section id="main-content">
		<section class="wrapper">
			<div class="row" style="margin-top: 90px">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div class="jumbotron well">
						<p>
						<h2>Ezen Fulfillment Service 창고 관리자 모드</h2>
						</p>
						<p>
						<h4>☞ 물품 정보</h4>
						</p>
						<p>
						<h4>☞ 물품 청구 & 지급</h4>
						</p>
						<p>
						<h4>☞ 물품 발주 & 출고</h4>
						</p>
						<p>
						<h4>☞ 물품 재고 조사</h4>
						</p>
					</div>
				</div>
				<div class="col-md-1"></div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div id="myCarousel" class="carousel slide" data-ride="carousel">
						<!-- Indicators -->
						<ol class="carousel-indicators">
							<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
							<li data-target="#myCarousel" data-slide-to="1"></li>
							<li data-target="#myCarousel" data-slide-to="2"></li>
						</ol>

						<!-- Wrapper for slides -->
						<div class="carousel-inner" role="listbox">
							<div class="item active">
								<img src="../img/가구/musinsa.jpg" alt="무신사">
							</div>
							<div class="item">
								<img src="../img/가구/under.jpg" alt="언더아머">
							</div>
							<div class="item">
								<img src="../img/가구/ikea.jpg" alt="이케아">
							</div>
						</div>

						<!-- Controls -->
						<a class="left carousel-control" href="#myCarousel" role="button"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#myCarousel"
							role="button" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
				</div>
				<div class="col-md-1"></div>
			</div>
		</section>
		<%@ include file="../common/_bottom.jspf"%>
	</section>
</body>
</html>