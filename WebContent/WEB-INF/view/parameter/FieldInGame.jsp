<%@page import="model.field.IFieldJsp"%>
<%@page import="model.field.Field"%>
<%@page import="controller.game.GameMaster"%>
<%
Field field = (Field) session.getAttribute("field");
%>
<%= field.toJsonInGame() %>