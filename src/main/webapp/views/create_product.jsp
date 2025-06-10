<%--
  Created by IntelliJ IDEA.
  User: lenam
  Date: 6/10/2025
  Time: 8:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Product</title>
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
    />
    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/8.0.0/mdb.min.css"
            rel="stylesheet"
    />
</head>
<body>
<div class="col-12 col-md-12">
    <div class="card mt-2">
        <div class="card-header">
            <h5>Create New Product</h5>
        </div>
        <div class="card-body">
            <form action="/management/create" method="post">
                <!-- Text input -->
                <div data-mdb-input-init class="form-outline mb-4">
                    <input name="name" type="text" id="form6Example3" class="form-control" />
                    <label class="form-label" for="form6Example3">Name</label>
                </div>

                <!-- Text input -->
                <div data-mdb-input-init class="form-outline mb-4">
                    <input  name="price" type="number" id="form6Example4" class="form-control" />
                    <label class="form-label" for="form6Example4">Price</label>
                </div>

                <!-- Number input -->
                <div data-mdb-input-init class="form-outline mb-4">
                    <label class="form-label" for="form6Example5">Sale</label>
                    <select class="form-select" name="discount" id="form6Example5">
                        <option selected disabled value="">Chose discount</option>
                        <option value="0">0%</option>
                        <option value="5">5%</option>
                        <option value="10">10%</option>
                        <option value="15">15%</option>
                    </select>
                </div>

                <div data-mdb-input-init class="form-outline mb-4">
                    <input  name="stock" type="number" id="form6Example8" class="form-control" />
                    <label class="form-label" for="form6Example8">Stock</label>
                </div>

                <!-- Submit button -->
                <button type="submit" class="btn btn-primary btn-block mb-4">Create Product</button>
                <a class="btn btn-secondary btn-block mb-4" href="/management">Cancel</a>
            </form>
        </div>
    </div>

</div>


</body>
</html>
