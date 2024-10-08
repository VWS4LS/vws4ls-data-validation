#	MicroService „Data Validation“ 

- Der Web-Service ist in der Lage die Namen, die Struktur und den Datentyp der JSON Objekte zu validieren	.
- Im Fehlerfall soll eine für das IT-System verwendbare Fehlerbeschreibung mit Fehlercode zurückgesendet werden

Der Validierungsdienst ist als REST-Service mit Patch-Endpunkt implementiert. Der Service benötigt eine JSON-Datei als Input. Das JSON wird validiert und als Ergebnis wird ein serialisiertes JSON-Objekt zurückgegeben.
Die Abbildung zeigt den Aufbau des Tests. 

 ![image](https://github.com/user-attachments/assets/d2819f7f-a8f5-429c-aaad-fd7bc8bcdfb4)
Wenn der Validierungsdienst erfolgreich ausgeführt wird, gibt er eine JSON-Zeichenkette mit den Ergebnissen und dem HTTP-Code 200 zurück. In der JSON-Antwort ist der "resultValue" "true", wenn die Validierung erfolgreich war. Der Wert "valueType" ist per default "Boolean". Das Attribut "message" ist leer, wenn der Dienst erfolgreich ausgeführt wurde.
 
Die Validierung schlägt fehl, wenn die JSON-Datei nicht mit dem JSON-Schema übereinstimmt. Der Dienst gibt dann ebenfalls 200 zurück. Der Parameter "resultValue" liefert jedoch "false" zurück. Im Attribut "message" wird die Fehlermeldung des Validierungsdienstes angezeigt.

Wenn der Dienst mit einer nicht bekannten Semantic-ID fehlschlägt, gibt der Dienst 404 mit einer Fehlermeldung zurück.

## Installation 
- Installieren Sie das Java JDK 17; 
- Installieren Sie die Eclipse IDE für Java EE Developer; 
- Installieren Sie das Maven-Plugin für Eclipse m2eclipse.
- Checken Sie den Quellcode von (https://github.com/VWS4LS/vws4ls-data-validation/) aus.
- Importieren Sie das Projekt in die Eclipse IDE. Die Schritte für den Import sind die folgenden:
- Datei->Importieren...-> bestehendes Maven-Projekt

![image](https://github.com/user-attachments/assets/e144d3fe-a07e-4209-80fc-79530b1b2a59)


- Wählen Sie den Projektordner des Java-Projekts aus, überprüfen Sie die pom.xml-Datei dieses Projektordners und klicken Sie dann auf finish
- Danach wird Eclipse das Projekt in den Arbeitsbereich importieren.

![image](https://github.com/user-attachments/assets/7e2ec369-a5ef-4b50-b5d1-caf3c93f8a8a)

 
### Erstellen des Java-Projekts
- Klicken Sie im Projektexplorer mit der rechten Maustaste auf das „rest-service“ Projekt -> Ausführen als -> maven build... 
- Im Pop-up-Fenster im Feld „Goals“ „clean install“ eingeben, das Kästchen „SkiptTest“ ankreuzen. Dann klicken Sie auf „Ausführen

![image](https://github.com/user-attachments/assets/7715861c-619c-421d-959f-d0e76c6527ae)

- Wenn der Build erfolgreich war, sollte er in der Konsole angezeigt werden:

![image](https://github.com/user-attachments/assets/ed7a877a-7b67-4123-b01a-f0dc7a784ed0)


### Dienst starten
Der Validierungsdienst kann durch Ausführen der Hauptklasse „ValidationServiceApplication.java“ gestartet werden, die sich im Paket „org.arena.restservice“ befindet. Klicken Sie mit der rechten Maustaste auf die Klasse „ValidationServiceApplication.java“->Ausführen als->Java-Anwendung.

![image](https://github.com/user-attachments/assets/c553dd0e-91a1-4f54-ae75-739e19329c97)

Der Validierungsdienst kann in der Datei „application.properties“ konfiguriert werden. Die Datei befindet sich in dem Ordner „../src/main/resouces“. 

 ![image](https://github.com/user-attachments/assets/d5562280-bd46-477b-abe9-e8d21e85696e)

In der Datei können Sie den Server-Port des Validierungsdienstes, die URL des sermantischen Hubs und die Version des Validators, der zur Validierung der JSON-Datei mit dem JSON-Schema verwendet wird, konfigurieren. Die Standardversion ist „V7“.

![image](https://github.com/user-attachments/assets/a2e17ece-4e64-4b75-a285-dfdfbb00bcb4)

### Einrichten der Testumgebung
Um den Validierungsdienst zu testen, muss zu Testzwecken ein semantischer Hub eingerichtet werden. Im Ordner "../src/test/java", im Paket "com.example.restservice", befindet sich ein Dummy-Semantic Hub. Die Klasse ist eine Hauptklasse. Um den semantischen Hub zu starten, klicken Sie mit der rechten Maustaste auf die Klasse->Ausführen als->Java-Anwendung. Der semantische Hub wird gestartet und lädt alle Shells und Submodelle hoch, die zum Testen verwendet werden. Die vorkonfigurierten Shells und Submodelle sind in "../src/test/resources" verfügbar. Die JSON-Datei “aas-env-1.json” definiert alle Shells und Submodelle. 
Der semantische Hub wird unter „http:/localhost:8081/submodels“ verfügbar sein. Die Konfiguration des semantischen Hubs kann über die Datei application.properties erfolgen, die sich unter "--/src/test/resources" befindet.

![image](https://github.com/user-attachments/assets/b13a0038-e964-4960-b607-0b37cd26d90f)

