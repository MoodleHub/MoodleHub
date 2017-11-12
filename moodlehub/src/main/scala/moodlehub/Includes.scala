package moodlehub

object Includes {

  implicit def stringToPath(s: String) = Path(s)
  implicit def pathToString(p: Path) = p.toString

  implicit def stringToToken(s: String) = Token(s)
  implicit def tokenToString(t: Token) = t.token

}
