# Vaatimusmäärittely

## Sovelluksen käyttötarkoitus
Sovelluksella saa reaaliaikaista tietoa VR:n lähijunien liikkeistä ja aikatauluista pääkaupunkiseudulla.

## Käyttäjät
Sovelluksella on vain yksi käyttäjäryhmä: normaalit käyttäjät, jotka hakevat tietoa lähijunista.

## Käyttöliittymä
Käyttöliittymä koostuu viidestä eri sivusta/lehdestä: tietyn aseman lähtevät junat, tietyn junan liikumisen seuraaminen, viimeisimmät haut, kaikkien junien paikannus kartalla, käyttöohje.

## Suunnitellut toiminnallisuudet
Asematiedot: Käyttäjä valitsee aseman, ja sovellus näyttää seuraavan parin tunnin junien lähdöt valitulta asemalta: aikataulun mukainen lähtöaika, ennakoitu lähtöaika, raide, junan tunnus. Mikäli juna on peruutettu, sovellus näyttää syykoodin.
Kun käyttäjä valitsee jonkin junista, tämä toimii automaattisena valintana "junan tiedot" välilehdelle.

Junan tiedot: Käyttäjä valitsee junan asema -välilehdeltä, jonka jälkeen sovellus näyttää junan reitin asemat, millä kohtaa juna on tällä hetkellä menossa sekä missä kohtaa kartalla juna on menossa.

Käyttäjä voi omalla välilehdellä tarkastella kaikkien pääkaupunkiseudun alueella liikkuvien junien liikkeitä.

Ohjelma tallentaa käyttäjän viimeiset 20 hakua. Nämä on näkyvillä omalla lehdellä, josta haun voi uusia klikkaamalla sitä.

## Toiminnallisuuksien toteutus
- Viikko 3: Asematietojen haku tekstikäyttöliittymällä
- Viikko 4: Lisätty graafinen käyttöliittymä, junan aikautaulutiedot -välilehti ja ohje -välilehti. 
- Viikko 5: Lisätty kartta -välilehti, sekä karttanäkymä junan aikautalut -välilehdelle
- Viikko 6: Aiemmat haut -välilehti
- Viikko 7: Viimeistelyä ja testikattavuuden parantaminen

## Jatkokehtiysideoita
- laajentaminen pääkaupunkiseudun ulkopuolelle
- miten voitaisiin hyödyntää kulkutietoviestit-dataa?
