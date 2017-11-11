package moodlehub

import play.api.libs.json.{JsArray, JsObject, JsValue}
import scala.collection.Map

class Section(obj: JsObject) {
  private val value = obj.value
  private val name = value("name").as[String]
  private val summary = value("summary").as[String]
  private val modules = value("modules").as[JsArray].value

  private val fileModules: List[Map[String, JsValue]] = modules.filter(_.as[JsObject].value("modplural").as[String] == "Files").map(_.as[JsObject].value).toList

  fileModules.foreach { module =>
    val contents = module("contents").as[JsArray].value
    contents.foreach { file =>
      val fileMap = file.as[JsObject].value
      if(fileMap("type").as[String] == "file") println(fileMap("filename"))
    }
  }
  println()
}

object Section {
  def apply(obj: JsObject)(implicit path: Path): Section =
    new Section(Path(
      path.path + obj.value("name").as[String] + "/"
    ), obj)
}
