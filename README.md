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

# Házi feladat dokumentáció

**Architektúra**

A házi feladat az *Android Jetpack*-ben bevezetett *ViewModel architektúra* szerint készült. Ennek előnye, hogy az adatok és a megjelenítés elkülönül. Így sokkal átláthatóbb kódot kapunk, illetve az adatok megmaradnak a képernyő megsemmisülése esetén is.

Az *API* hívásokat *Repository* mintával készítettem el a *Retrofit* nevű library-val. Így az adatokat kezelő függvények elkülönülnek a megjelenítést végző objektumoktól. A State objektumokkal felhasználóbarát módon le tudtam kezelni a hibákat és a töltőképernyőt.

**Felhasználói felület**

Az alkalmazás támogatja az *éjszakai módot* is.

A *CheapShark API*-tól lekért akciós játékokat egy *listában* jelenítem meg. Mivel a képek internetes áruházakból vannak, így a *Glide* libraryval jelenítem meg a kapott URL-ket.

A *Steam API*-tól lekért PC követelményeket egy *lenyitható nézetben* jelenítem meg, hogy a néha túl hosszú leírások ne zavarják a felhasználót.

Ha megtetszik egy játék a gomra kattintva egy *implicit intent*-tel megnyitható a böngészőben az áruház, ahol meg lehet vásárolni a játékot.

**Perzisztens adattárolás**

Ha valami hiba történt az API hívás során, vagy nincs internet kapcsolat, a legutóbb letöltött adatokat jeleníti meg az alkalmazás. Ezeket az adatokat a *Room* library-val kezelem.
