package com.peopletechnologies.timeline.services

import java.util.Date

import com.peopletechnologies.timeline.domain._

import scala.concurrent.duration.Duration

/**
  * Created by lee on 10/12/16.
  */
object TimelineService {

}

class TimelineService {

  def addEvent(title: String, when: Date, duration: Duration) : Event = {


    val event = OneTimeEvent(null, title, when, duration, Day)

    event
  }
}
