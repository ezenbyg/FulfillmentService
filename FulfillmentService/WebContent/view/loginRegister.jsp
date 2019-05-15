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
	<%@ include file="common/_main_top.jspf"%>
<section style="height: 550px;">
	<div class="container">
		<div class="row" style="margin-top: 90px">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="jumbotron">
					<p><h2>회원가입</h2></p>
					<p><h5>Ezen FulfillmentService에 로그인하기 위해 회원가입을 해주세요.</h5></p>
				</div><br>
				<form action="/FulfillmentService/control/loginRegisterServlet?action=register" class="form-horizontal" 
										method="POST" onSubmit="return isValidForm();">
					<div class="form-group">
						<label class="col-md-4 control-label">id</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="cUserId" id="userId">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">이름</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="cName" id="name">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">패스워드</label>
						<div class="col-md-3">
							<input type="password" class="form-control" name="cPassword" id="password">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">패스워드 확인</label>
						<div class="col-md-3">
							<input type="password" class="form-control" name="password2" id="password2">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-4 col-md-6">
							<input class="btn btn-primary" type="submit" value="회원가입">&nbsp;
							<button class="btn btn-default" type="reset" type="button">취소</button>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-1"></div>
		</div>
		</div>
</section>
	<footer>
	<%@ include file="common/_bottom.jspf"%>
	</footer>
</body>
</html>