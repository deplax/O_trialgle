// // ctx.fillRect(0,0,150,75);

// //딱히 필요 없게 되버렸다.----------------------------
// function Triangle(p1, p2, p3) {
//     this.p1 = p1;
//     this.p2 = p2;
//     this.p3 = p3;
// }

// function Point(x, y) {
//     this.x = x;
//     this.y = y;
// }
// //----------------------------------------------

// function CanvasDrawer(canvasName) {
//     this.canvas;
//     this.ctx;

//     this.triangles = [];

//     this.init(canvasName);
// }

// CanvasDrawer.prototype.init = function(canvasName) {
//     this.canvas = document.getElementById(canvasName);
//     this.ctx = this.canvas.getContext("2d");

//     this.ctx.fillStyle = "#FF0000";
//     var path = new Path2D();
//     path.moveTo(35, 50);
//     path.lineTo(300, 25);
//     path.lineTo(55, 400);
//     this.ctx.fill(path);

//     this.ctx.fillStyle = "#00FF00";
//     var path2 = new Path2D();
//     path2.moveTo(400, 400);
//     path2.lineTo(300, 25);
//     path2.lineTo(55, 400);
//     this.ctx.fill(path2);

//     this.ctx.imageSmoothingEnabled = false;
// }

// CanvasDrawer.prototype.drawTriangle = function(triangle) {

// }

// CanvasDrawer.prototype.drawTriangles = function() {
//     this.triangles;
// }

// CanvasDrawer.prototype.createTriangle = function(x1, y1, x2, y2, x3, y3) {
//     var p1 = new Point(x1, y1);
//     var p2 = new Point(x2, y2);
//     var p3 = new Point(x3, y3);
//     var triangle = new Triangle(p1, p2, p3);
//     this.triangles.push(triangle);
// }

// //========================================================
// function ImageResizer() {
//     this.maxWidth;
//     this.maxHeight;
//     this.reader

// }
// //========================================================
// canvas = new CanvasDrawer("myCanvas");

// p1 = new Point(10, 20);

// //========================================================

// $("#fileSelect").change(function() {
//     var file = this.files[0];
//     var reader = new FileReader();
//     if (file) {
//         //여기에서 파일 타입이 이미지가 아닐 경우에 대한 처리 필요.
//         reader.readAsDataURL(file);
//     };

//     reader.onload = function() {
//         var img = document.createElement("img");
//         img.src = reader.result
//         resize(img);
//     };

//     // var data = [];
//     // data.push(this.files);
//     // post_to_url("/file", data);
// });

// function resize(img) {
//     var jcanvas = $("#myCanvas2");
//     jcanvas.hide();
//     var canvas = $("#myCanvas2").get(0);
//     var ctx = canvas.getContext("2d");

//     var MAX_WIDTH = 500;
//     var MAX_HEIGHT = 500;
//     var width = img.width;
//     var height = img.height;

//     if (width > height) {
//         if (width > MAX_WIDTH) {
//             height *= MAX_WIDTH / width;
//             width = MAX_WIDTH;
//         }
//     } else {
//         if (height > MAX_HEIGHT) {
//             width *= MAX_HEIGHT / height;
//             height = MAX_HEIGHT;
//         }
//     }
//     canvas.width = width;
//     canvas.height = height;

//     ctx.drawImage(img, 0, 0, width, height);
//     var dataurl = canvas.toDataURL("image/jpg");

//     var data = [];
//     data.push(dataurl);
//     post_to_url("/file", data);


// }

// function post_to_url(path, params, method) {
//     method = method || "post"; // 전송 방식 기본값을 POST로

//     var form = document.createElement("form");
//     //form.setAttribute("enctype", "multipart/form-data")
//     form.setAttribute("method", method);
//     form.setAttribute("action", path);

//     //히든으로 값을 주입시킨다.
//     for (var key in params) {
//         console.log(key);

//         var hiddenField = document.createElement("input");
//         hiddenField.setAttribute("type", "text");
//         hiddenField.setAttribute("name", "key");
//         hiddenField.setAttribute("value", params[key]);

