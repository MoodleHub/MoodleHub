package moodlehub

object util {

  private val formatRegex = "[^0-9a-zA-Z/]+".r

  def formatString(s: String): String =
    formatRegex.replaceAllIn(s, "_").replace("amp", "&")

}
