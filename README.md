To run this Spring Boot project with Gradle, include these instructions in your `README.md`:

---

**Prerequisites:**
- Java 23 or newer
- Gradle (or use the Gradle Wrapper: `./gradlew`)

**Steps:**

1. **Clone the repository:**
   ```
   git clone https://github.com/arpatilmh/Library.git
   cd Library
   ```

2. **Build the project:**
   ```
   ./gradlew build
   ```

3. **Run the application:**
   ```
   ./gradlew bootRun
   ```
   _or_
   ```
   java -jar build/libs/library.war
   ```

**Configuration:**
- Edit `src/main/resources/application.properties` for custom settings.

**Testing:**
```
./gradlew test
```

---
