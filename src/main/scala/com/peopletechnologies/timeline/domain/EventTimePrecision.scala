package com.peopletechnologies.timeline.domain

/**
  * Created by lee on 10/12/16.
  */

trait EventTimePrecision

object Century extends EventTimePrecision
object Year extends EventTimePrecision
object Month extends EventTimePrecision
object Day extends EventTimePrecision
object Hour extends EventTimePrecision
object Minute extends EventTimePrecision
object Seconds extends EventTimePrecision
object Milliseconds extends EventTimePrecision

