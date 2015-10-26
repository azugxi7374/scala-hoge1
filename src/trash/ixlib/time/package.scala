package ixlib

import java.util.{Calendar}


/**
 * Created on 2014/09/24.
 */
package object time {

  implicit def int2DurationBuilder(v: Int) = new TimeBuilder(Time(0), v)

  def Today: Date = {
    val cal = Calendar.getInstance
    cal.setTimeInMillis(System.currentTimeMillis)
    import Calendar._
    Date(cal.get(YEAR), cal.get(MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
  }

  def Tomorrow = Today + (1 days)

  def Now = Date(System.currentTimeMillis)

  /**
  {{{
	Date(2014, 9, 24)

	Date(2014, 9, 24) + Time(21, 59, 3)

	Today + (21 h 59 m)
	}}}
    *
    */
  object Date {
    def apply(y: Int, month: Int, d: Int, h: Int, min: Int, s: Int): Date = {
      val cal = Calendar.getInstance
      cal.set(y, month - 1, d, h, min, s)
      new Date(cal.getTimeInMillis)
    }

    def apply(y: Int, month: Int, d: Int): Date = {
      apply(y, month, d, 0, 0, 0)
    }

  }

  case class Date(ms: Long) {
    def +(time: Time) = {
      val cal = this.toCalendar
      Date(cal.getTimeInMillis + time.ms)
    }

    private def toCalendar = {
      val cal = Calendar.getInstance
      cal.setTimeInMillis(ms)
      cal
    }

    override def toString = {
      new java.util.Date(ms).toString
    }
  }

  object Time {
    //def apply(hh: Int, mm: Int, ss: Int) = hh h mm m ss s
  }

  case class Time(ms: Long) {
    def later: Date = Date(currentTime + ms)

    //		def toSec: Long = ms / 1000
    //		def toMin = toSec / 60
    //		def toHours = toMin / 60
    //		def toDays = toHours / 24

    def +(d2: Time) = Time(ms + d2.ms)

    def *(v: Int) = Time(ms * v)
  }

  class TimeBuilder(t: Time, v: Int) {
    def sec: Time = t + new Time(v * 1000)

    def minutes: Time = t + (v * 60 sec)

    def hours: Time = t + (v * 60 minutes)

    def days: Time = t + (v * 24 hours)

    def secs(v2: Int): TimeBuilder = new TimeBuilder(this.sec, v2)

    def minutes(v2: Int): TimeBuilder = new TimeBuilder(this.minutes, v2)

    def hours(v2: Int): TimeBuilder = new TimeBuilder(this.hours, v2)

    def days(v2: Int): TimeBuilder = new TimeBuilder(this.days, v2)

  }

}

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//



