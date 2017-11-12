package moodlehub

import java.io.File
import moodleElements.User

object Main {

  def main(args: Array[String]): Unit = {
    val token = Token(scala.io.Source.fromFile(new File("data/token")).getLines.mkString)
    User(token)

    Client.system.terminate()
  }

}
