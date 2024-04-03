import dayjs, { Dayjs } from 'dayjs';

export class PersonData {
  constructor(
    public id: number,
    public name: string,
    public avatar: string,
    public title?: string,
    public email?: string,
    public phone?: string
  ) {}

  static fromJSON(json: any): PersonData {
    return new PersonData(
      json.id,
      json.name,
      json.avatar,
      json.title,
      json.email,
      json.phone
    );
  }
}

export class MessageData {
  constructor(
    public id: number,
    public chatId: number,
    public senderId: number,
    public sender: PersonData,
    public message: string,
    public datetime: Dayjs
  ) {}

  static fromJSON(json: any): MessageData {
    return new MessageData(
      json.id,
      json.chat_id,
      json.sender_id,
      json.sender,
      json.message,
      dayjs(json.datetime)
    );
  }
}

export class ChatData {
  constructor(
    public id: number,
    public ownerId: number,
    public personId: number,
    public person: PersonData,
    public name: string
  ) {}

  static fromJSON(json: any): ChatData {
    return new ChatData(
      json.id,
      json.owner_id,
      json.person_id,
      PersonData.fromJSON(json.person),
      json.name
    );
  }
}
