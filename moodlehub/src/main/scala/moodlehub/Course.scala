package moodlehub

import play.api.libs.json.{JsArray, JsObject, JsValue}

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits._

class Course(token: String, name: String, courseId: Int) {

  var sections: Array[Section] = _

  Client.getContents(token, courseId).onComplete {
    case Success(s) => sections = s.as[Array[JsValue]].map{ section =>
      Section(section.as[JsObject])
    }
    case Failure(e) => throw e
  }

}

object Course {
  def apply(token: String, name: String, courseId: Int): Course = new Course(token, name, courseId)
}
