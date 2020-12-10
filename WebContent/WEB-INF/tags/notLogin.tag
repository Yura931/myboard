<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag trimDirectiveWhitespaces="true" %>
<c:if test="${empty sessionScope.authUser }">
<jsp:doBody />
</c:if>
