package nl.infowijs.codeassignment.models

import io.vertx.json.schema.JsonSchema
import io.vertx.json.schema.common.dsl.Schemas.objectSchema
import io.vertx.json.schema.common.dsl.Schemas.stringSchema
import io.vertx.json.schema.draft7.dsl.Keywords.maxLength

data class Person(
  var id: Int,
  val name: String,
  val avatar: String,
  val email: String? = null,
  val phone: String? = null
) {
  companion object {
    val SCHEMA: JsonSchema = JsonSchema.of(
      objectSchema()
        .requiredProperty("name", stringSchema().with(maxLength(128)))
        .requiredProperty("avatar", stringSchema().with(maxLength(512)))
        .optionalProperty("email", stringSchema().with(maxLength(128)))
        .optionalProperty("phone", stringSchema().with(maxLength(128)))
        .toJson()
    )
  }
}
