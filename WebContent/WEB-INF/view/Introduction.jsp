<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マインスイーパ (導入)</title>
<link rel="stylesheet" href="style/default.css" />
</head>
<body>

<h1>マインスイーパー</h1>

<h2>サイズと難易度を入力してください</h2>

<form action="SomethingSweeper" action="get">
	
	<table class="configuration">
		<tr>
			<td>
				横幅
			</td>
			<td>
				<input type="text" name="width" value="5">
			</td>
		</tr>
		<tr>
			<td>
				高さ
			</td>
			<td>
				<input type="text" name="height" value="5">
			</td>
		</tr>
	</table>
	
	<table class="configuration">
		<tr>
			<% for(int i = 1; i < 6; i++) {
				String id = "difficulty-" + i;
				String checked = (i == 2) ? " checked" : "";
				%>
			<td>
				<label for="<%= id %>"><%= i %></label>
			</td>
			<td>
				<input type="radio" name="difficulty" value="<%= i %>" id="<%= id %>"<%= checked %>>
			</td>
			<% } %>
	</table>

<input type="submit" value="開始">
</form>

</body>
</html>