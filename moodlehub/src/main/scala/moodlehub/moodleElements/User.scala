package moodlehub.moodleElements

import moodlehub._
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.util.{Failure, Success}

class User(token: Token, moodleHubPath: Path) extends MoodleElement(token, moodleHubPath) {

  var enrolledCourses: Array[Course] = _
  val path: Path = moodleHubPath

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
      case Success(s) => enrolledCourses = processCoursesInfo(s.as[Array[JsValue]], userPath)
      case Failure(e) => throw e
    }
  }

  private def processCoursesInfo(courses: Array[JsValue], userPath: Path): Array[Course] = {
    courses.map { course =>
      val shortname = course("shortname").as[String]
      val fullname = course("fullname").as[String]
      val courseId = course("id").as[Int]

      Course(s"${shortname}_$fullname", courseId)(token, userPath)
    }
  }
}

object User {
  def apply(token: Token = TOKEN, path: Path = DEFAULT_PATH): User = new User(token, path)

  private val TOKEN: Token = Token("6aca2ab143095b1e8498c6e8c3364898")
  private val DEFAULT_PATH: Path = Path("/tmp/moodleHub/")
}
