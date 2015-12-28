package factories

import com.example.api.daos.PostDAO
import com.example.api.models.Post
import com.google.inject.Inject
import java.util.UUID
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import utils.DatabaseHelper

class PostFactory @Inject() (postDAO: PostDAO, databaseConfigProvider: DatabaseConfigProvider) {

  def build(id: UUID = UUID.randomUUID(), deleted: Boolean = false): Post = Post(
    id = id,
    title = "title",
    body = "body",
    deleted = deleted
  )

  def create(deleted: Boolean = false)(implicit ec: ExecutionContext): Post = {
    DatabaseHelper.exec(postDAO.create(build(deleted = deleted)))
  }

}
