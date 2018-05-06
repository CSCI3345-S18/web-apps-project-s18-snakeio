package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.JsString
import play.api.libs.json.Reads
import play.api.libs.json.JsPath
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._



class SnakeActor (out: ActorRef, manager: ActorRef) extends Actor {
  
  out ! "We are connected!"
  manager ! SnakeManager.NewPlayer(self)
  
  import SnakeActor._

  implicit val snakeWrites = new Writes[Snake] {
    def writes(snake: Snake) = Json.obj(
      "dir" -> snake.dir,
      "body" -> snake.body)
  }

  val snakeReads: Reads[Snake] = (
    (JsPath \ "dir").read[String] and
    (JsPath \ "body").read[Array[(Int,Int)]]
  )(Snake.apply _)
 

  
  def receive = {
    case input: String =>
      println("socket got input: " + input)
      manager ! SnakeManager.UpdateSnakes(input)
    case ChatMessage(msg) => 
      out ! msg
    case m => 
      println("Got unknown message: "+m)
  }
}

object SnakeActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new SnakeActor(out, manager))
  
  case class ChatMessage(msg: String)
}
