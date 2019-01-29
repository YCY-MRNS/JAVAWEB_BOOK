<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script>
        $(function () {
            $(".delete").click(function () {
                var text = $(this).parent().parent().find("td:first").text();
                return confirm("确定要删除" + text + "吗");
            })
        })
    </script>
</head>
<body>

<center>
    数量:${sessionScope.shoppingCart.bookNumber}
    <br><br>
    <table cellpadding="15">
        <tr>
            <td>title</td>
            <td>price</td>
            <td>quantity</td>
            <td>&nbsp;</td>
        </tr>

        <c:forEach items="${sessionScope.shoppingCart.items}" var="items">
            <tr>
                <td>${items.book.title}</td>
                <td>${items.book.price}</td>
                <td>${items.quantity}</td>
                <td>
                    <a class="delete"
                       href="bookServlet?method=deleteCart&id=${items.book.id}&pageNo=${param.pageNo}&minPrice=${param.pageNo}&maxPrice=${param.pageNo}">删除</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4"> 总价:${sessionScope.shoppingCart.totalMoney}</td>
        </tr>
        <tr>
            <td colspan="4">
                <a href="">继续购物</a>
                <a href="">清空购物车</a>
                <a href="">结账</a>
            </td>
        </tr>

    </table>

</center>
</body>
</html>
