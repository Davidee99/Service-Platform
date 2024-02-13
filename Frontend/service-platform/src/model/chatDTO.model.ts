
export interface Message {
    content: string;
    timestamp: number; // Unix timestamp
    role: 'Operator' | 'User';
}

export interface Attachment {
    link: string;
    timestamp: number; // Unix timestamp
    role: 'Operator' | 'User';
}

export interface ChatResponse {
    chatId: string;
    ticketId: string;
    messages: Message[];
    attachments: Attachment[];
}