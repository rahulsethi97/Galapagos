<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Galapagos</title>
<style>
	@font-face {
			font-family: 'Sucrose Bold Two';
			src: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/sucrose.woff2') format('woff2');
			}
	@font-face {
	    font-family: 'IM Fell French Canon Pro';
	    src: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/im-fell-french-canon-pro.woff2') format('woff2');
	}
	* {
	  box-sizing: border-box;
	}
	body {
	  margin: 0;
	}
	header { 
		background: url(./assets/images/introbg.png) no-repeat;
		padding-top: 40%;
		background-size: cover;
	  font-family: 'Sucrose Bold Two';
	}
	header img {
	    position: absolute;
	    top: 0;
	    /* right: 0; */
	    width: 45.8%;
	    margin-top: 130px;
	    height: 20%;
	    width: 27.1%;
	    margin-left: 116px;
	    margin-top: 400px;
	    height: auto;
	}
	header h1 { 
		position: fixed;
		top: 2rem;
		left: 7rem;
	  font-size: 3vw;
	  line-height: .8;
	  margin-top: 0;
	  text-align: center;
	}
	header h1 span {
	  display: block;
	  font-size: 4.6vw;
	}
	main { 
	   	background: #fff;
	    position: relative;
	    border: 1px solid #fff;
	    font-family: 'IM Fell French Canon Pro';
	    font-size: 3rem;
	    padding: 2rem 7%;
	    line-height: 1.6;
	}
	@media all and (max-width: 400px) {
	  main { padding: 2rem; }
	}
	
	.battleOptionsButton{
			color: white;
			height: 4em;
			width: 15%;
			padding: 1.5em auto;
			margin: 1em auto;
			background-color: #0f0606cf;
			border: none;
			border-radius: 3px;
			text-transform: uppercase;
		  	letter-spacing: 0.5em;
		}
</style>
 <script>
		var allowed = "<%=session.getAttribute("GameStarted")%>";
		console.log(allowed);
		if(allowed != "null"){
			window.location.href = "/map.jsp";
		}
    </script>
</head>
<body>
	<header>
		<h1>The <span>Galapagos</span> Island.</h1>
	</header>
	<main>
	  <p id = "story"></p>
		<button class = "battleOptionsButton" onclick="begin();">Start Game</button>
	</main>
	<form id="startGame" action = "startGame"></form>
	
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script>
		var intro;
		jQuery.ajax({
			type : "GET",
			url : "getIntro",
			async: false,
			data: {},
			success : function(data) {
				console.log(data);
				intro = data.intro;
			},
			error : function(data) {
				alert("Some error occured.");
			}
		});
	
		document.getElementById("story").innerHTML = intro;
		
		function begin(){
			document.getElementById("startGame").submit();
		}
	</script>
</body>
</html>