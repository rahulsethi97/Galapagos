/**
 * Author: Michael Hadley, mikewesthad.com
 * Asset Credits:
 *  - Tuxemon, https://github.com/Tuxemon/Tuxemon
 */


const config = {
  type: Phaser.AUTO,
  width: 1250,
  height: 700,
//   height: 12090,
  parent: "game-container",
  pixelArt: true,
  physics: {
    default: "arcade",
    arcade: {
      gravity: { y: 0 }
    }
  },
  scene: {
    preload: preload,
    create: create,
    update: update
  }
};

const game = new Phaser.Game(config);
let cursors;
let player;
let showDebug = false;

var overlapTriggered = false;

function preload() {
  this.load.image("star", "./assets/images/card2.png");
  this.load.image("tiles", "./assets/tilesets/tuxmon-sample-32px-extruded.png");
  this.load.tilemapTiledJSON("map", "./assets/tilemaps/tuxemon-town.json");
  this.load.image("npc1", "./assets/images/test1.resized.png");

  // An atlas is a way to pack multiple images together into one texture. I'm using it to load all
  // the player animations (walking left, walking right, etc.) in one image. For more info see:
  //  https://labs.phaser.io/view.html?src=src/animation/texture%20atlas%20animation.js
  // If you don't use an atlas, you can do the same thing with a spritesheet, see:
  //  https://labs.phaser.io/view.html?src=src/animation/single%20sprite%20sheet.js
  this.load.atlas("atlas", "./assets/atlas/atlas.png", "./assets/atlas/atlas.json");
}

var mapData;

