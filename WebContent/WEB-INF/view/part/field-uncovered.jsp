<%@page import="model.field.Field"%>
<%@page import="model.cell.Cell"%>
<%@page import="controller.game.GameMaster"%>
<%
Field field = (Field) session.getAttribute(GameMaster.SESSION_FIELD_DATA);
int width = field.getWidth();
int height = field.getHeight();
%>

<div id="field">
    <% for(int y = 0; y < height; y++) { %>
    <div class="row">
        <% for(int x = 0; x < width; x++) {
            int index = y * width + x;
            Cell cell = field.getCell(index);
            %>
        <div class="col">
            <%= cell.getHtml() %>
        </div>
        <% } %>
    </div>
    <% } %>
</div>