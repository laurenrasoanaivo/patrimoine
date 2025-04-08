package school.hei.patrimoine.cas;

import static java.time.Month.DECEMBER;
import static java.time.Month.APRIL;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

import java.time.LocalDate;
import java.util.Set;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class BakoCas extends Cas {

    private final Compte compteBNI;
    private final Compte compteBMOI;
    private final Compte coffreFort;
    private final Materiel ordinateur;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Personne personne;

    public BakoCas(int startYear, int endYear, Personne personne) {
        super(LocalDate.of(startYear, APRIL, 8), LocalDate.of(endYear, DECEMBER, 31), personne);
        this.startDate = LocalDate.of(startYear, APRIL, 8);
        this.endDate = LocalDate.of(endYear, DECEMBER, 31);
        this.personne = personne;

        // Initialisation des comptes de Bako
        this.compteBNI = new Compte("Compte BNI", startDate, ariary(2_000_000));
        this.compteBMOI = new Compte("Compte BMOI", startDate, ariary(625_000));
        this.coffreFort = new Compte("Coffre Fort", startDate, ariary(1_750_000));

        // Initialisation de l'ordinateur
        this.ordinateur = new Materiel("Ordinateur portable", startDate, startDate, ariary(3_000_000), -12);
    }

    @Override
    protected String nom() {
        return personne.nom();
    }

    @Override
    protected Devise devise() {
        return MGA;
    }

    @Override
    protected void init() {
        // Flux financiers liés aux opérations de Bako
        new FluxArgent("Virement vers BMOI (épargne)", compteBNI, startDate.plusDays(1), ariary(200_000));
        new FluxArgent("Paiement colocation", compteBNI, startDate.plusDays(18), ariary(600_000));
        new FluxArgent("Dépenses mensuelles", compteBNI, startDate.plusDays(1), ariary(700_000));
    }

    @Override
    public Set<Possession> possessions() {
        // Définition des possessions de Bako : comptes et ordinateur
        var trainDeVie = new FluxArgent("Flux de vie courante", compteBNI, startDate, endDate, 12, ariary(0));
        return Set.of(compteBNI, compteBMOI, coffreFort, ordinateur, trainDeVie);
    }

    @Override
    protected void suivi() {
        // Correction à la hausse de l'épargne sur le compte BMOI
        new FluxArgent("Correction épargne BMOI", compteBMOI, LocalDate.now(), ariary(0));
    }

    // Méthodes getter pour accéder aux variables
    public Compte getCompteBNI() {
        return compteBNI;
    }

    public Compte getCompteBMOI() {
        return compteBMOI;
    }

    public Compte getCoffreFort() {
        return coffreFort;
    }

    public Materiel getOrdinateur() {
        return ordinateur;
    }

    // Méthodes getter pour les dates
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Méthode getter pour la personne
    public Personne getPersonne() {
        return personne;
    }
}