function create() {
  const map = this.make.tilemap({ key: "map" });

  // Parameters are the name you gave the tileset in Tiled and then the key of the tileset image in
  // Phaser's cache (i.e. the name you used in preload)
  const tileset = map.addTilesetImage("tuxmon-sample-32px-extruded", "tiles");

  // Parameters: layer name (or index) from Tiled, tileset, x, y
  const belowLayer = map.createStaticLayer("Below Player", tileset, 0, 0);
  const worldLayer = map.createStaticLayer("World", tileset, 0, 0);
  const aboveLayer = map.createStaticLayer("Above Player", tileset, 0, 0);

  worldLayer.setCollisionByProperty({ collides: true });



  // By default, everything gets depth sorted on the screen in the order we created things. Here, we
  // want the "Above Player" layer to sit on top of the player, so we explicitly give it a depth.
  // Higher depths will sit on top of lower depth objects.
  aboveLayer.setDepth(10);

  // Object layers in Tiled let you embed extra info into a map - like a spawn point or custom
  // collision shapes. In the tmx file, there's an object layer with a point named "Spawn Point"
  const spawnPoint = map.findObject("Objects", obj => obj.name === "Spawn Point");

  // Create a sprite with physics enabled via the physics system. The image used for the sprite has
  // a bit of whitespace, so I'm using setSize & setOffset to control the size of the player's body.
  player = this.physics.add
    .sprite(spawnPoint.x, spawnPoint.y, "atlas", "misa-front")
    .setSize(30, 40)
    .setOffset(0, 24);

  // Watch the player and worldLayer for collisions, for the duration of the scene:
  this.physics.add.collider(player, worldLayer);



  // Create the player's walking animations from the texture atlas. These are stored in the global
  // animation manager so any sprite can access them.
  const anims = this.anims;
  anims.create({
    key: "misa-left-walk",
    frames: anims.generateFrameNames("atlas", {
      prefix: "misa-left-walk.",
      start: 0,
      end: 3,
      zeroPad: 3
    }),
    frameRate: 10,
    repeat: -1
  });
  anims.create({
    key: "misa-right-walk",
    frames: anims.generateFrameNames("atlas", {
      prefix: "misa-right-walk.",
      start: 0,
      end: 3,
      zeroPad: 3
    }),
    frameRate: 10,
    repeat: -1
  });
  anims.create({
    key: "misa-front-walk",
    frames: anims.generateFrameNames("atlas", {
      prefix: "misa-front-walk.",
      start: 0,
      end: 3,
      zeroPad: 3
    }),
    frameRate: 10,
    repeat: -1
  });
  anims.create({
    key: "misa-back-walk",
    frames: anims.generateFrameNames("atlas", {
      prefix: "misa-back-walk.",
      start: 0,
      end: 3,
      zeroPad: 3
    }),
    frameRate: 10,
    repeat: -1
  });

  const camera = this.cameras.main;
  camera.startFollow(player);
  camera.setBounds(0, 0, map.widthInPixels, map.heightInPixels);

  
  /***********************Adding the cards and NPCs***********************************/
  	
  	var cardsAvailable;
  	var npcsAvailable;
  	var inventory;
  	
  	
	jQuery.ajax({
		type : "GET",
		url : "populateMap",
		async: false,
		data: {},
		success : function(data) {
			mapData = data.mapData;
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});
	console.log(mapData);
	cardsAvailable = mapData.map.cards;
	npcsAvailable = mapData.map.npcs;
	inventory = mapData.inventory;
	
	player.x = parseInt(mapData.currentpos.X);
	player.y = parseInt(mapData.currentpos.Y);
	console.log();
	document.getElementById("myright").innerHTML = 'Level: ' + mapData.level;
  
  for (var key in cardsAvailable) {	  
	  var a = this.physics.add.image(parseInt(cardsAvailable[key].X), parseInt(cardsAvailable[key].Y), 'star');
	  a.name = key;
	  this.physics.add.overlap(player, a, collectCard, null, this);
  }
  
  for (var key in npcsAvailable) {
	  var a = this.physics.add.image(parseInt(npcsAvailable[key].X), parseInt(npcsAvailable[key].Y), 'npc1');
	  a.name = key;
	  this.physics.add.overlap(player , a , battlenpc , null , this);
  }
  
  /*********************************************************************************/
  
  
  cursors = this.input.keyboard.createCursorKeys();

  // Debug graphics
  this.input.keyboard.once("keydown_D", event => {
    // Turn on physics debugging to show player's hitbox
    this.physics.world.createDebugGraphic();

    // Create worldLayer collision graphic above the player, but below the help text
    const graphics = this.add
      .graphics()
      .setAlpha(0.75)
      .setDepth(20);
    worldLayer.renderDebug(graphics, {
      tileColor: null, // Color of non-colliding tiles
      collidingTileColor: new Phaser.Display.Color(243, 134, 48, 255), // Color of colliding tiles
      faceColor: new Phaser.Display.Color(40, 39, 37, 255) // Color of colliding face edges
    });
  });
}

  

function update(time, delta) {
  
  const speed = 175;
  const prevVelocity = player.body.velocity.clone();

  // Stop any previous movement from the last frame
  player.body.setVelocity(0);

  // Horizontal movement
  if (cursors.left.isDown) {
    player.body.setVelocityX(-speed);
    storeCurrentLocation();
  } else if (cursors.right.isDown) {
    player.body.setVelocityX(speed);
    storeCurrentLocation();
  }

  // Vertical movement
  if (cursors.up.isDown) {
    player.body.setVelocityY(-speed);
    storeCurrentLocation();
  } else if (cursors.down.isDown) {
    player.body.setVelocityY(speed);
    storeCurrentLocation();
  }

  // Normalize and scale the velocity so that player can't move faster along a diagonal
  player.body.velocity.normalize().scale(speed);

  // Update the animation last and give left/right animations precedence over up/down animations
  if (cursors.left.isDown) {
    player.anims.play("misa-left-walk", true);
  } else if (cursors.right.isDown) {
    player.anims.play("misa-right-walk", true);
  } else if (cursors.up.isDown) {
    player.anims.play("misa-back-walk", true);
  } else if (cursors.down.isDown) {
    player.anims.play("misa-front-walk", true);
  } else {
    player.anims.stop();

    // If we were moving, pick and idle frame to use
    if (prevVelocity.x < 0) player.setTexture("atlas", "misa-left");
    else if (prevVelocity.x > 0) player.setTexture("atlas", "misa-right");
    else if (prevVelocity.y < 0) player.setTexture("atlas", "misa-back");
    else if (prevVelocity.y > 0) player.setTexture("atlas", "misa-front");
  }
}

function collectCard (player, a){
  var id = "" + a.name;
  var guideMessage;
  var cardDetails;
  jQuery.ajax({
		type : "GET",
		url : "collectCard",
		async: false,
		data: {card_id : id},
		success : function(data) {
			console.log(data.cardDetails);
			cardDetails = data.cardDetails;
			guideMessage = data.guideMessage;
		},
		error : function(data) {
			alert("Some error occured.");
		}
  });
  
  alert("Card '" + cardDetails.Name + "' Collected");
  a.disableBody(true, true);
  
  document.getElementById("dialogueBox").innerHTML = 'Posiedon: ' + guideMessage; 
  
}


function battlenpc(player , npc){
	this.input.disabled = true;
	var isInventoryEmpty;
	jQuery.ajax({
		type : "GET",
		url : "moveAway",
		async: false,
		data: {npc_id : npc.name},
		success : function(data) {
			console.log(data);
			player.x = parseInt(data.new_x);
			player.y = parseInt(data.new_y);
			isInventoryEmpty = data.isInventoryEmpty;
			storeCurrentLocation();
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});
	if(isInventoryEmpty == "1"){
		document.getElementById("dialogueBox").innerHTML = 'Posiedon: ' + "You must have atleast one card in your inventory to battle the NPC."; 
	}else{
		if(overlapTriggered == true)
			  return;
		 
		  
		overlapTriggered = true;
		var npc_name = "" + npc.name;
		ConfirmDialog("Do you want to battle?" , npc, player);
	}
}



function ConfirmDialog(message , npc, player){
	$('<div></div>').appendTo('body')
    .html('<div><h6>'+message+'?</h6></div>')
    .dialog({
      modal: true, title: 'Battle', zIndex: 10000, autoOpen: true,
      width: 'auto', resizable: false,
      buttons: {
          Yes: function () {
              console.log('Yes');
              overlapTriggered = false;
              jQuery.ajax({
          		type : "GET",
          		url : "populateBattle",
          		data: {npc_id : npc.name},
          		success : function(data) {
          			console.log(data);
          			player.x = parseInt(data.new_x);
          			player.y = parseInt(data.new_y);
          			storeCurrentLocation();
          		},
          		error : function(data) {
          			alert("Some error occured.");
          		}
          	});
              window.location.href = "/battle.jsp"
              
              $(this).dialog("close");
          },
          No: function () {                                               
              console.log('No');
              overlapTriggered = false;
              $(this).dialog("close");  
          }
      },
      close: function (event, ui) {
          overlapTriggered = false;
          $(this).remove();
      }
  });
};


function storeCurrentLocation(){
	jQuery.ajax({
		type : "GET",
		url : "storeLocation",
		data: {X : parseInt(player.x) , Y: parseInt(player.y)},
		success : function(data) {
//			console.log(data);
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});
}

jQuery(window).load(function() {
	
	function isEmpty(obj) {
	    for(var key in obj) {
	        if(obj.hasOwnProperty(key))
	            return false;
	    }
	    return true;
	}
	
	var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    
    if(!isEmpty(vars)){
    	if(vars.noNpcBattle != null){
    		console.log(vars.noNpcBattle);
    		if(vars.noNpcBattle == 1){
    			document.getElementById("dialogueBox").innerHTML = 'Posiedon: ' + "You must go to the NPC to battle."; 
    		}
    	}
    }
    
	var storyData;
	var flag = "0";
	jQuery.ajax({
		type : "GET",
		url : "showStory",
		async: false,
		data: {level: mapData.level},
		success : function(data) {
			console.log(data);
			storyData = data;
			flag = data.show_story;
		},
		error : function(data) {
			alert("Some error occured.");
		}
	});
	
	console.log(flag);
	
	if(flag == "1"){
		//Code to show story
		$(".backdrop").fadeTo(200, 1);
		document.getElementById("storyTitle").innerHTML = "<strong>Chapter: " + storyData.title + "</strong>";
		document.getElementById("storyContent").innerHTML = storyData.story;
	}
	
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
});


$("#but2").click(function() {
	$(".backdrop").fadeOut(200);
});
