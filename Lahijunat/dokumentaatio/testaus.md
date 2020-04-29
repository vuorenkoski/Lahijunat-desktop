# Testausdokumentti

## Yksikkö- ja integraatiotestaus
Suurimmalle osalle luokkia on luotu yksikkötestit. 

Tärkeimmille luokille se ei kuitenkaan onnistunut: StationTable, TrainTable ja Search table. Nämä luokat käsittelevät avoimen rajapinnan kautta saatua dataa. Vaikka näille luokille luotiin mahdollisuus ottaa syötteenä avoimesta rajapinnasta ladattua testidataa, homma kaatui tällaiseen virheilmoitukseen:

```
java.lang.ExceptionInInitializerError
	at lahijunat.data.StationTable.formatDataTable(StationTable.java:132)
	at lahijunat.data.StationTable.<init>(StationTable.java:32)
	at LahijunatTest.ParseDepartingTrains(LahijunatTest.java:200)
        ...
```

Testit kaatuvat tähän käskyyn:

```
this.dataTable = new TableView();
```

Tämän takia testikattavuus jäi vajaaksi. Rivikattavuus 63% ja haarautumakattavuus vain 21%.

## Järjestelmätestaus
Sovellusta on testattu kahdessa ympäristössä: omalla tietokoneella (Kubuntu 19.10) sekä yliopiston cubli ympäristössä. 

Sovelluksen kaikki toiminnallisuudet on testattu manuaalisesti varsin perinpohjaisesti. Sovellukselle ei pysty syöttämään väärää tietoa tai saada sitä kaatumaan. Sovellusta on testattu hakemistossa, johon on ei ole kirjoitiusöoikeutta. Ohjelmaa on myös testattu tilanteissa jossa tiedosto tai se puuttuu.

Sovellusta ei ole ollenkaan testattu tilanteissa jossa digitrafic -rajapinta antaa virheellistä tai normaalista poikkeavaa dataa. Ohjelma antaa käyttäjälleen tällöin varsin kryptisiä virheilmoituksia.

Mikäli sovelluksen käynnistyessä sen työhakemistossa on korruptoitunut seraches.csv tiedosto, sovellus ei toimi odotetulla tavalla.

## Sovellukseen jääneet laatuongelmat
Sovelluksen pitäisi kyetä tarkistamaan että searches.csv ei ole korruptoitunut, ja tarvittaessa tyhjentää tiedosto.

Sovellus ei hae säännöllisesti tietoa pääkaupunkiseudulla olevista asemista, vaikka tämä olisi rajapinnan kautta mahdollista. Se ei siis osaa pävittää mahdollisia uusia asemia tms.

