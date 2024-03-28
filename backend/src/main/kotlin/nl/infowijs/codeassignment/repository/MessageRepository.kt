package nl.infowijs.codeassignment.repository

import io.vertx.core.Future
import io.vertx.sqlclient.SqlClient
import io.vertx.sqlclient.templates.SqlTemplate
import nl.infowijs.codeassignment.models.Message

private const val SQL_SELECT_BY_CHATID = "SELECT * FROM messages WHERE chat_id = #{chat_id}"
private const val SQL_INSERT =
  "INSERT INTO messages (chat_id, sender, message) VALUES (#{chat_id}, #{sender}, #{message}) RETURNING id"

class MessageRepository {
  fun selectWhereChatId(client: SqlClient, chatId: Int): Future<List<Message>> {
    return SqlTemplate
      .forQuery(client, SQL_SELECT_BY_CHATID)
      .mapTo(Message::class.java)
      .execute(mapOf(Pair("chat_id", chatId)))
      .map { rowSet ->
        val messages = mutableListOf<Message>()
        rowSet.forEach(messages::add)
        messages
      }
  }

  fun create(client: SqlClient, message: Message): Future<Message> {
    return SqlTemplate
      .forQuery(client, SQL_INSERT)
      .mapFrom(Message::class.java)
      .mapTo(Message::class.java)
      .execute(message)
      .map { rowSet ->
        val iterator = rowSet.iterator()
        if (iterator.hasNext()) {
          iterator.next()
        } else {
          throw IllegalStateException("Error creating chat");
        }
      }
  }
}
