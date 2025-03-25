package tests;

import static org.junit.Assert.*;

import main.Mediatheque;
import main.Abonne;
import main.exceptions.*;
import main.documents.IDocument;
import org.junit.Before;
import org.junit.Test;

public class MediathequeTest {
    private Mediatheque mediatheque;

    @Before
    public void setUp() {
        // Création de l'instance de Mediathek avec des abonnés et des documents initialisés en dur
        mediatheque = new Mediatheque();
    }

    @Test
    public void testGetAbonne() {
        // Vérifier qu'un abonné existant est bien récupéré
        Abonne ab1 = mediatheque.getAbonne(1);
        assertNotNull("L'abonné avec le numéro 1 doit exister", ab1);
        assertEquals("Alice", ab1.getNom());

        // Vérifier qu'un abonné inexistant renvoie null
        Abonne abInexistant = mediatheque.getAbonne(999);
        assertNull("L'abonné avec le numéro 999 ne doit pas exister", abInexistant);
    }

    @Test
    public void testGetDocument() {
        // Vérifier qu'un document existant est bien récupéré
        IDocument doc101 = mediatheque.getDocument(101);
        assertNotNull("Le document 101 doit exister", doc101);
        assertEquals(101, doc101.numero());

        // Vérifier qu'un document inexistant renvoie null
        IDocument docInexistant = mediatheque.getDocument(999);
        assertNull("Le document avec le numéro 999 ne doit pas exister", docInexistant);
    }

    @Test
    public void testReservation() {
        // Récupérer un abonné et un document non réservé
        Abonne ab1Majeur = mediatheque.getAbonne(1);
        Abonne ab2Mineur = mediatheque.getAbonne(1);

        IDocument dvdFamilial = mediatheque.getDocument(1);
        assertNotNull("Le document 1 doit exister", ab2Mineur);

        // Test réservation sur doc non réservé
        try {
            dvdFamilial.reserver(ab2Mineur);
        } catch (ReservationException e) {
            fail("La réservation ne doit pas échouer pour un document disponible.");
        }

        // Test de réservation sur un doc réservé
        try {
            dvdFamilial.reserver(ab2Mineur);
            fail("La réservation doit échouer car le document est déjà réservé.");
        } catch (ReservationException e) {
        }


    }
}
