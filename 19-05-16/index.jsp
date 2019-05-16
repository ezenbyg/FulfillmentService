<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<style>
body {
	background-color: #2b2b30;
}
</style>
<body>
	<%@ include file="common/_main_top.jspf"%>
	<%@ include file="common/_main_nav.jspf"%>
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper site-min-height">
			<div class="row mt mb">
				<div class="col-lg-12">
					<h3>
						<i class="fa fa-angle-right"></i><b style="color: white;">&nbsp;thumbnail</b>
					</h3>
					<br>
					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="dmbox">
							<div id="myCarousel1" class="carousel slide" data-ride="carousel">
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<li data-target="#myCarousel1" data-slide-to="0" class="active"></li>
									<li data-target="#myCarousel1" data-slide-to="1"></li>
									<li data-target="#myCarousel1" data-slide-to="2"></li>
								</ol>

								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox" align="center">
									<div class="item active">
										<img src="/FulfillmentService/img/의류/기능성티.PNG" alt="의류">
										<!-- <div class="carousel-caption">우노</div> -->
									</div>
									<div class="item">
										<img src="../img/의류/볼트 로우 신발.PNG" alt="의류">
										<!-- <div class="carousel-caption">듀에</div> -->
									</div>
									<div class="item">
										<img src="../img/의류/커버낫 가방.PNG" alt="의류">
										<!-- <div class="carousel-caption">트레</div> -->
									</div>
								</div>

								<!-- Controls -->
								<a class="left carousel-control" href="#myCarousel1"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a> <a class="right carousel-control" href="#myCarousel1"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
								<div class="service-icon">
									<a href="/FulfillmentService/control/productServlet?action=category&categoryNum=30001&name=clothing&pathNum=1"><span class="icn-container"
										style="font-size: 20px;">Clothing</span></a>
								</div>
							</div>
						</div>
					</div>
					<!-- end dmbox -->
					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="dmbox">
							<div id="myCarousel2" class="carousel slide" data-ride="carousel">
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<li data-target="#myCarousel2" data-slide-to="0" class="active"></li>
									<li data-target="#myCarousel2" data-slide-to="1"></li>
									<li data-target="#myCarousel2" data-slide-to="2"></li>
								</ol>

								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox" align="center">
									<div class="item active">
										<img src="/FulfillmentService/img/식품/곱창.PNG" alt="식품">
										<!-- <div class="carousel-caption">우노</div> -->
									</div>
									<div class="item">
										<img src="../img/식품/뉴텔라.PNG" alt="식품">
										<!-- <div class="carousel-caption">듀에</div> -->
									</div>
									<div class="item">
										<img src="../img/식품/페레로로쉐.PNG" alt="식품">
										<!-- <div class="carousel-caption">트레</div> -->
									</div>
								</div>

								<!-- Controls -->
								<a class="left carousel-control" href="#myCarousel2"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a> <a class="right carousel-control" href="#myCarousel2"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
								<div class="service-icon">
									<a href="/FulfillmentService/control/productServlet?action=category&categoryNum=30002&name=food&pathNum=1"><span class="icn-container"
										style="font-size: 20px;">Food</span></a>
								</div>
							</div>
						</div>
					</div>
					<!-- end dmbox -->
					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="dmbox">
							<div id="myCarousel3" class="carousel slide" data-ride="carousel">
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<li data-target="#myCarousel3" data-slide-to="0" class="active"></li>
									<li data-target="#myCarousel3" data-slide-to="1"></li>
									<li data-target="#myCarousel3" data-slide-to="2"></li>
								</ol>

								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox" align="center">
									<div class="item active">
										<img src="../img/스포츠/덤벨세트.png" alt="스포츠">
										<!-- <div class="carousel-caption">우노</div> -->
									</div>
									<div class="item">
										<img src="../img/스포츠/밸런스파워.png" alt="스포츠">
										<!-- <div class="carousel-caption">듀에</div> -->
									</div>
									<div class="item">
										<img src="../img/스포츠/평벤치.png" alt="스포츠">
										<!-- <div class="carousel-caption">트레</div> -->
									</div>
								</div>

								<!-- Controls -->
								<a class="left carousel-control" href="#myCarousel3"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a> <a class="right carousel-control" href="#myCarousel3"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
								<div class="service-icon">
									<a href="/FulfillmentService/control/productServlet?action=category&categoryNum=30004&name=food&pathNum=1"><span class="icn-container"
										style="font-size: 20px;">sports</span></a>
								</div>
							</div>
						</div>
					</div>
					<!-- end dmbox -->
				</div>
				<div class="col-lg-12">
					<br>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="dmbox">
							<div id="myCarousel4" class="carousel slide" data-ride="carousel">
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<li data-target="#myCarousel4" data-slide-to="0" class="active"></li>
									<li data-target="#myCarousel4" data-slide-to="1"></li>
									<li data-target="#myCarousel4" data-slide-to="2"></li>
								</ol>

								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox" align="center">
									<div class="item active">
										<img src="../img/가전제품/노트북.PNG" alt="가전제품">
										<!-- <div class="carousel-caption">우노</div> -->
									</div>
									<div class="item">
										<img src="../img/가전제품/스탠드.PNG" alt="가전제품">
										<!-- <div class="carousel-caption">듀에</div> -->
									</div>
									<div class="item">
										<img src="../img/가전제품/에어프라이.PNG" alt="가전제품">
										<!-- <div class="carousel-caption">트레</div> -->
									</div>
								</div>

								<!-- Controls -->
								<a class="left carousel-control" href="#myCarousel4"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a> <a class="right carousel-control" href="#myCarousel4"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
								<div class="service-icon">
									<a href="/FulfillmentService/control/productServlet?action=category&categoryNum=30003&name=food&pathNum=1"><span class="icn-container"
										style="font-size: 20px;">Electronic</span></a>
								</div>
							</div>
						</div>
					</div>
					<!-- end dmbox -->
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="dmbox">
							<div id="myCarousel5" class="carousel slide" data-ride="carousel">
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<li data-target="#myCarousel5" data-slide-to="0" class="active"></li>
									<li data-target="#myCarousel5" data-slide-to="1"></li>
									<li data-target="#myCarousel5" data-slide-to="2"></li>
								</ol>

								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox" align="center">
									<div class="item active">
										<img src="../img/가구/노튼원목4인식탁세트.png" alt="가구">
										<!-- <div class="carousel-caption">우노</div> -->
									</div>
									<div class="item">
										<img src="../img/가구/레오사각거실장.png" alt="가구">
										<!-- <div class="carousel-caption">듀에</div> -->
									</div>
									<div class="item">
										<img src="../img/가구/클라라9자장롱(화이트).png" alt="가구">
										<!-- <div class="carousel-caption">트레</div> -->
									</div>
								</div>

								<!-- Controls -->
								<a class="left carousel-control" href="#myCarousel5"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a> <a class="right carousel-control" href="#myCarousel5"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
								<div class="service-icon">
									<a href="/FulfillmentService/control/productServlet?action=category&categoryNum=30005&name=food&pathNum=1"><span class="icn-container"
										style="font-size: 20px;">furniture</span></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row content-panel" style="margin: 15px;">
				<h2 class="centered">Ezen FulfillmentService</h2>
				<div class="col-md-10 col-md-offset-1 mt mb">
					<div class="accordion" id="accordion2" style="font-size: 20px;">
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse"
									data-parent="#accordion2" href="faq.html#collapseOne"> <em
									class="glyphicon glyphicon-chevron-right icon-fixed-width"></em>팀원
								</a>
							</div>
							<div id="collapseOne" class="accordion-body collapse in">
								<div class="accordion-inner">
									<p>권종환 : <a href="https://github.com/jonghwankwon">
									https://github.com/jonghwankwon<i class="fa fa-github"></i></a></p>
									<p>백정호 : <a href="https://github.com/BAEKJungHo">https://github.com/BAEKJungHo
									<i class="fa fa-github"></i></a></p>
									<p>유상원 : <a href="https://github.com/sangwonyu">https://github.com/sangwonyu
									<i class="fa fa-github"></i></a></p>
								</div>
							</div>
						</div>
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="faq.html#collapseThree">
								<em class="glyphicon glyphicon-chevron-right icon-fixed-width"></em>제작기간</a>
							</div>
							<div id="collapseThree" class="accordion-body collapse">
								<div class="accordion-inner">
									<p>2019.04.30 ~ 2019.05.17</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="common/_bottom.jspf"%>
	</section>
</body>
</html>
