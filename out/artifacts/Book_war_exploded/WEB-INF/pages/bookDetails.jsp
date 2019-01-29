<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<html>
<head>
    <title>Title</title>
    <script type="application/javascript" src="${path}/js/jquery-3.3.1.min.js"></script>
    <%@include file="/commons/commons.jsp" %>
</head>
<body>

<center>
    id:${requestScope.bookDetail.id}
    <br>
    <br>
    author:${requestScope.bookDetail.author}
    <br>
    <br>
    title:${requestScope.bookDetail.title}
    <br>
    <br>
    price: ${requestScope.bookDetail.price}
    <br>
    <br>
    publishingDate: ${requestScope.bookDetail.publishingDate}
    <br>
    <br>
    salesAmount:${requestScope.bookDetail.salesAmount}
    <br>
    <br>
    storeNumber:${requestScope.bookDetail.storeNumber}
    <br>
    <br>
    remark:${requestScope.bookDetail.remark}
    <br>
    <br>
    <a href="bookServlet?method=getBooks&pageNo=${param.pageNo}">返回继续购物</a>

</center>
</body>
</html>
