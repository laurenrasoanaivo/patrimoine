package school.hei.patrimoine.cas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static school.hei.patrimoine.modele.Argent.ariary;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;

class BakoCasTest {

    private BakoCas bakoCas;

    @BeforeEach
    void setUp() {
        // Création de Bako avec les données d'entrée
        Personne bako = new Personne("Bako");
        this.bakoCas = new BakoCas(2025, 2025, bako);
        bakoCas.init();  // Initialisation des flux financiers
    }

    @Test
    void testPatrimoineBakoFin2025() {
        // Date future : 31 décembre 2025
        LocalDate dateFuture = LocalDate.of(2025, Month.DECEMBER, 31);

        // Projection du patrimoine de Bako à la fin de l'année
        Patrimoine patrimoineFinal = bakoCas.patrimoine().projectionFuture(dateFuture);

        // Affichage de la valeur du patrimoine final pour inspection
        System.out.println("Valeur du patrimoine de Bako le 31 décembre 2025 : " + patrimoineFinal.getValeurComptable());

        // Calcul du patrimoine attendu à partir des possessions projetées
        double totalPatrimoine = bakoCas.getCompteBNI().projectionFuture(dateFuture).valeurComptable().getMontant()
                + bakoCas.getCompteBMOI().projectionFuture(dateFuture).valeurComptable().getMontant()
                + bakoCas.getCoffreFort().projectionFuture(dateFuture).valeurComptable().getMontant()
                + bakoCas.getOrdinateur().projectionFuture(dateFuture).valeurComptable().getMontant();

        // Vérification que le patrimoine final calculé est correct
        assertEquals(totalPatrimoine, patrimoineFinal.getValeurComptable().getMontant(), 0.1);
    }
}
