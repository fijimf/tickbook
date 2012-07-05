package com.fijimf.util

import org.joda.time.{DateTimeConstants, LocalDate}

object CalendarNY extends Calendar {
  val holidays = Map.empty[LocalDate, String]
}

trait Calendar {
  def holidays: Map[LocalDate, String]

  def isBusinessDay(d: LocalDate) = !(d.dayOfWeek().get() == DateTimeConstants.SATURDAY || d.dayOfWeek().get() == DateTimeConstants.SUNDAY || holidays.contains(d))

  def nextBusinessDay(d: LocalDate): LocalDate = {
    Stream.iterate(d.plusDays(1))(_.plusDays(1)).find(e => isBusinessDay(e)).get
  }

  def prevBusinessDay(d: LocalDate): LocalDate = {
    Stream.iterate(d.plusDays(1))(_.minusDays(1)).find(e => isBusinessDay(e)).get
  }

  def modNextBusinessDay(d: LocalDate): LocalDate = {
    val d1 = nextBusinessDay(d)
    if (d1.getMonthOfYear == d.getMonthOfYear) {
      d1
    } else {
      prevBusinessDay(d)
    }
  }

  def modPrevBusinessDay(d: LocalDate): LocalDate = {
    val d1 = prevBusinessDay(d)
    if (d1.getMonthOfYear == d.getMonthOfYear) {
      d1
    } else {
      nextBusinessDay(d)
    }
  }

  def isEom(d: LocalDate) = (d == d.dayOfMonth().withMaximumValue())

  def isLastBusinessDayOfMonth(d: LocalDate) = (isBusinessDay(d) && isEom(d)) || (isEom(nextBusinessDay(d)))

  def toEom(d: LocalDate) = d.dayOfMonth().withMaximumValue()

  def toLastBusinesDayOfMonth(d: LocalDate) = {
    val d1 = toEom(d)
    if (isBusinessDay(d1)) d1 else prevBusinessDay(d1)
  }

  def isSom(d: LocalDate) = (d == d.dayOfMonth().withMinimumValue())

  def isFirstBusinessDayOfMonth(d: LocalDate) = (isBusinessDay(d) && isSom(d)) || (isEom(prevBusinessDay(d)))

  def toSom(d: LocalDate) = d.dayOfMonth().withMinimumValue()

  def toFirstBusinesDayOfMonth(d: LocalDate) = {
    toBusinessDay(toSom(d))
  }

  def toBusinessDay(d: LocalDate) = {
    if (isBusinessDay(d)) d else nextBusinessDay(d)
  }


}
