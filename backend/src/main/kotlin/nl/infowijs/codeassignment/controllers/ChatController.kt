package nl.infowijs.codeassignment.controllers

import io.vertx.ext.web.RoutingContext
import io.vertx.json.schema.Draft
import io.vertx.json.schema.JsonSchemaOptions
import io.vertx.json.schema.Validator
import nl.infowijs.codeassignment.models.Chat
import nl.infowijs.codeassignment.service.ChatService
import nl.infowijs.codeassignment.utils.ResponseUtils.buildCreatedResponse
import nl.infowijs.codeassignment.utils.ResponseUtils.buildErrorResponse
import nl.infowijs.codeassignment.utils.ResponseUtils.buildOkResponse

class ChatController(private val chatService: ChatService) {
  fun readAll(routingContext: RoutingContext) {
    chatService
      .readAll()
      .onSuccess { buildOkResponse(routingContext, it) }
      .onFailure { routingContext.fail(it) }
  }

  fun create(routingContext: RoutingContext) {
    val result = Validator
      .create(Chat.schema, JsonSchemaOptions().setDraft(Draft.DRAFT7))
      .validate(routingContext.body().asJsonObject())
    if(!result.valid) {
      return buildErrorResponse(routingContext, result.errors)
    }
    val chat: Chat = result.toJson().mapTo(Chat::class.java)
    chatService
      .create(chat)
      .onSuccess { buildCreatedResponse(routingContext, it) }
      .onFailure { routingContext.fail(it) }
  }
}
