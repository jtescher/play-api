package services

import com.google.inject.Inject
import daos.PostDAO
import java.util.UUID
import models.Post
import models.errors.ModelNotFoundException
import scala.concurrent.{ ExecutionContext, Future }

class PostService @Inject() (postDAO: PostDAO)(implicit ec: ExecutionContext) {

  def all: Future[Seq[Post]] = postDAO.all

  def findOrCreate(post: Post): Future[Post] = postDAO.findOrCreate(post)

  def findById(id: UUID): Future[Post] = {
    postDAO.findById(id).flatMap {
      case Some(post) => Future.successful(post)
      case None => Future.failed(ModelNotFoundException)
    }
  }

  def update(post: Post): Future[Post] = postDAO.update(post)

  def destroyById(id: UUID): Future[Int] = postDAO.destroyById(id)

}
