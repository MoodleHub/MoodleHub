package moodlehub

import sys.process._
import java.net.URL
import java.io.{File, FileInputStream, FileOutputStream}

import play.api.libs.json._

object FileManager {

  val TIMESTAMPS = ".timestamps.json"

  def downloadFile(url: String, filename: Path)(implicit token: Token): Unit = {
    new URL(s"$url&token=${token.token}") #> new File(filename.path) !!
  }

  def createDirectory(path: Path): Unit = {
    new File(path.path).mkdir()
  }

  def getTimeStamps(section: Section): JsObject = {
    val tsPath = section.path add TIMESTAMPS
    val file = new File(tsPath.path)
    if(!file.exists) {
      // create dummy file
      writeTimeStamps(tsPath, Json.stringify(new JsObject(Map())))
    }
    val in = new FileInputStream(file)
    Json.parse(in).as[JsObject]
  }

  private def writeTimeStamps(path: Path, json: String): Unit = {
    val out = new FileOutputStream(new File(path.path))
    out.write(json.getBytes)
  }

  /**
    *
    * @param section
    * @param filename
    * @param stamp
    * @return true iff the timestamp was modified
    */
  def addTimeStamp(section: Section, filename: String, stamp: Int): Boolean = {
    val json = getTimeStamps(section)
    val tsPath = section.path add TIMESTAMPS
    val timeStamp = json.value.get(filename) match {
      case Some(jsValue) => jsValue.as[Int]
      case None => -1
    }
    val ret = stamp > timeStamp
    if(ret) {
      val jonSnow = JsObject(json.value + (filename -> JsNumber(stamp)))
      writeTimeStamps(tsPath, Json.stringify(jonSnow))
    }
    ret
  }
}