//         form.appendChild(hiddenField);
//     }

//     document.body.appendChild(form);
//     form.submit();
// }


// // video test 

// video = document.getElementById('myvideo');

// //크롬 아닌애들 지원하려면 webkit떼는 처리 해야함.
// if (navigator.webkitGetUserMedia) {
//     navigator.webkitGetUserMedia({
//         video: true
//     }, success, fail);
// } else {
//     navigator.getUserMedia({
//         video: true
//     }, success, fail);
// }

// function success(stream) {
//     if (window.webkitURL) {
//         video.src = window.webkitURL.createObjectURL(stream);
//     } else {
//         video.src = window.URL.createObjectURL(stream);
//     }
// }

// function fail(err) {
//     console.log("The following error occured: " + err);
// }


// $("#myvideo").click(function() {
//     var cvs = document.createElement("canvas");
//     debugger;
//     var ctx = cvs.getContext("2d");
//     ctx.drawImage(video, 0, 0, this.width, this.height);
//     document.body.appendChild(cvs);

// });


//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

function CanvasDrawer(canvasName) {
    this.canvas;
    this.ctx;
    this.triangles = [];
    this.imageString;
    this.file;

    this.init(canvasName);
    this.setFile("#fileSelect");
};

CanvasDrawer.prototype.init = function(canvasName) {
    this.canvas = $("#previewCanvas");
    this.ctx = this.canvas[0].getContext("2d");
    this.setCanvasSize();
};

CanvasDrawer.prototype.setCanvasSize = function() {
    var height = $(window).height() - 20;
    this.canvas.attr("height", height);
    this.canvas.attr("width", height);
}

CanvasDrawer.prototype.setFile = function(fileSelect) {
    var canvasSelf = this;

    $("#fileSelect").change(function() {
        canvasSelf.file = this.files[0];
        debugger;
        var reader = new FileReader();
        if (canvasSelf.file) {
            //여기에서 파일 타입이 이미지가 아닐 경우에 대한 처리 필요.
            reader.readAsDataURL(canvasSelf.file);
        };

        reader.onload = function() {
            canvasSelf.image = document.createElement("img");
            canvasSelf.image.src = reader.result;
            canvasSelf.resize();
        };
    });
};

CanvasDrawer.prototype.resize = function() {
    var MAX_WIDTH = this.canvas.attr("height");
    var MAX_HEIGHT = this.canvas.attr("height");
    var width = this.image.width;
    var height = this.image.height;

    if (width > height) {
        //if (width > MAX_WIDTH) {
        height *= MAX_WIDTH / width;
        width = MAX_WIDTH;
        //}
    } else {
        //if (height > MAX_HEIGHT) {
        width *= MAX_HEIGHT / height;
        height = MAX_HEIGHT;
        //}
    }

    this.canvas.attr("height", height);
    this.canvas.attr("width", width);

    this.ctx.drawImage(this.image, 0, 0, width, height);
    this.imageString = this.canvas[0].toDataURL("image/jpg");
}

$(".upload").click(function() {
    var jsonData = new Object();
    jsonData.imageString = canvasDrawer.imageString;
    if(jsonData.imageString === undefined)
        return;
    $.ajax({
        url: "/fileUpload",
        type: "POST",
        data: jsonData,
        success: function(result) {
            console.log(result);
        },
        error: function(e) {
            console.log(e.message);
        }
    });
});

canvasDrawer = new CanvasDrawer("previewCanvas");

//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
// a > b
// 직속 하위의 자식
// a b
// 자손
// a.b
// <a class="b">

//쉼표의 구분의 경우 or이다.

//:focus
//a + b
//a다음에 b가 있을 때
//:first-child
//:last-child
//:nth-child(n)
//ㄴ n에 odd, even 가능.
//[속성명=속성값]
//ㄴ input[type=text]

$(".webcam").click(function(){
	$(".contentsArea").addClass("contentsHide");
});
