package moodlehub

import play.api.libs.json.JsObject

class Section(path: Path, obj: JsObject) {
  println(path.path)
}

object Section {
  def apply(obj: JsObject)(implicit path: Path): Section =
    new Section(Path(
      path.path + obj.value("name").as[String] + "/"
    ), obj)
}