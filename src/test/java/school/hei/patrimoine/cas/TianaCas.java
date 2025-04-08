package school.hei.patrimoine.cas;

import static java.time.Month.*;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

import java.time.LocalDate;
import java.util.Set;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

public class TianaCas extends Cas {

    private final Compte compteBancaire;
    private final Materiel terrain;
    private final Dette detteBancaire;
    private final Personne personne;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TianaCas(int startYear, int endYear, Personne personne) {
        super(LocalDate.of(startYear, APRIL, 8), LocalDate.of(endYear, DECEMBER, 31), personne);
        this.personne = personne;
        this.startDate = LocalDate.of(startYear, APRIL, 8);
        this.endDate = LocalDate.of(endYear, DECEMBER, 31);

        // Init possessions
        this.compteBancaire = new Compte("Compte bancaire", startDate, ariary(60_000_000));
        this.terrain = new Materiel("Terrain bâti", startDate, startDate, ariary(100_000_000), 10);
        this.detteBancaire = new Dette("Prêt bancaire", LocalDate.of(2025, JULY, 27), ariary(-20_000_000));
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
        // Dépenses mensuelles logement/nourriture
        new FluxArgent("Dépenses famille", compteBancaire, startDate.withDayOfMonth(8), endDate, 12, ariary(4_000_000));

        // Dépenses projet entrepreneur
        new FluxArgent("Projet - dépenses", compteBancaire, LocalDate.of(2025, JUNE, 5), LocalDate.of(2025, DECEMBER, 5), 7, ariary(5_000_000));

        // Revenus projet
        new FluxArgent("Projet - revenu 10%", compteBancaire, LocalDate.of(2025, MAY, 1), ariary(7_000_000));
        new FluxArgent("Projet - revenu 90%", compteBancaire, LocalDate.of(2026, JANUARY, 31), ariary(63_000_000));

        // Prêt bancaire
        new FluxArgent("Emprunt bancaire", compteBancaire, LocalDate.of(2025, JULY, 27), ariary(20_000_000));

        // Remboursements mensuels du prêt
        new FluxArgent("Mensualité prêt", compteBancaire, LocalDate.of(2025, AUGUST, 27), LocalDate.of(2026, JULY, 27), 12, ariary(2_000_000));
    }

    @Override
    public Set<Possession> possessions() {
        return Set.of(compteBancaire, terrain, detteBancaire);
    }

    @Override
    protected void suivi() {
        // Aucun correctif nécessaire
    }

    // Méthodes d'accès si besoin dans les tests
    public Compte getCompteBancaire() {
        return compteBancaire;
    }

    public Materiel getTerrain() {
        return terrain;
    }

    public Dette getDetteBancaire() {
        return detteBancaire;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
