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
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<title>게시글 목록</title>
<style>
.num-col {
	width: 15%;
}

.title-col {
	width: 35%;
}

.writer-col {
	width: 30%;
}

.read-col {
	width: 20%;
}
</style>
</head>
<body>
	<u:navbar></u:navbar>

  <div class="container">
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <h1>게시글 목록</h1>

        <div class="list-container">
          <table class="table table-striped">
            <thead>
              <tr>
                <th class="num-col"><i class="fab fa-slack-hash"></i></th>
                <th class="title-col">제목</th>
                <th class="read-col"><i class="fas fa-eye"></i></th>
                <th class="writer-col"><i class="fas fa-user-edit"></i></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="article" items="${articlePage.content }">
                <tr>
                  <td class="text-right">${article.number }</td>
                  <td class="position-relative">
                  <a class="stretched-link" href="${root }/article/read.do?no=${article.number }&pageNo=${articlePage.currentPage}">
                    <c:out value="${article.title }" />
                  </a>
                  </td>
                  <td class="text-right">${article.readCount }</td>
                  <td>${article.writer.name }</td>
                </tr>
              </c:forEach>
            </tbody>

          </table>

        </div>
        <div class="mt-5 pagenation-container d-flex justify-content-center">
          <nav aria-label="Page navigation example">
            <ul class="pagination">
              <c:if test="${articlePage.startPage > 5}">
                <li class="page-item"><a class="page-link" href="${root }/article/list.do?pageNo=${articlePage.startPage - 5 }">Previous</a></li>
              </c:if>
              
              <c:forEach begin="${articlePage.startPage }" end="${articlePage.endPage }" var="pNo">
                <li class="page-item"><a class="page-link" href="${root }/article/list.do?pageNo=${pNo}">${pNo }</a></li>
              
              </c:forEach>
              <c:if test="${articlePage.endPage < articlePage.totalPages }">
                <li class="page-item"><a class="page-link" href="${root }/article/list.do?pageNo=${articlePage.startPage + 5 }">Next</a></li>
              </c:if>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>


































<%--
	<div class="container">
		<table border="1">
			<tr>
				<td colspan="4"><a href="write.do">[게시글쓰기]</a></td>
			</tr>
			<tr>
				<td>번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>조회수</td>
			</tr>

			<c:if test="${articlePage.hasNoArticles() }">
				<tr>
					<td colspan="4">게시글이 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach var="article" items="${articlePage.content }">
				<tr>
					<td>${article.number }</td>
					<td><a
						href="read.do?no=${article.number }&pageNo=${articlePage.currentPage}">
							<c:out value="${article.title}" />
					</a></td>
					<td>${article.writer.name }</td>
					<td>${article.readCount }</td>
				</tr>
			</c:forEach>
			<c:if test="${articlePage.hasArticles() }">
				<tr>
					<td colspan="4"><c:if test="${articlePage.startPage > 5}">
							<a href="list.do?pageNo=${articlePage.startPage - 5 }">[이전]</a>
						</c:if> <c:forEach var="pNo" begin="${articlePage.startPage }"
							end="${articlePage.endPage }">
							<a href="list.do?pageNo=${pNo }">[${pNo }]</a>
						</c:forEach> <c:if test="${articlePage.endPage < articlePage.totalPages }">
							<a href="list.do?pageNo=${articlePage.startPage + 5 }">[다음]</a>
						</c:if></td>
				</tr>
			</c:if>
		</table>
	</div>
 --%>
</body>
</html>