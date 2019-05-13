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
					<form name="customerLoginForm" action="/FulfillmentService/control/loginRegisterServlet?action=customersLogin" method=post>
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
					<form name="adminLoginForm" action="/FulfillmentService/control/loginRegisterServlet?action=adminsLogin" method=post>
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
</body>
</html>