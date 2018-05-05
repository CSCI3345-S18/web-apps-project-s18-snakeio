package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

case class User(userID: Int, username: String, password: String)
case class HighScore(scoreID: Int, userID: Int, score: Int)

object SnakeGameQueries {
  import Tables._
/*
  def addUser(nu: NewUser, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    // TODO: add user to database

    db.run {
      users += User(nu.userID, nu.username, nu.password)
    }
  }
  
  def checkCred(user: Login, db: Database)(implicit ec:ExecutionContext): Future[Boolean] = {
    // TODO: check if user inputed correct login creds
    db.run {
      users.filter(_.username === user.username).filter(_.password === user.password).exists.result
    }
  }
  
  def updateScores(sc: Score, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    // TODO: this is called after a player dies, score is compared to highScore table
    db.run {
      highscores += HighScore(sc.scoreID, sc.userID, sc.score)
    }
  }
  
  def getHighScores(db:Database)(implicit ec: ExecutionContext):Future[Seq[HighScore]] = {
     //TODO
     db.run {
       highscores.sortWith(_ > _).take(10)
     }
  }
*/
}

