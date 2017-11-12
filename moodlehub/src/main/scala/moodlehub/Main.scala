package moodlehub

import java.io.{File, FileInputStream}

object Main {

  def main(args: Array[String]): Unit = {
    val token = Token(scala.io.Source.fromFile(new File("data/token")).getLines.mkString)
    User(token)
  }

}
