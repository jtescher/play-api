package com.example.api.services

import com.example.api.daos.PostDAO
import com.example.api.models.Post
import com.example.api.models.exceptions.ModelNotFoundException
import com.google.inject.Inject
import java.util.UUID
import scala.concurrent.{ ExecutionContext, Future }

class PostService @Inject() (postDAO: PostDAO)(implicit ec: ExecutionContext) {

  def all: Future[Seq[Post]] = postDAO.all

  def findById(id: UUID): Future[Post] = {
    postDAO.findById(id).flatMap {
      case Some(post) => Future.successful(post)
      case None => Future.failed(ModelNotFoundException)
    }
  }

  def insertOrUpdate(post: Post): Future[Post] = postDAO.insertOrUpdate(post)

  def destroyById(id: UUID): Future[Int] = postDAO.destroyById(id)

}
