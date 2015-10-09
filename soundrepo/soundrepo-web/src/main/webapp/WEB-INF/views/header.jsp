<%@page import="ua.devfactory.users.dto.User"%>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SoundRepo</title>
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="/bootstrap/font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="/bootstrap/css/animate.min.css" type="text/css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="/bootstrap/css/creative.css" type="text/css">
 	<link rel="shortcut icon" href="/resources/img/favicon.ico" type="image/x-icon">
	</head>
	<body background="/resources/img/header.jpg">

<nav class="navbar navbar-default">
	<div class="container">
	
	<ul class="nav navbar-nav navbar-left">
	<li><a href="/explore.do">SoundRepo</a></li>
	<li><a href="/upload.do">Upload</a></li>
	</ul>

	<%User loggedUser = (User)session.getAttribute("user");
	if (loggedUser != null){
	%>		
			<ul class="nav navbar-nav navbar-right">
			  <li>
				<a href="/<%=loggedUser.getNick()%>/tracks.do">tracks</a>
			  </li>
			  <li>
				<a href="/<%=loggedUser.getNick()%>/sets.do">playlists</a>
			  </li>
			  <li>
				<a href="/<%=loggedUser.getNick()%>/sets/add_playlist.do">Add new playlist</a>
			  </li>
			  <li>
				<a href="/logout.do">logout</a>
			  </li>
			</ul>
			
	<%} else {%>
		<ul class="nav navbar-nav navbar-right">
			<li>
				<a href="/login.do">login</a>
			</li>
			<li>	
				<a href="/register.do">register</a>
			</li>
		</ul>
	<%} %>
	
</div>
</nav>	
		