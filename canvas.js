// ctx.fillRect(0,0,150,75);

function Triangle(p1, p2, p3) {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
}

function Point(x, y) {
    this.x = x;
    this.y = y;
}

function canvasDrawer(canvasName) {
	this.canvas;
	this.ctx;

    this.triangles = [];

    this.init(canvasName);
}

canvasDrawer.prototype.init = function(canvasName) {
    this.canvas = document.getElementById(canvasName);
    this.ctx = this.canvas.getContext("2d");

    this.ctx.fillStyle = "#FF0000";
    var path = new Path2D();
    path.moveTo(35, 50);
    path.lineTo(300, 25);
    path.lineTo(55, 400);
    this.ctx.fill(path);

    this.ctx.fillStyle = "#00FF00";
    var path2 = new Path2D();
    path2.moveTo(400, 400);
    path2.lineTo(300, 25);
    path2.lineTo(55, 400);
    this.ctx.fill(path2);

    this.ctx.imageSmoothingEnabled = false;
}

canvasDrawer.prototype.drawTriangle = function(triangle){

}


canvasDrawer.prototype.createTriangle = function(x1, y1, x2, y2, x3, y3){
	var p1 = new Point(x1, y1);
	var p2 = new Point(x2, y2);
	var p3 = new Point(x3, y3);
	var triangle = new Triangle(p1, p2 ,p3);
	this.triangles.push(triangle);
}

//========================================================

canvas = new canvasDrawer("myCanvas");

p1 = new Point(10, 20);
