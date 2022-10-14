package com.htn.mmt.network;

import com.htn.mmt.database.Database;
import com.htn.mmt.modal.Student;
import com.htn.mmt.service.StudentService;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Map;

public class Server {
    private static final Server server = new Server();
    private DatagramSocket socket;
    private DatagramPacket senderPacket;
    private DatagramPacket receiverPacket;

    private StudentService studentService;

    private Server() {

    }

    public static Server getInstance() {
        return server;
    }

    public void openServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public void connectToDatabase() throws IOException, RuntimeException {
        byte[] buffer = new byte[1024];
        receiverPacket = new DatagramPacket(buffer, buffer.length);
        System.out.println("Waiting for client connect...");
        socket.receive(receiverPacket);

        ByteArrayInputStream bais = new ByteArrayInputStream(receiverPacket.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            Map<String, String> map = (Map<String, String>) ois.readObject();
            Database.connect(map.get("url"), map.get("username"), map.get("password"));
            System.out.println("Client has connected database successfully!");
            send("Success!");
            studentService = new StudentService();

        } catch (ClassNotFoundException | SQLException e) {
            send(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void send(String message) throws IOException {
        senderPacket = new DatagramPacket(message.getBytes(), message.length(),
                receiverPacket.getAddress(), receiverPacket.getPort());
        socket.send(senderPacket);
    }

    public void send(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        byte[] data = baos.toByteArray();
        senderPacket = new DatagramPacket(data, data.length, receiverPacket.getAddress(), receiverPacket.getPort());
        socket.send(senderPacket);
    }

    public void handleConnect() throws IOException {
        byte[] buffer = new byte[1024];
        receiverPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receiverPacket);
        System.out.println(new String(receiverPacket.getData(), 0, receiverPacket.getLength()));
        socket.send(receiverPacket);
    }

    public void receiveAndHandle() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[1024];
        receiverPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receiverPacket);
        System.out.println("Has received message from client");

        ByteArrayInputStream bais = new ByteArrayInputStream(receiverPacket.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student student = (Student) ois.readObject();
        System.out.println("Student: " + student);
        studentService.save(student);

        try {
            send(studentService.gets());
        } catch(Exception ex) {
            ex.printStackTrace();
            send(ex.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server1 = Server.getInstance();
        server1.openServer(8000);
        server1.handleConnect();
        server1.connectToDatabase();
        while(true) {
            server1.receiveAndHandle();
        }
    }
}
