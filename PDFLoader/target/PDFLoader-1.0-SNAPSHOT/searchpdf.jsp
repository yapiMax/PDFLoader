<%-- 
    Document   : searchpdf
    Created on : Oct 12, 2018, 7:35:43 PM
    Author     : yapilk
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.google.api.services.drive.model.File"%>
<%@page import="com.google.api.services.drive.model.File"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open Sans">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <meta name="google-signin-client_id" content="421399282724-b0h825565r8btq1b6d0915ouql60bisg.apps.googleusercontent.com">
        <title>Your PDF list</title>
        <style>
            h1,h3 {font-family: "Oswald"}
            body {font-family: "Open Sans"}
            table {
                border-collapse: collapse;
                width: 100%;
            }

            th, td {
                padding: 8px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            tr:hover {background-color:#f5f5f5;}
            
            .signout{
                background-color: #f44336;
                border: none;
                text-align: center;
                height: 50px;
                width: 120px;
                font-size: 16px;
            }
        </style>
    </head>
    <body>
            <div class="w3-content" style="max-width:1600px">
                <button id="signout_button" class="signout" style="float: right" onclick="signout()">Sign Out</button>
                <header class="w3-container w3-center w3-padding-16 w3-light-grey">
                    <h3 class="w3-xxlarge"><b>PDFLoader</b></h3>
                </header>

                <div class="w3-row w3-padding w3-border">
                    <div class="w3-col 50">
                        <div class="w3-white w3-margin">
                            <ul class="w3-ul w3-hoverable w3-white">
                                <c:forEach items="${pdfFiles}" var="file" varStatus="stat">
                                    <li class="w3-padding-16">
                                        <c:out value="${file.name}"/>
                                        <input type="text" name="fileId" id="fileId${stat.index}" value="${file.id}"/>
                                        <button id="btnView" style="float: right" onclick="viewFile(${stat.index})">View</button>
                                    </li>

                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        <script>
            
            function signout()
            {
                document.location.href = "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost:8082/PDFLoader/index.jsp";
            }
            
            function viewFile(index)
            {
                var fileId = document.getElementById("fileId"+index).value;
                window.location.href = "https://drive.google.com/open?id="+fileId;
            }
        </script>
    </body>
</html>
