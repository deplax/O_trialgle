<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="/css/canvas.css">
</head>

<body>
    <canvas id="myCanvas" width="500" height="500">
    </canvas>

    <canvas id="myCanvas2" width="500" height="500">
    </canvas>
	
	<br>
	<form action="/file" method="post" enctype="multipart/form-data">
		<input id="fileSelect" name="key" type="file"><br>
		<input type="submit">
	</form>

	<video id="myvideo" width="1280" height="720"  autoplay></video>
	<!-- <img src="" height="200" alt="Image preview..."> -->
	

    <!-- <input type="file" id="fileUpload" /> -->

    <!-- ScriptArea ============================================== -->

	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <script type="text/javascript" src="/js/delaunay.js"></script>
    <script type="text/javascript" src="/js/canvas.js"></script>
</body>

</html>
