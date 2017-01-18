<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Products</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Products</h1>
			</div>
		</div>
		<div class="pull-right" style="padding-right: 50px">
			<a
				href="<spring:url value= "/products/product?id=${product.productId}&language=en" />"
				class="btn btn-primary"> <span
				class="glyphicon-info-sign glyphicon" /></span> English
			</a> <a
				href="<spring:url value= "/products/product?id=${product.productId}&language=nl" />"
				class="btn btn-primary"> <span
				class="glyphicon-info-sign glyphicon" /></span> Dutch
			</a>
		</div>
	</section>
	<section class="container">
		<div class="row">
			<div class="col-md-5">
				<img
					src="<c:url value="/resource/images/${product.productId}.png"></c:url>"
					alt="image" style="width: 100%" />
			</div>
			<div class="col-md-5">
				<h3>${product.name }</h3>
				<p>${product.description }</p>
				<p>
					<strong><spring:message code="product.form.itemCode.label" /></strong>
					: ${product.productID}
				</p>
				<p>
					<strong><spring:message
							code="product.form.manufacturer.label" /></strong> :
					${product.manufacturer }
				</p>
				<p>
					<strong><spring:message code="product.form.category.label" /></strong>
					: ${product.category }
				</p>
				<p>
					<strong><spring:message
							code="product.form.unitsInStock.label" /></strong> :
					${product.unitsInStock }
				</p>
				<h4>${product.unitPrice }USD</h4>
				<a href="<spring:url value="/products" />" class="btn btn-default">
					<span class="glyphicon-hand-left glyphicon"></span> <spring:message
						code="product.form.back.label" />
				</a>
				<p>
					<a href="#" class="btn btn-warning btn-large"> <span
						class="glyphicon-shopping-cart glyphicon"></span> <spring:message
							code="product.form.orderNow.label" />
					</a>
				</p>
			</div>
		</div>
	</section>
</body>
</html>