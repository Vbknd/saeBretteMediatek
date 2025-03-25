package main;

import main.documents.IDocument;

import java.io.*;
import java.net.Socket;

public class ServiceRetour implements Runnable {
    private Socket client;
    private Mediatheque mediatheque;

    public ServiceRetour(Socket client, Mediatheque mediatheque) {
        this.client = client;
        this.mediatheque = mediatheque;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            out.println("Entrez le numero du document à retourner :");
            int numDoc = Integer.parseInt(in.readLine());

            IDocument doc = mediatheque.getDocument(numDoc);
            String reponse;
            if (doc == null) {
                reponse = "Document non trouve.";
            } else {
                synchronized (doc) {
                    doc.retourner();
                    reponse = "Retour effectue pour le document " + numDoc;
                }
            }
            out.println(reponse);
        } catch (IOException e) {
            System.err.println("Erreur de communication (retour) : " + e.getMessage());
        } finally {
            try { client.close(); } catch (IOException ex) { }
        }
    }
}
