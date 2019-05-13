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
<title>Ezen Fulfillment Service</title>
<jsp:include page="common/resource.jspf"></jsp:include>
</head>

<body>
	<header>
		<%@ include file="common/_main_top.jspf" %>
	</header>
	<nav>
	<%@ include file="common/_main_nav.jspf"%>
	</nav>
	<section>
	<form action="">
		<div id="demo" class="carousel slide" data-ride="carousel">
			<div class="row" style="margin-top: 70px">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div class="jumbotron well">
						<p>
						<h2>Ezen Fulfillment Service</h2>
						<p>
						<h4>이젠 창고 서비스는 의류, 식품, 가전제품, 스포츠, 가구를 보관하고 있습니다.</h4>
						<p>
					</div>

						<ul class="carousel-indicators">
							<li data-target="#demo" data-slide-to="0" class="active"></li>
							<li data-target="#demo" data-slide-to="1"></li>
							<li data-target="#demo" data-slide-to="2"></li>
						</ul>

						<div class="carousel-inner">
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

						<a class="carousel-control-prev" href="#demo" data-slide="prev">
						<span class="carousel-control-prev-icon"></span></a>
						<a class="carousel-control-next" href="#demo" data-slide="next">
						<span class="carousel-control-next-icon"></span>
						</a>
					</div>
				</div>
			</div>
		</form>
	<form action="">
		<div id="demo" class="carousel slide" data-ride="carousel">
			<div class="row" style="margin-top: 70px">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div class="jumbotron well">
						<p>
						<h2>Ezen Fulfillment Service</h2>
						<p>
						<h4>이젠 창고 서비스는 의류, 식품, 가전제품, 스포츠, 가구를 보관하고 있습니다.</h4>
						<p>
					</div>

						<ul class="carousel-indicators">
							<li data-target="#demo" data-slide-to="0" class="active"></li>
							<li data-target="#demo" data-slide-to="1"></li>
							<li data-target="#demo" data-slide-to="2"></li>
						</ul>

						<div class="carousel-inner">
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

						<a class="carousel-control-prev" href="#demo" data-slide="prev">
						<span class="carousel-control-prev-icon"></span></a>
						<a class="carousel-control-next" href="#demo" data-slide="next">
						<span class="carousel-control-next-icon"></span>
						</a>
					</div>
				</div>
			</div>
		</form>
	</section>


	<!-- <div class="container">
		<div class="row" style="margin-top: 90px">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="jumbotron well">
					<p><h2>Ezen Fulfillment Service</h2></p>
					<p><h4>이젠 창고 서비스는 의류, 식품, 가전제품, 스포츠, 가구를 보관하고 있습니다.</h4></p>
				</div>
			</div>
			<div class="col-md-1"></div>
		</div>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div id="myCarousel" class="carousel slide" data-ride="carousel">
					Indicators
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>
			
					Wrapper for slides
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="../img/가구/musinsa.jpg" alt="무신사">
							<div class="carousel-caption">우노</div>
						</div>
						<div class="item">
							<img src="../img/가구/under.jpg" alt="언더아머">
							<div class="carousel-caption">듀에</div>
						</div>
						<div class="item">
							<img src="../img/가구/ikea.jpg" alt="이케아">
							<div class="carousel-caption">트레</div>
						</div>
					</div>
			
					Controls
					<a class="left carousel-control" href="#myCarousel"
						role="button" data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span
						class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#myCarousel"
						role="button" data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			<div class="col-md-1"></div>
		</div>
	</div> -->
	<footer>
	<%@ include file="common/_bottom.jspf"%>
	</footer>
</body>
</html>