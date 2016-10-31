<%@page import="model.Cell"%>
<%@page import="model.Field"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マインスイーパ (ゲーム本編)</title>
<link rel="stylesheet" href="style/default.css" />
</head>
<body>

<%
Field table = (Field) session.getAttribute("table");
int width = table.getWidth();
int height = table.getHeight();
%>

<h1>マインスイーパ</h1>

<form action="SomethingSweeper" method="post">
<div id="field">
	<% for(int y = 0; y < height; y++) { %>
	<div class="row">
		<% for(int x = 0; x < width; x++) {
			int index = y * width + x;
			Cell cell = table.getCell(index);
			%>
		<div class="col">
			<% if (cell.isOpen()) { 
				int aroundSomething = cell.getAroundSomething(); %>
				
				<div class="opened plain">
					<% if (aroundSomething > 0) { %>
						<%= aroundSomething %>
					<% } %>
				</div>
			<% } else { %>
				<input type="submit" name="clicked" value="<%= index %>">
			<% } %>
		</div>
		<% } %>
	</div>
	<% } %>
</div>
</form>

</body>
</html>