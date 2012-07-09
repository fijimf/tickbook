package com.fijimf.instrument

import util._
import com.fijimf.util.CalendarNY
import org.joda.time.LocalDate

case class USTreasuryNote(cusip: String, issueDate: LocalDate, firstCouponDate: LocalDate, maturity: LocalDate, coupon: Double, origTerm: Int) extends Bond {
  val calendar = CalendarNY
  val frequency = 2
  val coupons = scheduler.couponDates.map(d => Scheduled(d, coupon)).toList

  private[this] def scheduler: CouponScheduler = {
    if (calendar.isFirstBusinessDayOfMonth(firstCouponDate))
      new StartOfMonthCouponSchedule(firstCouponDate, maturity, frequency, calendar)
    else if (calendar.isLastBusinessDayOfMonth(firstCouponDate))
      new EndOfMonthCouponSchedule(firstCouponDate, maturity, frequency, calendar)
    else
      new DayOfMonthCouponSchedule(firstCouponDate, maturity, None, frequency, calendar)
  }

}
