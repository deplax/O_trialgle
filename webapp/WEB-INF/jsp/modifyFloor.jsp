<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <title>Triangle</title>
    <link rel="stylesheet" type="text/css" href="/css/modifyFloor.css">
</head>

<body>
	<div class="floorLabelBox">
		<span>4F</span>
	</div>
	<div class="wrapper">
		<div class="canvasBox ani">
			<div>
				<canvas id="previewCanvas">
   		 		</canvas>
   		 	</div>
   		 	<div>
				<div>
					<button class="nbutton upload">upload</button>
				</div>
				<div>
					<button class="nbutton cancel">cancel</button>
				</div> 
   		 	</div>
		</div>
	</div>
    <!-- ScriptArea -->

	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <script type="text/javascript" src="/js/delaunay.js"></script>
    <script type="text/javascript" src="/js/modifyFloor.js"></script>
</body>

</html>
