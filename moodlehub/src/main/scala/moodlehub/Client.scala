package moodlehub

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.concurrent.Future
import scala.util.{Success, Failure}

object Client {
  import DefaultBodyReadables._
  import scala.concurrent.ExecutionContext.Implicits._

  def main(args: Array[String]): Unit = {
    // Create Akka system for thread and streaming management
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    // Create the standalone WS client
    // no argument defaults to a AhcWSClientConfig created from
    // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
    val wsClient = StandaloneAhcWSClient()

    val x = call(wsClient)
      .andThen { case _ => wsClient.close() }
      .andThen { case _ => system.terminate() }
  }

  def call(wsClient: StandaloneWSClient): Future[Unit] = {
    wsClient.url("http://www.google.com").get().map { response =>
      val statusText: String = response.statusText
      val body = response.body[String]
      println(s"Got a response $statusText")
    }
  }
}