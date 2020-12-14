<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="value" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
value = value.replace("<", "&lt;");
value = value.replace(">", "&gt;");
value = value.replace("\n", "\n<br>");
value = value.replace("&", "&amp;");
value = value.replace(" ", "&nbsp;");
%>
<%= value %>