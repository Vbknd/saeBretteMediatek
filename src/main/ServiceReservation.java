package main;

import main.documents.IDocument;
import main.exceptions.ReservationException;

import java.io.*;
import java.net.Socket;

public class ServiceReservation implements Runnable {
    private Socket client;
    private Mediatheque Mediatheque;

    public ServiceReservation(Socket client, Mediatheque Mediatheque) {
        this.client = client;
        this.Mediatheque = Mediatheque;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            out.println("Entrez votre numero d'abonne :");
            int numAbonne = Integer.parseInt(in.readLine());
            out.println("Entrez le numero du document à reserver :");
            int numDoc = Integer.parseInt(in.readLine());

            Abonne ab = Mediatheque.getAbonne(numAbonne);
            IDocument doc = Mediatheque.getDocument(numDoc);
            String reponse;
            if (ab == null) {
                reponse = "Abonne non trouve.";
            } else if (doc == null) {
                reponse = "Document non trouve.";
            } else {
                synchronized (doc) {
                    try {
                        doc.reserver(ab);
                        reponse = "Reservation reussie pour le document " + numDoc;
                    } catch (ReservationException e) {
                        reponse = "Echec de la reservation: " + e.getMessage();
                    }
                }
            }
            out.println(reponse);
        } catch (IOException e) {
            System.err.println("Erreur de communication (reservation) : " + e.getMessage());
        } finally {
            try { client.close(); } catch (IOException ex) { }
        }
    }
}
