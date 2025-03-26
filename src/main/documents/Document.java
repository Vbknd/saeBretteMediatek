package main.documents;

import main.Abonne;
import main.exceptions.*;

public abstract class Document {
    protected int numero;
    protected String titre;
    protected boolean reserve;
    protected boolean emprunte;
    protected Abonne reservant;

    public Document(int numero, String titre) {
        this.numero = numero;
        this.titre = titre;
        this.reserve = false;
        this.emprunte = false;
        this.reservant = null;
    }

    public int getNumero() {
        return numero;
    }

    // Méthode de retour commune à tous les documents
    public void retourner() {
        if (emprunte || reserve) {
            emprunte = false;
            reserve = false;
            reservant = null;
            System.out.println("Document \"" + titre + "\" retourné.");
        }
    }

    // Méthodes à implémenter dans les sous-classes
    public abstract void reserver(Abonne ab) throws ReservationException;
    public abstract void emprunter(Abonne ab) throws EmpruntException;

    public String getTitre() {
        return titre;
    }
}
