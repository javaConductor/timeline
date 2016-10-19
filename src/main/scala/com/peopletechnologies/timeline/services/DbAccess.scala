package com.peopletechnologies.timeline.services

import java.util.Date

import com.peopletechnologies.timeline.domain.{Day, Event, OneTimeEvent}
import org.mongodb.scala.bson.{BsonDateTime, BsonDocument, BsonObjectId}
import org.mongodb.scala.{Completed, Document, MongoClient, MongoDatabase, Observable, Observer, bson}
import org.mongodb.scala.model.Filters._

import scala.concurrent.duration.Duration

/**
  * Created by lee on 10/12/16.
  */
object DbAccess {

  //  // To directly connect to the default server localhost on port 27017
  //  val mongoClient: MongoClient = MongoClient()
  //
  // Use a Connection String
  val mongoClient: MongoClient = MongoClient("mongodb://localhost")

  // or provide custom MongoClientSettings
  //  val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("localhost")).asJava).build()
  //  val settings: MongoClientSettings = MongoClientSettings.builder().clusterSettings(clusterSettings).build()
  //  val mongoClient: MongoClient = MongoClient(settings)
  val database: MongoDatabase = mongoClient.getDatabase("timeline")
  val eventCollection = database.getCollection("events")

  def createEventId(): BsonObjectId = BsonObjectId()

  def documentToEvent(document: Document): OneTimeEvent = {
    val id = document.getOrElse( "_id", createEventId()).toString
    val title = document.getOrElse( "title", "").toString
    val when = document.getOrElse( "when", new Date()).asInstanceOf[Date]
    val durationString = document.getOrElse( "duration", "24 hours").toString
    val duration = Duration.create(durationString)
    new OneTimeEvent( Some(BsonObjectId( id )), title, when, duration, Day )
  }

  def eventToDocument(event: OneTimeEvent): Document = {
    val eventId =  event.id.getOrElse( createEventId())
    val doc = Document(
      "title" -> event.title,
      "_id" -> eventId.toString,
      "when" -> new BsonDateTime(event.when.getTime),
      "duration" -> event.duration.toString
    )
    doc
  }

  def addEvent(event: OneTimeEvent): Observable[Completed] = {
    val observable: Observable[Completed] = eventCollection.insertOne(eventToDocument(event))
    observable.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println("Inserted")
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = println("Completed")
    })
    observable
  }

  def findEvents(start: Date, end: Date): Observable[Seq[OneTimeEvent]] = {
    val observable: Observable[Seq[Document]] = eventCollection.find(and(gt("when", start), lte("when", end)))
      .collect()

    observable.subscribe(
      (data: Seq[Document]) => { data foreach println }, // onNext
      (error: Throwable) => println(s"Query failed: ${error.getMessage}"), // onError
      () => println("Done")
    )

    observable.transform[Seq[OneTimeEvent]](
      (seq: Seq[Document]) => {
        seq map documentToEvent
      },
      (t:Throwable) =>{ println(t.getLocalizedMessage) ;t }
    )

  }


}
