package moodlehub

import sys.process._
import java.net.URL
import java.io.{File, FileInputStream, FileOutputStream}

import play.api.libs.json._

object FileManager {

  def downloadFile(url: String, filename: Path)(implicit token: Token) = {
    new URL(s"$url&token=${token.token}") #> new File(filename.path) !!
  }

  def createDirectory(path: Path) = {
    new File(path.path).mkdir()
  }

  def getTimeStamps(section: Section): JsObject = {
    val tsPath = section.coursePath add ".timestamps.json"
    val file = new File(tsPath.path)
    if(! file.exists) {
      // create dummy file
      instantiateTimeStamps(tsPath)
    }
    val in = new FileInputStream(file)
    Json.parse(in).as[JsObject]
  }

  private def instantiateTimeStamps(path: Path) = {
    val json = Json.stringify(new JsObject(Map()))
    val out = new FileOutputStream(new File(path.path))
    out.write(json.getBytes)
  }
}
