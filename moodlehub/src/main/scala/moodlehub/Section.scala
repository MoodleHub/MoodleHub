package moodlehub

import play.api.libs.json.{JsArray, JsObject, JsValue}
import scala.collection.Map

class Section(token: Token, val coursePath: Path, obj: JsObject) {
  private val value = obj.value
  private val name = value("name").as[String]

  val path: Path = coursePath add util.formatString(name)
  FileManager.createDirectory(path)
  private val summary = value("summary").as[String]
  private val modules = value("modules").as[JsArray].value

  private val fileModules: List[Map[String, JsValue]] =
    modules.filter(_.as[JsObject].value("modplural").as[String] == "Files")
      .map(_.as[JsObject].value).toList

  fileModules.foreach { module =>
    val contents = module("contents").as[JsArray].value
    contents.foreach { file =>
      val fileMap = file.as[JsObject].value
      if(fileMap("type").as[String] == "file") {
        processFileMap(fileMap)
      }
    }
  }

  private def processFileMap(fileMap: Map[String, JsValue]): Unit = {
    val filename = fileMap("filename").as[String]
    val filepath = path add filename
    val fileurl = fileMap("fileurl").as[String]

    val filesize = fileMap("filesize").as[Int]
    val lastMod = fileMap("timemodified").as[Int]

    val change = FileManager.addTimeStamp(this, filename, lastMod)
    if(change){
      FileManager.downloadFile(fileurl, filepath)(token)
    }
  }

}



object Section {
  def apply(obj: JsObject)(implicit token: Token, path: Path): Section =
    new Section(token, path, obj)
}
