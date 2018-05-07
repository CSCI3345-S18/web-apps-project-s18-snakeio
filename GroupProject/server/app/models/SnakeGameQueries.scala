package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import controllers.DatabaseController
import controllers.SnakeController
import controllers.Login
import controllers.NewUser
import controllers.Score

case class User(userID: Int, username: String, password: String)
case class HighScore(username:String, score: Int)

object SnakeGameQueries {
  import Tables._
  def allUsers(db:Database)(implicit ec:ExecutionContext):Future[Seq[User]] = {
    db.run(userAccounts.result)
  }

  def addUser(nu: NewUser, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    db.run {
      userAccounts += User(0, nu.username, nu.password)
    }
  }

  def checkCred(user: Login, db: Database)(implicit ec:ExecutionContext): Future[Boolean] = {
    // TODO: check if user inputed correct login creds
    db.run {
      userAccounts.filter(_.username === user.username).filter(_.password === user.password).exists.result
    }
  }
  
  def updateScores(sc: Score, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    // TODO: this is called after a player dies, score is compared to highScore table
    db.run {
      highScores += HighScore(sc.username, sc.score)
      
    }
  }
  
  def getHighScores(db:Database)(implicit ec: ExecutionContext):Future[Seq[HighScore]] = {
     //TODO
     db.run {
       highScores.sortBy(_.score.desc).take(5).result
     }
  }
  
  def sortScores(db:Database)(implicit ec:ExecutionContext):Future[Seq[HighScore]] = {
    db.run{
      highScores.sortBy(_.score.desc).result
    }
  }

}