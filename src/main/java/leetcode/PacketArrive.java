package leetcode;

public class PacketArrive {

    public static void main(String[] args) {
        System.out.println(new PacketArrive().maxShelfSize(new int[]{3, 2, 4, 5, 1}));
        System.out.println(new PacketArrive().maxShelfSize(new int[]{1,2,3,4,5}));
        System.out.println(new PacketArrive().maxShelfSize(new int[]{3, 2, 7, 5, 4, 1, 6}));
    }

    int maxShelfSize(int[] clients) {
        // clients: [3,2,4,5,1]
        // maxShelf = 0
        int currentShelf = 0;
        int maxShelf = 0;
        int currentPacketValue = 0;
        for (int client : clients) {
            int tobePacket = currentPacketValue + 1;
            if (client == tobePacket) {
                currentPacketValue = tobePacket;
                continue;
            }
            if (client > tobePacket) {
                currentShelf += (client - tobePacket);
                maxShelf = Math.max(currentShelf, maxShelf);
                // reset current packet to current client
                currentPacketValue = client;
            } else {
                currentShelf--;
            }
        }
        return maxShelf;
    }
}
