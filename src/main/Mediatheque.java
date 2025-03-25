package main;

import main.documents.Dvd;
import main.documents.IDocument;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Mediatheque {
    private Map<Integer, Abonne> abonnes;
    private Map<Integer, IDocument> documents;

    public Mediatheque() {
        abonnes = new HashMap<>();
        documents = new HashMap<>();
        initAbonnes();
        initDocuments();
    }

    private void initAbonnes() {
        abonnes.put(1, new Abonne(1, "Alice", LocalDate.of(2000, 5, 15)));
        abonnes.put(2, new Abonne(2, "Bob", LocalDate.of(2010, 3, 22)));
        abonnes.put(3, new Abonne(3, "Charlie", LocalDate.of(1995, 8, 30)));
    }

    private void initDocuments() {
        documents.put(101, new Dvd(101, "Film Action", true));
        documents.put(102, new Dvd(102, "Film Famille", false));

        // On peut ajouter d'autres types de documents (livres, CDs, etc.) si nécessaire
    }

    public Abonne getAbonne(int numero) {
        return abonnes.get(numero);
    }

    public IDocument getDocument(int numero) {
        return documents.get(numero);
    }
}