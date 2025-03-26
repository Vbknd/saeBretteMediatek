package main.documents;

import main.Abonne;
import main.exceptions.*;

public class Livre extends Document {
    private final int nbPages;

    public Livre(int numero, String titre, int nbPages) {
        super(numero, titre);
        this.nbPages = nbPages;
    }

    @Override
    public synchronized void reserver(Abonne ab) throws ReservationException {
        if (emprunte) {
            throw new ReservationException("Le livre est déjà emprunté.");
        }
        if (reserve) {
            throw new ReservationException("Le livre est déjà réservé.");
        }
        reserve = true;
        reservant = ab;
        System.out.println("Livre \"" + titre + "\" réservé par " + ab.getNom());
    }

    @Override
    public synchronized void emprunter(Abonne ab) throws EmpruntException {
        if (emprunte) {
            throw new EmpruntException("Le livre est déjà emprunté.");
        }
        if (reserve && (reservant == null || reservant.getNumero() != ab.getNumero())) {
            throw new EmpruntException("Le livre est réservé pour un autre abonné.");
        }
        emprunte = true;
        reserve = false;
        System.out.println("Livre \"" + titre + "\" emprunté par " + ab.getNom());
    }

    public int getNbPages() {
        return nbPages;
    }
}
