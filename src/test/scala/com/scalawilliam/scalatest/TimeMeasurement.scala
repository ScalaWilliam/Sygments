package com.scalawilliam.scalatest
import scala.concurrent.duration.FiniteDuration

trait TimeMeasurement {

  implicit class numToTimes(x: Int) {
    def times = Times(x)
  }

  case class Times(value: Int)

  case class Rate(time: FiniteDuration) {
    val timesPerSecond = (1000.0 / time.toMillis).toInt
    override def toString = s"$timesPerSecond/s"
  }

  case class Measurement(totalTime: FiniteDuration, medianRate: Rate) {
    override def toString = s"Took $totalTime, median rate $medianRate"
  }

  private def calculateMedian[A](items: Vector[A])(implicit num: Numeric[A], aToDouble: A => Double): Double = {
    val sortedItems = items.sorted
    (for {
      countOfSideCuts <- Vector(0, -1).map(sortedItems.length / 2 + _)
      cutItems = sortedItems.drop(countOfSideCuts).dropRight(countOfSideCuts)
      if cutItems.nonEmpty
      average = cutItems.sum / cutItems.length.toDouble
    } yield average).head
  }

  private def time = System.currentTimeMillis

  def run(f: => Unit)(n: Times) = {
    val start = time
    val durations = scala.collection.mutable.MutableList.empty[Long]
    Iterator.continually {
      val start = time
      f
      durations += (time - start)
    }.take(n.value).foreach(identity)
    val end = time
    import concurrent.duration._
    val medianTime = calculateMedian(durations.toVector).millis
    Measurement(
      totalTime = ((end - start) / 1000).seconds,
      medianRate = Rate(medianTime)
    )
  }
}