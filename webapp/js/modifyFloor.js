
function CanvasDrawer(canvasName) {
    this.canvas;
    this.ctx;
    this.triangles = [];
    this.imageString;
    this.file;
    this.points;

    this.init(canvasName);
    //this.setFile("#fileSelect");
};

CanvasDrawer.prototype.init = function(canvasName) {
    this.canvas = $(canvasName);
    this.ctx = this.canvas[0].getContext("2d");
    this.getData();
    this.makeVertex();
};

CanvasDrawer.prototype.getData = function(){
	var jsonData = new Object();
	var points;
	this.points = points;
	$.ajax({
	    url: "/modifyFloor/getPointList",
	    type: "GET",
	    success: function(data) {
	    	debugger;
	        console.log(data);
	        points = data;
	    },
	    error: function(e) {
	        console.log(e.message);
	    }
	});
}

CanvasDrawer.prototype.makeVertex = function() {
	debugger;
	var data = this.points;
	vertices = [];
	$.each(data, function(index, point) {
		v = new Vertex(point.x, point.y);
		vertices.push(v);
	});
	this.triangles = triangulate(vertices);
};

canvasDrawer = new CanvasDrawer("#previewCanvas");