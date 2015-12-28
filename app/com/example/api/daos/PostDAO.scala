package com.example.api.daos

import com.example.api.models.Post
import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

class PostDAO @Inject() (val dbConfigProvider: DatabaseConfigProvider) extends DAOConventions[Post] {
  import driver.api._
  val table = TableQuery[ModelTable]

  class ModelTable(tag: Tag) extends BaseTable(tag, "posts") {
    def title: Rep[String] = column[String]("title")
    def body: Rep[String] = column[String]("body")

    def * = (id, title, body, deleted) <> (Post.tupled, Post.unapply) // scalastyle:ignore
  }

}
