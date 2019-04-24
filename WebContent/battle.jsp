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
    <link rel="stylesheet" type="text/css" href="./assets/css/story.css">
    <link rel="stylesheet" type="text/css" href="./assets/css/conversation.css">
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
          <h1 id = "pageTitle"> Battle </h1>
          <div id = "player"></div>
          <div id = "villain"></div>
          <div id = "playerC"></div>
          <div id = "ground">
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
          	<div onclick = "chooseCard();"><button class = "battleOptionsButton">Change Card</button></div>
		  	<div onclick = "startBattle();"><button class = "battleOptionsButton">Begin</button></div>
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
  </body>

  <script type="text/javascript">
    
  	var npcCardId;
  	var playerChosenCardId;
  	var level = "";
  	var inventoryData;
	var cardDetails;
	var battleStory;
	
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
		var lostConversation;
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
				lostConversation = data.lostConversation;
			},
			error : function(data) {
				alert("Some error occured.");
			}
		});
		
		var htmlCode = '<div class="outerbg">' +
					 	'<div class="chatboxout">' +
				    		'<div class="chatboxin">';
		var afterBattle;
		if(result == "Card2"){
			document.getElementById("dialogueBox").innerHTML = "Alastor: You did a good job reaching till here. Maybe I have to wait for someone else to stop this organization";
			document.getElementById("mycenter").innerHTML = '<a style = "text-decoration: none;color: white" href = "/Galapagos" >Restart</a>'
			message = "You Lost";
			afterBattle = lostConversation;
			document.getElementById("pageTitle").innerHTML = message;
			
			for (var i = 0; i < afterBattle.length; i++) {
			      if((i%2) == 0){
			      	htmlCode += '<div class="chat-container" id = "' + 2*i + '" style="visibility:hidden">' +
				        			'<div class="chat-respond msg">' +
					        			'<div class="flippd">' +
											'<div class="chatmsg">'	+ afterBattle[i] +
											'</div>' +
										'</div>' +  
									'</div>' +
								'</div>';
			      }else{
			      	htmlCode += '<div class="chat-container" id = "' + 2*i + '" style="visibility:hidden">' +
				        			'<div class="chat-sender msg">' +
										'<div class="chatmsg">'	+ afterBattle[i] +
										'</div>' + 
									'</div>' +
								'</div>';
			      }
			}

			htmlCode += '</div></div></div>';
			htmlCode += '<div class = "battleButton" id="BattleButton" style="visibility:hidden"><button onclick="restart();">Restart</button></div>';
		}else{
			document.getElementById("dialogueBox").innerHTML = "Alastor: Attaboy..!! We are one step closer to finish this mission.";
			message = "You Won";
			afterBattle = battleStory.after_battle.split("#");
			var after_battle_init = battleStory.after_battle_init;
			document.getElementById("pageTitle").innerHTML = message;
			if(after_battle_init == "1"){//Player Starts
			 for (var i = 0; i < afterBattle.length; i++) {
			       if((i%2) == 0){
			       	htmlCode += '<div class="chat-container" id = "' + 2*i + '" style="visibility:hidden">' +
				        			'<div class="chat-sender msg">' +
										'<div class="chatmsg">'	+ afterBattle[i] +
										'</div>' + 
									'</div>' +
								'</div>'
			       }else{
			       	htmlCode += '<div class="chat-container" id = "' + 2*i + '" style="visibility:hidden">' +
				        			'<div class="chat-respond msg">' +
					        			'<div class="flippd">' +
											'<div class="chatmsg">'	+ afterBattle[i] +
											'</div>' +
										'</div>' +  
									'</div>' +
								'</div>'
			       }
			 }
			}else{//Opponent Starts
			 for (var i = 0; i < afterBattle.length; i++) {
			       if((i%2) == 0){
			       	htmlCode += '<div class="chat-container" id = "' + 2*i + '" style="visibility:hidden">' +
				        			'<div class="chat-respond msg">' +
					        			'<div class="flippd">' +
											'<div class="chatmsg">'	+ afterBattle[i] +
											'</div>' +
										'</div>' +  
									'</div>' +
								'</div>'
			       }else{
			       	htmlCode += '<div class="chat-container" id = "' + 2*i + '" style="visibility:hidden">' +
				        			'<div class="chat-sender msg">' +
										'<div class="chatmsg">'	+ afterBattle[i] +
										'</div>' + 
									'</div>' +
								'</div>'
			       }
			 }
			}

			htmlCode += '</div></div></div>'
			htmlCode += '<div class = "battleButton" id="BattleButton" style="visibility:hidden"><button onclick="goToMap();">Continue</button></div>';
		}
		
	      
	    document.getElementById("playercard").style.display = "none";
	    document.getElementById("opponentcard").style.display = "none";
	    document.getElementById("battleOptions").style.display = "none";
	    document.getElementById("ground").style.display = "block";
	    document.getElementById("ground").innerHTML = htmlCode;
	    
	    console.log(afterBattle);
	    
	    for (var i = 0; i < afterBattle.length; i++) {
	  	  showText("" + 2*i,i+1);
	    }
	    showText('BattleButton',afterBattle.length + 1);
	}
	
	function goToMap(){
		window.location.href = "/Galapagos/map.jsp";
	}
	
	function restart(){
		window.location.href = "/Galapagos";
	}
	
    function showText(id,delay)
    {
      var elem=document.getElementById(id);
      myVar = setTimeout(function(){elem.style.visibility='visible';},delay*1000)
    }
    
    window.onload=function()
    {
    	
    	jQuery.ajax({
			type : "GET",
			url : "getStoryBattle",
			async: false,
			data: {level: level},
			success : function(data) {
				console.log(data);
				battleStory = data;
			},
			error : function(data) {
				alert("Some error occured.");
			}
		});
    	
	      var beforeBattleConversation = battleStory.battle_conversation.split("#");
	      var before_battle_init = battleStory.before_battle_init;
	      var htmlCode = '<div class="outerbg">' +
						 	'<div class="chatboxout">' +
					        	'<div class="chatboxin">';
	      if(before_battle_init == "1"){//Player Starts
	    	  for (var i = 0; i < beforeBattleConversation.length; i++) {
	    	        if((i%2) == 0){
	    	        	htmlCode += '<div class="chat-container" id = "' + i + '" style="visibility:hidden">' +
	    	                			'<div class="chat-sender msg">' +
	    	            					'<div class="chatmsg">'	+ beforeBattleConversation[i] +
	    	            					'</div>' + 
	    	            				'</div>' +
	    	            			'</div>';
	    	        	//htmlCode += '<div class="playerc" >' + beforeBattleConversation[i] + '</div><br>'
	    	        }else{
	    	        	htmlCode += '<div class="chat-container" id = "' + i + '" style="visibility:hidden">' +
				            			'<div class="chat-respond msg">' +
					            			'<div class="flippd">' +
		            							'<div class="chatmsg">'	+ beforeBattleConversation[i] +
		            							'</div>' +
		            						'</div>' +  
				        				'</div>' +
				        			'</div>';
	    	        }
	    	  }
	      }else{//Opponent Starts
	    	  for (var i = 0; i < beforeBattleConversation.length; i++) {
		   	        if((i%2) == 0){
		   	        	htmlCode += '<div class="chat-container" id = "' + i + '" style="visibility:hidden">' +
				            			'<div class="chat-respond msg">' +
					            			'<div class="flippd">' +
				    							'<div class="chatmsg">'	+ beforeBattleConversation[i] +
				    							'</div>' +
				    						'</div>' +  
				        				'</div>' +
				        			'</div>';
		   	        	//htmlCode += '<div class="villainc" id = "' + i + '" style="visibility:hidden">' + beforeBattleConversation[i] + '</div><br>'
		   	        }else{
		   	        	htmlCode += '<div class="chat-container" id = "' + i + '" style="visibility:hidden">' +
				            			'<div class="chat-sender msg">' +
				        					'<div class="chatmsg">'	+ beforeBattleConversation[i] +
				        					'</div>' + 
				        				'</div>' +
				        			'</div>';
		   	        	//htmlCode += '<div class="playerc" id = "' + i + '" style="visibility:hidden">' + beforeBattleConversation[i] + '</div><br>'
		   	        }
		   	  }
	      }
	      
	      htmlCode += '</div></div></div>'
	      
	      htmlCode += '<div class = "battleButton" id="BattleButton" style="visibility:hidden"><button onclick="chooseCard();">Start Battle</button></div>';
	      
	      document.getElementById("ground").innerHTML = htmlCode;
	      
	      console.log(beforeBattleConversation);
	      
	      for (var i = 0; i < beforeBattleConversation.length; i++) {
	    	  showText("" + i,i+1);
	      }
	      
	      showText('BattleButton',beforeBattleConversation.length + 1);
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
	  	var guideMessage;
	   	jQuery.ajax({
			type : "GET",
			url : "getGuideMessage",
			async: false,
			data: {npcCardId : npcCardId},
			success : function(data) {
				console.log(data);
				guideMessage = data.guideMessage;
			},
			error : function(data) {
				alert("Some error occured.");
			}
		});
 	  document.getElementById("dialogueBox").innerHTML = "Alastor: " + guideMessage;
      document.getElementById("battleOptions").style.display = 'none';
      document.getElementById("playercard").style.display = 'none';
      document.getElementById("ground").style.display = 'block';
      
      var htmlCode = "";

      htmlCode += '<div id = "playercardlist">'+
                  '<div class="cardlistwrapper">';

      
      for (key in inventoryData) {
      	htmlCode += '<div onclick = "battle(' + key + ');" style="color:white;font-size:15px"><div class="box"><img src = "./assets/images/battlecard.jpg") style="height: 100%; width: 100%; object-fit: contain"></div>' + inventoryData[key].Name + '</div>';
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
