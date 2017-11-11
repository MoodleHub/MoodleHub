package moodlehub

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.concurrent.Future

object Client {

  import DefaultBodyReadables._
  import scala.concurrent.ExecutionContext.Implicits._

  // Create Akka system for thread and streaming management
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // Create the standalone WS client
  // no argument defaults to a AhcWSClientConfig created from
  // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
  val wsClient = StandaloneAhcWSClient()

  private final val BASE_URL = "https://moodle.epfl.ch/webservice/rest/server.php"
  private final val FORMAT = "json"
  private final val USERID = "userid"
  private final val COURSEID = "courseid"

  private final val GET_SITE_INFO_FUN = "core_webservice_get_site_info"
  private final val GET_USERS_COURSES_FUN = "core_enrol_get_users_courses"
  private final val GET_CONTENTS_FUN = "core_course_get_contents"

  private final val BASE_REQUEST = s"$BASE_URL?moodlewsrestformat=$FORMAT&wsfunction="

  def stop(): Unit = {
    wsClient.close()
    system.terminate()
  }

  def getSiteInfo(implicit token: Token): Future[JsValue] =
    callFunction(GET_SITE_INFO_FUN) map Json.parse

  def getUsersCourses(userId: Int)(implicit token: Token): Future[JsValue] =
    callFunction(GET_USERS_COURSES_FUN, Map(USERID -> userId.toString)) map Json.parse

  def getContents(courseId: Int)(implicit token: Token): Future[JsValue] =
    callFunction(GET_CONTENTS_FUN, Map(COURSEID -> courseId.toString)) map Json.parse

  private def callFunction(name: String, args: Map[String, String] = Map())(implicit token: Token): Future[String] = {

    val builtArgs = {
      val sb = new StringBuilder()
      args.foreach { arg =>
        val argName = arg._1
        val argValue = arg._2
        sb.append(s"&$argName=$argValue")
      }
      sb.toString
    }

    println(BASE_REQUEST +
      name +
      builtArgs +
      s"&wstoken=$token"
    )

    wsClient.url(
      BASE_REQUEST +
        name +
        builtArgs +
        s"&wstoken=${token.token}"
    ).get().map { response =>
      response.body[String]
    }
  }

}
