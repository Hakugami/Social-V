export interface Notification {
    id: number;
    title: string;
    description: string;
    image: string;
    time: string;
    senderUsername: string;
    receiverUsername: string;
    message: string;
    notificationType: string;
}
