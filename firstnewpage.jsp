<html>
<head>
    
    
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


<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<center><h1 style="color:darkgreen; margin-top: 150px">Mervick <small style="color:darkolivegreen;">(CS454 - Search Engine)</small></h1></center>
</head>
     <form action="advanceSearch.jsp">
         <br>
         <button class="btn btn-primary" style="margin-left: 800px">Advance Search</button>
    </form>
    
    
    
    
<script>

var myJson = {
    "WSDLS": [
        {
            "name": "Normalized",
            "id": "1",
            "methods": [
                {
                    "name": "Priority To TF-IDF",
                    "id": "A",
                    
                },
                {
                    "name": " Priority To LINK-ANALYSIS",
                    "id": "B",
                    }
            ]
        },
        {
            "name": "TF-IDF",
            "id": "2",
            
        },
        {
            "name": "PAGE-RANK",
            "id": "3",
            
        },
        {
            "name": "Vector Space Model (VSM) of Information Retrieval and the Boolean model",
            "id": "4",
            
        }
        
        
    ]
}


$( document ).ready(function() {
$('#method').hide();
$('#my').hide();

$.each(myJson.WSDLS, function (index, value) {
    $("#wsdl").append('<option value="'+value.id+'">'+value.name+'</option>');
});

$('#wsdl').on('change', function(){
    
if(myJson.WSDLS[0].id == $(this).val())
{  
$('#method').show();
    for(var i = 0; i < myJson.WSDLS.length; i++)
    {
      if(myJson.WSDLS[i].id == $(this).val())
      {
         $('#method').html('<option value="000">-Select Priority-</option>');
         $.each(myJson.WSDLS[i].methods, function (index, value) {
            $("#method").append('<option value="'+value.id+'">'+value.name+'</option>');
        });
      }
    }
}else
{
   $('#method').hide(); 
   $('#my').show(); 
   
   
}


    
});
$('#method').on('change', function(){
    $('#my').show();
});
})


</script>
</head>
<body>
<form id="dropdowns" action="practise">
			
			
			<div class="col-md-12">
                            <div class="row" style="margin-left: 450px">
			<div class="col-md-4">
			<div class="form-group">
                            <center>
				<select id="wsdl" name="wsdl" class="form-control"> 
					<option value="000">-TYPE OF RANK-</option>
				</select>
                            </center>
			</div>
			<div class="form-group">
			
				<select id="method" name="method" class="form-control">
					<option value="000">-Priority To-</option>
				</select>
			</div>
                          
                            <div class="form-group" id="my">
			
                        
                <input type="text"  name="txtSearch" placeholder="Enter the search term">
                <input type="submit" class="btn btn-primary" value="Search" />
               	
                   		
			</div>
			 
                            
                            
			</div>
			</div>
			</div>
			
				
		</form>
    
    
   
    
    
</body>
</html>