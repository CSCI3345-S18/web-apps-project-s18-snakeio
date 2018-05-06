
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

case class NewUser(userID: Int, username: String, password: String)
case class Login(userID: Int, username: String, password: String)
case class Score(scoreID: Int, userID: Int, score: Int)


class SnakeController @Inject()(
protected val dbConfigProvider: DatabaseConfigProvider,
  mcc: MessagesControllerComponents)(implicit ec: ExecutionContext)
  extends MessagesAbstractController(mcc) with HasDatabaseConfigProvider[JdbcProfile]{
  
  val NewUserForm=Form(
      mapping(
          "userID" -> number,
          "username" -> nonEmptyText,
          "password" -> nonEmptyText
        )(NewUser.apply)(NewUser.unapply)
      )
  
      val LoginForm=Form(
      mapping (
          "userID" -> number,
        "username" -> nonEmptyText,
         "password" -> nonEmptyText
      )(Login.apply)(Login.unapply)  
    )
      
    def login = Action.async { implicit request=>
    LoginForm.bindFromRequest().fold(
      formWithErrors=>{
        val userFuture = SnakeGameQueries.allUsers(db)
        userFuture.map(users => Ok(views.html.snakeLogin(NewUserForm,formWithErrors)))
      },
      loggingin =>{
         val loginFuture = SnakeGameQueries.checkCred(loggingin,db)
          loginFuture.map{ cnt =>
          if(cnt == true){
            Redirect(routes.SnakeController.view()).flashing("message" -> "Login successful.")
          }
          else Redirect(routes.SnakeController.loginPage()).flashing("message" -> "Failed to login.")
          
        }
        
      }
    )
  }
    
    def addUser = Action.async{ implicit request =>
    NewUserForm.bindFromRequest().fold(
      formWithErrors=>{
        val userFuture = SnakeGameQueries.allUsers(db)
        userFuture.map(users => BadRequest(views.html.snakeLogin(formWithErrors, LoginForm)))
      },
      newUser =>{
         val addFuture = SnakeGameQueries.addUser(newUser,db)
        addFuture.map{ cnt =>
            if(cnt == 1){Redirect(routes.SnakeController.loginPage()).flashing("message" -> "New user added.")
                      
          }
          else Redirect(routes.SnakeController.loginPage()).flashing("message" -> "Failed to add new user.")
          
        }
        
      }
    )
  }
  
  def loginPage = Action { implicit request =>
    Ok(views.html.snakeLogin(NewUserForm,LoginForm))
  }
  
  def logout = Action { implicit request =>
    //TODO: logout stuff with session variables!!
    Ok(views.html.snakeLogin(NewUserForm,LoginForm))
  }
  
  def highscores = Action { implicit request =>
    Ok(views.html.snakeHighscore())
  }
  
  def view = Action { implicit request =>
     Ok(views.html.snakeCanvas())
  }

  def snakeCanvas = Action { implicit request =>
     Ok(<canvas width = "500" height = "500" id = "snakeCanvas"/>)
  }

}
