package controllers.v1

import com.google.inject.Inject
import controllers.utils.ConstraintReadExtensions.nonEmpty
import controllers.utils.ControllerConventions
import java.util.UUID
import models.Post
import play.api.libs.functional.syntax._
import play.api.libs.json.{ Reads, __, Json }
import play.api.mvc.{ Action, AnyContent, Controller }
import scala.concurrent.ExecutionContext
import serializers.v1.PostSerializer.postJsonWrites
import services.PostService

class PostsController @Inject() (postService: PostService)(implicit ec: ExecutionContext) extends Controller with ControllerConventions {

  def index: Action[AnyContent] = Action.async {
    postService.all.map { posts =>
      Ok(Json.obj("posts" -> posts))
    }
  }

  def create: Action[Post] = Action.async(jsonModelParser[Post](__ \ 'post)) { request =>
    postService.findOrCreate(request.body).map { post =>
      Created(Json.obj("post" -> post))
    }
  }

  def show(id: UUID): Action[AnyContent] = Action.async {
    postService.findById(id).map { post =>
      Ok(Json.obj("post" -> post))
    }
  }

  def update(id: UUID): Action[Post] = Action.async(jsonModelParser[Post](__ \ 'post)) { request =>
    postService.update(request.body).map { post =>
      Ok(Json.obj("post" -> post))
    }
  }

  def destroy(id: UUID): Action[AnyContent] = Action.async {
    postService.destroyById(id).map { _ =>
      NoContent
    }
  }

  implicit val postJsonReads = (
    (__ \ "id").read[UUID] and
    (__ \ "title").read[String](nonEmpty) and
    (__ \ "body").read[String](nonEmpty) and
    Reads.pure(false)
  )(Post.apply _)

}
