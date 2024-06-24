import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.css']
})
export class ChatWindowComponent implements OnInit {
  messages = [
    { text: "How can we help? We're here for you! 😄", time: "6:45", isMine: false },
    { text: "Hey John, I am looking for the best admin template.", time: "6:48", isMine: true },
    { text: "Could you please help me to find it out? 🤔", time: "6:49", isMine: true }
  ];
  newMessage = '';

  constructor() { }

  ngOnInit(): void {
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      this.messages.push({ text: this.newMessage, time: new Date().toLocaleTimeString(), isMine: true });
      this.newMessage = '';
    }
  }
}
