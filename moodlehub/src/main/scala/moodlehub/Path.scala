package moodlehub

case class Path(path: String) {

  def add (str: String) : Path = {
    val sb = new StringBuilder(path)
    if(str.length > 0) {
      if(str(0) != '/') {sb.append('/')}
    }
    sb.append(str)
    Path(sb.toString)
  }


  lazy val format: String = util.formatString(path)

  override def toString: String = format
}
