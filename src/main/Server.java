package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT_RESERVATION = 2000;
    public static final int PORT_EMPRUNT = 3000;
    public static final int PORT_RETOUR = 4000;

    public static void main(String[] args) {
        Mediatheque Mediatheque = new Mediatheque();
        // Demarrer chaque service dans un thread distinct
        new Thread(() -> startService(PORT_RESERVATION, Mediatheque, "reservation")).start();
        new Thread(() -> startService(PORT_EMPRUNT, Mediatheque, "emprunt")).start();
        new Thread(() -> startService(PORT_RETOUR, Mediatheque, "retour")).start();
    }

    private static void startService(int port, Mediatheque Mediatheque, String typeService) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Service " + typeService + " demarre sur le port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Runnable service;
                switch (typeService) {
                    case "reservation":
                        service = new ServiceReservation(clientSocket, Mediatheque);
                        break;
                    case "emprunt":
                        service = new ServiceEmprunt(clientSocket, Mediatheque);
                        break;
                    case "retour":
                        service = new ServiceRetour(clientSocket, Mediatheque);
                        break;
                    default:
                        throw new IllegalArgumentException("Service inconnu");
                }
                new Thread(service).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur sur le port " + port + ": " + e.getMessage());
        }
    }
}