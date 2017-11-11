package moodlehub

import sys.process._
import java.net.URL
import java.io.File

object FileManager {

  def fileDownloader(url: String, filename: String)(implicit token: Token) = {
    new URL(s"$url&token=${token.token}") #> new File(filename) !!
  }
}
