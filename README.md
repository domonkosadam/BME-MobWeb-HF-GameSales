# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### [Dátum]
### [Alkalmazás neve]
### [Teljes név] - ([Neptun kód])
### [e-mail cím] 
### Laborvezető: [Laborvezető neve]

## Bemutatás

Az alkalmazás rövid, 2-3 mondatos bemutatása. Honnan az ötlet, mi szülte az igényt, ki lehetne a célközönség.
A laboron és előadáson bemutatott alkalmazásokat nem lehet házi feladatnak választani.

## Főbb funkciók

Az alkalmazás minden funkciójára kiterjedő leírás (röviden, lényegre törően). Legyen egyértelműen eldönthető, hogy az adott funkció implementálva van-e!
Pl.: Az alkalmazással lehetőség van tételek felvételére és tárolására, valamint azok rendezett megjelenítésére, illetve frissítésre X hálózati hívás segítségével.

## Választott technológiák:

- (UI)
- (fragmentek)
- (RecyclerView)
- (Perzisztens adattárolás)


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