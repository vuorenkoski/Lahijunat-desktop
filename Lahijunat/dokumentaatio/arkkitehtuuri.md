# Arkkitehtuurikuvaus

## Rakenne
Ohjelman pakkausrakenne on nelitasoinen. Ylimmällä tasolla on lahijunat.ui joka sisältää yhden luokan: Gui. Luokka sisältää käyttöliittymän ja sovelluslogiikan.

Toisella tasolla on lahijunat.data pakkaus, joka sisältämän luokat luo sovelluksen näyttämät taulukot ja kartat.

Kolmannella tasolla on pakkaus lahijunat.vrapi, jonka sisältämän luokan metodit hakevat datan VR:n avoimesta rajapinnasta. Pakkauksen lahijunat.data luokat hyödyntävät näitä metodeja.

Neljännellä tasolla (lahijunat.domain) on luokat, jotka kuvaavat sovelluksen käyttämiä tietorakenteita (asemalta lähtevä juna, tehty haku, junan pysähdys). Pakkauksen luokka Station sisältää dataa pääkaupunkiseudun juna-asemista. Tämän luokan tietoja hyödyntää useampi luokka. 

<img src="pakkauskaavio.png">

## Käyttöliittymä
Käyttöliittymä koostuu yhdestä Scene oliosta, jonka sisältää puolestaan yhden BorderPane olion. Tämä puolestaan sisältää kaksi oliota: alaosan virheteksti -kentän ja keskellä oleva TabPane välilehtiolion.

Jokaisen välilehden sisältö rakennetaan omassa metodissaan. Tiedon rakentaa näytettävään muoton data-pakkauksen luokat. Junia koskevat tiedot esitetään hyödyntäen TableView ja Canvas luokia.

Käyttäjä kommunikoi sovelluksen kanssa vain kolmella tavalla: (1) valitsemalla näytettävän välilehden, (2) valitsemmalla aseman (Asema -välilehti), ja (3) valitsemalla taulukon rivin (Asema -välilehti ja Aiemmat haut -välilehti).

Käyttöliittymän alaosan teksikenttään tulostetaan mahdolliset datan haussa tai sen käsittelyssä tapahtuneet virheet. Näitä syntyy silloin kun verkkoyhteydessä on ongelmia tai VR:n avoimen rajapinnan tuottamassa datassa on poikkeavuuksia.

## Sovelluslogiikka
Sovelluksen alimman tason datamallit luo domain -pakkauksen luokat. SearchItem tyypinen olio on yksi rivi hakulistassa. DepartingTrain tyyppinen olio on yksi rivi asemalta lähtevien junien listassa. TrainStop tyyppinen olio on puolestaan yksi rivi junan aikataulussa. 

Station luokka poikkeaa muista tämän pakkauksen luokista. Se sisältää vain metodeja ja dataa joita muut luokat hyödyntävät. Luokassa on tiedot pääkaupunkiseudun juna-asemien nimistä, lyhytnimistä, koodeista ja sijainneista. Tämän luokan metodien avulla voi esimerkiksi selvittää mikä on tietyn koodin omaavan juna-aseman todellinen nimi. 

Domain -pakkauksen luokkia hyödyntävät korkeamman tason data -pakkauksen luokat, jotka koostavat käyttäjälle näytettävän datan. Ne muokkaat datan myös näytettävään muotoon, sekä sisältävät raakadatan käsittelyyn ja päivitykseen tarpelliset toiminnallisuudet.

SearchTable -luokka erooa muista data -pakkauksen luokiosta. Sen datalähde ei ole VR:n avoin rajapinta, vaan paikallinen "searches.csv" tiedosto. Tiedosto päivitetään jokaisen haun yhteydessä. 

## Tietojen pysyväistallennus
Aiemmat haut tallenetaan tiedostoon "searches.csv" csv-muodossa. Tiedosto luodaan kun ohjelma käynnistetään mikäli sitä ei jo aiemmin ole. Mikäli se on olemassa, tiedoston tiedot ladataan muistiin. Kun käyttäjä tekee haun, haku päivitetään samantien tiedostoonkin.

Tiedostoon tallennetaan 20 viimeisen haun tiedot. Ensimmäiseen kenttään tallennetaan junan numero, ja toiseen String muodossa tarkempia tietoja hausta.

Esimerkki formaatista:
<pre>
8706,Huopalahti 18:17 P
8468,Kauklahti 14:19 U
8117,Huopalahti 14:12 A
</pre>

## Päätoiminnallisuudet

### Asemalta lähtevien junien haku

Ohjelman käynnistyessä luodaan uusi StationTable luokan olio, joka kiinnitetään näkyviin Asema -välilehteen. Samoin käynnistyksen yhteydessä pudotusvalikkoa varten haetaan Station luokan metodilla lista pääkaupunkiseudun asemista. 

Toiminnallisuuden aluksi käyttäjä valitsee valikosta yhden aseman (alla olevassa esimerkissä "Espoo"). Station luokan metodin avulla haetaan tämän aseman koodi.

Aseman koodi välitetään käynnistyksen yhteydessä luodulle StationTable luokan oliolle. Tämä olio puolestaan hakee kyseistä asemaa koskevan raaakadatan hyödyntymällä FetchData -luokan metodia. Tämän jälkeen olio rakentaa listan asemalta lähtevistä junista.

Jokaista lähtevää junaa kohden luodaan yksi DepartingTrain luokan olio.

<img src="sekvenssikaavio.png">

