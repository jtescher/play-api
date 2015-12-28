package com.example.api.models

import java.util.UUID

case class Post(id: UUID, title: String, body: String, deleted: Boolean)
