interface Osoba {
    fun returnIdentitet() : String
    fun returnTitula() : String
}

open class Inzenjer (
    var ime : String,
    var prezime :  String,
    var titula : String,
    var brojGodinaIskustva : Int,
    var skupEkspertiza : List<String>
): Osoba {
    override fun returnIdentitet() : String {
        return "$ime $prezime "
    }

    override fun returnTitula() : String {
        return titula
    }

    open fun ispisiInformacijeIzenjera() : String {
        var ispisi = "-----------------------------\n"
        ispisi += "Ime : $ime\nPrezime : $prezime\nTitula : $titula\nBroj godina iskustva : $brojGodinaIskustva\nSkup Ekspertiza :\n"
        for(ekspertiza in skupEkspertiza){
            ispisi += "- $ekspertiza\n"
        }
        return ispisi
    }

    init {
        require(ime.length>0){
            "Ime ne smije biti prazno!!"
        }

        require(prezime.length>0){
            "Prezime ne smije biti prazno!!"
        }

        require(titula.length>0){
            "Titula ne smije biti prazna!!"
        }

        require(brojGodinaIskustva>=0){
            "Broj godina iskustva mora biti >=0, 0 znaci da je inzenjeru trenutno prva godina rada"
        }
        require(skupEkspertiza.size>0){
            "Skup ekspertiza ne smije biti prazan"
        }
    }
}

class SoftverskiInzenjer(
    ime: String,
    prezime: String,
    brojGodinaIskustva: Int,
    skupEkspertiza: List<String>,
    var brojProjekata: Int
) : Inzenjer(
    ime,
    prezime,
    "Softverski inzenjer",
    brojGodinaIskustva,
    skupEkspertiza
) {
    init {
        require(brojProjekata >= 0) { "Broj projekata mora biti >= 0" }
    }

    override fun ispisiInformacijeIzenjera(): String {
        return super.ispisiInformacijeIzenjera() + "Projekti: $brojProjekata"
    }
}

class InzenjerElektrotehnike(
    ime: String,
    prezime: String,
    brojGodinaIskustva: Int,
    skupEkspertiza: List<String>,
    var brojCertifikata: Int
) : Inzenjer(
    ime,
    prezime,
    "Inzenjer elektrotehnike",
    brojGodinaIskustva,
    skupEkspertiza
) {
    init {
        require(brojCertifikata >= 0) { "Broj certifikata mora biti >= 0" }
    }

    override fun ispisiInformacijeIzenjera(): String {
        return super.ispisiInformacijeIzenjera() + "Certifikati: $brojCertifikata"
    }
}

fun grupisiPoEkspertizama(inzenjeri : List<Inzenjer>) :  Map<String, List<Inzenjer>> {
    var grupisaniInzenjeri = inzenjeri.fold(mutableMapOf<String , MutableList<Inzenjer>>()) { acc , inzenjer ->
        if(inzenjer.brojGodinaIskustva > 5){
            for(ekspertiza in inzenjer.skupEkspertiza){
                var lista = acc.getOrPut(ekspertiza){mutableListOf()}
                lista.add(inzenjer)
            }
        }
        acc
    }
    return grupisaniInzenjeri
}

fun expertiseExperienceIndex(inzenjeri : List<Inzenjer>) :  Map<String, Int> {
    var grupisaniInzenjeri = inzenjeri.fold(mutableMapOf<String , MutableList<Inzenjer>>()) { acc , inzenjer ->
        if(inzenjer.brojGodinaIskustva > 5){
            for(ekspertiza in inzenjer.skupEkspertiza){
                var lista = acc.getOrPut(ekspertiza){mutableListOf()}
                lista.add(inzenjer)
            }
        }
        acc
    }

    var rezultat : MutableMap<String, Int> = mutableMapOf()
    for ((ekspertiza, inzenjeri) in grupisaniInzenjeri){
        var godine = inzenjeri.sumOf{inzenjer ->
            inzenjer.brojGodinaIskustva
        }
        //IZVORNA LINIJA SA PROVJERE ZADACE
        //rezultat.add(pair(ekspertiza,godine))

        //ISPRAVKA KOD KUCE
        rezultat[ekspertiza]=godine
    }
    return rezultat

}



fun najiskusnijiInzenjeri(inzenjeri: List<Inzenjer>) {
    val grupisano = inzenjeri.groupBy { inzenjer ->
        when (inzenjer) {
            is SoftverskiInzenjer -> "Softverski Inzenjer"
            is InzenjerElektrotehnike -> "Inzenjer Elektrotehnike"
            else -> "Ostali"
        }
    }

    for ((tip, listaInzenjera) in grupisano) {
        if (listaInzenjera.isNotEmpty()) {
            val najiskusniji = listaInzenjera.reduce { acc, inzenjer ->
                if (inzenjer.brojGodinaIskustva > acc.brojGodinaIskustva) inzenjer else acc
            }
            println("Najiskusniji $tip: ${najiskusniji.returnIdentitet()}, broj godina iskustva ${najiskusniji.brojGodinaIskustva}")
        }
    }
}

fun ukupanBrojProjekataICertifikata(inzenjeri: List<Inzenjer>): Int {
    return inzenjeri.sumOf{inzenjer ->
        when (inzenjer) {
            is SoftverskiInzenjer -> inzenjer.brojProjekata
            is InzenjerElektrotehnike -> inzenjer.brojCertifikata
            else -> 0
        }
    }
}


fun main() {
    val soft1 = SoftverskiInzenjer("Amina", "Hadzic", 6, listOf("Kotlin", "Java"), 12)
    val soft2 = SoftverskiInzenjer("Marko", "Ilic", 7, listOf("Java", "C++"), 10)
    val soft3 = SoftverskiInzenjer("Sara", "Kovac", 8, listOf("Kotlin", "SQL"), 15)
    val elektro1 = InzenjerElektrotehnike("Ivan", "Petrovic", 9, listOf("C++", "Elektronika"), 5)
    val elektro2 = InzenjerElektrotehnike("Maja", "Jovic", 4, listOf("Elektronika", "Kotlin"), 3)

    val sviInzenjeri = listOf<Inzenjer>(soft1, soft2, soft3, elektro1, elektro2)

    println("Ispis svih inženjera")
    for (i in sviInzenjeri) {
        println(i.ispisiInformacijeIzenjera())
    }
    println("\n============================\n")

    println("Grupisanje po ekspertizama (samo inzenjeri sa vise od 5 godina iskustva)")
    val grupisano = grupisiPoEkspertizama(sviInzenjeri)
    for ((ekspertiza, lista) in grupisano) {
        println("Ekspertiza: $ekspertiza")
        for (i in lista) {
            println(" - ${i.returnIdentitet()} (${i.brojGodinaIskustva} godina iskustva, ${i.returnTitula()})")
        }
        println()
    }
    println("\n============================\n")

    println("Najiskusniji inženjeri po tipu\n")
    najiskusnijiInzenjeri(sviInzenjeri)
    println("\n============================\n")

    val ukupno = ukupanBrojProjekataICertifikata(sviInzenjeri)
    println("Ukupan broj projekata i certifikata: $ukupno")

    println("Expertise Experience Index funkcija")
    val zbirSatiPoEkspertizi = expertiseExperienceIndex(sviInzenjeri)
    for ((ekspertiza, godine) in zbirSatiPoEkspertizi) {
        println("$ekspertiza -> $godine")
    }

}
