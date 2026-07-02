package com.snaplist.data.ai

/**
 * Vinted's catalog taxonomy: one canonical tree (IDs shared by every Vinted
 * market) plus per-language display names.
 *
 * GENERATED from the live Vinted sites on 2026-07-02 — do not edit by
 * hand; regenerate with tools/vinted-taxonomy (extract_tree.rb + gen_catalog_kt.rb).
 * Sources: one domain per language (hr, cz, dk, nl, fi, fr, de, gr, hu, it, lt, pl, pt, ro, sk, es, se), canonical
 * English from vinted.co.uk, US English overrides from vinted.com. Structure
 * verified identical across all 23 reachable market domains except the
 * bulky-item categories noted on the nodes below (vinted.ca is bot-protected;
 * Canada assumes US naming, unverified).
 */
data class CatalogNode(
    val id: Int,
    val name: String,
    /** Markets carrying this node; null = available everywhere. */
    val onlyIn: Set<String>? = null,
    val children: List<CatalogNode> = emptyList(),
)

object VintedCatalog {

    /** Top two catalog levels, canonical (UK) English names. */
    val tree: List<CatalogNode> = listOf(
        CatalogNode(
            id = 1904, name = "Women",
            children = listOf(
                CatalogNode(id = 4, name = "Clothing"),
                CatalogNode(id = 16, name = "Shoes"),
                CatalogNode(id = 19, name = "Bags"),
                CatalogNode(id = 1187, name = "Accessories"),
                CatalogNode(id = 146, name = "Beauty"),
            ),
        ),
        CatalogNode(
            id = 5, name = "Men",
            children = listOf(
                CatalogNode(id = 2050, name = "Clothing"),
                CatalogNode(id = 1231, name = "Shoes"),
                CatalogNode(id = 82, name = "Accessories"),
                CatalogNode(id = 139, name = "Grooming"),
            ),
        ),
        CatalogNode(
            id = 2993, name = "Designer",
            children = listOf(
                CatalogNode(id = 2983, name = "Designer women"),
                CatalogNode(id = 2988, name = "Designer men"),
            ),
        ),
        CatalogNode(
            id = 1193, name = "Kids",
            children = listOf(
                CatalogNode(id = 1195, name = "Girls clothing"),
                CatalogNode(id = 1194, name = "Boys clothing"),
                CatalogNode(id = 1499, name = "Toys"),
                CatalogNode(id = 1496, name = "Pushchairs, carriers & car seats"),
                CatalogNode(id = 1498, name = "Furniture & decor"),
                CatalogNode(id = 3393, name = "Bathing & changing"),
                CatalogNode(id = 3427, name = "Childproofing & safety equipment"),
                CatalogNode(id = 3419, name = "Health & pregnancy"),
                CatalogNode(id = 3432, name = "Nursing & feeding"),
                CatalogNode(id = 3296, name = "Sleep & bedding"),
                CatalogNode(id = 1501, name = "School supplies"),
                CatalogNode(id = 1502, name = "Other kids' items"),
            ),
        ),
        CatalogNode(
            id = 1918, name = "Home",
            children = listOf(
                CatalogNode(id = 3474, name = "Small kitchen appliances"),
                CatalogNode(id = 3475, name = "Large appliances", onlyIn = setOf("Belgium", "Lithuania", "Netherlands", "United Kingdom")),
                CatalogNode(id = 3476, name = "Cookware & bakeware"),
                CatalogNode(id = 3477, name = "Kitchen tools"),
                CatalogNode(id = 1920, name = "Tableware"),
                CatalogNode(id = 3478, name = "Household care"),
                CatalogNode(id = 1919, name = "Textiles"),
                CatalogNode(id = 1934, name = "Home accessories"),
                CatalogNode(id = 5428, name = "Office supplies"),
                CatalogNode(id = 2915, name = "Celebrations & holidays"),
                CatalogNode(id = 3811, name = "Tools & DIY"),
                CatalogNode(id = 3812, name = "Outdoor & garden"),
                CatalogNode(id = 3154, name = "Furniture", onlyIn = setOf("Belgium", "Lithuania", "Netherlands", "United Kingdom")),
                CatalogNode(id = 5106, name = "Pet care"),
            ),
        ),
        CatalogNode(
            id = 2994, name = "Electronics",
            children = listOf(
                CatalogNode(id = 3002, name = "Video games & consoles"),
                CatalogNode(id = 3564, name = "Computers & accessories"),
                CatalogNode(id = 3565, name = "Mobile phones & communication"),
                CatalogNode(id = 3566, name = "Audio, headphones & hi-fi"),
                CatalogNode(id = 3054, name = "Cameras & accessories"),
                CatalogNode(id = 3567, name = "Tablets, e-readers & accessories"),
                CatalogNode(id = 3568, name = "TV & home cinema"),
                CatalogNode(id = 3569, name = "Beauty & personal care electronics"),
                CatalogNode(id = 3004, name = "Wearables"),
                CatalogNode(id = 2995, name = "Other devices & accessories"),
            ),
        ),
        CatalogNode(
            id = 2309, name = "Entertainment",
            children = listOf(
                CatalogNode(id = 2312, name = "Books"),
                CatalogNode(id = 5424, name = "Magazines"),
                CatalogNode(id = 3036, name = "Music"),
                CatalogNode(id = 3037, name = "Video"),
            ),
        ),
        CatalogNode(
            id = 4824, name = "Hobbies & collectables",
            children = listOf(
                CatalogNode(id = 4874, name = "Trading cards"),
                CatalogNode(id = 4881, name = "Board games"),
                CatalogNode(id = 4882, name = "Puzzles"),
                CatalogNode(id = 4883, name = "Tabletop & miniature gaming"),
                CatalogNode(id = 4901, name = "Memorabilia"),
                CatalogNode(id = 4895, name = "Coins & banknotes"),
                CatalogNode(id = 4888, name = "Stamps"),
                CatalogNode(id = 4894, name = "Postcards"),
                CatalogNode(id = 4825, name = "Musical instruments & gear"),
                CatalogNode(id = 5151, name = "Arts & crafts"),
                CatalogNode(id = 4906, name = "Collectables storage"),
                CatalogNode(id = 4916, name = "Gaming accessories"),
            ),
        ),
        CatalogNode(
            id = 4332, name = "Sports",
            children = listOf(
                CatalogNode(id = 4333, name = "Cycling"),
                CatalogNode(id = 4334, name = "Fitness, running & yoga"),
                CatalogNode(id = 4335, name = "Outdoor sports"),
                CatalogNode(id = 4336, name = "Water sports"),
                CatalogNode(id = 4337, name = "Team sports"),
                CatalogNode(id = 4338, name = "Racket sports"),
                CatalogNode(id = 4339, name = "Golf"),
                CatalogNode(id = 4340, name = "Equestrian"),
                CatalogNode(id = 4341, name = "Skateboards & scooters"),
                CatalogNode(id = 4342, name = "Boxing & martial arts"),
                CatalogNode(id = 4343, name = "Casual sports & games"),
                CatalogNode(id = 4344, name = "Winter sports"),
            ),
        ),
    )

