
function CanvasDrawer(canvasName) {
    this.canvas;
    this.ctx;
    this.triangles = [];
    this.imageString;
    this.file;
    this.points;
    this.pixels = [];

    this.init(canvasName);
    //this.setFile("#fileSelect");
};

CanvasDrawer.prototype.init = function(canvasName) {
    this.canvas = $(canvasName);
    this.ctx = this.canvas[0].getContext("2d");
    this.getData().then(function(arrPointObj){
    	this.makeVertex(arrPointObj);
    }.bind(this));

};

CanvasDrawer.prototype.getData = function(){
	return new Promise(function(resolve, reject) {
		$.ajax({
	    	url: "/modifyFloor/getPointList",
	    	type: "GET",
	    	success: function(arrPointObj) {
	        	resolve(arrPointObj);
	    	},
	    	error: function(e) {
		        reject(e);
		    }
		});
	});
};

CanvasDrawer.prototype.makeVertex = function(arrPointObj) {
	var data = arrPointObj;
	var vertices = [];
	$.each(data, function(index, point) {
		v = new Vertex(point.x, point.y);
		vertices.push(v);
	}.bind(this));
	this.vertices = vertices;
	this.triangles = triangulate(this.vertices);


	////////////////
	this.hHeight = localStorage.getItem("hiddenHeight");
	this.hWidth = localStorage.getItem("hiddenWidth");

	this.setSvg();
	//this.drawPoint();
	//this.getPixel(10, 10);
	this.drawTriangle();
};

CanvasDrawer.prototype.setSvg = function(){
	$("#svg").attr("height", this.height);
	$("#svg").attr("width", this.width);	
};

CanvasDrawer.prototype.drawPoint = function(){
	for(var i = 0; i < this.vertices.length; i++){
		var circle = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
		var cx = this.calcPointX(this.vertices[i].x);
		var cy = this.calcPointY(this.vertices[i].y);
		Math.round(this.vertices[i].y * (this.height / this.hHeight));
		$(circle).attr("cx", cx);
		$(circle).attr("cy", cy);
		$(circle).attr("r", 1);
		$("#svg").append(circle);
	}
};

CanvasDrawer.prototype.calcPointX = function(x){
	return Math.round(x * (this.width / this.hWidth));
}

CanvasDrawer.prototype.calcPointY = function(y){
	return Math.round(y * (this.width / this.hWidth));
}

CanvasDrawer.prototype.drawTriangle = function(){
	for(var i = 0; i < this.triangles.length; i++){
		var path = new Path2D();
		path.moveTo(this.calcPointX(this.triangles[i].v0.x), this.calcPointY(this.triangles[i].v0.y));
		path.lineTo(this.calcPointX(this.triangles[i].v1.x), this.calcPointY(this.triangles[i].v1.y));
		path.lineTo(this.calcPointX(this.triangles[i].v2.x), this.calcPointY(this.triangles[i].v2.y));

		this.ctx.fillStyle = this.triangleAvgColor(this.triangles[i]);
		console.log(this.ctx.fillStyle);
    	this.ctx.fill(path);
	}
}

CanvasDrawer.prototype.getPixel = function(x, y){
	var imageData = this.ctx.getImageData(0, 0, this.width, this.height);

	var i = ((y * this.width) + x) * 4;
	var r = imageData.data[i];        
    var g = imageData.data[i+1];
    var b = imageData.data[i+2];
    var a = imageData.data[i+3];
    var pixel = {r, g, b};
    return pixel;
};

