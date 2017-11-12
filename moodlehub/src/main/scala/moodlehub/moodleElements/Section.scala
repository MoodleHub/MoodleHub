package moodlehub.moodleElements

import moodlehub.GUI.scenePresenter
import moodlehub._
import play.api.libs.json.{JsArray, JsObject, JsValue}

import scala.collection.Map
import scalafx.scene.control.TextArea

class Section(value: Map[String, JsValue], val path: Path, val newPath: Path, console: TextArea)(implicit token: Token) extends MoodleElement(token, newPath, console) {
  private val name = value("name").as[String]

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
    val filepath = newPath add filename
    val fileurl = fileMap("fileurl").as[String]

    val filesize = fileMap("filesize").as[Int]
    val lastMod = fileMap("timemodified").as[Int]

    val change = FileManager.addTimeStamp(this, filename, lastMod)
    if(change){
      FileManager.downloadFile(fileurl, filepath)(token)
      log(s"$filename downloaded\n")
    }
  }

}



object Section {
  def apply(value: Map[String, JsValue], console: TextArea)(implicit token: Token, path: Path): Section =
    new Section(value, path, path add util.formatString(value("name").as[String]), console)

}
