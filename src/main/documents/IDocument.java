package main.documents;

import main.Abonne;
import main.exceptions.EmpruntException;
import main.exceptions.ReservationException;

public interface IDocument {
    int numero();
    // Lève une exception si le document est déjà réservé ou emprunté
    void reserver(Abonne ab) throws ReservationException;
    // Lève une exception si le document est réservé pour un autre abonné ou déjà emprunté
    void emprunter(Abonne ab) throws EmpruntException;
    // Sert au retour ou à l'annulation d'une réservation
    void retourner();
}
