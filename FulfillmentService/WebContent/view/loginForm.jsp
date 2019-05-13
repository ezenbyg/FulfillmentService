<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cUserId = request.getParameter("cUserId");
	String aUserId = request.getParameter("aUserId");
	if ((cUserId != null) && (aUserId == null))
		out.println("<script>alert('고객님께서 사용하실 ID는 " + cUserId + " 입니다.');</script>");
	else if((cUserId == null) && (aUserId != null))
		out.println("<script>alert('고객님께서 사용하실 ID는 " + aUserId + " 입니다.');</script>");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- ==================================================================== -->
<title>Ezen FulfillService</title>
<jsp:include page="common/resource.jspf"></jsp:include>
</head>
<body>
	<header>
		<%@ include file="common/_main_top.jspf"%>
	</header>
	<section style="height: 550px;;">
		<div class="container login-container" style="margin-left: 25%;height: 400px;">
			<div class="row">
				<div class="col-md-4 login-form-1">
					<h3>회원 로그인</h3>
					<form>
						<div class="form-group">
							<input type="text" name="cUserId" class="form-control" placeholder="Your ID *"
								value="" />
						</div>
						<div class="form-group">
							<input type="password" name="cPassword" class="form-control"
								placeholder="Your Password *" value="" />
						</div>
						<div class="form-group">
							<input type="submit" class="btnSubmit" value="Login" />
						</div>
					</form>
				</div>
				<div class="col-md-4 login-form-2">
					<h3>관리자 로그인</h3>
					<form>
						<div class="form-group">
							<input type="text" name="aUserId" class="form-control"
								placeholder="Your Admin ID *" value="" />
						</div>
						<div class="form-group">
							<input type="password" name="aPassword" class="form-control"
								placeholder="Your Password *" value="" />
						</div>
						<div class="form-group">
							<input type="submit" class="btnSubmit" value="Login" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<footer>
		<%@ include file="common/_bottom.jspf"%>
	</footer>

	<!-- 	<div></div>
	<div align="middle"><img src="../img/캡처.PNG"  alt="My Image"></div>
	<div></div>
	<div class="container login-container">
          <div class="row">
              <div class="col-md-6 login-form-1">
                  <h5>회원 로그인</h5>
                  <form action="/FulfillmentService/control/productServlet?action=customerLogin" method="POST" onSubmit="return isValidLogin();">
                      <div class="form-group">
                          <input type="text" class="form-control" name="cusId" placeholder="아이디를 입력하세요" value="" />
                      </div>
                      <div class="form-group">
                          <input type="password" class="form-control" name="cusPassword" placeholder="비밀번호를 입력하세요" value="" />
                      </div>
                      <div class="form-group">
                          <input type="submit" class="btnSubmit" value="Login" />
                           <input type="submit" class="btnSubmit" value="cancel" />
                      </div>
                  </form>
     				</div>

			<div class="col-md-6 login-form-2">
                   <h5>관리자 로그인</h5>
                   <form action="/FulfillmentService/control/productServlet?action=adminLogin" method="POST" onSubmit="return isValidLogin();">
                       <div class="form-group">
                           <input type="text" class="form-control" name="adminId" placeholder="아이디를 입력하세요" value="" />
                       </div>
                       <div class="form-group">
                           <input type="password" class="form-control" name="adminPassword" placeholder="비밀번호를 입력하세요"  value="" />
                       </div>
                       <div class="form-group">
                           <input type="submit" class="btnSubmit" value="Login" />
                            <input type="submit" class="btnSubmit" value="cancel" />
                       </div>
                       
          			</form>
      		   </div>
   			</div>
     </div> -->
</body>
</html>