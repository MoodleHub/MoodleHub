package moodlehub.moodleElements

import moodlehub.{FileManager, Path, Token}

abstract class MoodleElement(token: Token, path: Path) {

  FileManager.createDirectory(path)

}

object MoodleElement {

  private val TOKEN: Token = Token("6aca2ab143095b1e8498c6e8c3364898")
  private val DEFAULT_PATH: Path = Path("/tmp/moodleHub")

}
