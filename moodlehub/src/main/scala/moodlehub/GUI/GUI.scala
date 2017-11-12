package moodlehub.GUI

import java.io.IOException

import moodlehub.{Path, User}
import java.io.IOException
import java.net.URL

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

object GUI extends JFXApp {
/*

  stage = new PrimaryStage {
    title = "MoodleHub GUI"
    scene = new Scene {
      fill = White
      content = new HBox {
        padding = Insets(20)
        children = Seq(
          new Button{
            text = "Download moodle"
            defaultButton = true
          }
        )
      }
    }
  }

  val a = new DirectoryChooser{
    title = "test"
    initialDirectory = new java.io.File("/tmp/test/")
  }
  val selected = a.showDialog(stage)
  User(path = Path(selected.getAbsolutePath))*/

  val resource: URL = getClass.getResource("/scene.fxml")
  if (resource == null) {
    throw new IOException("Cannot load resource: scene.fxml")
  }

  val root = FXMLView(resource, NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "FXML GridPane Demo"
    scene = new Scene(root)
  }
}
