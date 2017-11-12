package moodlehub.moodleElements

import akka.actor.Actor
import moodlehub._
import moodlehub.observer.{Observer, Subject}
import play.api.libs.json.{JsObject, JsValue}

import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

class Course(val name: String, courseId: Int, path: Path)(implicit val token: Token) extends MoodleElement(token, path) { self =>

  var sections: Array[Section] = Array[Section]()
  private var user: User = _
  private var subjects = Array[Section]()

  Client.getContents(courseId)(token).onComplete {
    case Success(s) => sections = s.as[Array[JsValue]].map { section =>
      val s = Section(section.as[JsObject].value)(token, path)
      s.addObserver(self)

      s
    }
    case Failure(e) => throw e
  }

  def addObserver(observer: User): Unit = user = observer

  def notified(by: Section): Unit = {
    subjects = subjects.filter(_ == by)
    if(subjects.isEmpty) {
      user.notified(self)
    }
  }
}

object Course {
  def apply(name: String, courseId: Int)(implicit token: Token, path: Path): Course = new Course(name, courseId, path add name)
}
