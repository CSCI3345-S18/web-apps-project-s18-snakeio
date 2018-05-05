package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

case class User(userID: Int, username: String, password: String)
case class HighScore(scoreID: Int, userID: Int, score: Int)

object SnakeGameQueries {
  import Tables._
  
  def addUser()(implicit ec: ExecutionContext) {
    // TODO
  }
  
  def checkCred()(implicit ec:ExecutionContext) {
    // TODO: check if user inputed correct login creds
  }
  
  def updateScores()(implicit ec: ExecutionContext) {
    // TODO: this is called after a player dies, score is compared to highScore table
  }
  
  def getHighScores()(implicit ec: ExecutionContext) {
     //TODO
  }
}