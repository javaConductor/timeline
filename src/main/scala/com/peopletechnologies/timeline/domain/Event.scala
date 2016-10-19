package com.peopletechnologies.timeline.domain

import java.util.Date

import org.mongodb.scala.bson.BsonObjectId

import scala.concurrent.duration.Duration

/**
  * Created by lee on 10/12/16.
  */
trait Event

class BaseEvent(id: Option[BsonObjectId], title: String, when: Date, duration: Duration ) extends Event {
}

object OneTimeEvent {
  def apply(title: String, when: Date, duration: Duration,
            precision: EventTimePrecision ) = new OneTimeEvent( None, title, when, duration, precision)
}

case class OneTimeEvent(id: Option[BsonObjectId], title: String, when: Date, duration: Duration,
                        precision: EventTimePrecision = Day ) extends BaseEvent(id, title, when, duration) {
}
