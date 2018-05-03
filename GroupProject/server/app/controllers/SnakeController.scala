
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



class SnakeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def view = Action { implicit request =>
     Ok(views.html.snakeCanvas())
  }

  def snakeCanvas = Action { implicit request =>
     Ok(<canvas width = "500" height = "500" id = "snakeCanvas"/>)
  }

}
