// ctx.fillRect(0,0,150,75);

//딱히 필요 없게 되버렸다.----------------------------
function Triangle(p1, p2, p3) {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
}

function Point(x, y) {
    this.x = x;
    this.y = y;
}
//----------------------------------------------

function CanvasDrawer(canvasName) {
    this.canvas;
    this.ctx;

    this.triangles = [];

    this.init(canvasName);
}

CanvasDrawer.prototype.init = function(canvasName) {
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

CanvasDrawer.prototype.drawTriangle = function(triangle) {

}

CanvasDrawer.prototype.drawTriangles = function() {
    this.triangles;
}

CanvasDrawer.prototype.createTriangle = function(x1, y1, x2, y2, x3, y3) {
    var p1 = new Point(x1, y1);
    var p2 = new Point(x2, y2);
    var p3 = new Point(x3, y3);
    var triangle = new Triangle(p1, p2, p3);
    this.triangles.push(triangle);
}

//========================================================
function ImageResizer() {
    this.maxWidth;
    this.maxHeight;
    this.reader

}
//========================================================
canvas = new CanvasDrawer("myCanvas");

p1 = new Point(10, 20);

//========================================================

$("#fileSelect").change(function() {
    var file = this.files[0];
    var reader = new FileReader();
    if (file) {
        //여기에서 파일 타입이 이미지가 아닐 경우에 대한 처리 필요.
        reader.readAsDataURL(file);
    };

    reader.onload = function() {
        var img = document.createElement("img");
        img.src = reader.result
        resize(img);
    };
});

function resize(img) {
	var jcanvas = $("#myCanvas2");
	jcanvas.hide();
    var canvas = $("#myCanvas2").get(0);
    var ctx = canvas.getContext("2d");

    var MAX_WIDTH = 500;
    var MAX_HEIGHT = 500;
    var width = img.width;
    var height = img.height;

    if (width > height) {
        if (width > MAX_WIDTH) {
            height *= MAX_WIDTH / width;
            width = MAX_WIDTH;
        }
    } else {
        if (height > MAX_HEIGHT) {
            width *= MAX_HEIGHT / height;
            height = MAX_HEIGHT;
        }
    }
    canvas.width = width;
    canvas.height = height;

    ctx.drawImage(img, 0, 0, width, height);
    var dataurl = canvas.toDataURL("image/png");
    debugger;

}