    /**
     * Display names by listing language (English = canonical names above).
     * Key: catalog id. Verified against the live market sites.
     */
    val translations: Map<String, Map<Int, String>> = mapOf(
        "Croatian" to mapOf(
            1904 to "Žene", 4 to "Odjeća", 16 to "Obuća", 19 to "Torbe", 1187 to "Dodaci", 146 to "Ljepota",
            5 to "Muškarci", 2050 to "Odjeća", 1231 to "Obuća", 82 to "Dodaci", 139 to "Osobna njega",
            2993 to "Dizajnerski artikli", 2983 to "Dizajnerski artikli za žene", 2988 to "Dizajnerski artikli za muškarce",
            1193 to "Djeca", 1195 to "Odjeća za djevojčice", 1194 to "Odjeća za dječake", 1499 to "Igračke", 1496 to "Dječja kolica, nosiljke i sjedalice", 1498 to "Namještaj i dekor", 3393 to "Kupanje i presvlačenje", 3427 to "Zaštita za djecu i sigurnosna oprema", 3419 to "Zdravlje i trudnoća", 3432 to "Dojenje i hranjenje", 3296 to "Spavanje i posteljina", 1501 to "Školski pribor", 1502 to "Ostali artikli za djecu",
            1918 to "Dom", 3474 to "Mali kuhinjski uređaji", 3476 to "Posude za kuhanje i pečenje", 3477 to "Kuhinjska pomagala", 1920 to "Posuđe i pribor za stol", 3478 to "Održavanje kućanstva", 1919 to "Tekstili", 1934 to "Ukrasni dodaci za dom", 5428 to "Uredski materijal", 2915 to "Proslave i praznici", 3811 to "Alati i pribor", 3812 to "Vrt i dvorište", 5106 to "Briga o kućnim ljubimcima",
            2994 to "Elektronika", 3002 to "Videoigre i konzole", 3564 to "Računala i dodaci", 3565 to "Mobilni telefoni i komunikacija", 3566 to "Audio, slušalice i hi-fi", 3054 to "Fotoaparati i pribor", 3567 to "Tableti, e-čitači i dodaci", 3568 to "TV i kućno kino", 3569 to "Elektronika za ljepotu i osobnu njegu", 3004 to "Nosivi uređaji", 2995 to "Ostali uređaji i dodaci",
            2309 to "Zabava", 2312 to "Knjige", 5424 to "Časopisi", 3036 to "Glazba", 3037 to "Video",
            4824 to "Hobiji i kolekcionarstvo", 4874 to "Kartice za razmjenu", 4881 to "Društvene igre", 4882 to "Puzzle", 4883 to "Stolne i minijaturne igre", 4901 to "Memorabilije", 4895 to "Kovanice i novčanice", 4888 to "Markice", 4894 to "Razglednice", 4825 to "Glazbeni instrumenti i oprema", 5151 to "Umjetnost i rukotvorine", 4906 to "Pohrana kolekcionarskih predmeta", 4916 to "Dodaci za igre",
            4332 to "Sport", 4333 to "Biciklizam", 4334 to "Fitness, trčanje i joga", 4335 to "Oprema za sportove na otvorenom", 4336 to "Vodeni sportovi", 4337 to "Timski sportovi", 4338 to "Reketni sportovi", 4339 to "Golf", 4340 to "Konjički sport", 4341 to "skateboardi i romobili", 4342 to "Boks i borilački sportovi", 4343 to "Rekreativni sportovi i igre", 4344 to "Zimski sportovi",
        ),
        "Czech" to mapOf(
            1904 to "Ženy", 4 to "Oblečení", 16 to "Obuv", 19 to "Tašky", 1187 to "Doplňky", 146 to "Kosmetika",
            5 to "Muži", 2050 to "Oblečení", 1231 to "Obuv", 82 to "Doplňky", 139 to "Kosmetika",
            2993 to "Návrhářské kousky", 2983 to "Ženy – od návrhářů", 2988 to "Muži – od návrhářů",
            1193 to "Děti", 1195 to "Dívčí oblečení", 1194 to "Chlapecké oblečení", 1499 to "Hračky", 1496 to "Kočárky, nosítka a autosedačky", 1498 to "Nábytek a dekorace", 3393 to "Koupání a přebalování", 3427 to "Dětské pojistky a ochrana dětí", 3419 to "Zdraví a těhotenství", 3432 to "Kojení a krmení", 3296 to "Spánek a lůžkoviny", 1501 to "Školní potřeby", 1502 to "Ostatní věci pro děti",
            1918 to "Bydlení", 3474 to "Malé kuchyňské spotřebiče", 3476 to "Potřeby na vaření a pečení", 3477 to "Kuchyňské nástroje", 1920 to "Stolování", 3478 to "Péče o domácnost", 1919 to "Textil", 1934 to "Bytové doplňky", 5428 to "Kancelářské potřeby", 2915 to "Oslavy a svátky", 3811 to "Nářadí a kutilství", 3812 to "Exteriér a zahrada", 5106 to "Mazlíčci",
            2994 to "Elektronika", 3002 to "Počítačové hry a konzole", 3564 to "Počítače a příslušenství", 3565 to "Mobilní telefony a komunikace", 3566 to "Audio, sluchátka a hi-fi", 3054 to "Fotoaparáty, kamery a příslušenství", 3567 to "Tablety, elektronické čtečky a příslušenství", 3568 to "Televize a domácí kino", 3569 to "Elektronika pro kosmetickou a osobní péči", 3004 to "Nositelná elektronika", 2995 to "Další zařízení a příslušenství",
            2309 to "Zábava", 2312 to "Knihy", 5424 to "Časopisy", 3036 to "Hudba", 3037 to "Video",
            4824 to "Koníčky a sběratelství", 4874 to "Sběratelské kartičky", 4881 to "Deskové hry", 4882 to "Puzzle", 4883 to "Figurkové hry", 4901 to "Memorabilie", 4895 to "Mince a bankovky", 4888 to "Známky", 4894 to "Pohlednice", 4825 to "Hudební nástroje a vybavení", 5151 to "Umění a tvoření", 4906 to "Skladování sběratelských předmětů", 4916 to "Herní příslušenství",
            4332 to "Sport", 4333 to "Cyklistika", 4334 to "Fitness, běh a jóga", 4335 to "Outdoorové sporty", 4336 to "Vodní sporty", 4337 to "Týmové sporty", 4338 to "Raketové sporty", 4339 to "Golf", 4340 to "Jezdectví", 4341 to "Skateboardy a koloběžky", 4342 to "Box a bojová umění", 4343 to "Rekreační sporty a hry", 4344 to "Zimní sporty",
        ),
        "Danish" to mapOf(
            1904 to "Kvinder", 4 to "Tøj", 16 to "Sko", 19 to "Tasker", 1187 to "Tilbehør", 146 to "Skønhed",
            5 to "Mænd", 2050 to "Tøj", 1231 to "Sko", 82 to "Tilbehør", 139 to "Personlig pleje",
            2993 to "Designer", 2983 to "Designerartikler til kvinder", 2988 to "Designerartikler til mænd",
            1193 to "Børn", 1195 to "Tøj til piger", 1194 to "Tøj til drenge", 1499 to "Legetøj", 1496 to "Klapvogne, bæreseler og autostole", 1498 to "Møbler og indretning", 3393 to "Badning og bleskift", 3427 to "Børnesikring og sikkerhedsudstyr", 3419 to "Sundhed og graviditet", 3432 to "Amning og madning", 3296 to "Søvn og sengetøj", 1501 to "Skoleartikler", 1502 to "Andre artikler til børn",
            1918 to "Bolig", 3474 to "Små husholdningsmaskiner", 3476 to "Køkkengrej og bageudstyr", 3477 to "Køkkenredskaber", 1920 to "Bordservice", 3478 to "Husholdningsbrug", 1919 to "Tekstiler", 1934 to "Tilbehør til hjemmet", 5428 to "Kontorartikler", 2915 to "Fester og højtider", 3811 to "Værktøj og gør-det-selv", 3812 to "Have og udendørs", 5106 to "Kæledyr",
            2994 to "Elektronik", 3002 to "Videospil og konsoller", 3564 to "Computere og tilbehør", 3565 to "Mobiltelefoner og kommunikation", 3566 to "Lyd, hovedtelefoner og hi-fi", 3054 to "Kameraer og tilbehør", 3567 to "Tablets, e-læsere og tilbehør", 3568 to "TV og hjemmebiograf", 3569 to "Elektronik til skønhed og personlig pleje", 3004 to "Wearables", 2995 to "Andre enheder og tilbehør",
            2309 to "Underholdning", 2312 to "Bøger", 5424 to "Magasiner", 3036 to "Musik", 3037 to "Video",
            4824 to "Hobby og samlerobjekter", 4874 to "Samlekort", 4881 to "Brætspil", 4882 to "Puslespil", 4883 to "Bræt- og miniaturespil", 4901 to "Samleobjekter", 4895 to "Mønter og pengesedler", 4888 to "Frimærker", 4894 to "Postkort", 4825 to "Musikinstrumenter og udstyr", 5151 to "Krea og håndarbejde", 4906 to "Opbevaring af samleobjekter", 4916 to "Tilbehør til gaming",
            4332 to "Sport", 4333 to "Cykling", 4334 to "Fitness, løb og yoga", 4335 to "Udendørssport", 4336 to "Vandsport", 4337 to "Holdsport", 4338 to "Ketsjersport", 4339 to "Golf", 4340 to "Ridning", 4341 to "Skateboards og løbehjul", 4342 to "Boksning og kampsport", 4343 to "Selskabssport og havespil", 4344 to "Vintersport",
        ),
        "Dutch" to mapOf(
            1904 to "Dames", 4 to "Kleding", 16 to "Schoenen", 19 to "Tassen", 1187 to "Accessoires", 146 to "Beautyproducten",
            5 to "Heren", 2050 to "Kleding", 1231 to "Schoenen", 82 to "Accessoires", 139 to "Verzorging",
            2993 to "Designer", 2983 to "Designerartikelen Dames", 2988 to "Designerartikelen Heren",
            1193 to "Kinderen", 1195 to "Meisjeskleding", 1194 to "Jongenskleding", 1499 to "Speelgoed", 1496 to "Kinderwagens, buggy's & autostoeltjes", 1498 to "Meubilair & decoratie", 3393 to "Badderen & verschonen", 3427 to "Veiligheid in en om het huis", 3419 to "Gezondheid & zwangerschap", 3432 to "Voeden", 3296 to "Slapen & beddengoed", 1501 to "Schoolbenodigdheden", 1502 to "Overige kinderartikelen",
            1918 to "Home", 3474 to "Kleine keukenapparaten", 3475 to "Grote huishoudapparaten", 3476 to "Kook- & bakgerei", 3477 to "Keukenbenodigdheden", 1920 to "Eetkamer", 3478 to "Huishoudapparaten", 1919 to "Textiel", 1934 to "Woonaccessoires", 5428 to "Kantoorbenodigdheden", 2915 to "Vieringen & Feestdagen", 3811 to "Gereedschap & Klussen", 3812 to "Buiten & tuin", 3154 to "Meubilair", 5106 to "Huisdieren",
            2994 to "Elektronica", 3002 to "Videogames & consoles", 3564 to "Computers & accessoires", 3565 to "Mobiele telefoons & communicatie", 3566 to "Audio, hoofdtelefoons & hi-fi", 3054 to "Camera's & accessoires", 3567 to "Tablets, e-readers & accessoires", 3568 to "TV & home cinema", 3569 to "Beauty & persoonlijke verzorging elektronica", 3004 to "Wearables", 2995 to "Overige apparaten & accessoires",
            2309 to "Entertainment", 2312 to "Boeken", 5424 to "Tijdschriften", 3036 to "Muziek", 3037 to "Video",
            4824 to "Hobby's & verzamelen", 4874 to "Ruilkaarten", 4881 to "Bordspellen", 4882 to "Puzzels", 4883 to "Tafel- & miniatuurspellen", 4901 to "Memorabilia", 4895 to "Munten & bankbiljetten", 4888 to "Postzegels", 4894 to "Ansichtkaarten", 4825 to "Muziekinstrumenten & -apparatuur", 5151 to "Knutselen", 4906 to "Opberging voor verzamelobjecten", 4916 to "Gameaccessoires",
            4332 to "Sport", 4333 to "Fietsen", 4334 to "Fitness, hardlopen & yoga", 4335 to "Buitensporten", 4336 to "Watersporten", 4337 to "Teamsporten", 4338 to "Racketsporten", 4339 to "Golf", 4340 to "Paardensport", 4341 to "Skateboards & steps", 4342 to "Boksen & vechtsporten", 4343 to "Recreatieve sporten & spelen", 4344 to "Wintersporten",
        ),
        "Finnish" to mapOf(
            1904 to "Naiset", 4 to "Vaatteet", 16 to "Kengät", 19 to "Laukut", 1187 to "Asusteet", 146 to "Kauneus",
            5 to "Miehet", 2050 to "Vaatteet", 1231 to "Kengät", 82 to "Asusteet", 139 to "Huolittelu",
            2993 to "Merkkituotteet", 2983 to "Naisten merkkituotteet", 2988 to "Miesten merkkivaatteet",
            1193 to "Lapset", 1195 to "Tyttöjen vaatteet", 1194 to "Poikien vaatteet", 1499 to "Lelut", 1496 to "Rattaat, vaunut, kantoreput ja autonistuimet", 1498 to "Huonekalut ja sisustus", 3393 to "Kylvetys ja vaipanvaihto", 3427 to "Turvavarusteet lapsille", 3419 to "Raskaudenaikainen terveys", 3432 to "Imetys ja ruokinta", 3296 to "Uniaika ja vuodevaatteet", 1501 to "Koulutarvikkeet", 1502 to "Muut lastentuotteet",
            1918 to "Koti", 3474 to "Keittiön pienkoneet", 3476 to "Leivonta- ja ruoanlaittovälineet", 3477 to "Keittiötarvikkeet", 1920 to "Astiat", 3478 to "Kodinhoito", 1919 to "Tekstiilit", 1934 to "Kodin sisustustavarat", 5428 to "Toimistotarvikkeet", 2915 to "Juhlat", 3811 to "Työkalut ja tee-se-itse", 3812 to "Piha ja puutarha", 5106 to "Lemmikit",
            2994 to "Elektroniikka", 3002 to "Videopelit ja konsolit", 3564 to "Tietokoneet ja lisätarvikkeet", 3565 to "Matkapuhelimet ja viestintä", 3566 to "Audio, kuulokkeet ja äänentoisto", 3054 to "Kamerat ja kameratarvikkeet", 3567 to "Tabletit, lukulaitteet ja tarvikkeet", 3568 to "TV ja kotiteatteri", 3569 to "Kauneudenhoito- ja hygienialaitteet", 3004 to "Puettava elektroniikka", 2995 to "Muut laitteet ja lisätarvikkeet",
            2309 to "Viihde", 2312 to "Kirjat", 5424 to "Aikakauslehdet", 3036 to "Musiikki", 3037 to "Videot",
            4824 to "Harrastukset ja keräily", 4874 to "Keräilykortit", 4881 to "Lautapelit", 4882 to "Palapelit", 4883 to "Lautapelit ja miniatyyripelit", 4901 to "Muisto- ja keräilyesineet", 4895 to "Kolikot ja setelit", 4888 to "Postimerkit", 4894 to "Postikortit", 4825 to "Musiikkisoittimet ja soitintarvikkeet", 5151 to "Käsityöt ja askartelu", 4906 to "Säilytysratkaisut keräilyesineille", 4916 to "Pelitarvikkeet",
            4332 to "Urheilu", 4333 to "Pyöräily", 4334 to "Fitness, juokseminen ja jooga", 4335 to "Ulkourheilu", 4336 to "Vesiurheilu", 4337 to "Joukkueurheilu", 4338 to "Mailapelit", 4339 to "Golf", 4340 to "Ratsastus", 4341 to "Potku- ja skeittilaudat", 4342 to "Nyrkkeily ja kamppailulajit", 4343 to "Vapaa-ajan urheilu ja pelit", 4344 to "Talviurheilu",
        ),
        "French" to mapOf(
            1904 to "Femmes", 4 to "Vêtements", 16 to "Chaussures", 19 to "Sacs", 1187 to "Accessoires", 146 to "Beauté",
            5 to "Hommes", 2050 to "Vêtements", 1231 to "Chaussures", 82 to "Accessoires", 139 to "Soins",
            2993 to "Articles de créateurs", 2983 to "Articles de créateurs pour femmes", 2988 to "Articles de créateurs pour hommes",
            1193 to "Enfants", 1195 to "Vêtements pour filles", 1194 to "Vêtements pour garçons", 1499 to "Jeux et jouets", 1496 to "Poussettes, porte-bébé et sièges auto", 1498 to "Meubles et décoration", 3393 to "Bain et change", 3427 to "Sécurité bébé et enfant", 3419 to "Santé et grossesse", 3432 to "Allaitement et alimentation", 3296 to "Sommeil et literie", 1501 to "Fournitures scolaires", 1502 to "Autres articles pour bébé et enfant",
            1918 to "Maison", 3474 to "Petits appareils de cuisine", 3475 to "Gros appareils électroménagers", 3476 to "Cuisson et pâtisserie", 3477 to "Outils de cuisine", 1920 to "Arts de la table", 3478 to "Entretien de la maison", 1919 to "Textiles", 1934 to "Décoration", 5428 to "Fournitures de bureau", 2915 to "Célébrations et fêtes", 3811 to "Outils et bricolage", 3812 to "Extérieur et jardin", 3154 to "Mobilier", 5106 to "Animaux",
            2994 to "Électronique", 3002 to "Jeux vidéo et consoles", 3564 to "Ordinateurs et accessoires", 3565 to "Téléphones portables et équipements de communication", 3566 to "Audio, casques et hi-fi", 3054 to "Appareils photo et accessoires", 3567 to "Tablettes, liseuses et accessoires", 3568 to "TV et home cinema", 3569 to "Produits de beauté et de soins personnels", 3004 to "Objets connectés", 2995 to "Autres appareils et accessoires",
            2309 to "Divertissement", 2312 to "Livres", 5424 to "Magazines", 3036 to "Musique", 3037 to "Vidéo",
            4824 to "Loisirs et collections", 4874 to "Cartes à collectionner", 4881 to "Jeux de société", 4882 to "Puzzles", 4883 to "Jeux de plateau et jeux miniatures", 4901 to "Souvenirs", 4895 to "Pièces de monnaie et billets", 4888 to "Timbres", 4894 to "Cartes postales", 4825 to "Instruments de musique et équipement", 5151 to "Loisirs créatifs", 4906 to "Rangements pour objets de collection", 4916 to "Accessoires de jeux",
            4332 to "Sport", 4333 to "Cyclisme", 4334 to "Fitness, course à pied et yoga", 4335 to "Sports de plein air", 4336 to "Sports nautiques", 4337 to "Sports d'équipe", 4338 to "Sports de raquette", 4339 to "Golf", 4340 to "Équitation", 4341 to "Skateboards et trottinettes", 4342 to "Boxe et arts martiaux", 4343 to "Sports et jeux de loisir", 4344 to "Sports d'hiver",
        ),
        "German" to mapOf(
            1904 to "Damen", 4 to "Kleidung", 16 to "Schuhe", 19 to "Taschen", 1187 to "Accessoires", 146 to "Beauty",
            5 to "Herren", 2050 to "Kleidung", 1231 to "Schuhe", 82 to "Accessoires", 139 to "Körper- & Gesichtspflege",
            2993 to "Designerartikel", 2983 to "Designerartikel für Damen", 2988 to "Designerartikel für Herren",
            1193 to "Kinder", 1195 to "Mädchen", 1194 to "Jungs", 1499 to "Spielzeug", 1496 to "Kinderwagen, Tragen & Autositze", 1498 to "Möbel & Deko", 3393 to "Baden & Wickeln", 3427 to "Kindersicherung & Sicherheitsausstattung", 3419 to "Gesundheit & Schwangerschaft", 3432 to "Stillen & Füttern", 3296 to "Schlafen & Bettzeug", 1501 to "Schulbedarf", 1502 to "Sonstige Artikel für Kinder",
            1918 to "Home", 3474 to "Kleine Küchengeräte", 3476 to "Koch- und Backutensilien", 3477 to "Küchenhelfer", 1920 to "Essen", 3478 to "Haushaltsgeräte", 1919 to "Textilien", 1934 to "Wohnaccessoires", 5428 to "Büromaterial", 2915 to "Feste & Feiertage", 3811 to "Werkzeuge & Heimwerken", 3812 to "Außenbereich & Garten", 5106 to "Haustierbedarf",
            2994 to "Elektronik", 3002 to "Videospiele & Konsolen", 3564 to "Computer & Zubehör", 3565 to "Mobiltelefone & Kommunikation", 3566 to "Audio, Kopfhörer & Hi-Fi", 3054 to "Kameras & Zubehör", 3567 to "Tablets, E-Reader & Zubehör", 3568 to "TV & Heimkino", 3569 to "Elektronik für Beauty & Körperpflege", 3004 to "Wearables", 2995 to "Andere Geräte & Zubehör",
            2309 to "Unterhaltung", 2312 to "Bücher", 5424 to "Zeitschriften", 3036 to "Musik", 3037 to "Video",
            4824 to "Hobby- & Sammlerartikel", 4874 to "Sammelkarten", 4881 to "Brettspiele", 4882 to "Puzzles", 4883 to "Tabletop- & Miniaturspiele", 4901 to "Erinnerungsstücke", 4895 to "Münzen & Geldscheine", 4888 to "Briefmarken", 4894 to "Postkarten", 4825 to "Musikinstrumente & -ausrüstung", 5151 to "Kunst & Basteln", 4906 to "Aufbewahrung für Sammlerstücke", 4916 to "Spielzubehör",
            4332 to "Sport", 4333 to "Fahrradfahren", 4334 to "Fitness, Laufen & Yoga", 4335 to "Outdoor-Sport", 4336 to "Wassersport", 4337 to "Teamsport", 4338 to "Schlägersportarten", 4339 to "Golf", 4340 to "Reitsport", 4341 to "Skateboards & Roller", 4342 to "Boxen & Kampfsport", 4343 to "Freizeitsport & -spiele", 4344 to "Wintersport",
        ),
        "Greek" to mapOf(
            1904 to "Γυναίκα", 4 to "Ρούχα", 16 to "Παπούτσια", 19 to "Τσάντες", 1187 to "Αξεσουάρ", 146 to "Ομορφιά",
            5 to "Άνδρας", 2050 to "Ρούχα", 1231 to "Παπούτσια", 82 to "Αξεσουάρ", 139 to "Περιποίηση",
            2993 to "Επώνυμοι σχεδιαστές", 2983 to "Επώνυμα γυναικεία", 2988 to "Επώνυμα ανδρικά",
            1193 to "Παιδιά", 1195 to "Ρούχα για κορίτσια", 1194 to "Ρούχα για αγόρια", 1499 to "Παιχνίδια", 1496 to "Καρότσια και καθίσματα αυτοκινήτου", 1498 to "Έπιπλα και διακόσμηση", 3393 to "Μπάνιο και άλλαγμα", 3427 to "Εξοπλισμός παιδικής προστασίας και ασφάλειας", 3419 to "Υγεία και εγκυμοσύνη", 3432 to "Θηλασμός και τάισμα", 3296 to "Ύπνος και κλινοσκεπάσματα", 1501 to "Σχολικά είδη", 1502 to "Άλλα παιδικά είδη",
            1918 to "Σπίτι", 3474 to "Μικρές συσκευές κουζίνας", 3476 to "Μαγειρικά σκεύη και σκεύη ψησίματος", 3477 to "Εργαλεία κουζίνας", 1920 to "Επιτραπέζια σκεύη", 3478 to "Οικιακή φροντίδα", 1919 to "Υφάσματα", 1934 to "Αξεσουάρ σπιτιού", 5428 to "Είδη γραφείου", 2915 to "Γιορτές και αργίες", 3811 to "Εργαλεία και μαστορέματα", 3812 to "Εξωτερικός χώρος και κήπος", 5106 to "Φροντίδα κατοικίδιων",
            2994 to "Ηλεκτρονικά είδη", 3002 to "Βιντεοπαιχνίδια & κονσόλες", 3564 to "Υπολογιστές και αξεσουάρ", 3565 to "Κινητά τηλέφωνα και επικοινωνία", 3566 to "Ήχος, ακουστικά και hi-fi", 3054 to "Φωτογραφικές μηχανές και αξεσουάρ", 3567 to "Τάμπλετ, ηλεκτρονικοί αναγνώστες και αξεσουάρ", 3568 to "Τηλεόραση και οικιακός κινηματογράφος", 3569 to "Ηλεκτρονικά είδη ομορφιάς και προσωπικής φροντίδας", 3004 to "Φορετή τεχνολογία", 2995 to "Άλλες συσκευές και αξεσουάρ",
            2309 to "Ψυχαγωγία", 2312 to "Βιβλία", 5424 to "Περιοδικά", 3036 to "Μουσική", 3037 to "Βίντεο",
            4824 to "Χόμπι και συλλεκτικά είδη", 4874 to "Συλλεκτικές κάρτες", 4881 to "Επιτραπέζια παιχνίδια", 4882 to "Παζλ", 4883 to "Επιτραπέζια παιχνίδια και παιχνίδια με μινιατούρες", 4901 to "Αναμνηστικά", 4895 to "Νομίσματα και χαρτονομίσματα", 4888 to "Γραμματόσημα", 4894 to "Καρτ ποστάλ", 4825 to "Μουσικά όργανα και εξοπλισμός", 5151 to "Τέχνες και χειροτεχνία", 4906 to "Οργάνωση και αποθήκευση συλλεκτικών ειδών", 4916 to "Αξεσουάρ παιχνιδιών",
            4332 to "Άθληση", 4333 to "Ποδηλασία", 4334 to "Γυμναστική, τρέξιμο και γιόγκα", 4335 to "Άθληση σε εξωτερικό χώρο", 4336 to "Θαλάσσια σπορ", 4337 to "Ομαδικά αθλήματα", 4338 to "Αθλήματα ρακέτας", 4339 to "Γκολφ", 4340 to "Ιππασία", 4341 to "Σκέιτμπορντ και πατίνια", 4342 to "Πυγμαχία και πολεμικές τέχνες", 4343 to "Αθλήματα και παιχνίδια ψυχαγωγίας", 4344 to "Χειμερινά αθλήματα",
        ),
        "Hungarian" to mapOf(
            1904 to "Női", 4 to "Ruhák", 16 to "Cipők", 19 to "Táskák", 1187 to "Kiegészítők", 146 to "Szépségápolás",
            5 to "Férfi", 2050 to "Ruhák", 1231 to "Cipők", 82 to "Kiegészítők", 139 to "Szőrzetápolás",
            2993 to "Dizájner", 2983 to "Dizájner női", 2988 to "Dizájner férfi",
            1193 to "Gyerek", 1195 to "Lányruházat", 1194 to "Fiúruházat", 1499 to "Játékok", 1496 to "Babakocsik, hordozók és autósülések", 1498 to "Bútorok és dekoráció", 3393 to "Fürdetés és pelenkázás", 3427 to "Gyermekbiztonság, biztonsági felszerelés", 3419 to "Egészség és terhesség", 3432 to "Szoptatás és etetés", 3296 to "Alvás és ágynemű", 1501 to "Iskolai felszerelés", 1502 to "Egyéb gyermekholmik",
            1918 to "Otthon", 3474 to "Konyhai kisgépek", 3476 to "Főző- és sütőedények", 3477 to "Konyhai eszközök", 1920 to "Étkészletek", 3478 to "Háztartás", 1919 to "Textíliák", 1934 to "Otthoni kiegészítők", 5428 to "Irodai kellékek", 2915 to "Ünnepek", 3811 to "Szerszámok és barkácsolás", 3812 to "Kültér és kert", 5106 to "Kisállatgondozás",
            2994 to "Elektronika", 3002 to "Videojátékok és konzolok", 3564 to "Számítógépek és kiegészítőik", 3565 to "Mobiltelefonok és kommunikáció", 3566 to "Audio, fejhallgató és hifi", 3054 to "Kamerák és tartozékaik", 3567 to "Tabletek, e-olvasók és kiegészítők", 3568 to "TV és házimozi", 3569 to "Szépség- és testápoló elektronika", 3004 to "Viselhető elektronika", 2995 to "Egyéb eszközök és tartozékok",
            2309 to "Szórakozás", 2312 to "Könyvek", 5424 to "Magazinok", 3036 to "Zene", 3037 to "Video",
            4824 to "Hobbi és gyűjtemények", 4874 to "Gyűjthető kártyák", 4881 to "Társasjátékok", 4882 to "Kirakók", 4883 to "Asztali és miniatűr játékok", 4901 to "Emléktárgyak", 4895 to "Érmék és bankjegyek", 4888 to "Bélyegek", 4894 to "Képeslapok", 4825 to "Hangszerek és zenei berendezések", 5151 to "Kézművesség", 4906 to "Gyűjthető tárgyak tárolása", 4916 to "Játékkiegészítők",
            4332 to "Sport", 4333 to "Kerékpározás", 4334 to "Fitnesz, futás és jóga", 4335 to "Kültéri sport", 4336 to "Vízi sportok", 4337 to "Csapatsportok", 4338 to "Ütős sportok", 4339 to "Golf", 4340 to "Lovassport", 4341 to "Gördeszkák és rollerek", 4342 to "Boksz és harcművészetek", 4343 to "Alkalmi sportok és játékok", 4344 to "Téli sportok",
        ),
        "Italian" to mapOf(
            1904 to "Donna", 4 to "Vestiti", 16 to "Scarpe", 19 to "Borse", 1187 to "Accessori", 146 to "Bellezza",
            5 to "Uomo", 2050 to "Vestiti", 1231 to "Scarpe", 82 to "Accessori", 139 to "Cura del corpo",
            2993 to "Articoli griffati", 2983 to "Articoli griffati per donna", 2988 to "Articoli griffati per uomo",
            1193 to "Bambini", 1195 to "Abbigliamento bambina", 1194 to "Abbigliamento bambino", 1499 to "Giocattoli", 1496 to "Passeggini, marsupi e seggiolini per auto", 1498 to "Arredamento e decorazioni", 3393 to "Bagnetto e cambio", 3427 to "Attrezzature di sicurezza per bambini", 3419 to "Salute e gravidanza", 3432 to "Allattamento e alimentazione", 3296 to "Sonno e biancheria da letto", 1501 to "Articoli per la scuola", 1502 to "Altri articoli per bambini",
            1918 to "Casa", 3474 to "Piccoli elettrodomestici da cucina", 3476 to "Utensili per cucina e forno", 3477 to "Utensili da cucina", 1920 to "Stoviglie", 3478 to "Cura della casa", 1919 to "Tessili", 1934 to "Accessori per la casa", 5428 to "Materiale per ufficio", 2915 to "Festeggiamenti e feste", 3811 to "Attrezzi e bricolage", 3812 to "Esterni e giardino", 5106 to "Animali",
            2994 to "Elettronica", 3002 to "Videogiochi e console", 3564 to "Computer e accessori", 3565 to "Telefoni cellulari e comunicazione", 3566 to "Audio, cuffie e hi-fi", 3054 to "Fotocamere e accessori", 3567 to "Tablet, e-reader e accessori", 3568 to "TV e home cinema", 3569 to "Elettronica per la bellezza e cura personale", 3004 to "Dispositivi indossabili", 2995 to "Altri dispositivi e accessori",
            2309 to "Intrattenimento", 2312 to "Libri", 5424 to "Riviste", 3036 to "Musica", 3037 to "Video",
            4824 to "Hobby e collezionismo", 4874 to "Carte collezionabili", 4881 to "Giochi da tavolo", 4882 to "Puzzle", 4883 to "Giochi da tavolo con miniature", 4901 to "Cimeli", 4895 to "Monete e banconote", 4888 to "Francobolli", 4894 to "Cartoline", 4825 to "Strumenti e attrezzature musicali", 5151 to "Arte e creatività", 4906 to "Contenitori per pezzi da collezione", 4916 to "Accessori da gioco",
            4332 to "Sport", 4333 to "Ciclismo", 4334 to "Fitness, corsa e yoga", 4335 to "Sport all’aperto", 4336 to "Sport acquatici", 4337 to "Sport di squadra", 4338 to "Sport con racchette", 4339 to "Golf", 4340 to "Equitazione", 4341 to "Skateboard e monopattini", 4342 to "Boxe e arti marziali", 4343 to "Sport e giochi ricreativi", 4344 to "Sport invernali",
        ),
        "Lithuanian" to mapOf(
            1904 to "Moterims", 4 to "Drabužiai", 16 to "Avalynė", 19 to "Rankinės", 1187 to "Aksesuarai", 146 to "Kosmetika",
            5 to "Vyrams", 2050 to "Drabužiai", 1231 to "Avalynė", 82 to "Aksesuarai", 139 to "Kosmetika",
            2993 to "Dizainerių prekės", 2983 to "Dizainerių prekės moterims", 2988 to "Dizainerių prekės vyrams",
            1193 to "Vaikams", 1195 to "Apranga mergaitėms", 1194 to "Apranga berniukams", 1499 to "Žaislai", 1496 to "Vežimėliai, nešioklės, automobilinės kėdutės", 1498 to "Baldai ir dekoracijos", 3393 to "Maudynės ir pervystymas", 3427 to "Vaikų saugos priemonės", 3419 to "Sveikatai ir nėštumui", 3432 to "Žindymas ir (pri)maitinimas", 3296 to "Priemonės miegui, patalynė", 1501 to "Mokyklos reikmenys", 1502 to "Kitos prekės vaikams",
            1918 to "Namams", 3474 to "Smulkūs virtuvės prietaisai", 3475 to "Stambi buitinė technika", 3476 to "Virimo ir kepimo indai", 3477 to "Virtuvės reikmenys", 1920 to "Stalo serviravimas", 3478 to "Buitinė technika", 1919 to "Tekstilė", 1934 to "Interjero akcentai", 5428 to "Kanceliarinės prekės", 2915 to "Šventės", 3811 to "Meistravimo priemonės ir įrankiai", 3812 to "Laukas ir sodas", 3154 to "Baldai", 5106 to "Gyvūnams",
            2994 to "Elektronika", 3002 to "Vaizdo žaidimai ir konsolės", 3564 to "Kompiuteriai ir jų priedai", 3565 to "Mobilieji telefonai ir ryšiai", 3566 to "Garso aparatūra, ausinės ir hi-fi įtaisai", 3054 to "Vaizdo kameros ir fotoaparatai bei jų priedai", 3567 to "Planšetiniai kompiuteriai, el. skaityklės ir priedai", 3568 to "Televizoriai ir namų kinas", 3569 to "Grožio ir asmeninės priežiūros elektronika", 3004 to "Dėvimieji įrenginiai", 2995 to "Kiti prietaisai ir priedai",
            2309 to "Pramogos", 2312 to "Knygos", 5424 to "Žurnalai", 3036 to "Muzika", 3037 to "Vaizdo įrašai",
            4824 to "Hobiams ir kolekcijoms", 4874 to "Kolekcinės kortelės", 4881 to "Stalo žaidimai", 4882 to "Dėlionės", 4883 to "Miniatiūrų stalo žaidimai", 4901 to "Suvenyrai", 4895 to "Monetos ir banknotai", 4888 to "Pašto ženklai", 4894 to "Atvirukai", 4825 to "Muzikos instrumentai ir įranga", 5151 to "Kūryba ir rankdarbiai", 4906 to "Kolekcinių daiktų laikymas", 4916 to "Žaidimų priedai",
            4332 to "Sportui", 4333 to "Dviračių sportas", 4334 to "Kūno rengyba, bėgimas ir joga", 4335 to "Lauko sportas", 4336 to "Vandens sportas", 4337 to "Komandinis sportas", 4338 to "Rakečių sportas", 4339 to "Golfas", 4340 to "Jojimas", 4341 to "Riedlentės ir paspirtukai", 4342 to "Boksas ir kovos menai", 4343 to "Laisvalaikio sporto šakos ir žaidimai", 4344 to "Žiemos sportas",
        ),
        "Polish" to mapOf(
            1904 to "Kobiety", 4 to "Ubrania", 16 to "Obuwie", 19 to "Torby", 1187 to "Akcesoria", 146 to "Kosmetyki",
            5 to "Mężczyźni", 2050 to "Ubrania", 1231 to "Obuwie", 82 to "Akcesoria, dodatki", 139 to "Kosmetyki",
            2993 to "Przedmioty designerskie", 2983 to "Damskie przedmioty designerskie", 2988 to "Męskie przedmioty designerskie",
            1193 to "Dzieci", 1195 to "Ubrania dla dziewczynek", 1194 to "Ubrania dla chłopców", 1499 to "Zabawki", 1496 to "Wózki spacerowe, nosidełka i foteliki samochodowe", 1498 to "Meble i dekoracje", 3393 to "Kąpiel i przewijanie", 3427 to "Blokady i zabezpieczenia", 3419 to "Zdrowie i ciąża", 3432 to "Pielęgnacja i karmienie", 3296 to "Akcesoria do sypialni", 1501 to "Artykuły szkolne", 1502 to "Inne przedmioty dla dzieci",
            1918 to "Dom", 3474 to "Małe AGD kuchenne", 3476 to "Naczynia do gotowania i pieczenia", 3477 to "Akcesoria kuchenne", 1920 to "Akcesoria stołowe", 3478 to "AGD i akcesoria do sprzątania", 1919 to "Tekstylia", 1934 to "Akcesoria i ozdoby", 5428 to "Artykuły biurowe", 2915 to "Uroczystości i święta", 3811 to "Narzędzia i majsterkowanie", 3812 to "Wyposażenie ogrodowe", 5106 to "Zwierzęta",
            2994 to "Elektronika", 3002 to "Gry wideo i konsole", 3564 to "Komputery i akcesoria", 3565 to "Telefony komórkowe i komunikacja", 3566 to "Audio i słuchawki", 3054 to "Aparaty fotograficzne i akcesoria", 3567 to "Tablety, czytniki e-booków i akcesoria", 3568 to "Telewizor i kino domowe", 3569 to "Urządzenia do pielęgnacji urody", 3004 to "Urządzenia ubieralne", 2995 to "Inne urządzenia i akcesoria",
            2309 to "Rozrywka", 2312 to "Książki", 5424 to "Czasopisma", 3036 to "Muzyka", 3037 to "Wideo",
            4824 to "Hobby i kolekcjonerstwo", 4874 to "Karty kolekcjonerskie", 4881 to "Gry planszowe", 4882 to "Puzzle", 4883 to "Gry stołowe i bitewne", 4901 to "Pamiątki kolekcjonerskie", 4895 to "Monety i banknoty", 4888 to "Znaczki pocztowe", 4894 to "Pocztówki", 4825 to "Instrumenty i sprzęt muzyczny", 5151 to "Plastyka i rękodzieło", 4906 to "Przechowywanie przedmiotów kolekcjonerskich", 4916 to "Akcesoria do gier",
            4332 to "Sport", 4333 to "Jazda na rowerze", 4334 to "Fitness, bieganie i joga", 4335 to "Sporty outdoorowe", 4336 to "Sporty wodne", 4337 to "Sporty zespołowe", 4338 to "Sporty rakietowe", 4339 to "Golf", 4340 to "Jazda konna", 4341 to "Deskorolki i hulajnogi", 4342 to "Boks i sztuki walki", 4343 to "Gry rekreacyjne", 4344 to "Sporty zimowe",
        ),
        "Portuguese" to mapOf(
            1904 to "Mulher", 4 to "Roupa", 16 to "Calçado", 19 to "Malas", 1187 to "Acessórios", 146 to "Beleza",
            5 to "Homem", 2050 to "Roupa", 1231 to "Sapatos", 82 to "Acessórios", 139 to "Cuidados pessoais",
            2993 to "Peças de estilista", 2983 to "Peças de estilista para mulher", 2988 to "Peças de estilista para homem",
            1193 to "Criança", 1195 to "Vestuário para rapariga", 1194 to "Vestuário para rapaz", 1499 to "Brinquedos", 1496 to "Carrinhos de bebé, alcofas e cadeiras auto", 1498 to "Mobiliário e decoração", 3393 to "Banho e muda fraldas", 3427 to "Proteção e segurança infantil", 3419 to "Saúde e gravidez", 3432 to "Alimentação e amamentação", 3296 to "Sono e roupa de cama", 1501 to "Material escolar", 1502 to "Outros artigos de criança",
            1918 to "Casa", 3474 to "Pequenos eletrodomésticos de cozinha", 3476 to "Utensílios de cozinha e pastelaria", 3477 to "Utensílios de cozinha", 1920 to "Artigos de mesa", 3478 to "Cuidados domésticos", 1919 to "Têxteis", 1934 to "Acessórios para a casa", 5428 to "Material de escritório", 2915 to "Celebrações e festividades", 3811 to "Ferramentas & DIY", 3812 to "Exterior e jardim", 5106 to "Animais",
            2994 to "Eletrónica", 3002 to "Videojogos & consolas", 3564 to "Computadores e acessórios", 3565 to "Telemóveis e comunicação", 3566 to "Áudio, auscultadores e hi-fi", 3054 to "Câmaras e acessórios", 3567 to "Tablets, e-readers e acessórios", 3568 to "TV e cinema em casa", 3569 to "Dispositivos elétricos de beleza e cuidados pessoais", 3004 to "Tecnologia wearable", 2995 to "Outros dispositivos e acessórios",
            2309 to "Entretenimento", 2312 to "Livros", 5424 to "Revistas", 3036 to "Música", 3037 to "Vídeo",
            4824 to "Hobbies e Coleções", 4874 to "Cartas colecionáveis", 4881 to "Jogos de tabuleiro", 4882 to "Puzzles", 4883 to "Jogos de mesa e de miniaturas", 4901 to "Memorabilia", 4895 to "Moedas e notas", 4888 to "Selos", 4894 to "Postais", 4825 to "Instrumentos musicais e equipamento", 5151 to "Trabalhos manuais", 4906 to "Organização de objetos de coleção", 4916 to "Acessórios para jogos",
            4332 to "Desporto", 4333 to "Ciclismo", 4334 to "Fitness, corrida e yoga", 4335 to "Desportos de exterior", 4336 to "Desportos aquáticos", 4337 to "Desportos coletivos", 4338 to "Desportos com raqueta", 4339 to "Golfe", 4340 to "Equitação", 4341 to "Skates e trotinetes", 4342 to "Boxe e artes marciais", 4343 to "Desportos e jogos casuais", 4344 to "Desportos de inverno",
        ),
        "Romanian" to mapOf(
            1904 to "Femei", 4 to "Haine", 16 to "Pantofi", 19 to "Genți", 1187 to "Accesorii", 146 to "Frumusețe",
            5 to "Bărbați", 2050 to "Haine", 1231 to "Pantofi", 82 to "Accesorii", 139 to "Îngrijire",
            2993 to "Designer", 2983 to "Designer femei", 2988 to "Designer bărbați",
            1193 to "Copii", 1195 to "Îmbrăcăminte pentru fete", 1194 to "Îmbrăcăminte pentru băieți", 1499 to "Jucării", 1496 to "Cărucioare, landouri și scaune auto", 1498 to "Mobilier și decorațiuni", 3393 to "Îmbăiere și înfășare", 3427 to "Echipamente de protecție și siguranță pentru copii", 3419 to "Sănătate și sarcină", 3432 to "Alăptare și hrănire", 3296 to "Dormit și lenjerie de pat", 1501 to "Rechizite școlare", 1502 to "Alte articole pentru copii",
            1918 to "Casă", 3474 to "Aparate electrocasnice mici", 3476 to "Ustensile de gătit și de copt", 3477 to "Ustensile de bucătărie", 1920 to "Articole de masă", 3478 to "Îngrijirea gospodăriei", 1919 to "Textile", 1934 to "Accesorii pentru casă", 5428 to "Consumabile de birou", 2915 to "Festivități și sărbători", 3811 to "Unelte și DIY", 3812 to "Exterior și grădină", 5106 to "Animale",
            2994 to "Electronice", 3002 to "Jocuri video și console", 3564 to "Calculatoare și accesorii", 3565 to "Telefoane mobile și comunicare", 3566 to "Audio, căști și hi-fi", 3054 to "Camere foto și accesorii", 3567 to "Tablete, e-readere și accesorii", 3568 to "TV și home cinema", 3569 to "Electronice pentru frumusețe și îngrijire personală", 3004 to "Portabile", 2995 to "Alte dispozitive și accesorii",
            2309 to "Divertisment", 2312 to "Cărți", 5424 to "Reviste", 3036 to "Muzică", 3037 to "Video",
            4824 to "Hobbyuri și colecții", 4874 to "Carduri de tranzacționare", 4881 to "Jocuri de societate", 4882 to "Puzzle-uri", 4883 to "Jocuri de masă și în miniatură", 4901 to "Suveniruri", 4895 to "Monede și bancnote", 4888 to "Timbre", 4894 to "Cărți poștale", 4825 to "Instrumente muzicale și echipamente", 5151 to "Arte și meșteșuguri", 4906 to "Depozitare obiecte de colecție", 4916 to "Accesorii pentru jocuri",
            4332 to "Sporturi", 4333 to "Ciclism", 4334 to "Fitness, alergare și yoga", 4335 to "Sporturi în aer liber", 4336 to "Sporturi nautice", 4337 to "Sporturi de echipă", 4338 to "Sporturi cu rachetă", 4339 to "Golf", 4340 to "Echitație", 4341 to "Skateboard-uri și scutere", 4342 to "Box și arte marțiale", 4343 to "Sporturi și jocuri ocazionale", 4344 to "Sporturi de iarnă",
        ),
        "Slovak" to mapOf(
            1904 to "Ženy", 4 to "Oblečenie", 16 to "Obuv", 19 to "Tašky", 1187 to "Doplnky", 146 to "Kozmetika",
            5 to "Muži", 2050 to "Oblečenie", 1231 to "Obuv", 82 to "Doplnky", 139 to "Kozmetika",
            2993 to "Dizajnérske", 2983 to "Dizajnérske kúsky pre ženy", 2988 to "Dizajnérske kúsky pre mužov",
            1193 to "Deti", 1195 to "Dievčenské oblečenie", 1194 to "Chlapčenské oblečenie", 1499 to "Hračky", 1496 to "Detské kočíky, nosiče a autosedačky", 1498 to "Nábytok a dekorácie", 3393 to "Kúpanie a prebaľovanie", 3427 to "Detské poistky a ochrana detí", 3419 to "Zdravie a tehotenstvo", 3432 to "Dojčenie a kŕmenie", 3296 to "Spánok a posteľná bielizeň", 1501 to "Školské potreby", 1502 to "Ďalšie predmety pre deti",
            1918 to "Domov", 3474 to "Malé kuchynské spotrebiče", 3476 to "Potreby na varenie a pečenie", 3477 to "Kuchynské náradie", 1920 to "Stolovanie", 3478 to "Starostlivosť o domácnosť", 1919 to "Textil", 1934 to "Dekorácie", 5428 to "Kancelárske potreby", 2915 to "Oslavy a sviatky", 3811 to "Náradie a DIY", 3812 to "Exteriér a záhrada", 5106 to "Zvieratá",
            2994 to "Elektronika", 3002 to "Videohry a konzoly", 3564 to "Počítače a príslušenstvo", 3565 to "Mobilné telefóny a komunikácia", 3566 to "Audio, slúchadlá a hi-fi", 3054 to "Fotoaparáty a príslušenstvo", 3567 to "Tablety, elektronické čítačky a príslušenstvo", 3568 to "TV a domáce kino", 3569 to "Elektronika pre kozmetickú a osobnú starostlivosť", 3004 to "Nositeľná elektronika", 2995 to "Ďalšie zariadenia a príslušenstvo",
            2309 to "Zábava", 2312 to "Knihy", 5424 to "Časopisy", 3036 to "Hudba", 3037 to "Video",
            4824 to "Koníčky a zberateľstvo", 4874 to "Zberateľské kartičky", 4881 to "Stolové hry", 4882 to "Puzzle", 4883 to "Stolové a cestovné hry", 4901 to "Suveníry", 4895 to "Mince a bankovky", 4888 to "Známky", 4894 to "Pohľadnice", 4825 to "Hudobné nástroje a vybavenie", 5151 to "Umenie a tvorenie", 4906 to "Skladovanie zberateľských predmetov", 4916 to "Herné príslušenstvo",
            4332 to "Šport", 4333 to "Cyklistika", 4334 to "Fitness, beh a joga", 4335 to "Vonkajšie športy", 4336 to "Vodné športy", 4337 to "Tímové športy", 4338 to "Raketové športy", 4339 to "Golf", 4340 to "Jazdectvo", 4341 to "Skateboardy a kolobežky", 4342 to "Box a bojové umenia", 4343 to "Príležitostné športy a hry", 4344 to "Zimné športy",
        ),
        "Spanish" to mapOf(
            1904 to "Mujer", 4 to "Ropa", 16 to "Calzado", 19 to "Bolsos", 1187 to "Accesorios", 146 to "Cuidado y belleza",
            5 to "Hombre", 2050 to "Ropa", 1231 to "Calzado", 82 to "Accesorios", 139 to "Cuidado y belleza",
            2993 to "Moda de diseño", 2983 to "Moda de diseño para mujer", 2988 to "Moda de diseño para hombre",
            1193 to "Niños", 1195 to "Ropa para niñas", 1194 to "Ropa para niños", 1499 to "Juguetes", 1496 to "Carritos, portabebés y sillas de coche", 1498 to "Muebles y decoración", 3393 to "Baño y cambio", 3427 to "Elementos de seguridad y protección infantil", 3419 to "Salud y embarazo", 3432 to "Lactancia y alimentación", 3296 to "Descanso y ropa de cama", 1501 to "Material escolar", 1502 to "Otros artículos infantiles",
            1918 to "Hogar", 3474 to "Pequeños electrodomésticos de cocina", 3476 to "Menaje de cocina y repostería", 3477 to "Utensilios de cocina", 1920 to "Menaje", 3478 to "Cuidado del hogar", 1919 to "Textiles", 1934 to "Decoración", 5428 to "Materiales de oficina", 2915 to "Celebraciones y fiestas", 3811 to "Bricolaje y herramientas", 3812 to "Jardín, terraza y balcón", 5106 to "Mascotas",
            2994 to "Electrónica", 3002 to "Videojuegos y consolas", 3564 to "Ordenadores y accesorios", 3565 to "Teléfonos móviles y comunicación", 3566 to "Sonido, auriculares y Hi-Fi", 3054 to "Cámaras y accesorios", 3567 to "Tablets, lectores electrónicos y accesorios", 3568 to "TV y Home Cinema", 3569 to "Electrónica para belleza y cuidado personal", 3004 to "Wearables", 2995 to "Otros dispositivos y accesorios",
            2309 to "Entretenimiento", 2312 to "Libros", 5424 to "Revistas", 3036 to "Música", 3037 to "Vídeo",
            4824 to "Hobbies y coleccionismo", 4874 to "Cartas coleccionables", 4881 to "Juegos de mesa", 4882 to "Puzles", 4883 to "Juegos de miniaturas", 4901 to "Memorabilia", 4895 to "Monedas y billetes", 4888 to "Sellos", 4894 to "Postales", 4825 to "Instrumentos y equipos musicales", 5151 to "Arte y manualidades", 4906 to "Organización de colecciones", 4916 to "Accesorios para juegos",
            4332 to "Deportes", 4333 to "Ciclismo", 4334 to "Fitness, running y yoga", 4335 to "Deportes al aire libre", 4336 to "Deportes acuáticos", 4337 to "Deportes de equipo", 4338 to "Deportes de raqueta", 4339 to "Golf", 4340 to "Equitación", 4341 to "Tablas de skate y patinetes", 4342 to "Boxeo y artes marciales", 4343 to "Deportes y juegos recreativos", 4344 to "Deportes de invierno",
        ),
        "Swedish" to mapOf(
            1904 to "Dam", 4 to "Kläder", 16 to "Skor", 19 to "Väskor", 1187 to "Accessoarer", 146 to "Skönhet",
            5 to "Herr", 2050 to "Kläder", 1231 to "Skor", 82 to "Accessoarer", 139 to "Hygien och skönhet",
            2993 to "Designer", 2983 to "Designer dam", 2988 to "Designer män",
            1193 to "Barn", 1195 to "Flickkläder", 1194 to "Pojkkläder", 1499 to "Leksaker", 1496 to "Barnvagnar, bärselar & bilstolar", 1498 to "Möbler & inredning", 3393 to "Bad & ombyte", 3427 to "Barnsäkerhet, barnskydd & säkerhetsutrustning", 3419 to "Hälsa & graviditet", 3432 to "Amning & matning", 3296 to "Sängkläder", 1501 to "Skolmaterial", 1502 to "Övriga barnartiklar",
            1918 to "Hem", 3474 to "Små köksapparater", 3476 to "Köks- och bakredskap", 3477 to "Köksredskap", 1920 to "Serviser", 3478 to "Maskiner för hemmet", 1919 to "Textilier", 1934 to "Hemtillbehör", 5428 to "Kontorsmaterial", 2915 to "Högtider och helgdagar", 3811 to "Verktyg & DIY", 3812 to "Utemiljö & trädgård", 5106 to "Husdjur",
            2994 to "Elektronik", 3002 to "Spel & konsoler", 3564 to "Datorer & tillbehör", 3565 to "Mobiltelefoner & kommunikation", 3566 to "Ljud, hörlurar & hi-fi", 3054 to "Kameror & tillbehör", 3567 to "Surfplattor, läsplattor & tillbehör", 3568 to "TV & hemmabio", 3569 to "Elektroniska skönhets- & hygienprodukter", 3004 to "Wearables", 2995 to "Övriga enheter & tillbehör",
            2309 to "Underhållning", 2312 to "Böcker", 5424 to "Tidskrifter", 3036 to "Musik", 3037 to "Video",
            4824 to "Hobby & samlarobjekt", 4874 to "Samlarkort", 4881 to "Brädspel", 4882 to "Pussel", 4883 to "Bords- & miniatyrspel", 4901 to "Minnessaker", 4895 to "Mynt & sedlar", 4888 to "Frimärken", 4894 to "Vykort", 4825 to "Musikinstrument & utrustning", 5151 to "Hantverk & hobby", 4906 to "Förvaring av samlarföremål", 4916 to "Tillbehör för spel",
            4332 to "Sport", 4333 to "Cykling", 4334 to "Träning, löpning & yoga", 4335 to "Utomhussport", 4336 to "Vattensporter", 4337 to "Lagsporter", 4338 to "Racketsporter", 4339 to "Gold", 4340 to "Ridsport", 4341 to "Skateboards & sparkcyklar", 4342 to "Boxning & kampsport", 4343 to "Fritidssporter & spel", 4344 to "Vintersport",
        ),
    )

    /**
     * Per-country name overrides on top of the language table (US English
     * differs from UK English in a handful of names; Canada is assumed to
     * match the US — vinted.ca could not be verified).
     */
    private val usEnglishOverrides: Map<Int, String> = mapOf(
        1496 to "Strollers",
        3565 to "Cell phones & communication",
        3568 to "TV & home theater",
        4824 to "Hobbies & collectibles",
        4906 to "Collectibles storage",
        4340 to "Equestrian & horseback riding",
    )

    val countryOverrides: Map<String, Map<Int, String>> = mapOf(
        "United States" to usEnglishOverrides,
        "Canada" to usEnglishOverrides,
    )
}
