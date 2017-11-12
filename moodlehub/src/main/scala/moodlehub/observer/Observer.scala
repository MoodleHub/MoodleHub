package moodlehub.observer

import moodlehub.moodleElements.MoodleElement

trait Observer[T <: MoodleElement] { this: T =>

  protected var children: List[Subject[T]] = Nil

  def addChild(child: Subject[T]): Unit = {
    children = child :: children
    child.addObserver(this)
  }

  def notified(by: T)

}
