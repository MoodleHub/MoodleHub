package moodlehub

import sys.process._
import java.net.URL
import java.io.File

object FileManager {

  def fileDownloader(url: String, filename: String) = {
    new URL(url) #> new File(filename) !!
  }
}
