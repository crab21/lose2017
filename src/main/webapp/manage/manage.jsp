<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 11/23/17
  Time: 10:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>明德微校园</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no"/>

    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/??sm.min.css,sm-extend.min.css">
    <script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/??sm.min.js,sm-extend.min.js'
            charset='utf-8'></script>
    <script type='text/javascript' src='../js/jquery-2.1.4.js'></script>
    <script src="http://code.changer.hk/jquery/plugins/jquery.cookie.js"></script>

    <!--jQuery-->
    <!-- head 中 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">

    <!-- body 最后 -->

    <script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>

    <!-- 如果使用了某些拓展插件还需要额外的JS -->
    <script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/swiper.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/city-picker.min.js"></script>
    <!--jQuery结束-->
</head>
<body style="text-align: center">
<br><br><br>
<h4>请输入要查询的编号</h4>
<div style="height: 30px">
    <div class="weui-search-bar" id="searchBar" style="">
        <form class="weui-search-bar__form" action="javascript:return true;">
            <div class="weui-search-bar__box">
                <i class="weui-icon-search"></i>
                <input type="search" class="weui-search-bar__input" id="searchInput" name="searchInput"
                       placeholder="搜索"
                       required="">
                <a href="javascript:;" class="weui-icon-clear" id="searchClear"></a>
            </div>
            <label class="weui-search-bar__label" id="searchText"
                   style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
                <i class="weui-icon-search"></i>
                <span>搜索</span>
            </label>
        </form>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
    </div>
</div>

<script>
    //主页中点击
    function showOnes(id) {
        location.href = "page/manage_one.html?id=" + id;
    }


    $(function () {
        $("form").submit(function (e) {
            e.preventDefault();
            $("#appendContent").empty();
            var searchInput = $("#searchInput").val();
            showOnes(searchInput);
        });
    });
</script>

</body>
</html>
