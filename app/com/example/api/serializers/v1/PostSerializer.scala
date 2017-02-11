package com.example.api.serializers.v1

import com.example.api.models.Post
import play.api.libs.functional.syntax._
import play.api.libs.json._
import java.util.UUID
import com.example.api.controllers.utils.ConstraintReadExtensions._

object PostSerializer {

  implicit val postJsonWrites = new Writes[Post] {
    def writes(post: Post): JsObject = Json.obj(
      "id" -> post.id,
      "title" -> post.title,
      "body" -> post.body
    )
  }

  implicit val postJsonReads: Reads[Post] = (
    (__ \ "id").read[UUID] and
    (__ \ "title").read[String](nonEmpty) and
    (__ \ "body").read[String](nonEmpty) and
    Reads.pure(false)
  )(Post.apply _)

}
