package com.example.api.controllers.v1

import com.example.api.controllers.utils.ControllerConventions
import com.example.api.models.Post
import com.example.api.serializers.v1.PostSerializer._
import com.example.api.services.PostService
import com.google.inject.Inject
import java.util.UUID
import play.api.libs.json.{ Json, __ }
import play.api.mvc.{ Action, AnyContent, Controller }
import scala.concurrent.ExecutionContext

class PostsController @Inject() (postService: PostService)(implicit ec: ExecutionContext)
    extends Controller
    with ControllerConventions {

  def index: Action[AnyContent] = Action.async {
    postService.all.map { posts =>
      Ok(Json.obj("posts" -> posts))
    }
  }

  def create: Action[Post] = Action.async(jsonModelParser[Post](__ \ 'post)) { request =>
    postService.insertOrUpdate(request.body).map { post =>
      Created(Json.obj("post" -> post))
    }
  }

  def show(id: UUID): Action[AnyContent] = Action.async {
    postService.findById(id).map { post =>
      Ok(Json.obj("post" -> post))
    }
  }

  def update(id: UUID): Action[Post] = Action.async(jsonModelParser[Post](__ \ 'post)) { request =>
    postService.insertOrUpdate(request.body).map { post =>
      Ok(Json.obj("post" -> post))
    }
  }

  def destroy(id: UUID): Action[AnyContent] = Action.async {
    postService.destroyById(id).map { _ =>
      NoContent
    }
  }

}
