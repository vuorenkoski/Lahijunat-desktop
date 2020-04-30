# Lähijunat -sovellus

Sovelluksella saa reaaliaikaista tietoa VR:n lähijunien liikkeistä ja aikatauluista. Käyttöliittymä koostuu viidestä eri välilehdestä: tietyn aseman lähtevät junat, tietyn junan liikumisen seuraaminen, viimeisimmät haut, kaikkien junien paikannus kartalla, käyttöohje.

## Dokumentaatio

* [Vaatimusmäärittely](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/vaatimusmaarittely.md)
* [Työaikakirjanpito](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/tyoaikakirjanpito.md)
* [Arkkitehtuurikuvaus](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/arkkitehtuuri.md)
* [Käyttöohje](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/kayttoohje.md)
* [Testausdokumentti](https://github.com/vuorenkoski/ot-harjoitustyo/blob/master/Lahijunat/dokumentaatio/testaus.md)

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
java -jar target/Lahijunat-1.0.jar
```


JavaDoc luonti

```
mvn javadoc:javadoc
```