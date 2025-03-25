package main.documents;

import main.Abonne;
import main.exceptions.EmpruntException;
import main.exceptions.ReservationException;

public class Dvd implements IDocument {
    private int numero;
    private String titre;
    private boolean isAdult;
    private boolean reserve;
    private boolean emprunte;
    private Abonne reservant; // L'abonné qui a réservé (si applicable)

    public Dvd(int numero, String titre, boolean isAdult) {
        this.numero = numero;
        this.titre = titre;
        this.isAdult = isAdult;
        this.reserve = false;
        this.emprunte = false;
        this.reservant = null;
    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public synchronized void reserver(Abonne ab) throws ReservationException {
        if (emprunte) {
            throw new ReservationException("Le document est déjà emprunté.");
        }
        if (reserve) {
            throw new ReservationException("Le document est déjà réservé.");
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
            throw new EmpruntException("Le document est déjà emprunté.");
        }
        if (reserve && (reservant == null || reservant.getNumero() != ab.getNumero())) {
            throw new EmpruntException("Le document est réservé pour un autre abonné.");
        }
        if (isAdult && ab.getAge() < 16) {
            throw new EmpruntException("Vous n'avez pas l'âge requis pour emprunter ce DVD.");
        }
        emprunte = true;
        reserve = false;
        System.out.println("DVD \"" + titre + "\" emprunté par " + ab.getNom());
    }

    @Override
    public synchronized void retourner() {
        if (emprunte || reserve) {
            emprunte = false;
            reserve = false;
            reservant = null;
            System.out.println("DVD \"" + titre + "\" retourné.");
        }
    }
}
