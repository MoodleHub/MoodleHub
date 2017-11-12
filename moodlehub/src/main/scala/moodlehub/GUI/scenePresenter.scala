package moodlehub.GUI

import java.io.File

import moodlehub.{Path, User}

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextField}
import scalafx.stage.DirectoryChooser
import scalafxml.core.macros.sfxml

@sfxml
class scenePresenter(private val launchButton: Button,
                     private val dirButton: Button,
                     private val login: TextField,
                     private val password: TextField) {

  var selected: File = _


  def launch(event: ActionEvent) = {
    User(path = Path(selected.getAbsolutePath))
  }

  def findDir(event: ActionEvent) = {
    val dirChooser = new DirectoryChooser{
      title = "MoodleHub Directory location"
      initialDirectory = new java.io.File("/tmp/test/")
    }

    selected = dirChooser.showDialog(GUI.stage)
  }


}
