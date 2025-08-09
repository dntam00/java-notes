import ws from 'k6/ws';

export const options = {
    vus: 10,
    duration: '30s',
};

export default function () {
    const url = 'ws://127.0.0.1:8082/ws';
    const params = {};
    const messageCount = 10000; // Total messages to send

    const res = ws.connect(url, params, function (socket) {
        socket.on('open', () => {
            console.log('WebSocket connection established');

            // Send messages with loop instead of setTimeout
            for (let i = 0; i < messageCount; i++) {
                socket.send(`Message ${i + 1} from k6`);
                sleep(0.01); // 10ms pause between messages
            }

            console.log(`Finished sending ${messageCount} messages`);
            socket.close();
        });

        socket.on('message', (data) => console.log('Message received: ', data));
        socket.on('close', () => console.log('disconnected'));

    });

    sleep(messageCount * 0.01 + 20);

}