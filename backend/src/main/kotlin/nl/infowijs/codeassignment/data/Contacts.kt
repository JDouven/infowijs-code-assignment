package nl.infowijs.codeassignment.data

import nl.infowijs.codeassignment.models.Person

class Contacts() {
  companion object {
    fun getContacts(): List<Person> {
      return listOf(
          Person(
              "Hans van Maanen",
              "https://randomuser.me/api/portraits/men/12.jpg",
              "hans@email.com"
          ),
          Person(
              "Belinda de Vries",
              "https://randomuser.me/api/portraits/women/12.jpg",
              phone = "+31612345678"
          ),
          Person(
              "Gerrit Groot",
              "https://randomuser.me/api/portraits/men/24.jpg",
              "gerritemail.com",
              "06 - 00 000 000"
          ),
          Person(
              "Monique Willems",
              "https://randomuser.me/api/portraits/women/24.jpg",
              "monique@email.com"
          ),
      )
    }
  }
}
