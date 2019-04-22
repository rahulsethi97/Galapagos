<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="stylesheet" type="text/css" href="./assets/css/battle.css">
    <link rel="stylesheet" type="text/css" href="./assets/css/battle_card.css">
    <title>Phaser Template</title>

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
        <div class ="wrapper">
          <h1> Battle </h1>
          <div id = "player"></div>
          <div id = "villain"></div>
          <div id = "playerC"></div>
          <div id = "ground">
            <div id="playerc1" style="visibility:hidden">I have come here to fight you</div>
            <div id="villainc1" style="visibility:hidden">Are you ready to lose?</div>
            <div id="playerc2" style="visibility:hidden">Not if I defeat you first.</div>
            <div id="BattleButton" style="visibility:hidden"><button onclick="chooseCard();">Start Battle</button></div>
          </div>
          
          <div id = "playercard" style = "display:none">
			<div class="player_card">
				<div class="health" id = "player_name"></div>
				<br>
				<!-- <img src="http://x.annihil.us/u/prod/marvel/i/mg/e/e0/537bafa34baa9.jpg" class="hero"> -->
				<br><br>
				<label>Race</label><b id = "player_race"></b>
				<label>Type</label><b id = "player_type"></b><br><br>
				<label>HP</label><b id = "player_hp"></b>
				<label>Speed</label><b id = "player_speed"></b>
				<label>Offense</label><b id = "player_offense"></b>
				<label>Defense</label><b id = "player_defense"></b>
			</div>
          </div>
          
          <div id = "opponentcard" style = "display:none">
			<div class="oppenent_card">
				  <div class="health" id = "opponent_name"></div>
				<br>
				<!-- <img src="http://x.annihil.us/u/prod/marvel/i/mg/e/e0/537bafa34baa9.jpg" class="hero"> -->
				<br><br>
				<label>Race</label><b id = "opponent_race"></b>
				<label>Type</label><b id = "opponent_type"></b><br><br>
				<label>HP</label><b id = "opponent_hp"></b>
				<label>Speed</label><b id = "opponent_speed"></b>
				<label>Offense</label><b id = "opponent_offense"></b>
				<label>Defense</label><b id = "opponent_defense"></b>
			</div>
          </div>
          <div id = "battleOptions" style = "display:none;color:white">
          	<div onclick = "chooseCard();"><button>Choose Another Card</button></div>
		  	<div onclick = "startBattle();"><button>Begin</button></div>
          </div>
        </div>
      </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" />
    <script src="//cdn.jsdelivr.net/npm/phaser@3.16.2/dist/phaser.js"></script>
  </body>

  <script type="text/javascript">
    
  	var npcCardId;
  	var playerChosenCardId;
  	var level = "";
  	var inventoryData;
	var cardDetails;
	
	jQuery.ajax({
		type : "GET",
		url : "populateInventory",
		async: false,
		data: {},
		success : function(data) {
			console.log(data);
			inventoryData = data.inventoryData;
			cardDetails = data.cardDetails;
			npcCardId = data.opponentCard;
			level = data.level;
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});
	
	document.getElementById("myright").innerHTML = "Level: " + level;
	
	function startBattle(){
		console.log(npcCardId + " - " + playerChosenCardId);
		var result;
		var message;
		jQuery.ajax({
			type : "GET",
			url : "startBattle",
			async: false,
			data: {npc_card_id : npcCardId , player_card_id : playerChosenCardId},
			success : function(data) {
				console.log(data);
				result = data.result;
			},
			error : function(data) {
				alert("Some error occured.");
			}
		});
		
		if(result == "Card2"){
			message = "You Lost";
		}else{
			message = "You Won";
		}
		
		document.getElementById("battleOptions").innerHTML = message;
	}
	
    function showText(id,delay)
    {
      var elem=document.getElementById(id);
      myVar = setTimeout(function(){elem.style.visibility='visible';},delay*1000)
    }
    window.onload=function()
    {
      showText('playerc1',1);
      showText('villainc1',2);
      showText('playerc2',3);
      showText('BattleButton',4);
    }

    function battle(cardId){
      //var htmlCode = '<div><img src = "./assets/images/battlecard.jpg" width="23%" height="23%" style = "float:left"></div>';
      playerChosenCardId = cardId;
      document.getElementById("player_race").innerHTML = inventoryData[cardId].Race;
      document.getElementById("player_type").innerHTML = inventoryData[cardId].Type;
      document.getElementById("player_hp").innerHTML = inventoryData[cardId].HP;
      document.getElementById("player_speed").innerHTML = inventoryData[cardId].Speed;
      document.getElementById("player_offense").innerHTML = inventoryData[cardId].Offense;
      document.getElementById("player_defense").innerHTML = inventoryData[cardId].Defense;
      document.getElementById("player_name").innerHTML = inventoryData[cardId].Name;
      
      document.getElementById("playercard").style.display = 'block';
      document.getElementById("battleOptions").style.display = 'block';
      
      document.getElementById("ground").style.display = 'none';
		
    }

    function chooseCard(){
	
      document.getElementById("battleOptions").style.display = 'none';
      document.getElementById("playercard").style.display = 'none';
      document.getElementById("ground").style.display = 'block';
      
      var htmlCode = "";

      htmlCode += '<div id = "playercardlist">'+
                  '<div class="cardlistwrapper">';

      
      for (key in inventoryData) {
      	htmlCode += '<div onclick = "battle(' + key + ');" style="font-size:15px"><div class="box"><img src = "./assets/images/battlecard.jpg") style="height: 100%; width: 100%; object-fit: contain"></div>' + inventoryData[key].Name + '</div>';
      }
      


      htmlCode += '</div></div>';

      document.getElementById("ground").innerHTML = htmlCode;
      
      
      
      document.getElementById("opponent_race").innerHTML = cardDetails[npcCardId].Race;
      document.getElementById("opponent_type").innerHTML = cardDetails[npcCardId].Type;
      document.getElementById("opponent_hp").innerHTML = cardDetails[npcCardId].HP;
      document.getElementById("opponent_speed").innerHTML = cardDetails[npcCardId].Speed;
      document.getElementById("opponent_offense").innerHTML = cardDetails[npcCardId].Offense;
      document.getElementById("opponent_defense").innerHTML = cardDetails[npcCardId].Defense;
      document.getElementById("opponent_name").innerHTML = cardDetails[npcCardId].Name;
      
      document.getElementById("opponentcard").style.display = 'block';
    }

  </script>
</html>
