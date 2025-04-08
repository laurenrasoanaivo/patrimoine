package school.hei.patrimoine.cas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;

class TianaCasTest {

    private TianaCas tianaCas;

    @BeforeEach
    void setUp() {
        // Initialisation du cas de Tiana avec l'année 2025
        tianaCas = new TianaCas(2025, 2025, new Personne("Tiana"));
        tianaCas.init();  // Initialisation des flux d'argent
    }

    @Test
    void testPatrimoineTianaFin2025() {
        // Date de fin du test : 31 décembre 2025
        LocalDate dateFuture = LocalDate.of(2025, Month.DECEMBER, 31);

        // Obtenons la projection du patrimoine à la date future
        Patrimoine patrimoineFinal = tianaCas.patrimoine().projectionFuture(dateFuture);

        // Affichage de la valeur comptable du patrimoine
        System.out.println("Valeur du patrimoine de Tiana le 31 décembre 2025 : " +
                patrimoineFinal.getValeurComptable());

        // Calcul du patrimoine attendu
        // 1. Compte bancaire final
        double compteBancaire = tianaCas.getCompteBancaire().projectionFuture(dateFuture).valeurComptable().getMontant();

        // 2. Valeur du terrain avec appréciation de 10%
        double valeurTerrain = 100_000_000 * (1 + 0.10); // +10% pour l'appréciation

        // 3. Dette bancaire : montant négatif
        double dette = tianaCas.getDetteBancaire().projectionFuture(dateFuture).valeurComptable().getMontant();

        // 4. Calcul du patrimoine attendu
        double patrimoineAttendu = compteBancaire + valeurTerrain + dette;

        // Assert que la valeur du patrimoine final est proche de la valeur attendue
        assertEquals(patrimoineAttendu, patrimoineFinal.getValeurComptable().getMontant(), 0.1);
    }
}
