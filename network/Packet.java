package network;

import java.io.Serializable;

// An information class to be serialized and sent from client handlers to clients, who will deserialize the class

/* The plan is for two types of packets to ever be sent (although they could be changed in the future).

The first packet type is just a message packet from the server, whether it's from another client or from the server itself.
The client that receives this type will just print out the message to the player.

The second packet type is for updating a player's position and state in the (hopefully) multiplayer game.
This second packet type would have the coordinates as well as any other state information (like attackState or something like that).
The client that receives this would have a function in the game class (or whatever class handles players) that updates
the senderClient's position and states on the receivingClient's end. */

public class Packet implements Serializable {

    private static final long serialVersionUID = 4L;

    public String message;      // for a message sent by the server (maybe from another client)

    public int clientID;        // what client sent this packet
    public int x;               // x position of sender client
    public int y;               // y position of sender client
    public boolean attackState; // placeholder boolean just to test if multiple data types are sent correctly

    /* for the client to determine if the packet sent was a message packet or an update player packet.
        0: message packet
        1: update player packet
    */
    public int type;

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
