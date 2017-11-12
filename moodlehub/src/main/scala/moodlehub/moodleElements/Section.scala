package moodlehub.moodleElements

import moodlehub.GUI.{SceneController, scenePresenter}
import moodlehub._
import play.api.libs.json.{JsArray, JsObject, JsValue}

import scala.collection.Map
import scalafx.scene.control.TextArea

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits._

class Section(value: Map[String, JsValue], val path: Path, val newPath: Path, sceneController: SceneController)(implicit token: Token)
  extends MoodleElement(token, newPath, sceneController) { self =>

  private val summary = value("summary").as[String]
  private val modules = value("modules").as[JsArray].value

  private var course: Course = _

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

    val missing = FileManager.fileMissing(filepath)

    val change = FileManager.addTimeStamp(this, filename, lastMod)
    if(missing || change){
      FileManager.downloadFile(fileurl, filepath)(token)
    }

    Future(course.notified(self))
  }

  def addObserver(observer: Course): Unit = course = observer

}

object Section {
  def apply(value: Map[String, JsValue], sceneController: SceneController)(implicit token: Token, path: Path): Section =
    new Section(value, path, path add util.formatString(value("name").as[String]), sceneController)

}
