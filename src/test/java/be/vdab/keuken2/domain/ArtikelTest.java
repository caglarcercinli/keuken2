package be.vdab.keuken2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ArtikelTest {
    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikel = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, 1);    }

    @Test
    void verhoogVerkoopPrijs() {
        artikel.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("11");
    }

    @Test
    void verhoogVerkoopPrijsMetNullMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(null));
    }

    @Test
    void verhoogVerkoopPrijsMetMet0Mislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.ZERO));
    }

    @Test
    void verhoogVerkoopPrijsMetMetNegatieveWaardeMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
}
