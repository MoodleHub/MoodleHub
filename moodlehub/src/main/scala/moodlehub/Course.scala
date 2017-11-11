package moodlehub

class Course(name: String, courseId: Int) {



}

object Course {
  def apply(name: String, courseId: Int): Course = new Course(name, courseId)
}
