package com.fijimf.instrument.util

import org.joda.time.LocalDate
import com.fijimf.util.Calendar
import scala.math._

class DayOfMonthCouponSchedule(val firstCouponDate: LocalDate, val maturityDate: LocalDate, val dayOfMonth:Option[Int], val frequency: Int, implicit val cal: Calendar) extends CouponScheduler {
  require(List(1, 2, 3, 4, 6, 12).contains(frequency), "Bad frequency passed to DOM coupon scheduler")

  val inc = 12 / frequency
  val dom = dayOfMonth match {
    case Some(day) => day
    case None=> min(firstCouponDate.dayOfMonth().get,maturityDate.dayOfMonth().get)
  }

  def gen = d => cal.toBusinessDay(d.plusMonths(inc).withDayOfMonth(dom))
}
