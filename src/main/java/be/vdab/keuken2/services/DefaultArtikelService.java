package be.vdab.keuken2.services;

import be.vdab.keuken2.exceptions.ArtikelNietGevondenException;
import be.vdab.keuken2.repositories.ArtikelRepository;

import java.math.BigDecimal;

public class DefaultArtikelService implements ArtikelService{
    private final ArtikelRepository artikelRepository;

    public DefaultArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Override
    public void verhoogVerkoopPrijs(long id, BigDecimal waarde) {
        artikelRepository.findById(id)
                .orElseThrow(ArtikelNietGevondenException::new)
                .verhoogVerkoopPrijs(waarde);
    }
}
