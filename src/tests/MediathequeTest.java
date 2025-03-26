package tests;

import org.junit.Before; 
import org.junit.Test; 
import static org.junit.Assert.*;

import main.Mediatheque; 
import main.Abonne; 
import main.documents.*;

public class MediathequeTest {

    private Abonne adulte, enfant;
    private Document dvdAdult, dvdFamilial, livre;

    @Before
    public void setUp() {
        Mediatheque mediatheque = new Mediatheque();
        // Mediatheque définit : 1 (majeure), 2 (mineur)
        adulte = mediatheque.getAbonne(1);
        enfant = mediatheque.getAbonne(2);
        dvdAdult = mediatheque.getDocument(101);
        dvdFamilial = mediatheque.getDocument(102);
        // Création manuelle d'un Livre
        livre = mediatheque.getDocument(1);
    }

    @Test
    public void testReservationSuccess() {
        try {
            dvdFamilial.reserver(adulte);
        } catch (Exception e) {
            fail("Réservation doit réussir");
        }
    }

    @Test
    public void testDoubleReservationFail() {
        try {
            dvdFamilial.reserver(adulte);
        } catch (Exception e) {
            fail("1ère résa OK");
        }
        try {
            dvdFamilial.reserver(adulte);
            fail("Double résa doit échouer");
        } catch (Exception e) {
        }
    }

    @Test
    public void testReservationAdultFailForEnfant() {
        try {
            dvdAdult.reserver(enfant);
            fail("Mineur sur DVD adulte");
        } catch (Exception e) {
        }
    }

    @Test
    public void testBorrowSuccess() {
        try {
            dvdFamilial.reserver(adulte);
            dvdFamilial.emprunter(adulte);
        } catch (Exception e) {
            fail("Emprunt doit réussir");
        }
    }

    @Test
    public void testBorrowFailDifferent() {
        try {
            dvdFamilial.reserver(adulte);
        } catch (Exception e) {
            fail("Résa OK");
        }
        try {
            dvdFamilial.emprunter(enfant);
            fail("Emprunt par autre doit échouer");
        } catch (Exception e) {
        }
    }

    @Test
    public void testReturnResets() {
        try {
            dvdFamilial.reserver(adulte);
            dvdFamilial.emprunter(adulte);
            dvdFamilial.retourner();
            dvdFamilial.reserver(enfant);
        } catch (Exception e) {
            fail("Retour doit réinitialiser");
        }
    }

    @Test
    public void testLivreReservationAndBorrow() {
        try {
            livre.reserver(enfant);
            livre.emprunter(enfant);
            livre.retourner();
            livre.reserver(adulte);
            livre.emprunter(adulte);
        } catch (Exception e) {
            fail("Livre doit fonctionner");
        }
    }

    @Test
    public void testLivreReempruntAfterReturn() {
        try {
            // Premier cycle : emprunt par major
            livre.reserver(adulte);
            livre.emprunter(adulte);
            livre.retourner();
            // Deuxième cycle : réemprunt par major
            livre.reserver(adulte);
            livre.emprunter(adulte);
            livre.retourner();
            // Troisième cycle : emprunt par minor
            livre.reserver(enfant);
            livre.emprunter(enfant);
        } catch (Exception e) {
            fail("Livre doit être réempruntable après retour");
        }
    }
}