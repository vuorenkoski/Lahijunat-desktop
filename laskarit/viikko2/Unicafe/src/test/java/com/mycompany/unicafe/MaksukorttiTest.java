package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void SaldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(5000);
        assertEquals("saldo: 60.0", kortti.toString());
    }
    
    @Test
    public void ottaminenVahentaaSaldoaOikein() {
        kortti.otaRahaa(250);
        assertEquals("saldo: 7.50", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosRaahaEiTarpeeksi() {
        kortti.otaRahaa(1250);
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void ottaminenPalauttaaTrue() {
        assertEquals(true,kortti.otaRahaa(250));
    }
    
    @Test
    public void ottaminenPalauttaaFalse() {
        assertEquals(false,kortti.otaRahaa(1250));
    }
    
    @Test
    public void palauttaaSaldon() {
        kortti.otaRahaa(250);
        assertEquals(750,kortti.saldo());
    }
}
