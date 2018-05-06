package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef


case class Snake(dir:String, var body:Array[(Int,Int)])
case class Fruit(x:Int, y:Int)

class SnakeManager extends Actor {
  import SnakeManager._

  private var players = List[ActorRef]()
  private var name = ""
  
  def receive = {
    case NewPlayer(player) =>
      players ::= player
    case UpdateSnakes(msg) =>
      players.foreach(_ ! SnakeActor.ChatMessage(msg))
  }
}

object SnakeManager {
  def props = Props[SnakeManager]
  
  case class NewPlayer(player: ActorRef)
  case class UpdateSnakes(msg: String)
}
