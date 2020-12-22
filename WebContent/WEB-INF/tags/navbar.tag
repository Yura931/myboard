<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="${root }/index.jsp">중앙프로젝트</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  
<div class="container mb-3">
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="${root }/index.jsp"><i class="fas fa-home"></i>Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${root }/article/list.do"><i class="fas fa-list"></i>글 목록</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="${root }/article/write.do"><i class="fas fa-pen-square"></i>글 쓰기</a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
      </li>
    </ul>
    
    <!-- ul.navbar-nav>li.nav-item*2>a.nav-link 로그인 되었을 때만 보이도록-->
    <u:notLogin>
    <ul class="navbar-nav">
    	<li class="nav-item"><a href="${root }/join.do" class="nav-link">회원가입</a></li>
    	<li class="nav-item"><a href="${root }/login.do" class="nav-link">로그인</a></li>
    </ul>
    </u:notLogin>
    
    <u:isLogin>
     <ul class="navbar-nav">
    	<li class="nav-item"><a href="${root }/changePwd.do" class="nav-link">변경</a></li>
    	<li class="nav-item"><a href="${root }/logout.do" class="nav-link">로그아웃</a></li>
    	<li class="nav-item"><a href="${root }/removeMember.do" class="nav-link">탈퇴</a></li>
    </ul>
    </u:isLogin>
  </div>
</div>
</nav>