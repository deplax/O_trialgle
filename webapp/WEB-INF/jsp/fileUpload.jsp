<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <title>Triangle</title>
    <link rel="stylesheet" type="text/css" href="/css/fileInput.css">
</head>

<body>

	<canvas id="previewCanvas" width="500" height="500">
    </canvas>    

	<div class="upload">Select Picture
    	<input id="fileSelect" type="file">
    </div>
    <div class="capture">
    	<button class="captureButton">Capture Start</button>
    </div>


 

    <!-- ScriptArea ============================================== -->

	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <script type="text/javascript" src="/js/delaunay.js"></script>
    <script type="text/javascript" src="/js/fileInput.js"></script>
</body>

</html>