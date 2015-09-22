$(".signin").click(function() {

	var emailCheck = false;
	var passwordCheck = false;
	if($(".email").eq(0).val() === $(".email").eq(1).val() && $(".email").eq(0).val() !== "")
		emailCheck = true;
	if($(".password").eq(0).val() === $(".password").eq(1).val() && $(".password").eq(1).val() !== "")
		passwordCheck = true;

	if(emailCheck !== true || passwordCheck !== true)
		return;

    var jsonData = new Object();
    jsonData.email = $(".email").eq(0).val();
    jsonData.password = $(".password").eq(0).val()
    console.log(jsonData);

    $.ajax({
        url: "/registerFloor/signup",
        type: "POST",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        success: function(data) {
            console.log(data.message);
            if(data.message === "Success")
            	window.location.replace("/frontFloor");
        },
        error: function(e) {
            console.log("error");
        }
    });
});