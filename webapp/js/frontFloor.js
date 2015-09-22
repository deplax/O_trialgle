$(".signin").click(function() {
    var jsonData = new Object();
    jsonData.email = $(".email").eq(0).val();
    jsonData.password = $(".password").eq(0).val()
    console.log(jsonData);

    $.ajax({
        url: "/frontFloor/login",
        type: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        success: function(data) {
            console.log(data.message);
            if(data.message === "Success")
            	window.location.replace("/fileUploadFloor");
        },
        error: function(e) {
            console.log("error");
        }
    });
});