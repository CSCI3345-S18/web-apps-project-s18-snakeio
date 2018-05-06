package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.{ActorRef, ActorSystem}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.collection.mutable.Queue
import javax.inject.{Inject, Named}
import ExecutionContext.Implicits.global

case class Snake(id: String, var score: Int, var dir:String, var dead:Boolean, var body:Queue[(Int,Int)])
case class Fruit(x:Int, y:Int)

class SnakeManager extends Actor {
  
  import SnakeManager._
  
  val system = akka.actor.ActorSystem("system")
  system.scheduler.schedule(0 milliseconds, 500 milliseconds)(updateSnakes)
  
  private var players = List[ActorRef]()
  private var name = ""
  private var snakes = List[Snake]()
  
  val r = scala.util.Random
  var fruit = Fruit(r.nextInt(400),r.nextInt(400))
  
  def receive = {
    case NewPlayer(player) =>
      players ::= player
      var x = 5*r.nextInt(80) //400/5 = 80
      var y = 5*r.nextInt(80)
      var tempQue = Queue((x,y),(x+5,y),(x+10,y))
      var newSnake = new Snake("1", 0, "right", false, tempQue);
      snakes ::= newSnake
      println("New Snake added! There are now " + snakes.length + " snakes!")
    case UpdateDirections(msg) =>
      println("Direction Change: " + msg)
      if(msg.split(",").length == 2){
        var id = msg.split(",")(0)
        var dir = msg.split(",")(1)
        for(i <- 0 until snakes.length){
          if(snakes(i).id == id){
            snakes(i).dir = dir
          }
        }
      }
    case UpdateSnakes() =>
//      println("Updating Snakes")
//      var snakeInfo = snakeStrings()
//      moveSnakes()
//      players.foreach(_ ! SnakeActor.SnakeLocations(snakeInfo))
  }
  
  def updateSnakes(): Unit = {
      //println("Updating Snakes")
      var snakeInfo = snakeStrings()
      moveSnakes()
      players.foreach(_ ! SnakeActor.SnakeLocations(snakeInfo))
  }
  
    def moveSnakes():Unit = {
      for(i <- 0 until snakes.length){
        var x = snakes(i).body.last._1
        var y = snakes(i).body.last._2
        var dir = snakes(i).dir
        if(dir == "right") snakes(i).body.enqueue((x+5,y))
        if(dir == "left") snakes(i).body.enqueue((x-5,y))
        if(dir == "up") snakes(i).body.enqueue((x,y-5))
        if(dir == "down") snakes(i).body.enqueue((x,y+5))
        
        snakes(i).body.dequeue()
      }
    }
    
    def snakeStrings():String = {
      var finString = ""
      for(i <- 0 until snakes.length){
        var s = snakes(i)
        //finString += s.id + "," + s.score + "," + s.dead + ","
        for(j <- 0 until s.body.length){
          finString += s.body.get(j).get._1 + ":" + s.body.get(j).get._2
          //Cause JSON Objects are for nerds I guess
          
          if(j != s.body.length - 1){
            finString += ","
          }
        }
        finString += " "
      }
      finString
    }
  
}

object SnakeManager {
  def props = Props[SnakeManager]
  //def updateSnakes = Unit
  case class NewPlayer(player: ActorRef)
  case class UpdateSnakes()
  case class UpdateDirections(msg: String)
}


