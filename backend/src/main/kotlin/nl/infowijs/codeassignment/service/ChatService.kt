package nl.infowijs.codeassignment.service

import io.vertx.core.Future
import io.vertx.sqlclient.Pool
import nl.infowijs.codeassignment.models.Chat
import nl.infowijs.codeassignment.repository.ChatRepository

class ChatService(private val dbClient: Pool, private val chatRepository: ChatRepository) {
  fun readAll(): Future<List<Chat>> {
    return dbClient
      .withTransaction { chatRepository.selectAll(it) }
      .onSuccess { println("Read all chats") }
      .onFailure { println("Error: ${it.message}") }
  }

  fun create(chat: Chat): Future<Chat> {
    return dbClient
      .withTransaction { chatRepository.create(it, chat) }
      .onSuccess { println("Created chat") }
      .onFailure { println("Error: ${it.message}") }
  }
}
