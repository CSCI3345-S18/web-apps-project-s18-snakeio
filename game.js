var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");

ctx.fillStyle = "#262626";
ctx.rect(0,0,c.width,c.height);
ctx.fill();

ctx.beginPath();
ctx.arc(40,40,20,0,2*Math.PI);
ctx.fillStyle = "red";
ctx.fill();
ctx.beginPath();
ctx.arc(80,40,20,0,2*Math.PI);
ctx.fillStyle = "red";
ctx.fill();
ctx.beginPath();
ctx.arc(120,40,20,0,2*Math.PI);
ctx.fillStyle = "red";
ctx.fill();
ctx.beginPath();
ctx.arc(160,40,20,0,2*Math.PI);
ctx.fillStyle = "red";
ctx.fill();
ctx.beginPath();
ctx.arc(205,40,25,0,2*Math.PI);
ctx.fillStyle = "red";
ctx.fill();



ctx.beginPath();
ctx.arc(400,100,20,0,2*Math.PI);
ctx.fillStyle = "yellow";
ctx.fill();
ctx.beginPath();
ctx.arc(400,140,20,0,2*Math.PI);
ctx.fillStyle = "yellow";
ctx.fill();
ctx.beginPath();
ctx.arc(400,180,20,0,2*Math.PI);
ctx.fillStyle = "yellow";
ctx.fill();
ctx.beginPath();
ctx.arc(360,180,20,0,2*Math.PI);
ctx.fillStyle = "yellow";
ctx.fill();
ctx.beginPath();
ctx.arc(315,180,25,0,2*Math.PI);
ctx.fillStyle = "yellow";
ctx.fill();



ctx.beginPath();
ctx.arc(500,360,20,0,2*Math.PI);
ctx.fillStyle = "blue";
ctx.fill();
ctx.beginPath();
ctx.arc(460,360,20,0,2*Math.PI);
ctx.fillStyle = "blue";
ctx.fill();
ctx.beginPath();
ctx.arc(420,360,20,0,2*Math.PI);
ctx.fillStyle = "blue";
ctx.fill();
ctx.beginPath();
ctx.arc(380,360,20,0,2*Math.PI);
ctx.fillStyle = "blue";
ctx.fill();
ctx.beginPath();
ctx.arc(335,360,25,0,2*Math.PI);
ctx.fillStyle = "blue";
ctx.fill();

ctx.beginPath();
ctx.fillStyle = "limegreen";
ctx.rect(100,300,25,25);
ctx.fill();

ctx.beginPath();
ctx.fillStyle = "limegreen";
ctx.rect(525,50,25,25);
ctx.fill();


