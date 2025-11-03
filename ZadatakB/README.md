# ZADAĆA 1 (ZADATAK B)

## STRUKTURA KODA

U kodu imam **interface `Osoba`** koji deklariše dva metoda: `returnIdentitet` i `returnTitula`.  
Također, imam i nadređenu klasu **`Inzenjer`** koja implementira interface `Osoba`, ali dodaje svoje atribute i metod `isipisiInformacijeInzenjera`.  

Postoje i dvije podređene klase: **`SoftverskiInzenjer`** i **`InzenjerElektrotehnike`**, koje dodaju respektabilno atribute `projekti` i `certifikati` i override-aju metod iz nadređene klase `isipisiInformacijeInzenjera` da bi metod zadovoljio dodane atribute.

---

## FUNKCIJE

- **grupisiPoEkspertizama** – funkcija koja vraća mapu u kojoj su ekspertize spojene sa listama inženjera koje te ekspertize posjeduju. Korištena je Kotlin funkcija **`fold`**.  

- **najiskusnijiInzenjeri** – funkcija koja ispisuje najiskusnijeg inženjera u obje kategorije (**SoftverskiInzenjer** i **InzenjerElektrotehnike**). Korištene su Kotlin funkcije **`reduce`** i **`groupBy`**.  

- **ukupanBrojProjekataICertifikata** – funkcija koja vraća sumu svih projekata i certifikata u listi inženjera. Prema postavci zadatka, trebalo je koristiti funkciju **`aggregate`**, ali pri istraživanju dokumentacije i ispitivanju ChatGPT-a sam saznao da ta funkcija ne postoji u tom obliku, pa sam koristio **`sumOf`**.

---

## POREĐENJE FOLD I REDUCE

Obje funkcije su agregatnog tipa. Razlika je u tome što se kod **`fold`** može postaviti početno stanje agregacije, tako da rezultat nije vezan za tip objekta, dok **`reduce`** ne zahtijeva početnu vrijednost agregacije, pa je povratni tip isti kao i objekat.

---

## NAČIN POKRETANJA

Za razvoj sam koristio **online IDE Kotlin Playground** zbog jednostavnosti zadatka, pa nisam imao potrebu za nekim težim IDE-om.  
Procedura pokretanja je jednostavna: potrebno je kod iz datoteke `Inzenjeri.kt` kopirati u Kotlin Playground i pokrenuti ga.

---

## AI UPOTREBA

AI je korišten u **`main`** funkciji za generisanje podataka za punjenje objekata klasa, kao i za istraživanje definicija određenih ugrađenih Kotlin funkcija i u formatiranju ovog README.md.
