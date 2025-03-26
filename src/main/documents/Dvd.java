package main.documents;

import main.Abonne;
import main.exceptions.*;

public class Dvd extends Document {
    private boolean isAdult;

    public Dvd(int numero, String titre, boolean isAdult) {
        super(numero, titre);
        this.isAdult = isAdult;
    }

    @Override
    public synchronized void reserver(Abonne ab) throws ReservationException {
        if (emprunte) {
            throw new ReservationException("Le DVD est déjà emprunté.");
        }
        if (reserve) {
            throw new ReservationException("Le DVD est déjà réservé.");
        }
        if (isAdult && ab.getAge() < 16) {
            throw new ReservationException("Vous n'avez pas l'âge requis pour réserver ce DVD.");
        }
        reserve = true;
        reservant = ab;
        System.out.println("DVD \"" + titre + "\" réservé par " + ab.getNom());
    }

    @Override
    public synchronized void emprunter(Abonne ab) throws EmpruntException {
        if (emprunte) {
            throw new EmpruntException("Le DVD est déjà emprunté.");
        }
        if (reserve && (reservant == null || reservant.getNumero() != ab.getNumero())) {
            throw new EmpruntException("Le DVD est réservé pour un autre abonné.");
        }
        if (isAdult && ab.getAge() < 16) {
            throw new EmpruntException("Vous n'avez pas l'âge requis pour emprunter ce DVD.");
        }
        emprunte = true;
        reserve = false;
        System.out.println("DVD \"" + titre + "\" emprunté par " + ab.getNom());
    }
}
