<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <title>Triangle</title>
    <link rel="stylesheet" type="text/css" href="/css/fileUploadFloor.css">
</head>

<body>
	<div class="floorLabelBox">
		<span>3F</span>
	</div>
	<div class="wrapper">
		<div class="contentsArea ani">
   		 	<div class="titleBox">
   		 		Please select an image to be triangulation.
   		 	</div>
			<div class="fieldBox"> 
	   		 	<div id="dropzone">
	  				<div class="text ani">File Select <br> Drag&amp;Drop</div>
	  				<input id="fileSelect" type="file" accept="image/png, application/pdf" />
				</div>
			</div>
			<div class="fieldBox">
				<div class="captureBox">
					<button class="button webcam ani">WebCam<br>Capture</button>
				</div>
			</div>
			<div class="titleBox down">
   		 		Upload your portrait file. You can get better results. This service is equipped with a face recognition system.
   		 	</div>
		</div>

		<div class="canvasBox">
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
    <script type="text/javascript" src="/js/fileUploadFloor.js"></script>
</body>

</html>
