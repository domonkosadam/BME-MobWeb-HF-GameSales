# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### 2022.10.17.
### Game Sales
### Domonkos Ádám - (CWGYWC)
### [domonkosadam01@gmail.com] 
### Laborvezető: Kapus Ádám

## Bemutatás

A CheapShark API-jával szeretném elkészíteni az alkalmazást. Azoknak a fiataloknak lesz hasznos, akik akciós játékokat szeretnének vásárolni.

## Főbb funkciók

Az alkalmazás megjeleníti egy listában az aktuális akciókat. Ezt a listát különböző paraméterek alapján lehet szűrni.
Egy elemre kattintva meg lehet nézni a a játék részleteit. Innen meg lehet nyitni az adott akció alkalmazásboltját, ahol akár meg is lehet vásárolni.


[CheapShark API](https://apidocs.cheapshark.com/)
Az API eléréshez a [Retrofit](https://square.github.io/retrofit/) libraryt fogom használni.

## Választott technológiák:

- UI: Fragmentek, NavigationComponenttel
- RecyclerView: Játékok listájának megjelenítéséhez
- Hálózat kezelés: Az alkalmazás a [CheapShark API](https://apidocs.cheapshark.com/)-t használja, és REST végponton keresztül tölti le az adatokat.
- Adatbáziskezelés: Internet nélkül a legutóbbi elemek jelennek meg.
- Intent: Implicit intent az alkalmazásboltok megnyitásához.


# Házi feladat dokumentáció (ha nincs, ez a fejezet törölhető)
