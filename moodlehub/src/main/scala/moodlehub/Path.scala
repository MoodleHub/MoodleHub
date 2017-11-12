package moodlehub

import Includes._

case class Path(path: String) {

  def add(str: String): Path =
    path + appendSlash(path) + str + appendSlash(str)

  private def appendSlash(s: String): String = if(s.last != '/') "/" else ""


  lazy val format: String = util.formatString(path)

  override def toString: String = format
}
