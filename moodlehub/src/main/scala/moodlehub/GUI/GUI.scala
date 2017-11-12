package moodlehub.GUI

import java.io.IOException

import moodlehub.Path
import java.io.IOException
import java.net.URL

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

object GUI extends JFXApp {

  val resource: URL = getClass.getResource("/leo.fxml")
  if (resource == null) {
    throw new IOException("Cannot load resource: scene.fxml")
  }

  val root = FXMLView(resource, NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "FXML GridPane Demo"
    scene = new Scene(root)
  }
}
