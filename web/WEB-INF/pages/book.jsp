<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<html>
<head>
    <title>Title</title>
    <script type="application/javascript" src="${path}/js/jquery-3.3.1.min.js"></script>
    <%@include file="/commons/commons.jsp" %>
</head>
<body>

<center>
    <c:if test="${!empty param.title}">
        你已经将${param.title}放在购物车中！
        <br>
    </c:if>
    <c:if test="${!empty sessionScope.shoppingCart}">
        你的购物车有 ${ sessionScope.shoppingCart.bookNumber } 本书！<a href="bookServlet?method=toCartPages&pageNo=${requestScope.bookpage.pageNo}">查看购物车</a>
    </c:if>
    <br>
    <br>
    <form action="bookServlet?method=getBooks" method="post">
        Price:
        <label>
            <input type="text" size="1" name="minPrice">
        </label>
        <label>
            <input type="text" size="1" name="maxPrice">
        </label>
        <input type="submit" value="submit">
    </form>

    <br>
    <br>
    <table cellpadding="10">
        <c:forEach items="${requestScope.bookpage.list}" var="book">
            <tr>
                <td>
                    <a href="bookServlet?method=getDetailBooks&id=${book.id}&pageNo=${requestScope.bookpage.pageNo}">${book.title}</a>
                    <br>${book.author}
                </td>
                <td>${book.price}</td>
                <td>
                    <a href="bookServlet?method=addToCart&id=${book.id}&pageNo=${requestScope.bookpage.pageNo}&title=${book.title}">加入购物车</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br><br>
    共有${requestScope.bookpage.totalPageNumber}页
    &nbsp;&nbsp;
    当前第 &nbsp;${requestScope.bookpage.pageNo} &nbsp;页
    &nbsp;&nbsp;

    <c:if test="${requestScope.bookpage.hasPrev}">
        <a href="bookServlet?method=getBooks&pageNo=1">首页</a>
        <a href="bookServlet?method=getBooks&pageNo=${requestScope.bookpage.prevPage}">上一页</a>
    </c:if>

    &nbsp;&nbsp;
    <c:if test="${requestScope.bookpage.hasNext}">
        <a href="bookServlet?method=getBooks&pageNo=${requestScope.bookpage.nextPage}">下一页</a>
        <a href="bookServlet?method=getBooks&pageNo=${requestScope.bookpage.totalPageNumber}">末页</a>
    </c:if>
    &nbsp;&nbsp;


    转到：<label for="pageNo"></label><input size="1" type="text" id="pageNo">页
    <br>
    <script>
        $("#pageNo").change(function () {
            var val = $(this).val();
            if (!/\d/.test(val)) {
                alert("你输入的内容不合法！");
                return
            }
            var pageNo = parseInt(val);
            if (pageNo < 1 || pageNo > parseInt(${requestScope.bookpage.totalPageNumber})) {
                alert("你输入的内容不合法！");
                return
            }
            window.location.href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
        })
    </script>

</center>
</body>
</html>
