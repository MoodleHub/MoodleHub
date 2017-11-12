package moodlehub.GUI

import java.io.File

import moodlehub.{Path, Token}
import moodlehub.moodleElements.User

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.stage.DirectoryChooser
import scalafxml.core.macros.sfxml

@sfxml
class scenePresenter(val launchButton: Button,
                     val dirButton: Button,
                     val login: TextField,
                     val password: TextField,
                     val console: TextArea) {


  var selected: File = _

  val sceneController = new SceneController(console)

//  val scene : moodlehub.GUI.scenePresenter = this

  def launch(event: ActionEvent) = {
    val token = Token(scala.io.Source.fromFile(new File("data/token")).getLines.mkString)
    User(token, Path(selected.getAbsolutePath), sceneController)
    sceneController.clear().log("launched\n")
  }

  def findDir(event: ActionEvent) = {
    val dirChooser = new DirectoryChooser{
      title = "MoodleHub Directory location"
      initialDirectory = new java.io.File("/tmp")
    }

    selected = dirChooser.showDialog(GUI.stage)
  }



}

class SceneController(val console: TextArea){

  def log(str: String) = {
    if(console.length > 100) clear()
    console.appendText(str + "\n")
  }

  def clear() = {
    console.clear()
    this
  }
}
