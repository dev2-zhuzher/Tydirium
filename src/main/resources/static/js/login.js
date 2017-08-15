﻿/**
 * 登陆页JS
 */

layui.define(['element', 'layer', 'form'], function (exports) {
    var form = layui.form();
    var $ = layui.jquery;
    //自定义验证
    form.verify({
        passWord: [/^[\S]{6,12}$/, '密码必须6到12位'],
        account: function (value) {
            if (value.length <= 0 || value.length > 10) {
                return "账号必须1到10位"
            }
            var reg = /^[a-zA-Z0-9]*$/;
            if (!reg.test(value)) {
                return "账号只能为英文或数字";
            }
        },
        result_response: function (value) {
            if (value.length < 1) {
                return '请点击人机识别验证';
            }
        },
    });
    //监听登陆提交
    form.on('submit(login)', function (data) {
        var index = layer.load(1);
        setTimeout(function () {
            //模拟登陆
            layer.close(index);
            if (data.field.account != 'admin' || data.field.password != '123456') {
                layer.msg('账号或者密码错误', { icon: 5 });
            } else {
                layer.msg('登陆成功，正在跳转......', { icon: 6 });
                layer.closeAll('page');
                setTimeout(function () {
                    location.href = "/index/session";
                }, 500);
            }
        }, 400);
        return false;
    });
    //检测键盘按下
    $('body').keydown(function (e) {
        if (e.keyCode == 13) {  //Enter键
            if ($('#layer-login').length <= 0) {
                login();
            } else {
                $('button[lay-filter=login]').click();
            }
        }
    });
    // 登陆入口弹出
    function login() {
        var loginHtml = $("#login").html(); 
        $("#login").remove();
        layer.open({
            id: 'layer-login',
            type: 1,
            title: false,
            shade: 0.4,
            //shadeClose: true,
            area: ['500px', '360px'],
            closeBtn: 0,
            anim: 1,
            skin: 'pm-layer-login',
            content: loginHtml
        });
        layui.form().render('checkbox');
    }
    login();
    
    exports('login', {});
});

