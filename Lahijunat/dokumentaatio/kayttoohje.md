# Käyttöohje
Ohjelmalla käyttäjä voi selailla VR:n pääkaupunkiseudun lähijunien aikatauluja sekä ennakoituja ja todellisia kulkuaikoja. Sovelluksella voi seurata myös junien liikkeitä kartalla.

Ohjelma vaatii toimiakseen internetyhteyden.

## Aloitus
Lataa tiedosto [lahijunat.jar](https://github.com/vuorenkoski/ot-harjoitustyo/releases/tag/viikko7)

Ohjelma olettaa, että käyttäjällä on kirjoitusoikeus ohjelman käynnistyshakemistoon. Se luo hakemistoon ensimmäisen käynnistyksen yhteydessä searches.csv tiedoston, johon se tallentaa tehtyjä hakuja.

## Ohjelman käynnistäminen
Ohjelma käynnistetään komennolla

```
java -jar lahijunat.jar
```

## Ohjelman käyttö
Ohjelman näkymä koostuu viidestä välilehdestä, joiden välillä käyttäjä voi siirtyä painamalla välilehden nimeä. Välilehdet ovat asema, juna, kartta, aiemmat haut ja ohje.

### Asema -välilehti
Välilehden yläreunassa on pudotusvalikko, jossa on valittavana kaikki pääkaupunkiseudun juna-asemat aakkosjärjestyksessä. Kun juna-asema valitaan välilehdeltä, tulostuu välilehdelle asemalta seuraavan kahden tunnin aikana lähtevät junat. Mikäli junan ennakoidaan myöhästyvän aikataulun mukaisesta lähtöajasta, näkyy ennakoitu aika toisessa sarakkeessa. Mikäli juna on peruttu, näkyy tästä tieto viimeisessä sarakkeessa. Mikäli peruutukselle on kirjattu syy, näkyy se samassa sarakkeessa.

Kun junan tietoja kaksoisklikkaa, tulostuu kyseiset junan tiedot Juna -välilehdelle ja sovelluksen näkymä siirtyy siihen välilehteen. Haun tiedot tallentuvat automaattisesti Aiemmat haut -listaan.

### Juna -välilehti
Välilehdellä näkyy valitun junan aikataulutiedot. Junan seuraava asema on merkitty X-merkillä.

Mikäli junan ennakoidaan myöhästyvän aikataulun mukaisesta lähtöajasta, näkyy ennakoitu aika toisessa sarakkeessa. Mikäli juna on peruttu, aikataulua ei tulostu, vaan sen sijasta näytetään tieto junan peruutuksesta.

Junan sijainti näkyy oikealla olevassa kartassa punaisena pisteenä. Tiedot päivittyvät kahden minuutin välein.

### Aiemmat haut -välilehti
Välilehdellä näkyy 20 viimeisintä hakua. Kun haun tietoja kaksoisklikkaa, tulostuu kyseiset junan tiedot Juna -välilehdelle ja sovelluksen näkymä siirtyy siihen välilehteen.

### Kartta -välilehti
Kaikki lähijunat näkyvät kartalla punaisina pisteinä. Tiedot päivittyvät 10 sekunnin välein. Mustat pisteet ovat juna-asemia.
