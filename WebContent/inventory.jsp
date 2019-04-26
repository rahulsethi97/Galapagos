<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="stylesheet" type="text/css" href="./assets/css/inventory.css">
    <link rel="stylesheet" type="text/css" href="./assets/css/card.css">
    <link rel="stylesheet" type="text/css" href="./assets/css/story.css">
    <title>Galapagos</title>

    <style>
      html,
      body,
      .grid-container {
        display: grid;
        grid-gap: 10px;
        background-image: url("./assets/images/bg.png");
        /*background-color: #2196F3;*/
        padding: 10px;
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


      .cardlistwrapper {
        display: grid;
        max-height: 400px;
        width: 340px;
        overflow: auto;
        grid-template-columns: 100px 100px 100px;
        grid-gap: 10px;
        float:left;
		margin-left: 70px;
      }
      .box {
        color: #fff;
        border-radius: 5px;
        padding: 20px;
        font-size: 150%;
        height: 80px;
        margin-bottom: -15px;
      }
    </style>
     <script>
		var allowed = "<%=session.getAttribute("GameStarted")%>";
		console.log(allowed);
		if(allowed == "null"){
			window.location.href = "/";
		}
    </script>
  </head>

  <body>
    <div class="grid-container">
		<div class="grid-item item1"></div>
		<div class="grid-item item2">
		  <div id='myleft'>Percy</div> 
		  <div id='myright'></div> 
		  <div id='mycenter'><a style = "text-decoration: none;color: white" href = "/map.jsp" >Home</a>  |  <a href = "/inventory.jsp" style = "text-decoration: none;color: white"  >Inventory</a>  |  <a id="restartLink" style = "text-decoration: none;color: white" href = "#" >Restart</a></div> 
		</div>
		<div class="grid-item item3">
		  <div class ="wrapper">
		    <h1> Inventory </h1>
		      <div id = "cardList" class="cardlistwrapper">
		
		      </div>
		      <!-- <div id = "cardDetails" style = "display:none"><img src = "./assets/images/battlecard.jpg" width="23%" height="23%"></div> -->
		      <div class="card" id = "cardDetails" style = "display:none">
		      	<div>
	            	<div id = "leftimg" class = "img"></div>
	            	<div id = "rightimg" class = "img2"></div>
				</div>
		 		<div class="health" id = "name"></div>
				<label>Race</label><b id = "race"></b>
				<label>Type</label><b id = "type"></b><br><br>
				<label>HP</label><b id = "hp"></b>
				<label>Speed</label><b id = "speed"></b>
				<label>Offense</label><b id = "offense"></b>
				<label>Defense</label><b id = "defense"></b>
			</div>
	       </div>
	    </div>
		<div class="grid-item item4">
      		Posiedon: Welcome to the inventory. You can click on the cards to see its details...!!
    	</div>
     </div>
    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" />
    <script src="//cdn.jsdelivr.net/npm/phaser@3.16.2/dist/phaser.js"></script>
  </body>

  <script type="text/javascript">
	
  var a = document.getElementById("restartLink");

  //Set code to run when the link is clicked
  // by assigning a function to "onclick"
  a.onclick = function() {
  	$('<div></div>').appendTo('body')
      .html('<div><h6>Do you want to restart the Game?</h6></div>')
      .dialog({
        modal: true, title: 'Confirmation', zIndex: 10000, autoOpen: true,
        width: 'auto', resizable: false,
        buttons: {
            Yes: function () {
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
                $(this).dialog("close");
            },
            No: function () {     
                $(this).dialog("close");
                console.log("sads");
            }
        },
        close: function (event, ui) {
            $(this).remove();
        }
    });
  }
  
  	var inventoryData;
  	var level;
  	jQuery.ajax({
		type : "GET",
		url : "populateInventory",
		async: false,
		data: {},
		success : function(data) {
			inventoryData = data.inventoryData;
			level = data.level;
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});
  	
  	document.getElementById("myright").innerHTML = "Level: " + level;
  
    var htmlCode = "";
    //<img src = "./assets/images/battlecard.jpg") style="height: 100%; width: 100%; object-fit: contain">
    for (key in inventoryData) {
    	htmlCode += '<div onclick = "showCard(' + key + ');" style="color:white;font-size:15px"><div class="box"><img src = "./assets/images/battlecard.jpg") style="height: 100%; width: 100%; object-fit: contain"></div>' + inventoryData[key].Name + '</div>';
    }

    document.getElementById("cardList").innerHTML = htmlCode;

    function showCard(key){
	  console.log(inventoryData[key]);
	  document.getElementById("race").innerHTML = inventoryData[key].Race;
	  document.getElementById("type").innerHTML = inventoryData[key].Type;
	  document.getElementById("hp").innerHTML = inventoryData[key].HP;
	  document.getElementById("speed").innerHTML = inventoryData[key].Speed;
	  document.getElementById("offense").innerHTML = inventoryData[key].Offense;
	  document.getElementById("defense").innerHTML = inventoryData[key].Defense;
	  document.getElementById("name").innerHTML = inventoryData[key].Name;
	  document.getElementById("leftimg").style.backgroundImage = 'url(./assets/images/'+ inventoryData[key].Race + '.png)';
	  document.getElementById("rightimg").style.backgroundImage = 'url(./assets/images/'+ inventoryData[key].Type + '.png)';
      document.getElementById("cardDetails").style.display = 'block';
    }
  </script>
</html>
