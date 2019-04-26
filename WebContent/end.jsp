<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Galapagos</title>
<style>
	body {
		height: 100vh;
		background: linear-gradient(to left, #3c3b3f, #605c3c);
		color: white;
		margin: 0;
		overflow: hidden;
	}
	#credits {
		display: grid;
		justify-content: center;
		grid-gap: 1.4rem;
		text-align: center;
		animation: 30s credits linear infinite;
	}
	.end {
		font-size: 2rem;
		text-transform: uppercase;
	}
	
	.app {
		font-size: 3.9rem;
	}
	.story {
		font-size: 2.4rem;
	    width: 1200px;
	    text-align: justify;
		color: grey;
	}
	.job {
		font-size: 2.4rem;
		text-transform: uppercase;
	}
	.name {
		font-size: 2.8rem;
		color: grey;
	}
	@keyframes credits {
		0% { transform: translateY(100%); }
		100% { transform: translateY(-100%); }
	}
	
	.battleButton button{
		color: white;
		height: 4em;
		width: 10%;
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
		if(allowed == "null"){
			window.location.href = "/";
		}
		
		var showEnd = "<%=session.getAttribute("ShowEnd")%>";
		console.log(showEnd);
		if(showEnd != "1"){
			window.location.href = "/";
		}
    </script>
</head>
<body>
	
<div id="credits">
  <div class="end">The End</div>
  <div class="app" id = "chapterTitle"></div>
  <div class="story" id="endStory"></div>
  <div class="category">
    <div class="job">Developers</div>
    <div class="name">Team Instinct</div>
  </div>
  <div class="category">
    <div class="job">Special Thanks</div>
    <div class="name">www.stackoverflow.com</div>
    <div class="name">www.google.com</div>
    <div class="name">www.codepen.io</div>
    <div class="name">www.w3chools.com</div>
  </div>
  <div class = "battleButton" id="BattleButton"><button onclick="restart();">Restart</button></div>
</div>
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script>

	function restart(){
		jQuery.ajax({
  			type : "GET",
  			url : "restart",
  			data: {},
  			success : function(data) {
  	              window.location.href = "/"
  			},
  			error : function(data) {
  				alert("Some error occured.");
  			}
  		});
	}
	var endStory;
	var endTitle;
	jQuery.ajax({
		type : "GET",
		url : "getIntro",
		async: false,
		data: {},
		success : function(data) {
			console.log(data);
			endStory = data.end.story;
			endTitle = data.end.title;
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});

	document.getElementById("endStory").innerHTML = endStory;
	document.getElementById("chapterTitle").innerHTML = "Chapter: " + endTitle;
</script>
</body>
</html>