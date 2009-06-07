<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<html>
<head>
<style>
body { background: #ffffff; }
</style>
</head>
<body>
<% if (request.getAttribute("success") != null) { %>
     <p><%= StringEscapeUtils.escapeHtml(request.getAttribute("success").toString()) %></p>
<% } else {
     String errorMsg = "An unexpected error has occurred";
     if (request.getAttribute("error") != null) {
       errorMsg = request.getAttribute("error").toString();
     } %>
     <p><%= StringEscapeUtils.escapeHtml(errorMsg) %></p>
<% } %>
</body>
</html>