package nl.infowijs.codeassignment.repository

import io.vertx.core.Future
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.templates.SqlTemplate
import nl.infowijs.codeassignment.models.Chat

private const val SQL_SELECT_ALL = "SELECT * FROM chats"
private const val SQL_INSERT = "INSERT INTO chats (name) VALUES (#{name}) RETURNING id"

class ChatRepository {
  fun selectAll(connection: SqlConnection): Future<List<Chat>> {
    return SqlTemplate
      .forQuery(connection, SQL_SELECT_ALL)
      .mapTo(Chat::class.java)
      .execute(emptyMap())
      .map { rowSet ->
        val chats = mutableListOf<Chat>()
        rowSet.forEach(chats::add)
        chats
      }
  }

  fun create(connection: SqlConnection, chat: Chat): Future<Chat> {
    return SqlTemplate
      .forQuery(connection, SQL_INSERT)
      .mapFrom(Chat::class.java)
      .mapTo(Chat::class.java)
      .execute(chat)
      .map { rowSet ->
        val iterator = rowSet.iterator()
        if (iterator.hasNext()) {
          chat.id = iterator.next().id
          chat
        } else {
          throw IllegalStateException("Error creating chat");
        }
      }
  }
}
