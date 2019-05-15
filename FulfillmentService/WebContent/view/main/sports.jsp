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
	<title>스포츠</title>
	<jsp:include page="../common/resource.jspf"></jsp:include>
</head>
<body>
	<%@ include file="../common/_main_top.jspf" %>
	<%@ include file="../common/_main_nav.jspf"%>
    <section id="main-content">
      <section class="wrapper site-min-height">
        <h3><i class="fa fa-angle-right"></i> 스포츠</h3>
        <div class="row mt">
          <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
            <div class="project-wrapper">
              <div class="project">
                <div class="photo-wrapper">
                  <div class="photo">
                    <a class="fancybox" href="/FulfillmentService/img/의류/기능성티.PNG">  <img class="../img-responsive" src="/FulfillmentService/img/의류/기능성티.PNG"" alt=""></a>
                    <p style="font-size: 20px;">기능성 티</p>
                  </div>
                  <div class="overlay"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- col-lg-4 -->
          <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
            <div class="project-wrapper">
              <div class="project">
                <div class="photo-wrapper">
                  <div class="photo">
                    <a class="fancybox" href="../img/의류/볼트 로우 신발.PNG"><img class="../img-responsive" src="../img/의류/볼트 로우 신발.PNG" alt=""></a>
                  	 <p style="font-size: 20px;">볼트 로우 신발</p>
                  </div>
                  <div class="overlay"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- col-lg-4 -->
          <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
            <div class="project-wrapper">
              <div class="project">
                <div class="photo-wrapper">
                  <div class="photo">
                    <a class="fancybox" href="../img/의류/사이드홀 로퍼.PNG"><img class="../img-responsive" src="../img/의류/사이드홀 로퍼.PNG" alt=""></a>
                 	 <p style="font-size: 20px;">사이드홀 로퍼</p>
                  </div>
                  <div class="overlay"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- col-lg-4 -->
        </div>
        <!-- /row -->
        <div class="row mt">
          <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
            <div class="project-wrapper">
              <div class="project">
                <div class="photo-wrapper">
                  <div class="photo">
                    <a class="fancybox" href="../img/의류/엔더슨벨 후드.PNG"><img class="../img-responsive" src="../img/의류/엔더슨벨 후드.PNG" alt=""></a>
                   <p style="font-size: 20px;">엔더슨벨 후드</p>
                  </div>
                  <div class="overlay"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- col-lg-4 -->
          <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
            <div class="project-wrapper">
              <div class="project">
                <div class="photo-wrapper">
                  <div class="photo">
                    <a class="fancybox" href="../img/의류/커버낫 가방.PNG"><img class="../img-responsive" src="../img/의류/커버낫 가방.PNG" alt=""></a>
                   <p style="font-size: 20px;">커버낫 가방</p>
                  </div>
                  <div class="overlay"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- col-lg-4 -->
          <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
            <div class="project-wrapper">
              <div class="project">
                <div class="photo-wrapper">
                  <div class="photo">
                    <a class="fancybox" href="../img/의류/크로스백 2.PNG"><img class="../img-responsive" src="../img/의류/크로스백 2.PNG" alt=""></a>
                   <p style="font-size: 20px;">크로스백</p>
                  </div>
                  <div class="overlay"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- col-lg-4 -->
        </div>
        <!-- /row -->
        <div class="row mt mb">
          <!-- col-lg-4 -->
          <!-- col-lg-4 -->
        </div>
        <!-- /row -->
      </section>
      <!-- /wrapper -->
    </section>
    
<%@ include file="../common/_bottom.jspf" %>
  <!-- js placed at the end of the document so the pages load faster -->
  <script src="lib/fancybox/jquery.fancybox.js"></script>
  <script src="lib/bootstrap/js/bootstrap.min.js"></script>
  <script class="include" type="text/javascript" src="lib/jquery.dcjqaccordion.2.7.js"></script>
  <script src="lib/jquery.scrollTo.min.js"></script>
  <script src="lib/jquery.nicescroll.js" type="text/javascript"></script>
  <!--common script for all pages-->
  <script src="lib/common-scripts.js"></script>
  <!--script for this page-->
  <script type="text/javascript">
    $(function() {
      //    fancybox
      jQuery(".fancybox").fancybox();
    });
  </script>
</body>
</html>