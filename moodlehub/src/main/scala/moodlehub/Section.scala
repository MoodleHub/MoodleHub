package moodlehub

import play.api.libs.json.JsObject

class Section(obj: JsObject) {
  println(obj.value("name"))
}

object Section{
  def apply(obj: JsObject): Section = new Section(obj)
}