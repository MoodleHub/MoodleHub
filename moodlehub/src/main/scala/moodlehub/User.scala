package moodlehub

import scala.util.{Failure, Success}
import play.api.libs.json.JsValue

import scala.concurrent.Future

class User(token: String = "6aca2ab143095b1e8498c6e8c3364898") {
  var enrolledCourses: List[Course] = List()

  val siteInfo: Future[JsValue] = Client.getSiteInfo(token)

  siteInfo.onComplete {
    case Success(s) => {
      process(s)
    }
    case Failure(e) => throw e
  }

  private def process(value: JsValue): Unit = {
    val username= value("username").as[String]
    val userid = value("userid").as[Int]

    val coursesInfo: Future[JsValue] = Client.getUsersCourses(token, userid)
  }
}
