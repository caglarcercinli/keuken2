package be.vdab.keuken2.repositories;

import be.vdab.keuken2.domain.Artikel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
    void create(Artikel artikel);
    List<Artikel> findByNaamContains(String woord);
    int verhoogAlleVerkoopPrijzen(BigDecimal percentage);
}
