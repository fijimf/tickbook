package com.fijimf.instrument.util

import org.joda.time.LocalDate
import com.fijimf.util.Calendar


class SOMCouponSchedule(val firstCouponDate: LocalDate, val maturityDate: LocalDate, val frequency: Int, implicit val cal: Calendar) extends CouponScheduler {
  require(List(1, 2, 3, 4, 6, 12).contains(frequency), "Bad frequency passed to SOM coupon scheduler")
  val inc = 12 / frequency
  def gen = d => cal.toFirstBusinesDayOfMonth(d.plusMonths(inc))
}
