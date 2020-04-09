# Lähijunat -sovellus

Sovelluksella saa reaaliaikaista tietoa VR:n lähijunien liikkeistä ja aikatauluista. Käyttöliittymä koostuu neljästä eri sivusta/lehdestä: tietyn aseman lähtevät junat, tietyn junan liikumisen seuraaminen, viimeisimmät haut, kaikkien junien paikannus kartalla.

## Dokumentaatio

* [Vaatimusmäärittely](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/vaatimusmaarittely.md)
* [Työaikakirjanpito](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/tyoaikakirjanpito.md)
* [Arkkitehtuurikuvaus](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Checkstyle raportti

```
mvn jxr:jxr checkstyle:checkstyle
```

Ohjelman suoritus

```
mvn compile exec:java -Dexec.mainClass=lahijunat.Main
```

Suorituskelpoinen jar-tiedosto

```
mvn package
```
```
java -jar target/Lahijunat-1.0-SNAPSHOT.jar
```
