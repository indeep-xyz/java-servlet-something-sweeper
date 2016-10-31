<%@page import="model.Cell"%>
<%@page import="controller.game.GameMaster"%>
<%@page import="model.Field"%>
<%
Field field = (Field) session.getAttribute(GameMaster.SESSION_FIELD_DATA);
int width = field.getWidth();
int height = field.getHeight();
%>

<form action="SomethingSweeper" method="post">
	<section id="field">
	    <% for(int y = 0; y < height; y++) { %>
	    <ul class="row">
	        <% for(int x = 0; x < width; x++) {
	            int index = y * width + x;
	            Cell cell = field.getCell(index);
	            %>
	        <li class="col">
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
	        </li>
	        <% } %>
	    </ul>
	    <% } %>
	</section>
</form>
