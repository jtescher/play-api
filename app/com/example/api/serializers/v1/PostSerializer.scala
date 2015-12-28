package com.example.api.serializers.v1

import com.example.api.models.Post
import play.api.libs.json.{ JsObject, Json, Writes }

object PostSerializer {

  implicit val postJsonWrites = new Writes[Post] {
    def writes(post: Post): JsObject = Json.obj(
      "id" -> post.id,
      "title" -> post.title,
      "body" -> post.body
    )
  }

}
