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
<script type="text/javascript">		// Input 항목 유효성 검사
		function isValidForm() {
			var pwd = document.getElementById("password");
			var pwd2 = document.getElementById("password2");
			var rgx_pwd = /^[a-zA-Z0-9!@#$%^*+=-_]{8,20}$/;	// 패스워드 정규 표현식
return true;
			if (rgx_pwd.test(pwd.val) != true) {
				alert('[PWD 입력 오류] 유효한 패스워드를 입력해 주십시오.');
				pwd.focus();
				return false;
			}
			if (pwd.val != pwd2.val) {
				alert('[PWD 확인 오류] 동일한 패스워드를 입력해 주십시오.');
				pwd2.focus();
				return false
			}
			return true;
		}
	</script>
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
				<form>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Your ID *"
							value="" />
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Your Name *"
							value="" />
					</div>
					<div class="form-group">
						<input type="password" class="form-control"
							placeholder="Your Password *" value="" />
					</div>
					<div class="form-group">
						<input type="password" class="form-control"
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