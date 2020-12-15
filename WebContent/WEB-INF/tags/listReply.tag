<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<c:forEach items="${replyList }" var="reply">
	<div>
		<input type="text" value="${reply.body }" readonly />
		<span>${reply.memberId }</span>
	</div>
	</c:forEach>
</div>