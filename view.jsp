
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<title>Search Engine</title>
<%@page import="controller.*"%>

<%@page import="controller.*"%>

<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>




</head>
<body>

  <% String value1=(String)request.getAttribute("my");
    %>    
    <c:set var="salary" scope="session" value='<%=value1%>' />
    
    
    
<table class="table" border="1">
<thead>
    
  <c:choose>
   <c:when test="${salary eq 2}">
     <th>Rank</th>    
<th>Document id</th>  

<th>Score</th>
    </c:when>
    <c:when test="${salary eq 3}">
   <th>Rank</th>    
<th>Document id</th>  
<th>file path</th>
<th>Score</th>
    </c:when>
    <c:when test="${salary eq 4}">
        <th>Rank</th>    
<th>Document id</th>  

<th>Score</th>
    </c:when>
    <c:otherwise>
       <th>Rank</th>    
<th>Document id</th>  
<th>file path</th>
<th>Link Analysis</th>
<th>tfidf</th>
<th>Score</th>
    </c:otherwise>
</c:choose>  
    
    
    
    
    

</thead>
<tbody>

<a href="firstnewpage.jsp"> Home </a>   
    
  
<c:forEach items="${query}" var="product" varStatus="status">

    
 
    <c:choose>
    <c:when test="${salary eq 2}">
        
      <tr>
<c:set var="data" value="${fn:split(product.key,'~')}" />
 <td>${status.index + 1}</td>
<td>${data[0]}

</td>


<td>
${product.value}
</td>


</tr>  
        
        
      
    </c:when>
    <c:when test="${salary eq 3}">
      <tr>
<c:set var="data" value="${fn:split(product.key,'~')}" />
 <td>${status.index + 1}</td>
<td>${data[0]}

</td>
<td>
   
                <a href="file:///${data[1]}" target="_blank">${data[1]}</a>


</td>


<td>
${product.value}
</td>


</tr>
        
        
        
    </c:when>
    <c:when test="${salary eq 4}">
       <tr>
<c:set var="data" value="${fn:split(product.key,'~')}" />
 <td>${status.index + 1}</td>
<td>${data[0]}

</td>

<td>
${product.value}
</td>


</tr> 
        
        
        
    </c:when>
        
        
    <c:otherwise>
    
        <tr>
<c:set var="data" value="${fn:split(product.key,'~')}" />
 <td>${status.index + 1}</td>
<td>${data[0]}

</td>
<td>
   
                <a href="file:///${data[1]}" target="_blank">${data[1]}</a>


</td>
<td>
    ${data[2]}


</td>
<td>
    ${data[3]}


</td>


<td>
${product.value}
</td>


</tr>
        
        
        
    </c:otherwise>
</c:choose>
    
    
    
    
    
    

</c:forEach>



</tbody>
</table>









</table>
</td>
</tr>
</table>
</form>
</div>
</div>
</div>
</div>
</body>
</html>