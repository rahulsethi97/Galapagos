<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "s" uri = "/struts-tags" %>

<html>
	<style>
		* { margin:0; padding:0 }
		body {
			background: url("./assets/images/gpbg.jpg");
		    background-size: cover;
		    }
		figure button[name="play"] {
		  width: 85px;
		  height: 85px;
		  background: red;
		  border: none;
		  border-radius: 100%;
		  position: absolute;
		  left: 50%;
		  bottom: 12%;
		  cursor: pointer;
		}
		figure button[name="play"]:focus {
		  outline: 0;
		  border: 1px solid hsl(210, 58%, 69%);
		  box-shadow: 0 0 0 3px hsla(210, 76%, 57%, 0.5);
		}
		figure button[name="play"]::after {
		  content: '';
		  display: inline-block;
		  position: relative;
		  top: 1px;
		  left: 3px;
		  border-style: solid;
		  border-width: 10px 0 10px 20px;
		  border-color: transparent transparent transparent white;
		}
	</style>
</head>

<body>
<figure>
  <button onclick="begin();" name="play"></button>
  <form id="startGame" action = "startGame">
  </form>
</figure>

<script>
	function begin(){
		document.getElementById("startGame").submit();
	}
</script>

</body>
</html>