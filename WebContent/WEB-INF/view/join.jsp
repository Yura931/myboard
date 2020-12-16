<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<title>Insert title here</title>
</head>
<body>
<u:navbar/>
<div class="container">
<div class="row">
		<div class="col-3"></div>
		<div class="col-6">
		<h1>회원 가입</h1>
<form action="${root }/join.do" method="post">
   
   <div class="form-group">
    <label for="input1-id">아이디</label>
    <input type="text" name="id" class="form-control" id="input1-id" >  <!-- id 라벨사용시 id구분을 위해, 라벨은 엘리먼트의 설명, label의 for 값이 input의 id값과 같게 작성-->
    <c:if test="${errors.id }" >
    <small class="form-text text-muted">ID를 입력하세요</small>
    </c:if>
    <c:if test="${errors.duplicateId }">
    <small class="form-text text-muted">이미 사용중인 아이디입니다.</small>
    </c:if>
  </div>
 
  <div class="form-group">
    <label for="input2-name">이름</label>
    <input type="text" name="name" class="form-control" id="input2-name" aria-describedby="emailHelp">
    <c:if test="${errors.name }">
    <small id="nameHelp" class="form-text text-muted">이름을 입력하세요.</small>
    </c:if>
  </div>
  
  <div class="form-group">
    <label for="input3-password">암호</label>
    <input type="password" name="password" class="form-control" id="input3-password">
    <c:if test="${errors.password }">
    <small class="form-text text-muted">암호를 입력하세요.</small>
    </c:if>
  </div>
  
  <div class="form-group">
    <label for="input4-confirmPassword">확인</label>
    <input type="password" name="confirmPassword" class="form-control" id="input4-confirmpassword">   
    <c:if test="${errors.confirmPassword } ">
    <small class="form-text text-muted">확인을 입력하세요.</small>
    </c:if>    
    <c:if test="${errors.notMatch } ">
    <small class="form-text text-muted">암호와 확인이 일치하지 않습니다.</small>
    </c:if>
  </div>
  <input type="submit" class="btn btn-primary" value="회원가입"/>
</form>
</div>
</div>
</div>

<div class="container">
<h1>회원가입</h1>
	<form action="join.do" method="post"> <!-- submit 버튼을 누르면 post방식으로 요청을 보내기 때문에 JoinHandler의 processSubmit메소드가 실행 됨 -->
		<p>
			아이디 : <br />
			<input type="text" name="id" value="${param.id }" /> <!-- 입력에 실패했을 때 그대로 남아있게 하려고 parameter id값을 가져옴 -->
			<c:if test="${errors.id }">ID를 입력하세요.</c:if> <!-- errors라는 이름으로 어트리뷰트에 넣어둠, errors에 id가 있으면 문제가 생긴것 -->
			<c:if test="${errors.duplicateId }">이미 사용중인 아이디입니다.</c:if>
		</p>
		<p>
			이름 : <br />
			<input type="text" name="name" value="${param.name }" />
			<c:if test="${errors.name }">이름을 입력하세요.</c:if>
		</p>
		<p>
			암호 : <br />
			<input type="password" name="password" />
			<c:if test="${errors.password }">암호를 입력하세요.</c:if>
		</p>
		<p>
			확인 : <br />
			<input type="password" name="confirmPassword" />
			<c:if test="${errors.confirmPassword } ">확인을 입력하세요.</c:if>
			<c:if test="${errors.notMatch } ">암호와 확인이 일치하지 않습니다.</c:if>
		</p>
		<input type="submit" value="가입" />
	</form>
</div>
</body>
</html>