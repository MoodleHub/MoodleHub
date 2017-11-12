package moodlehub.moodleElements

import moodlehub._
import play.api.libs.json.{JsObject, JsValue}

import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

class Course(courseId: Int, path: Path)(implicit val token: Token) extends MoodleElement(token, path) {

  var sections: Array[Section] = _

  Client.getContents(courseId)(token).onComplete {
    case Success(s) => sections = s.as[Array[JsValue]].map { section =>
      Section(section.as[JsObject].value)(token, path)
    }
    case Failure(e) => throw e
  }

}

object Course {
  def apply(name: String, courseId: Int)(implicit token: Token, path: Path): Course = new Course(courseId, path add name)
}
