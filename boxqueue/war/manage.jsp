<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<html>
<head></head>
<body>
Here's the feed: <%= StringEscapeUtils.escapeHtml(request.getAttribute("feedurl").toString()) %><br /><br />
<a href="<%= request.getAttribute("bookmarklet").toString() %>">Add to Boxee</a><br /><br />
</body>
</html>