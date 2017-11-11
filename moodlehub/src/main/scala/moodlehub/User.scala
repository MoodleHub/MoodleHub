package moodlehub

import com.sun.codemodel.internal.JArray

import scala.util.{Failure, Success}
import play.api.libs.json.{JsArray, JsValue}

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class User(token: String = "6aca2ab143095b1e8498c6e8c3364898") {

  var enrolledCourses: List[Course] = List()

  val siteInfo: Future[JsValue] = Client.getSiteInfo(token)

  siteInfo.onComplete {
    case Success(s) => processUserInfo(s)
    case Failure(e) => throw e
  }

  private def processUserInfo(value: JsValue): Unit = {
    val username= value("username").as[String]
    val userid = value("userid").as[Int]

    val coursesInfo: Future[JsValue] = Client.getUsersCourses(token, userid)

    coursesInfo.onComplete {
      case Success(s) => processCoursesInfo(s.as[Array[JsValue]])
      case Failure(e) => throw e
    }
  }

  private def processCoursesInfo(courses: Array[JsValue]): Unit = {
    courses.map { course =>
      val shortname = course("shortname")
      val fullname = course("fullname")
      val courseId = course("id")

      Course(s"${shortname}_$fullname", courseId)
    }
  }
}

object User {
  def apply(token: String = "6aca2ab143095b1e8498c6e8c3364898"): User = new User(token)
}
