import dayjs, { Dayjs } from 'dayjs';

export class Person {
  constructor(
    public name: string,
    public avatar: string,
    public title?: string,
    public email?: string,
    public phone?: string
  ) {}

  static fromJSON(json: any): Person {
    return new Person(
      json.name,
      json.avatar,
      json.title,
      json.email,
      json.phone
    );
  }
}

export class Message {
  constructor(
    public id: number,
    public message: string,
    public person: Person,
    public datetime: Dayjs
  ) {}

  static fromJSON(json: any): Message {
    return new Message(
      json.id,
      json.message,
      Person.fromJSON(json.person),
      dayjs(json.datetime)
    );
  }
}
