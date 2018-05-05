
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


case class Player(dir:String, var body:Queue[(Int,Int)])
case class Fruit(x:Int, y:Int)


object SnakeMain {
  
   //random spawn site
   val r = scala.util.Random

   //global vals
   var x = 5*r.nextInt(80) //400/5 = 80
   var y = 5*r.nextInt(80)
   var tempQue = Queue((x,y),(x+5,y),(x+10,y))
   var player = Player("left", tempQue)
   var fruit = Fruit(r.nextInt(400),r.nextInt(400))
   var dead = false
   val canvas = $("#snakeCanvas")(0).asInstanceOf[html.Canvas]
   val gc = canvas.getContext("2d").
         asInstanceOf[dom.CanvasRenderingContext2D]

   def main():Unit = {
     // Setup
     gc.strokeRect(0,0,canvas.width,canvas.height)
     drawSnake()

     //interval
     dom.window.setInterval(() => {
        println("Dead status = " + dead)
        if(!dead){
           println("Player Direction: " + player.dir);
           movePlayer()
           checkHit()
           eatFruit()
           update()
        }
     },40.0)


     //keyboard controls -> changes directions
     dom.window.addEventListener("keydown",(e: dom.KeyboardEvent) => {
       if (e.keyCode == 37) goLeft()
       if (e.keyCode == 39) goRight()
       if (e.keyCode == 38) goUp()
       if (e.keyCode == 40) goDown()
     })
     
   }

   def update() = {
     if (!dead) {
       gc.clearRect(0,0,canvas.width,canvas.height)
       gc.strokeRect(0,0,canvas.width,canvas.height)
       drawSnake()
       //draw fruit
       gc.fillRect(fruit.x,fruit.y,10,10)
     }
     else {
       gc.fillStyle = "#000000"
       gc.fillRect(0,0,canvas.width,canvas.height)
     }
   }

   //draws snake
   def drawSnake() = {
     //var playerCopy = player
     for(i <- 0 until player.body.length){
       //var temp = playerCopy.body.dequeue()
       var temp = player.body.get(i).get
       println("Rectangle at: " +temp._1 + " " + temp._2);
       gc.fillRect(temp._1, temp._2, 4,4)
     }
     /*gc.fillRect(player.x,player.y,5,5)
     var newX = player.x+5
     var newY = player.y+5

     for(i <- 1 to player.len) {
        if(player.dir == "right") newX = newX-12
        if(player.dir == "left") newX = newX+12
        if(player.dir == "up") newY = newY+12
        if(player.dir == "down")newY = newY-12
        gc.fillRect(newX,newY,10,10)
     }*/
     gc.fill()
   }

   //continuous moves snake
   def movePlayer() = {
     var dir = player.dir
     var px = player.body.last._1
     var py = player.body.last._2
     if(dir == "right") player.body.enqueue((px+5,py))
     if(dir == "left") player.body.enqueue((px-5,py))
     if(dir == "up") player.body.enqueue((px,py-5))
     if(dir == "down") player.body.enqueue((px,py+5))
     
     player.body.dequeue();
     
     /*var x = player.x
     var y = player.y
     var dir = player.dir
     var len = player.len

     if(dir == "right") player = Player(x+5,y,dir,len)
     if(dir == "left") player = Player(x-5,y,dir,len)
     if(dir == "up") player = Player(x,y-5,dir,len)
     if(dir == "down") player = Player(x,y+5,dir,len)*/
     update()
   }

   //changes direction of snake
   def goRight() = player = Player("right", player.body)
   def goLeft() = player = Player("left", player.body)
   def goUp() = player = Player("up", player.body)
   def goDown() = player = Player("down", player.body)

   def checkHit():Boolean = {
     //hits border
     if(player.body.front._1 <= 0 || player.body.front._1 >= canvas.width || 
        player.body.front._2 <= 0 || player.body.front._2 >= canvas.height) dead = true
     return dead
   }

   def eatFruit() = {
     if ((player.body.front._1 < fruit.x && (player.body.front._1+20) > fruit.x) &&
         (player.body.front._2 < fruit.y && (player.body.front._2+20) > fruit.y)) {
        fruit = Fruit(r.nextInt(400),r.nextInt(400))
        player.body.enqueue((player.body.last._1, player.body.last._2 + 5));
     }
   }



}
