package moodlehub.moodleElements

import moodlehub.GUI.scenePresenter
import moodlehub._
import play.api.libs.json.{JsObject, JsValue}

import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}
import scalafx.scene.control.TextArea

class Course(courseId: Int, path: Path, console: TextArea)(implicit val token: Token) extends MoodleElement(token, path, console) {

  var sections: Array[Section] = _

  Client.getContents(courseId)(token).onComplete {
    case Success(s) => sections = s.as[Array[JsValue]].map { section =>
      Section(section.as[JsObject].value, console)(token, path)
    }
    case Failure(e) => throw e
  }

}

object Course {
  def apply(name: String, courseId: Int, console: TextArea)(implicit token: Token, path: Path): Course = new Course(courseId, path add name, console)
}
