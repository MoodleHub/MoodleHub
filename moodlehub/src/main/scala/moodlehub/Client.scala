package moodlehub

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object Client {
  import DefaultBodyReadables._
  import scala.concurrent.ExecutionContext.Implicits._

  private var stopped = false

  // Create Akka system for thread and streaming management
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // Create the standalone WS client
  // no argument defaults to a AhcWSClientConfig created from
  // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
  val wsClient = StandaloneAhcWSClient()

  private final val BASE_URL = "https://moodle.epfl.ch/webservice/rest/server.php"
  private final val FORMAT = "json"
  private final val TOKEN = "6aca2ab143095b1e8498c6e8c3364898"

  private final val GET_SITE_INFO_FUN = "core_webservice_get_site_info"

  private final val BASE_REQUEST = s"$BASE_URL?moodlewsrestformat=$FORMAT&wstoken=$TOKEN&wsfunction="

  def stop(): Unit = {
    wsClient.close()
    system.terminate()
    stopped = true
  }

  def getSiteInfo(): Unit = {
    callFunction(GET_SITE_INFO_FUN).onComplete {
      case Success(s) =>
        val json = Json.parse(s)
        println(json)
      case Failure(e) => throw e
    }
  }

  private def callFunction(name: String, args: Map[String, String] = Map()): Future[String] = {

    println(BASE_REQUEST +
      name +
      args.foreach{ arg =>
        val argName = arg._1
        val argValue = arg._2
        s"&$argName&$argValue"
      })

    wsClient.url(
      BASE_REQUEST +
      name +
        (if(args.nonEmpty) args.foreach{ arg =>
        val argName = arg._1
        val argValue = arg._2
        s"&$argName&$argValue"
      } else "")
    ).get().map { response =>
      response.body[String]
    }.
      andThen{case _ => wsClient.close()}.
      andThen{case _ => system.terminate()}
  }
}