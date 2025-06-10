<%--
  Created by IntelliJ IDEA.
  User: lenam
  Date: 6/10/2025
  Time: 7:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="list_product" value="${requestScope['list_product']}"/>
<html>
<head>
    <title>Manager</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/8.0.0/mdb.min.css" rel="stylesheet"/>
</head>
<body>
<div class="class=col-12 col-md-12">
    <div class="card mt-2">
        <div class="card-header">
            <h5>List Product</h5>
            <form action="${pageContext.request.contextPath}/management/top-products" method="get" class="row mb-3">
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text">Danh sách top:</span>
                        <select class="form-select" name="limit" onchange="this.form.submit()">
                            <option value="3" ${param.limit == '3' ? 'selected' : ''}>3</option>
                            <option value="5" ${param.limit == '5' ? 'selected' : ''}>5</option>
                            <option value="10" ${param.limit == '10' ? 'selected' : ''}>10</option>
                        </select>
                        <button class="btn btn-outline-secondary" type="submit">Xem</button>
                    </div>
                </div>
            </form>
            <a href="${pageContext.request.contextPath}/management/create" class="btn btn-primary float-end">Create New Product</a>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Discount</th>
                    <th>Stock</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list_product}" var="product">
                    <tr>
                        <td>
                            <div class="d-flex align-items-center">
                                <div class="ms-3">
                                    <p class="fw-bold mb-1"><c:out value="${product.getId()}"/></p>
                                </div>
                            </div>
                        </td>
                        <td>
                            <p class="text-muted mb-0"><c:out value="${product.getName()}"/></p>
                        </td>
                        <td>
                            <c:out value="${product.getPrice()}"/>
                        </td>
                        <td>
                            <p><c:out value="${product.getDiscount()}"/> %</p>
                        </td>
                        <td>
                            <c:out value="${product.getStock()}"/>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/management/edit?id=<c:out value="${product.getId()}"/>" type="button"
                               class="btn btn-info">
                                Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/management/delete?id=<c:out value="${product.getId()}"/>" type="button"
                               onclick="return confirm('Bạn muốn xóa sản phẩm này?')" class="btn btn-danger">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>