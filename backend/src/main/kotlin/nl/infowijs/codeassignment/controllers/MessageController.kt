package nl.infowijs.codeassignment.controllers

import io.vertx.ext.web.RoutingContext
import io.vertx.json.schema.Draft
import io.vertx.json.schema.JsonSchemaOptions
import io.vertx.json.schema.Validator
import nl.infowijs.codeassignment.models.Message
import nl.infowijs.codeassignment.service.MessageService
import nl.infowijs.codeassignment.utils.ResponseUtils.buildCreatedResponse
import nl.infowijs.codeassignment.utils.ResponseUtils.buildErrorResponse
import nl.infowijs.codeassignment.utils.ResponseUtils.buildOkResponse

class MessageController(private val messageService: MessageService) {

  fun readAll(routingContext: RoutingContext) {
    val chatId: Int = routingContext.request().getParam("chatId").toInt()
    messageService
      .readAllFromChat(chatId)
      .onSuccess { buildOkResponse(routingContext, it) }
      .onFailure { routingContext.fail(it) }
  }

  fun readLast(routingContext: RoutingContext) {
    val chatId: Int = routingContext.request().getParam("chatId").toInt()
    messageService
      .readLastFromChat(chatId)
      .onSuccess { buildOkResponse(routingContext, it) }
      .onFailure { routingContext.fail(it) }
  }

  fun create(routingContext: RoutingContext) {
    val result = Validator
      .create(Message.SCHEMA, JsonSchemaOptions().setDraft(Draft.DRAFT7))
      .validate(routingContext.body().asJsonObject())
    if (!result.valid) {
      return buildErrorResponse(routingContext, result.errors)
    }
    val message: Message = result.toJson().mapTo(Message::class.java)
    messageService
      .create(message)
      .onSuccess { buildCreatedResponse(routingContext, it) }
      .onFailure { routingContext.fail(it) }
  }
}
