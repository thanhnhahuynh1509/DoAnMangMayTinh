package com.htn.mmt.network;

import java.io.*;
import java.net.*;

public class Client {
    private DatagramSocket socket;
    private DatagramPacket senderPacket;
    private DatagramPacket receiverPacket;

    private String host;
    private int port;

    public Client(String host, int port) throws IOException {
        socket = new DatagramSocket();
        socket.setSoTimeout(5000);
        String check = "New client has just connected";
        DatagramPacket checkDg = new DatagramPacket(check.getBytes(), check.length(), InetAddress.getByName(host), port);
        socket.send(checkDg);
        this.host = host;
        this.port = port;
    }

    public void send(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        byte[] data = baos.toByteArray();

        senderPacket = new DatagramPacket(data, data.length, InetAddress.getByName(host), port);
        socket.send(senderPacket);
    }

    public Object receivedObject() throws IOException, ClassNotFoundException {
        socket.receive(receiverPacket);
        ByteArrayInputStream bais = new ByteArrayInputStream(receiverPacket.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    public String receiveMessage() throws IOException {
        byte[] message = new byte[254];
        receiverPacket = new DatagramPacket(message, message.length);
        socket.receive(receiverPacket);
        return new String(receiverPacket.getData(), 0, receiverPacket.getLength());
    }

}
