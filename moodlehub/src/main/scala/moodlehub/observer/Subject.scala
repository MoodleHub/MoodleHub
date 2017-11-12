package moodlehub.observer

import moodlehub.moodleElements.MoodleElement

trait Subject[T <: MoodleElement] { this: T =>

  private var observer: Observer[T] = _

  def addObserver(observer: Observer[T]): Unit = this.observer = observer

  def notifyObserver(): Unit = observer.notified(this)

}
