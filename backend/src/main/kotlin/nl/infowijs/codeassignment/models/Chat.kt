package nl.infowijs.codeassignment.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.vertx.json.schema.JsonSchema
import io.vertx.json.schema.common.dsl.Schemas.*
import io.vertx.json.schema.draft7.dsl.Keywords.maxLength

data class Chat(
  var id: Int,
  @JsonProperty("user_id") var userId: Int,
  @JsonProperty("person_id") var personId: Int,
  val name: String
) {
  companion object {
    val schema: JsonSchema = JsonSchema.of(
      objectSchema()
        .requiredProperty("person_id", intSchema())
        .requiredProperty("name", stringSchema().with(maxLength(128)))
        .toJson()
    )
  }
}
