<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
<link href="https://fonts.googleapis.com/css?family=Alfa+Slab+One" rel="stylesheet">
<title>S'authentifier</title>
</head>
<body>
<% String erreur = (String)request.getAttribute("erreur"); %>

<div class="container col-10">
<div class="margin-top">

<h1 class="text-center my-5 police">Veuillez vous identifier</h1>
	
<div class="col-12 col-sm-8 col-md-6 col-lg-5 mx-auto shadow">
	<form action="<%=request.getContextPath()%>/ServletAuthentification" method="post">
	  <div class="form-group">
	    <label class="mt-3" for="utilisateur">Identifiant</label>
	    <input type="text" class="form-control" name="utilisateur" required>
	  </div>
	  <div class="form-group">
	    <label for="motDePasse">Mot de passe</label>
	    <input type="password" class="form-control" name="motDePasse" required>
	  </div>
	  <div class="form-group">
	     <button type="submit" class="btn btn-outline-info d-block mx-auto my-4">Valider</button>
	  </div>
	</form>
	
</div>

<c:if test="${not empty erreur }">
  <div class="alert alert-danger alert-dismissible fade show col-12 col-sm-8 col-md-6 col-lg-5 mx-auto" role="alert">
  	<strong class="mx-auto"><img src="<%=request.getContextPath()%>/images/warning.png" alt="danger"> &nbsp Identifiant ou mot de passe erroné !</strong>
  	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
	</div>
</c:if>

</div>
</div>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
 	
</body>
</html>