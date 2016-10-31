<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マインスイーパ (成功)</title>
<link rel="stylesheet" href="style/default.css" />
</head>
<body>

<%
Random rand = new Random();
String[] colors = {"black", "red", "yellow", "green", "lightgreen", "skyblue", "magenta", "olieve"};
%>

<h1>成功</h1>

<jsp:include page="part/retry-form.jsp"></jsp:include>

<div id="result">
	<div id="message">
		<% for(int i = 0; i < 20; i++) {
			String attr = (i % 2 == 0) ? "" : " direction=\"right\"";
		%>
			<marquee <%= attr %>>
			<% for(int j = 0; j < 16; j++) {
				String color = colors[rand.nextInt(colors.length)];
				%>
				
				<span style="color: <%= color %>">成功</span> 
			<% } %>
			</marquee>
		<% } %>
	</div>
	
    <jsp:include page="part/field-uncovered.jsp"></jsp:include>
</div>

</body>
</html>