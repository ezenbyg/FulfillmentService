<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
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
<section style="height: 550px;">
	<div class="container join-container" style="margin-left: 30%; margin-top: 4%; margin-left: 25%; height: 400px;">
		<div class="row">
			<div class="col-md-6 login-form-1">
				<h3>회원 가입</h3>
				<form name="registerForm" action="/FulfillmentService/control/loginRegisterServlet?action=register" method=post>
					<div class="form-group">
						<input type="text" name="cUserId" class="form-control" placeholder="Your ID *"
							value="" />
					</div>
					<div class="form-group">
						<input type="text" name="cName" class="form-control" placeholder="Your Name *"
							value="" />
					</div>
					<div class="form-group">
						<input type="password" name="cPassword" id="password" class="form-control"
							placeholder="Your Password *" value="" />
					</div>
					<div class="form-group">
						<input type="password" id="password2" class="form-control"
							placeholder="Your Password  check *" value="" />
					</div>
					<div class="form-group" align="center">
						<input type="submit" class="btnSubmit" value="가입"><br><br> 
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
	<footer>
	<%@ include file="common/_bottom.jspf"%>
	</footer>
	<!-- ==================================================================== -->
</body>
</html>