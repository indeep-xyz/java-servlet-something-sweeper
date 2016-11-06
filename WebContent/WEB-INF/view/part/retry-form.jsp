<%@page import="controller.game.GameMaster"%>
<%@page import="model.Field"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Field field = (Field) session.getAttribute(GameMaster.SESSION_FIELD_DATA);
int width = field.getWidth();
int height = field.getHeight();
int difficulty = field.getDifficulty();
%>

<form action="Config" method="post">
    <input type="hidden" name="width" value="<%= width %>">
    <input type="hidden" name="height" value="<%= height %>">
    <input type="hidden" name="difficulty" value="<%= difficulty %>">
    <p>
        <input type="submit" value="同じ設定で再挑戦">
    </p>
</form>

<form action="Config" method="get">
    <p>
        <input type="submit" value="設定を初期化して最初から">
    </p>
</form>
