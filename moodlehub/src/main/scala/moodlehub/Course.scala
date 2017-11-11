package moodlehub

class Course(name: String, courseId: String) {



}

object Course {
  def apply(name: String, courseId: String): Course = new Course(name, courseId)
}