CanvasDrawer.prototype.triangleAvgColor = function(triangle){
	var objectArr = [];
	var v0 = {
		"x" : this.calcPointX(triangle.v0.x),
		"y" : this.calcPointY(triangle.v0.y)
	};
	var v1 = {
		"x" : this.calcPointX(triangle.v1.x),
		"y" : this.calcPointY(triangle.v1.y)
	};
	var v2 = {
		"x" : this.calcPointX(triangle.v2.x),
		"y" : this.calcPointY(triangle.v2.y)
	};
	objectArr.push(v0);
	objectArr.push(v1);
	objectArr.push(v2);
	this.arrSort(objectArr);
	v1 = objectArr[0];
	v2 = objectArr[1];
	v3 = objectArr[2];

	if(v2.y === v3.y)
		this.fillBottomFlatTriangle(v1, v2, v3);
	else if(v1.y === v2.y)
		this.fillTopFlatTriangle(v1, v2, v3);
	else{
		v4 = new Vertex(Math.round(v1.x + (v2.y - v1.y) / (v3.y - v1.y) / (v3.y - v1.y) * (v3.x - v1.x)), v2.y);
		this.fillBottomFlatTriangle(v1, v2, v4);
		this.fillTopFlatTriangle(v2, v4, v3);
	}

	return this.avgPixels();
}

CanvasDrawer.prototype.avgPixels = function(){
	var r = 0;
	var g = 0;
	var b = 0;
	for(var i = 0; i < this.pixels.length; i++){
		r += this.pixels[i].r;
		g += this.pixels[i].g;
		b += this.pixels[i].b;
	}

	r = Math.round(r / this.pixels.length);
	g = Math.round(g / this.pixels.length);
	b = Math.round(b / this.pixels.length);
	pixel = rgbToHex(r, g, b);
	this.pixels = [];

	return pixel;
}

CanvasDrawer.prototype.pushPixel = function(sx, ex, sy){
	for(var i = 0; i < ex - sx; i += 10)
		this.pixels.push(this.getPixel(sx + i, sy));
}

CanvasDrawer.prototype.fillBottomFlatTriangle = function(v1, v2, v3){
	if(v2.x > v3.x){
		var temp = v2;
		v2 = v3;
		v3 = temp;
	}

	var invslope1 = (v2.x - v1.x) / (v2.y - v1.y);
	var invslope2 = (v3.x - v1.x) / (v3.y - v1.y);

	var curx1 = v1.x;
	var curx2 = v1.x;
	for (var scanlineY = v1.y; scanlineY <= v2.y; scanlineY += 10){
		this.pushPixel(Math.round(curx1), Math.round(curx2), scanlineY);
		curx1 += invslope1;
		curx2 += invslope2;
	}
}

CanvasDrawer.prototype.fillTopFlatTriangle = function(v1, v2, v3){
	if(v1.x > v2.x){
		var temp = v1;
		v1 = v2;
		v2 = temp;
	}

	var invslope1 = (v3.x - v1.x) / (v3.y - v1.y);
	var invslope2 = (v3.x - v2.x) / (v3.y - v2.y);

	var curx1 = v3.x;
	var curx2 = v3.x;
	for (var scanlineY = v3.y; scanlineY > v1.y; scanlineY -= 10){
		curx1 -= invslope1;
		curx2 -= invslope2;
		this.pushPixel(Math.round(curx1), Math.round(curx2), scanlineY)
	}
}

CanvasDrawer.prototype.arrSort = function(arr){
	arr.sort(function (a, b) { 
		return a.y < b.y ? -1 : a.y > b.y ? 1 : 0;
	});
}

CanvasDrawer.prototype.setCanvasImage = function(){
	this.imageString = localStorage.getItem("previewString");
	this.width = localStorage.getItem("previewWidth");
	this.height = localStorage.getItem("previewHeight");
	var image = new Image();
	image.src = this.imageString;

	canvasDrawer.canvas.css("height", "auto");
	canvasDrawer.canvas.css("width", "auto");

	canvasDrawer.canvas.attr("height", canvasDrawer.height);
	canvasDrawer.canvas.attr("width", canvasDrawer.width);

	this.ctx.drawImage(image, 0, 0);
};

function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}


canvasDrawer = new CanvasDrawer("#previewCanvas");
canvasDrawer.setCanvasImage();