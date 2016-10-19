
package com.peopletechnologies.timeline

import java.util.Date

import com.peopletechnologies.timeline.domain.{Day, OneTimeEvent}
import com.peopletechnologies.timeline.services.DbAccess
import org.mongodb.scala.Completed

import scala.concurrent.duration.Duration

object TimelineApp {
  def main(args: Array[String]) {
    println("Hello, world!")

    val e = OneTimeEvent ("Lee's birthday", new Date(1966,10,28), Duration.create("24 hours"), Day)
      val observer = DbAccess.addEvent( e )
      observer.subscribe((c: Completed) => {
      println(" Just wrote event.")

    })

    val now = new Date().getTime
    while (new Date().getTime < now + 5000){

    }

    Thread.currentThread().join()

  }
}

