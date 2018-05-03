
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


case class Player(x:Int, y:Int, dir:String, len:Int)


object SnakeMain {
  
   //random spawn site
   val randX = scala.util.Random.nextInt(400)
   val randY = scala.util.Random.nextInt(400)

   //global vals
   var player = Player(randX,randY,"left",3)
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
        if(!dead){
           movePlayer()
           checkHit()
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
     }
     else {
       gc.fillStyle = "#000000"
       gc.fillRect(0,0,canvas.width,canvas.height)
     }
   }

   //draws snake
   def drawSnake() = {
     gc.fillRect(player.x,player.y,20,20)
     var newX = player.x+5
     var newY = player.y+5

     for(i <- 1 to player.len) {
        if(player.dir == "right") newX = newX-12
        if(player.dir == "left") newX = newX+12
        if(player.dir == "up") newY = newY+12
        if(player.dir == "down")newY = newY-12
        gc.fillRect(newX,newY,10,10)
     }
     gc.fill()
   }

   //continuous moves snake
   def movePlayer() = {
     var x = player.x
     var y = player.y
     var dir = player.dir
     var len = player.len

     if(dir == "right") player = Player(x+5,y,dir,len)
     if(dir == "left") player = Player(x-5,y,dir,len)
     if(dir == "up") player = Player(x,y-5,dir,len)
     if(dir == "down") player = Player(x,y+5,dir,len)
     update()
   }

   //changes direction of snake
   def goRight() = player = Player(player.x, player.y,"right",player.len)
   def goLeft() = player = Player(player.x, player.y,"left",player.len)
   def goUp() = player = Player(player.x, player.y,"up",player.len)
   def goDown() = player = Player(player.x, player.y,"down",player.len)

   def checkHit():Boolean = {
     //hits border
     if(player.x == 0 || player.x == canvas.width || 
        player.y == 0 || player.y == canvas.height) dead = true
     return dead
   }

}
