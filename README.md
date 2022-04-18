# Java lab 2 - Miękkie referencje
Aplikacja zrobiona w ramach przedmiotu "Programowanie w języku Java - techniki zaawansowane"  

## Uruchamianie:  
Aby uruchomić program, należy w konsoli przejść do katalogu z pilikiem lab2.jar i uruchomić komendę:
**java -jar lab2.jar**  
  
W pliku **Data.zip** znajdują się przykładowe dane do przetestowania działania programu.

## Obserwacje po analizowaniu różnych Garbage Collectorów:

### Wstęp
Otwierając za każdym razem **15 plików tekstowych i 15 obrazów**, sprawdzałam, w których momentach są one wczytywane z pamięci, a w których z dysku. Zapisywałam, przy której ilości wczytanych danych, wywoływane było odśmiecanie.
Granicą dla wszyskich pomiarów było 75. wczytanie pliku tekstowego i 75. wczytanie obrazu z dysku.
Do pomiarów wykorzystałam parametr ograniczający wielkość stosu na **5** oraz na **10 MB**. Dla każdego z tych wariantów uruchomiłam program 5 razy dla 5 różnych parametrów:
* "-XX:+ShrinkHeapInSteps"				
* "-XX:-ShrinkHeapInSteps"			
* "-XX:+UseParallelGC" -> Parallel GC				
* "-XX:+UseG1GC" -> G1 GC 				
* "-XX:+UseSerialGC" -> Serial GC 				


### Obserwacje z podziałem na limit pamięci:

#### Dla 5MB 
* GC najszybciej uruchomił się dla G1 GC
* GC najpóźniej uruchomił się dla Serial GC
* GC najwcześniej pomiary został zakończone dla G1 GC
* GC najpóźniej pomiary zostały zakończone dla Serial GC

#### Dla 10MB
* GC najpóźniej uruchomił się dla Serial GC
* GC najwcześniej odśmiecanie wywołało się dla parametru +ShrinkHeapInSteps
* GC najpóźniej pomiary zostały zakończone dla Serial GC
* GC najwcześniej pomiary został zakończone dla Parallel GC

### Obserwacje dla poszczególnych parametrów:

#### "-XX:+ShrinkHeapInSteps"
* Zazwyczaj zwalniał pamięć po równo dla plików tekstowych i obrazów. Jeden wyjątek to sytuacja, gdy wczytał 34. raz obraz z dysku, natomiast plik txt został wtedy wczytany 33. raz.
* Czyszczenie nie było wywoływane co stałą liczbę wczytanych plików. 
* Liczba wczytywanych plików nie zawsze była wielokrotnością liczby rozpatrywanych plików. (19, 31, 48)
				
#### "-XX:-ShrinkHeapInSteps"	
* Dla obu pomiarów zwalniał pamięć po równo i liczba wczytywanych plików była zawsze wielokrotnością liczby rozpatrywanych plików.
* Dla 5MB czyszczenie było wywoływane dokładnie co 435 wczytanych plików, natomiast dla 10MB co około 1335. Można zatem uznać, że dla tego parametru GC jest wywoływany ze stałą częstotliwością.
* Oba wykresy dla tego parametru przedstawiają prostą.

		
#### Parallel GC
* Dla obu ograniczeń pamięciowych zdarzały się wyłowywania pomiędzy całkowitymi wielokrotnościami liczby 15.
* Były 2 przypadki, gdzie liczba obrazów pobranych z dysku była o 1 większa od ilości plików tekstowych pobranych z dysku w tym samym momencie.
* Częstotliwość zwalniania pamięci nie jest stała.

				
#### G1 GC
* Podobnie jak dla Parallel GC, były 2 przypadki, w których liczba obrazów wczytanych z dysku była o 1 większa od liczby wczytanych plików txt w tej samej chwili.
* Jeśli by nie brać pod uwagę tych przypadków, to możnaby stwierdzić, że zależność wywołań GC jest wprost proporcjonalna do liczby otwieranych plików.
				
#### Serial GC	
* Dla tego parametru wywołane było dodatkowe (poza wielokrotnościami 15) odśmiecanie dla obu limitów pamięciowych - 70. wczytanie dla 10MB oraz 18. dla 5MB.
* Wczytywał on takie same liczby obrazów i plików txt w tej samej chwili.

#### Treść zadania:

Napisz aplikację, która umożliwi przeglądanie danych osobowych zapisanych na dysku. Zakładamy, że dane osobowe zapisywane będą w folderach o nazwach odpowiadających identyfikatorom osób, których dotyczą. Dokładniej, w folderach pojawiać się mają dwa pliki: record.txt (o dowolnej strukturze, w pliku tym zapisane mają być: imię, nazwisko, wiek, ....) oraz image.png (ze zdjęciem danej osoby, przy czym do celów testowych można zamiast zdjęcia użyć dowolnego obrazka).

Interfejs graficzny tej aplikacji można zrealizować za pomocą dwóch paneli - jednego, przeznaczonego na listę przeglądanych folderów oraz drugiego, służącego do wyświetlania danych osobowych i zdjęcia odpowiedniego do folderu wybranego z listy.

Aplikację należy zaprojektować z wykorzystaniem słabych referencji (ang. weak references). Zakładamy, że podczas przeglądania folderów zawartość plików tekstowych i  plików zawierających obrazki będzie ładowana do odpowiedniej struktury. Słabe referencje powinny pozwolić na ominięcie konieczności wielokrotnego ładowania tej samej zawartości - co może nastąpić podczas poruszanie się wprzód i wstecz po liście folderów.

Aplikacja powinna wskazywać, czy zawartość pliku została załadowana ponownie, czy też została pobrana z pamięci. Wskazanie to może być zrealizowane za pomocą jakiegoś znacznika prezentowanego na interfejsie.

W celu oceny poprawności działania aplikację należy uruchamiać przekazując wirtualnej maszynie parametry ograniczające przydzielaną jej pamięć. Na przykład -Xms512m (co oznacza minimalnie 512 MB pamięci), -Xmx1024m (co oznacza maksymalnie 1GB).
Należy też przetestować możliwość regulowania zachowania się algorytmu odśmiecania, do czego przydają się opcje -XX:+ShrinkHeapInSteps, -XX:-ShrinkHeapInSteps. Proszę przestudiować, jakie inne atrybuty można przekazać do wirtualnej maszyny, w tym selekcji algorytmu -XX:+UseSerialGC, -XX:+UseParNewGC (deprecated), -XX:+UseParallelGC, -XX:+UseG1GC.

Architektura aplikacji powinna umożliwiać dołączanie różnych podglądaczy zawartości (czyli klas odpowiedzialnych za renderowanie zawartości plików z danymi), przy czym podglądacze powinny być konfigurowalne (np. poprzez określenie sposobu renderowania czcionek czy obrazków).

Proszę dodać do źródeł plik readme.md z wnioskami co do stosowalności opcji wirtualnej maszyny.

Proszę sięgnąć do materiałów http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/UnderstandingWeakReferences.pdf
