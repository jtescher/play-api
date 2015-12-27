package utils

import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

object DatabaseHelper {

  def exec[T](action: Future[T]): T = Await.result(action, 2.seconds)

}
