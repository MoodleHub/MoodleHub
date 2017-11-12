package moodlehub.moodleElements

import moodlehub.GUI.{SceneController, scenePresenter}
import moodlehub.{FileManager, Path, Token}

import scalafx.scene.control.TextArea

abstract class MoodleElement(token: Token, path: Path, sceneController: SceneController) {

  FileManager.createDirectory(path)

  def log(str: String) = {
    if(sceneController != null) sceneController.log(str)
  }

}

object MoodleElement {

  private val TOKEN: Token = Token("6aca2ab143095b1e8498c6e8c3364898")
  private val DEFAULT_PATH: Path = Path("/tmp/moodleHub")

}
