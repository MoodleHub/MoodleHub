package moodlehub.moodleElements

import moodlehub.GUI.{SceneController, scenePresenter}
import moodlehub._
import moodlehub.observer.Observer
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalafx.scene.control.TextArea

class User(token: Token, moodleHubPath: Path, sceneController: SceneController) extends MoodleElement(token, moodleHubPath, sceneController) { self =>

  var enrolledCourses: Array[Course] = Array[Course]()
  val path: Path = moodleHubPath

  private var subjects = Array[Course]()

  val siteInfo: Future[JsValue] = Client.getSiteInfo(token)

  siteInfo.onComplete {
    case Success(s) => processUserInfo(s)
    case Failure(e) => throw e
  }

  private def processUserInfo(value: JsValue): Unit = {
    val username = value("username").as[String]
    val userid = value("userid").as[Int]
    val userPath = path add username
    FileManager.createDirectory(userPath)


    val coursesInfo: Future[JsValue] = Client.getUsersCourses(userid)(token)

    coursesInfo.onComplete {
      case Success(s) =>
        enrolledCourses = processCoursesInfo(s.as[Array[JsValue]], userPath)
        subjects = enrolledCourses.clone()
      case Failure(e) => throw e
    }
  }

  private def processCoursesInfo(courses: Array[JsValue], userPath: Path): Array[Course] = {
    courses.map { course =>
      val shortname = course("shortname").as[String]
      val fullname = course("fullname").as[String]
      val courseId = course("id").as[Int]

      val c: Course = Course(s"${shortname}_$fullname", courseId, sceneController)(token, userPath)
      c.addObserver(self)

      c
    }
  }

  def notified(by: Course): Unit = {
    subjects = subjects.filter(_ == by)
    if(subjects.isEmpty) {
      Client.stop()
    }
  }

}

object User {
  def apply(token: Token, path: Path = DEFAULT_PATH, sceneController: SceneController): User = new User(token, path, sceneController)

  private val DEFAULT_PATH: Path = Path("/tmp/moodleHub")
}
