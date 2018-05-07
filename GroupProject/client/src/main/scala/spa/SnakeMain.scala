package spa

import org.scalajs.dom
import org.scalajs.dom._
import dom.document
import scala.scalajs.js.annotation.JSExportTopLevel
import org.querki.jquery._
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js._
import org.scalajs.dom.ext.KeyCode
import scala.collection.mutable.Queue


case class Player(id:String, dir:String, var body:Queue[(Int,Int)])
case class Fruit(x:Int, y:Int)

object SnakeMain {
  
  val socket = new dom.WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/socket")
  val r = scala.util.Random
  var direction = "right"
  var id = r.nextInt(999999).toString()//"testID1"
  //val id = document.getElementById("userID").innerHTML
  
  var fruitColor = "#880000"
  var playerColor = "crimson"
  var enemyColor = "#000088"
  
  var scoreXCoord = 410
  var scoreYCoord = 20
  var scoreYCoordOffset = 60
  
  var sarahColor = "#008080" //It's a lovely color but I need obnoxious ones atm
  
  var dead = false

   //global vals
//   var x = 5*r.nextInt(80) //400/5 = 80
//   var y = 5*r.nextInt(80)
//   var tempQue = Queue((x,y),(x+5,y),(x+10,y))
//   var player = Player("1", "left", tempQue)
//   var fruit = Fruit(r.nextInt(400),r.nextInt(400))
//   var score = 0
//   var dead = false
   val canvas = $("#snakeCanvas")(0).asInstanceOf[html.Canvas]
   val gc = canvas.getContext("2d").
         asInstanceOf[dom.CanvasRenderingContext2D]


   def main():Unit = {
     
    
    
     //sockets
     //socket.onopen = { (e: dom.Event) =>
     socket.addEventListener("open",(event: Event) => {
       socket.send("Open socket")
     })
     
     //socket.onmessage = { (e: dom.MessageEvent) =>
     socket.addEventListener("message",(event: MessageEvent) => {
       println("got message: " + event.data.toString())
       println("current direction: " + direction)
       //val snakeData = JSON.parse(event.data.toString())
       //Here we call draw snake
       
       update(event.data.toString())
       //socket.send(id + "," + direction)
       
     })
     
     dom.window.addEventListener("keydown",(e: dom.KeyboardEvent) => {
       if (e.keyCode == 37) direction = "left"
       if (e.keyCode == 39) direction = "right"
       if (e.keyCode == 38) direction = "up"
       if (e.keyCode == 40) direction = "down"
       socket.send(id + "," + direction)
       
     })
     
   }

   def update(s: String) = { 
       println("Recieved String: " + s)
       gc.clearRect(0,0,canvas.width,canvas.height)
       gc.strokeRect(0,0,canvas.width,canvas.height)
       
       //displayScore(playerScore)
       gc.fillStyle = "black"
       gc.font = "30px Arial"
       gc.fillText("Scores", 410, 50)
       
       drawSnake(s)
       //draw fruit
       gc.fillStyle = "#008000"
       
       if(dead){
         gc.fillStyle = "#000000"
         gc.fillRect(0, 0, canvas.width,canvas.height)
       }
   }
   
   //shows score
   def displayScore(id: String, score: String, x: Int, y: Int) = {
     gc.fillStyle = "black"
     gc.font = "12px Arial"
     gc.fillText(id + ": " + score, x, y)
   }
   
   //draws snake
   def drawSnake(s: String) = {
     //s is the string containing all snakes (And the fruit)
     
     
     var snakeList = s.split(" ") //Contains a list of strings. EACH string is one snake
     print("Draw Snakes: ")
     var fruit = snakeList(0)
     gc.fillStyle = fruitColor
     gc.fillRect(fruit.split(":")(0).toDouble, fruit.split(":")(1).toDouble, 9,9)
     for(i <- 1 until snakeList.length){
       var cutString = snakeList(i).split(",")
       var snakeId = cutString(0)
       var snakeScore = cutString(1)
       
       // DISPLAY SCORE!!!
       // y -coordinates will change!
       displayScore(snakeId, snakeScore, scoreXCoord, scoreYCoord*i + scoreYCoordOffset)
       
       if(id == snakeId){
         gc.fillStyle = playerColor
       } else {
         gc.fillStyle = enemyColor
       }
       
       for(j <- 2 until cutString.length){
         print(cutString(j) + " ")
         var x = cutString(j).split(":")(0).toInt
         var y = cutString(j).split(":")(1).toInt
         gc.fillRect(x, y, 4,4)
       }
     }
     println("")
     gc.fill()
   }

   //continuous moves snake
//   def movePlayer() = {
//     var dir = player.dir
//     var px = player.body.last._1
//     var py = player.body.last._2
//     if(dir == "right") player.body.enqueue((px+5,py))
//     if(dir == "left") player.body.enqueue((px-5,py))
//     if(dir == "up") player.body.enqueue((px,py-5))
//     if(dir == "down") player.body.enqueue((px,py+5))
//     
//     player.body.dequeue()
//     update()
//   }

   //changes direction of snake
//   def goRight() = player = Player("right", player.body)
//   def goLeft() = player = Player("left", player.body)
//   def goUp() = player = Player("up", player.body)
//   def goDown() = player = Player("down", player.body)

//   def checkHit():Boolean = {
//     //hits border
//     if(player.body.front._1 <= 0 || player.body.front._1 >= canvas.width || 
//        player.body.front._2 <= 0 || player.body.front._2 >= canvas.height) dead = true
//	    
//      player.body.last._1
//     return dead
//   }
//
//   def eatFruit() = {
//     if ((player.body.front._1-3 <= fruit.x && (player.body.front._1+5) >= fruit.x) &&
//         (player.body.front._2-3 <= fruit.y && (player.body.front._2+5) >= fruit.y)) {
//        fruit = Fruit(r.nextInt(400),r.nextInt(400))
//        player.body.enqueue((player.body.last._1, player.body.last._2 + 5))
//        score += 500
//     }
//   }



}
