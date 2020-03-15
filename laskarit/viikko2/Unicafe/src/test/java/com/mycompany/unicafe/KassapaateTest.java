package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassapaate;
    
    @Before
    public void setUp() {
        kassapaate=new Kassapaate();
    }  

    @Test
    public void alussaOikeaRahamaara() {
        assertEquals(100000,kassapaate.kassassaRahaa());
    }

    @Test
    public void alussaOikeaEdullisetlounaat() {
        assertEquals(0,kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void alussaOikeaMaukkaatlounaat() {
        assertEquals(0,kassapaate.maukkaitaLounaitaMyyty());
    }

// Edullisten lounaiden testit
     
    @Test
    public void kateisostoEdullinenPalautus() {
        int vastaus=kassapaate.syoEdullisesti(1000);
        assertEquals(760,vastaus);
    }
     
    @Test
    public void kateisostoEdullinenRahamaaraKasvaa() {
        int vastaus=kassapaate.syoEdullisesti(1000);
        assertEquals(100240,kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoEdullinenMaaraKasvaa() {
        int vastaus=kassapaate.syoEdullisesti(1000);
        assertEquals(1,kassapaate.edullisiaLounaitaMyyty());
    }

   @Test
    public void kateisostoEdullinenPalautusRiittamaton() {
        int vastaus=kassapaate.syoEdullisesti(100);
        assertEquals(100,vastaus);
    }

    @Test
    public void kateisostoEdullinenRahamaaraKasvaaRiittamaton() {
        int vastaus=kassapaate.syoEdullisesti(100);
        assertEquals(100000,kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoEdullinenMaaraKasvaaRiittamaton() {
        int vastaus=kassapaate.syoEdullisesti(100);
        assertEquals(0,kassapaate.edullisiaLounaitaMyyty());
    }

// Maukkaiden lounaiden testit

    @Test
    public void kateisostoMaukasPalautus() {
        int vastaus=kassapaate.syoMaukkaasti(1000);
        assertEquals(600,vastaus);
    }

    @Test
    public void kateisostoMaukasRahamaaraKasvaa() {
        int vastaus=kassapaate.syoMaukkaasti(1000);
        assertEquals(100400,kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoMaukasMaaraKasvaa() {
        int vastaus=kassapaate.syoMaukkaasti(1000);
        assertEquals(1,kassapaate.maukkaitaLounaitaMyyty());
    }

   @Test
    public void kateisostoMaukasPalautusRiittamaton() {
        int vastaus=kassapaate.syoMaukkaasti(100);
        assertEquals(100,vastaus);
    }

    @Test
    public void kateisostoMaukasRahamaaraKasvaaRiittamaton() {
        int vastaus=kassapaate.syoMaukkaasti(100);
        assertEquals(100000,kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoMaukasMaaraKasvaaRiittamaton() {
        int vastaus=kassapaate.syoMaukkaasti(100);
        assertEquals(0,kassapaate.maukkaitaLounaitaMyyty());
    }
     
// Edullisten lounaiden testit Maksukortilla
     
    @Test
    public void korttiostoEdullinenVeloitetaanKortilta() {
        Maksukortti kortti=new Maksukortti(1000);     
        boolean vastaus=kassapaate.syoEdullisesti(kortti);
        assertEquals(760,kortti.saldo());
    }

    @Test
    public void korttiostoEdullinenPalautetaanTrue() {
        Maksukortti kortti=new Maksukortti(1000);     
        boolean vastaus=kassapaate.syoEdullisesti(kortti);
        assertEquals(true,vastaus);
    }
    
    @Test
    public void korttiostoEdullinenMaaraKasvaa() {
        Maksukortti kortti=new Maksukortti(1000);     
        boolean vastaus=kassapaate.syoEdullisesti(kortti);
        assertEquals(1,kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void korttiostoEdullinenVeloitetaanKortiltaRiittamaton() {
        Maksukortti kortti=new Maksukortti(100);     
        boolean vastaus=kassapaate.syoEdullisesti(kortti);
        assertEquals(100,kortti.saldo());
    }

    @Test
    public void korttiostoEdullinenPalautetaanFalseRiittamaton() {
        Maksukortti kortti=new Maksukortti(100);     
        boolean vastaus=kassapaate.syoEdullisesti(kortti);
        assertEquals(false,vastaus);
    }
    
    @Test
    public void korttiostoEdullinenMaaraKasvaaRiittamaton() {
        Maksukortti kortti=new Maksukortti(100);     
        boolean vastaus=kassapaate.syoEdullisesti(kortti);
        assertEquals(0,kassapaate.edullisiaLounaitaMyyty());
    }
    
    // Maukkaiden lounaiden testit Maksukortilla
     
    @Test
    public void korttiostoMaukasVeloitetaanKortilta() {
        Maksukortti kortti=new Maksukortti(1000);     
        boolean vastaus=kassapaate.syoMaukkaasti(kortti);
        assertEquals(600,kortti.saldo());
    }

    @Test
    public void korttiostoMaukasPalautetaanTrue() {
        Maksukortti kortti=new Maksukortti(1000);     
        boolean vastaus=kassapaate.syoMaukkaasti(kortti);
        assertEquals(true,vastaus);
    }
    
    @Test
    public void korttiostoMaukasMaaraKasvaa() {
        Maksukortti kortti=new Maksukortti(1000);     
        boolean vastaus=kassapaate.syoMaukkaasti(kortti);
        assertEquals(1,kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoMaukasVeloitetaanKortiltaRiittamaton() {
        Maksukortti kortti=new Maksukortti(100);     
        boolean vastaus=kassapaate.syoMaukkaasti(kortti);
        assertEquals(100,kortti.saldo());
    }

    @Test
    public void korttiostoMaukasPalautetaanFalseRiittamaton() {
        Maksukortti kortti=new Maksukortti(100);     
        boolean vastaus=kassapaate.syoMaukkaasti(kortti);
        assertEquals(false,vastaus);
    }
    
    @Test
    public void korttiostoMaukasMaaraKasvaaRiittamaton() {
        Maksukortti kortti=new Maksukortti(100);     
        boolean vastaus=kassapaate.syoMaukkaasti(kortti);
        assertEquals(0,kassapaate.maukkaitaLounaitaMyyty());
    }
    
    // kortille lataus

    @Test
    public void kortilleLadattaessaSaldo() {
        Maksukortti kortti=new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti,1000);
        assertEquals(1000,kortti.saldo());
    }

    @Test
    public void kortilleLadattaessaRahaa() {
        Maksukortti kortti=new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti,1000);
        assertEquals(101000,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kortilleLadattaessaRahaaNegatiivinen() {
        Maksukortti kortti=new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti,-1000);
        assertEquals(100000,kassapaate.kassassaRahaa());
    }
}
