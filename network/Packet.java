package network;

import java.io.Serializable;

// just an information class to be serialized and sent to clients from client handlers
public class Packet implements Serializable {

    private static final long serialVersionUID = 4L;

    public String message; //for a message sent by the server (maybe from another client)

    public int clientID; //what client sent this packet
    public int x; //x position
    public int y; //y position
    public boolean attackState; //placeholder boolean just to test if multiple data types are sent correctly

    public int type; // for the client to determine if the packet sent was a message packet or a update player packet.
                    // 0: message packet
                    // 1: update player packet

    public Packet(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public Packet(int type, int clientID, int x, int y, boolean attackState) {
        this.type = type;
        this.clientID = clientID;
        this.x = x;
        this.y = y;
        this.attackState = attackState;
    }
}
