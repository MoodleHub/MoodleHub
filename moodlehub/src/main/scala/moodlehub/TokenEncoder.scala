package moodlehub

import java.io.{File, FileOutputStream}

object TokenEncoder {

  def apply(strToken: String): Unit = {
    val out = new FileOutputStream(new File("data/token"))
    out.write(strToken.getBytes)
  }
}
