
package controllers

import javax.inject._
import java.util.concurrent.atomic.AtomicInteger
import play.api.mvc._
import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.Reads
import play.api.libs.json.JsPath
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import java.util.concurrent.atomic.AtomicReference
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcCapabilities
import slick.jdbc.MySQLProfile.api._
import models.SnakeGameQueries
import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject.Inject
import play.api.libs.streams.ActorFlow
import play.api.mvc.AbstractController
import play.api.mvc.ControllerComponents
import play.api.mvc.WebSocket
import actors.SnakeActor
import actors.SnakeManager

case class NewUser(userID: Int, username: String, password: String)
case class Login(userID: Int, username: String, password: String)
case class Score(username:String, score: Int)


class SnakeController @Inject()(cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc){
//protected val dbConfigProvider: DatabaseConfigProvider,
  //mcc: MessagesControllerComponents)(implicit ec: ExecutionContext, system: ActorSystem)
  //extends MessagesAbstractController(mcc) with HasDatabaseConfigProvider[JdbcProfile]{
  

  val snakeManager = system.actorOf(SnakeManager.props)
  
  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      SnakeActor.props(out, snakeManager)
    }
  }
  
  def view() = Action { implicit request =>
    /*
    val r = scala.util.Random
    val tmpUserName = "" + r.nextInt(100)
     Ok(views.html.snakeCanvas(username))
     */
    
    request.session.get("connection").map { userName =>
      Ok(views.html.snakeCanvas(userName))
    }.getOrElse(Redirect(routes.DatabaseController.login()))
  }

  def snakeCanvas = Action { implicit request =>
     Ok(<canvas width = "500" height = "500" id = "snakeCanvas"/>)
  }

}
