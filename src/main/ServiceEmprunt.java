package main;

import main.documents.IDocument;
import main.exceptions.EmpruntException;

import java.io.*;
import java.net.Socket;

public class ServiceEmprunt implements Runnable {
    private Socket client;
    private Mediatheque mediatheque;

    public ServiceEmprunt(Socket client, Mediatheque Mediatheque) {
        this.client = client;
        this.mediatheque = Mediatheque;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            out.println("Entrez votre numero d'abonne :");
            int numAbonne = Integer.parseInt(in.readLine());
            out.println("Entrez le numero du document a emprunter :");
            int numDoc = Integer.parseInt(in.readLine());

            Abonne ab = mediatheque.getAbonne(numAbonne);
            IDocument doc = mediatheque.getDocument(numDoc);
            String reponse;
            if (ab == null) {
                reponse = "Abonne non trouve.";
            } else if (doc == null) {
                reponse = "Document non trouve.";
            } else {
                synchronized (doc) {
                    try {
                        doc.emprunter(ab);
                        reponse = "Emprunt reussi pour le document " + numDoc;
                    } catch (EmpruntException e) {
                        reponse = "Echec de l'emprunt: " + e.getMessage();
                    }
                }
            }
            out.println(reponse);
        } catch (IOException e) {
            System.err.println("Erreur de communication (emprunt) : " + e.getMessage());
        } finally {
            try { client.close(); } catch (IOException ex) { }
        }
    }
}
