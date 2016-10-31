<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Something Sweeper (Introduction)</title>
<link rel="stylesheet" href="style/default.css" />
</head>
<body>

<header>
    <h1>Something Sweeper</h1>
</header>

<h2>サイズと難易度を入力してください</h2>

<form action="SomethingSweeper" action="get">
	
	<table class="configuration">
		<tr>
			<th>
				width:
			</th>
			<td>
				<input type="text" name="width" value="5">
			</td>
		</tr>
		<tr>
			<th>
				height:
			</th>
			<td>
				<input type="text" name="height" value="5">
			</td>
		</tr>
        <tr>
            <th>
                difficulty:
            </th>
            <td>
			    <ul id="difficulty-list">
			        <% for(int i = 1; i < 6; i++) {
			            String id = "difficulty-" + i;
			            String checked = (i == 2) ? " checked" : "";
			            %>
			                <li>
			                    <label for="<%= id %>"><%= i %></label><br>
			                    <input type="radio" name="difficulty" value="<%= i %>" id="<%= id %>"<%= checked %>>
			                </li>
			        <% } %>
			    </ul>
			    <br clear="all">
            </td>
        </tr>
	</table>
	
    <input type="submit" value="Start">
</form>

</body>
</html>