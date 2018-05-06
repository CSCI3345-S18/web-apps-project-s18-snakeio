package models

object Tables {
  val profile = slick.jdbc.MySQLProfile
  import profile.api._
  
  class Users(tag: Tag) extends Table[User](tag, "userAccounts") {
    def userID = column[Int]("userID", O.PrimaryKey)
    def username = column[String]("username")
    def password = column[String]("password")
    def * = (userID, username, password) <> (User.tupled, User.unapply)
  }
  
  class HighScores(tag: Tag) extends Table[HighScore](tag, "highScores") {
    def scoreID = column[Int]("scoreID", O.PrimaryKey)
    def userID = column[Int]("userID")
    def score = column[Int]("score")
    def * = (scoreID, userID, score) <> (HighScore.tupled, HighScore.unapply)
  }
  
  val userAccounts = TableQuery[Users]
  val highscores = TableQuery[HighScores]
}