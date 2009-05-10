<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<html>
<head>
  <link rel="stylesheet" href="/static/css/main.css" type="text/css" />
</head>
<body>
<div id="feedurl">
<%= StringEscapeUtils.escapeHtml(request.getAttribute("feedurl").toString()) %>
</div>
<a href="<%= request.getAttribute("bookmarklet").toString() %>">Add to Boxqueue</a><br /><br />
</body>
</html>