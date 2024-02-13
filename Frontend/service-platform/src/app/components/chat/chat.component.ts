import { Attachment, ChatResponse, Message } from 'src/model/chatDTO.model';
import { ChatService } from './chat.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  constructor(private chatService: ChatService) {}

  ticketId: any = 1;

  chatData: ChatResponse = {   
    chatId: "",
    ticketId: "",
    messages: [],
    attachments: []
  };

  isUser: boolean = true; // Assuming user is the default role

  ngOnInit(): void {
    this.chatService.getChatByTicketId(this.ticketId).subscribe(data => this.chatData = data );
  }

  // Function to set the role based on a given message
  setMessageRole(message: Message): void {
    this.isUser = message.role === "User";
  }

  sortedMessages: (Message | Attachment)[] = [];

  sortMessages(): void {
    this.sortedMessages = [...this.chatData.messages, ...this.chatData.attachments].sort((a, b) => a.timestamp - b.timestamp);
  }
}
