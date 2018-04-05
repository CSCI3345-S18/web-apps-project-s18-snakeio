var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");
var headx = Math.floor(Math.random()*(c.width)+1);
var heady = Math.floor(Math.random()*(c.height)+1);
var direction = Math.floor(Math.random()*4)+1;

function draw(){
console.log(direction);
ctx.fillStyle = "#262626";
ctx.rect(0,0,c.width,c.height);
ctx.fill();
ctx.beginPath();
ctx.arc(headx,heady,20,0,2*Math.PI);
ctx.fillStyle = "red";
ctx.fill();
}

function move(){
if(direction === 1){
		headx+=1;
		draw();

}else if (direction === 2){
		headx-=1;
		draw();
}else if (direction === 3){
		heady+=1;
		draw();

}else{
		heady-=1;
		draw();
}
}

setInterval(move(),10);
