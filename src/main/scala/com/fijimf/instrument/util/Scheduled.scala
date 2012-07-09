package com.fijimf.instrument.util

import org.joda.time.LocalDate

case class Scheduled[T](date: LocalDate, value: T)
