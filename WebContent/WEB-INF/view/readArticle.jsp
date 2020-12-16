<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Insert title here</title>
<style>
.border {width =100%;
	
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-3"></div>
			<div class="col-6">
				<h1 class="text-center">게시글</h1>

				<table class="table table-sm">
					<thead>
						<tr>
							<th scope="col" class="text-center">번호</th>
							<th scope="col" class="text-center">작성자</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center">${articleData.article.number }</td>
							<td class="text-center">${articleData.article.writer.name }</td>
						</tr>
					</tbody>
				</table>

				<div class="card">
					<div class="card-header">${articleData.article.title }</div>
					<div class="card-body">
						<p class="card-text">${articleData.content.content }</p>
					</div>
				</div>

				<div class="card-footer text-muted mt-5">
					<nav class="nav">
						<c:set var="pageNo"
							value="${empty param.pageNo ? '1' : param.pageNo }" />
						<a class="nav-link active" href="list.do?pageNo=${pageNo}">목록</a>
						<c:if test="${authUser.id == articleData.article.writer.id}">
							<a class="nav-link"
								href="modify.do?no=${articleData.article.number }">게시글 수정</a>
							<a class="nav-link"
								href="delete.do?no=${articleData.article.number }">게시글 삭제</a>
						</c:if>
					</nav>
					
				</div>
			</div>
		</div>
	</div>
























<%--
	<div class="container">
		<table border="1" width="100%">
			<tr>
				<td>번호</td>
				<td>${articleData.article.number }</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${articleData.article.writer.name }</td>
			</tr>
			<tr>
				<td>제목</td>
				<td><c:out value="${articleData.article.title }"></c:out></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><u:pre value="${articleData.content.content }" /></td>
			</tr>



			<tr>
				<td colspan="2"><c:set var="pageNo"
						value="${empty param.pageNo ? '1' : param.pageNo }" /> <a
					href="list.do?pageNo=${pageNo}">[목록]</a> <c:if
						test="${authUser.id == articleData.article.writer.id}">
						<a href="modify.do?no=${articleData.article.number }">[게시글수정]</a>
						<a href="delete.do?no=${articleData.article.number }">[게시글삭제]</a>
					</c:if></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>

	 
    로그인 한 경우만
    댓글 폼 출력
    
   
		<u:replyForm articleNo="${articleData.article.number }" />

		<u:listReply />
	</div>
 --%>
	
</body>
</html>
































