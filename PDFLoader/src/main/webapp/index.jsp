<%-- 
    Document   : index
    Created on : Oct 12, 2018, 3:52:29 PM
    Author     : yapilk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open Sans">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id"
              content="421399282724-q4vuv19id11eh4mb1rkv9a3bntkuu80s.apps.googleusercontent.com">

        <title>Login Page</title>
        <style>
            h1,h3 {font-family: "Oswald"}
            body {font-family: "Open Sans"}
            
        </style>
    </head>
    <body class="w3-light-grey">
        <form method="POST" action="IndexController">
            <div class="w3-content" style="max-width:100%;max-height:100%">
                <header class="w3-container w3-center w3-padding-48 w3-white">
                    <h1 class="w3-xxxlarge"><b>PDFLoader</b></h1>
                    <h3>Search for your PDF files in Google Drive</h3>
                </header>
            </div>
            <div class="w3-content" style="max-width:50%;max-height:50%;margin-top: 5%">
                <button id="btnAuthorize" ><img src="button.png"/></button>
            </div>
        </form>
    </body>
</html>
