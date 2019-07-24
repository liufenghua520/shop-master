$(function () {
    $.ajax({
        url:"http://localhost:8084/sso/checkLogined",
        success:function (data) {
            var html = "";
            if (data!=null){
                html = data.nickname + "您好，欢迎来到<b><a href=\"/\">ShopCZ商城</a></b><a href='http://localhost:8084/sso/logout'>注销</a>"
            }else {
                html = "[<a onclick='login();'>登录</a>][<a href=\"http://localhost:8084/sso/toregister\">注册</a>]";
            }
            $("#pid").html(html);
        },
        dataType:"jsonp",
        jsonpCallback:"method"
    })
})

//转至登陆页
function login() {
    var returnUrl = location.href;

    //对returnUrl进行编码
    returnUrl = encodeURIComponent(returnUrl);

    location.href = "http://localhost:8084/sso/tologin?returnUrl="+returnUrl;
}