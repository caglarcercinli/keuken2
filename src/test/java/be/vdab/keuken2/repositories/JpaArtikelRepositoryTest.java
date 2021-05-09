package be.vdab.keuken2.repositories;

import be.vdab.keuken2.domain.Artikel;
import be.vdab.keuken2.domain.FoodArtikel;
import be.vdab.keuken2.domain.Korting;
import be.vdab.keuken2.domain.NonFoodArtikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikel.sql")
public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private static final String ARTIKELS = "artikels";
    private Artikel artikel;
    private final EntityManager manager;

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    /*
        private long idVanTestArtikel() {
            return jdbcTemplate.queryForObject(
                    "select id from artikels where naam='test'", Long.class);
        }

     */
    private long idVanTestFoodArtikel() {
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testfood'", Long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testnonfood'", Long.class);
    }
/*
    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test2", BigDecimal.ONE, BigDecimal.TEN);
    }

 */

    @Test
    void findFoodArtikelById() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .containsInstanceOf(FoodArtikel.class)
                .hasValueSatisfying(
                        artikel -> assertThat(artikel.getNaam()).isEqualTo("testfood"));
    }

    @Test
    void findNonFoodArtikelById() {
        assertThat(repository.findById(idVanTestNonFoodArtikel()))
                .containsInstanceOf(NonFoodArtikel.class)
                .hasValueSatisfying(
                        artikel -> assertThat(artikel.getNaam()).isEqualTo("testnonfood"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    /*
        @Test
        void create() {
            repository.create(artikel);
            assertThat(artikel.getId()).isPositive();
            assertThat(countRowsInTableWhere(ARTIKELS, "id = " + artikel.getId())).isOne();
        }
     */
    @Test
    void createFoodArtikel() {
        var artikel = new FoodArtikel("testfood2", BigDecimal.ONE, BigDecimal.TEN, 7);
        repository.create(artikel);
        assertThat(countRowsInTableWhere(ARTIKELS, "id =" + artikel.getId())).isOne();
    }

    @Test
    void createNonFoodArtikel() {
        var artikel = new NonFoodArtikel("testnonfood2", BigDecimal.ONE, BigDecimal.TEN, 30);
        repository.create(artikel);
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void findBijNaamContains() {
        assertThat(repository.findByNaamContains("es"))
                .hasSize(countRowsInTableWhere(ARTIKELS,
                        "naam like '%es%'"))
                .extracting(Artikel::getNaam)
                .allSatisfy(naam -> assertThat(naam).containsIgnoringCase("es"))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void verhoogAlleVerkoopPrijzen() {
        assertThat(repository.verhoogAlleVerkoopPrijzen(BigDecimal.TEN))
                .isEqualTo(countRowsInTable("artikels"));
        assertThat(countRowsInTableWhere(ARTIKELS,
                "verkoopprijs = 132 and id = " + idVanTestFoodArtikel())).isOne();
    }

    @Test
    void kortingenLezen() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .hasValueSatisfying(artikel -> assertThat(artikel.getKortingen())
                        .containsOnly(new Korting(1, BigDecimal.TEN)));
    }
}