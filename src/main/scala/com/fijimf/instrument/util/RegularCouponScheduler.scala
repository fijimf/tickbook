package com.fijimf.instrument.util

import org.joda.time.LocalDate

trait RegularCouponScheduler extends CouponScheduler {
  def firstCouponDate: LocalDate

  def maturityDate: LocalDate

  def couponDates: Seq[LocalDate] = Stream.iterate(firstCouponDate)(gen)
    .takeWhile(d => !d.isAfter(maturityDate))
    .map(List(_, maturityDate).minBy(_.toDateMidnight.getMillis))

  def gen: (LocalDate) => LocalDate
}
