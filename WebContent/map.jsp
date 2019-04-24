<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "s" uri = "/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="stylesheet" type="text/css" href="./assets/css/story.css">
    
    <title>Phaser Template</title>

    <style>
      html,
      body,
      #game-container {
        margin: 0;
        padding: 0;
      }

      #game-container {
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .grid-container {
        display: grid;
        grid-gap: 10px;
        background-image: url("./assets/images/bg.png");
        /*background-color: #2196F3;*/
        padding: 10px;
      }

      #game-container > canvas {
        border-radius: 25px;
      }

      .grid-item {
        /*background-color: rgba(255, 255, 255, 0.8);*/
        text-align: center;
        padding: 20px;
        font-size: 30px;
      }

      .item2 {
        grid-column: 1;
        grid-row: 1;
      }

      .item3 {
        grid-column: 1;
        grid-row: 2;
      }
      
      #myleft{
        position:relative;
        float:left;
        width:200px;
        color: white;
      }
      #mycenter{
        position:relative;
        margin:0 201px 0 201px;
        color: white;
      }
      * html #mycenter{height:1%}/*defeat ie 3 pixel jog*/
      #myright{
        position:relative;
        float:right;
        width:200px;
        color: white;
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
  </head>

  <body>
    <div class="grid-container">
      <div class="grid-item item1"></div>
      <div class="grid-item item2">
        <div id='myleft'>Player Name</div> 
        <div id='myright'></div> 
        <div id='mycenter'><a style = "text-decoration: none;color: white" href = "/Galapagos/map.jsp" >Home</a>  |  <a href = "/Galapagos/inventory.jsp" style = "text-decoration: none;color: white"  >Inventory</a></div> 
      </div>
      <div class="grid-item item3">
        <div id="game-container"></div>
	        <div class="backdrop">
			  <div id="popdiv">
			  	<div id = "storyTitle"></div>
			  	<div id = "storyContent" style = "text-align: left"></div>
			    <button class = "battleOptionsButton" id="but2">Continue</button>
			</div>
		</div>
      </div>
      <div class="grid-item item4">
      	<div id = "dialogueBox">
      	</div>
      </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" />
    <script src="//cdn.jsdelivr.net/npm/phaser@3.16.2/dist/phaser.js"></script>
    <script src="./assets/js/index.js" type="module"></script>
  </body>
</html>
