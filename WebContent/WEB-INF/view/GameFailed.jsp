<%@page import="controller.game.GameMaster"%>
<%@page import="model.Cell"%>
<%@page import="model.Field"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マインスイーパ (失敗)</title>
<link rel="stylesheet" href="style/default.css" />
</head>
<body>

<%
Field field = (Field) session.getAttribute(GameMaster.SESSION_FIELD_DATA);
int width = field.getWidth();
int height = field.getHeight();
int difficulty = field.getDifficulty();
%>

<h1>失敗</h1>

<form action="SomethingSweeper" method="get">
	<input type="hidden" name="width" value="<%= width %>">
	<input type="hidden" name="height" value="<%= height %>">
	<input type="hidden" name="difficulty" value="<%= difficulty %>">
	<p>
		<input type="submit" value="同じ設定で再挑戦">
	</p>
</form>

<form action="SomethingSweeper" method="get">
	<p>
		<input type="submit" value="設定を初期化して最初から">
	</p>
</form>

<div id="field">
	<% for(int y = 0; y < height; y++) { %>
	<div class="row">
		<% for(int x = 0; x < width; x++) {
			int index = y * width + x;
			Cell cell = field.getCell(index);
			%>
		<div class="col">
			<%
			int aroundSomething = cell.getAroundSomething();
			String className = (cell.isSomething()) ? "something" : "plain";
			%>
			
			<div class="opened <%= className %>">
				<% if (aroundSomething > 0 && !(cell.isSomething())) { %>
					<%= aroundSomething %>
				<% } %>
			</div>
		</div>
		<% } %>
	</div>
	<% } %>
</div>

</body>
</html>