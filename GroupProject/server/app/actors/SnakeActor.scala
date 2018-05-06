package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

class SnakeActor (out: ActorRef, manager: ActorRef) extends Actor {
  
  out ! "We are connected!"
  manager ! SnakeManager.NewPlayer(self)
  
  import SnakeActor._
  
  def receive = {
    case input: String =>
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