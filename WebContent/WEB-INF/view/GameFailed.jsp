<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Something Sweeper (Failed)</title>
<link rel="stylesheet" href="style/default.css" />

<script type="text/javascript" src="script/Game/game-result.js"></script>
<script type="text/javascript" src="script/Game/field-manager.js"></script>
<script type="text/javascript" src="script/Game/field-loader.js"></script>
<script type="text/javascript" src="script/Game/field-object.js"></script>
<script type="text/javascript" src="script/Game/history-loader.js"></script>
<script type="text/javascript" src="script/Game/history-viewer.js"></script>
<script type="text/javascript" src="script/Game/cell-object.js"></script>
</head>
<body>

<header class="failed">
    <h1>Something Sweeper</h1>
</header>

<h2>FAILED</h2>

<jsp:include page="part/RetryForm.jsp"></jsp:include>

<section id="field"></section>

</body>
</html>