# SzoKereso

Szókereső - keressük azokat a szavakat melyek egymástól független kiolvashatók a mátrixból. Adott betük, ha szerepelnek már egy megtalált szóban akkor nem használhatók fel következő esetben.

A program elindítása a következő képpen zajlik:

A terminál abban a könyvtárban kell benne legyen ahol a fájlok mappája szerepel. Az az:

C:> java "Adott mappa amiben a fajlok vannak".Main "Adott metodus számerteke amire lefut a program pl.(1, 2, 3)"

Pl.

C:> java Boggle.Main 1 __
C:> java Boggle.Main 2 __

Dictionary : "GEEKS", "FOR", "QUIZ", "GEE"

MATRIX

[G I Z] __
[U E K] __
[Q S E]

VÁLTOZOK: A szavak. Esetünkben ( Pl. V1 = GEEKS, V2=FOR, V3=QUIZ, ...) DOMÉNIUM: A mátrix betűi CONSTRAINTS: Ha a V1 első karakterét megkaptuk a mátrixban, akkor a V4 változónak nem marad G betű a mátrixban, tehát megváltozik a V4 doméniuma. Így már csak a két darab E betü marad a doméniumában.

Mivel már nem szerepel a V4 változó doméniumában G betű ezért a Forward checking kiveszi a változók közül, ezáltal dramatikusan lecsökkentve a futási idejét. Hasonlóan a AC3 is ugyan ezt végzi csak több értékadással..

Az MVR pedig előlegesen megszűri a szótárunkat az adott mátrix szerint, úgy hogy végigmegy a szavakon és kiszűri azokat a szavakat amelyeknek a betüi nem szerepelnek a mátrixban, csökkentve a fölösleges változók számát, ugyanakkor rendezi a szavakat hosszúság szerint növekvő sorrendbe, hogy az első szó amit a keresünk a backtrack-el azt a lehető legkevesebb mátrixban levő betü lefoglalásával növelje a megoldások számát. Így sűlyozzuk a szótárban levő szavakat.

Vegyük D0-t a összes betű halmazának. akkor: C1: D1 = D0 - (D2 U D3 U D4)

Ck: Dk = D0 - (D1 U D2 U ... U Dn-1 U Dn - Dk) | k<=n, k, n e N

V = { V1, V2,.. Vn } e Szótár

pl. A válasz az adott példára QUIZ, GEE Különböző módszerek segítségével sokkal kevesebb lépés szám után lefut a program BT - 24 lepes BT + MRV + FORWARD CHECK 13 lepes BT + MRV + AC-3 18 lepes
