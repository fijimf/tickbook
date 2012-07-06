package com.fijimf.util

import com.fijimf.instrument.USTreasuryNote
import org.joda.time.{DateTime, LocalDate}

object Tester {

  //    2-YEAR		NOTE	05-31-2012	05-31-2014	0.250	0.300	99.900374	912828SW1
  //    5-YEAR		NOTE	05-31-2012	05-31-2017	0.625	0.748	99.397464	912828SY7
  //    7-YEAR		NOTE	05-31-2012	05-31-2019	1.125	1.203	99.477861	912828SX9
  //    3-YEAR		NOTE	05-15-2012	05-15-2015	0.250	0.362	99.666118	912828SU5
  //    10-YEAR		NOTE	05-15-2012	05-15-2022	1.750	1.855	99.045657	912828SV3
  //    30-YEAR		BOND	05-15-2012	05-15-2042	3.000	3.090	98.248216	912810QW1
  def main(args: Array[String]) {
    new DateTime()
    val iss: LocalDate = new LocalDate("2012-05-15")
    val fcd: LocalDate = new LocalDate("2012-11-15")
    val mat: LocalDate = new LocalDate("2042-05-15")
    val t2y = USTreasuryNote("912810QW1", iss, fcd, mat, 3.0, 2)

    t2y.coupons.foreach(c=>{
      println(c)
      println(c.date.dayOfWeek().getAsText)

    })

    println(t2y.price(new LocalDate("2012-05-15"), 3.0))

  }
}
