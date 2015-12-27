package controllers.utils

import play.api.data.validation.ValidationError
import play.api.libs.json.Reads
import play.api.libs.json.Reads.filter

object ConstraintReadExtensions {

  /**
   * Defines a nonEmpty constraint for String Reads.
   * `.read(nonEmpty)`.
   */
  def nonEmpty(implicit reads: Reads[String]): Reads[String] = {
    filter[String](ValidationError("invalid"))(_.nonEmpty)
  }

}
