# Voxera

Kurzanleitung zum schnellen Starten und Importieren in IntelliJ.

Voraussetzungen:
- Java 25 JDK installiert
- Git installiert
- Maven (optional, das Projekt enthält mvnw)

Clone (HTTPS):

```bash
git clone https://github.com/Samvel-opta-data/Voxera.git
cd Voxera
```

IntelliJ: `File` → `Open` → Ordner `Voxera` auswählen oder `File` → `New` → `Project from Version Control` → URL eingeben.

Build & Run (mit Maven wrapper):

```bash
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

Wichtige Hinweise:
- Devtools ist als Runtime-Dependency enthalten für schnelleres Entwickeln (Auto-Restart).
- Die Standard-Java-Version ist auf 25 gesetzt (siehe `pom.xml`).
- Aktuator-Endpunkte für `health` und `info` sind aktiviert (siehe `application.properties`).

Wenn du willst, kann ich noch CI (GitHub Actions) und ein `Dockerfile` hinzufügen.

