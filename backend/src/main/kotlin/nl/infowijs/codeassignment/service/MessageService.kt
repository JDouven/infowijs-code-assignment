package nl.infowijs.codeassignment.service

import io.vertx.core.Future
import io.vertx.sqlclient.Pool
import nl.infowijs.codeassignment.models.Message
import nl.infowijs.codeassignment.repository.MessageRepository

class MessageService(private val dbClient: Pool, private val messageRepository: MessageRepository) {
  fun readAllFromChat(chatId: Int): Future<List<Message>> {
    return dbClient
      .withTransaction { messageRepository.selectWhereChatId(it, chatId) }
      .onSuccess { println("Read messages from chat $chatId") }
      .onFailure { println("Error: ${it.message}") }
  }

  fun create(message: Message): Future<Message> {
    return dbClient
      .withTransaction { messageRepository.create(it, message) }
      .onSuccess { println("Created message: ${it.id}") }
      .onFailure { println("Error: ${it.message}") }
  }
}
