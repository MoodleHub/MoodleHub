package moodlehub

case class Path(path: String) {

  lazy val format: String = util.formatString(path)

  override def toString: String = format

}