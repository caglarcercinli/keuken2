package be.vdab.keuken2.services;

import be.vdab.keuken2.domain.Artikel;
import be.vdab.keuken2.domain.FoodArtikel;
import be.vdab.keuken2.exceptions.ArtikelNietGevondenException;
import be.vdab.keuken2.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultArtikelServiceTest {
    private DefaultArtikelService service;
    @Mock
    private ArtikelRepository repository;
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikel = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, 1);
        service = new DefaultArtikelService(repository);
    }

    @Test
    void verhoogVerkoopPrijs() {
        when(repository.findById(1)).thenReturn(Optional.of(artikel));
        service.verhoogVerkoopPrijs(1, BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("11");
        verify(repository).findById(1);
    }

    @Test
    void verhoogVerkooPPrijsVoorOnbestaandArtikel() {
        assertThatExceptionOfType(ArtikelNietGevondenException.class).isThrownBy(() -> service.verhoogVerkoopPrijs(-1, BigDecimal.ONE));
        verify(repository).findById(-1);
    }
}
