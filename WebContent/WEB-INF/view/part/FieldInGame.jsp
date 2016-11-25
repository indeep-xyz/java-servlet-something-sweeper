<%@page import="model.field.IFieldJsp"%>
<%@page import="model.field.Field"%>
<%@page import="model.cell.Cell"%>
<%@page import="controller.game.GameMaster"%>
<%
IFieldJsp field = (Field) session.getAttribute("field");
int width = field.getWidth();
int height = field.getHeight();
%>

<form action="Game" method="post">
	<section id="field">
	    <% for(int y = 0; y < height; y++) { %>
	    <ul class="row">
	        <% for(int x = 0; x < width; x++) {
	            %>
	        <li class="col">
                <%= field.getCellHtmlInGame(x, y) %>
	        </li>
	        <% } %>
	    </ul>
	    <% } %>
	</section>
</form>