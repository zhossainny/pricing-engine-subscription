# Pricing Engine Subscription – Stock Price Alert Service

A Spring Boot application that monitors real-time stock prices and notifies users when configured price alert conditions are met. The service exposes REST APIs that can be tested via ***Swagger*** UI and prints live price streams and alert notifications to the console - one per alert.

## Download Code and Run & Test (Without IDE)
```markdown
git clone https://github.com/zhossainny/pricing-engine-subscription.git
```
```markdown
cd pricing-engine-subscription
```

### Requirements
- **Java**: Java 11 or later - Spring boot 3 compatible
- No Maven installation required (uses Maven Wrapper)
- This project was built on Java 17, used in the pom ${Jave.version} and <java.version>17</java.version> based on my computer JAVA_HOME setup. Change it in the POM.xml based on your setup

### Run the Application
```bash
./mvnw spring-boot:run
```

The application starts at: `http://localhost:8080`

Swagger UI is available at: `http://localhost:8080/swagger-ui/index.html`

### How to Run (With IntelliJ)
**Requirements**
- Java 17+ should work fine
- No Maven installation needed (uses Maven Wrapper)

If Java 17 does NOT exist, change the Java version in `pom.xml`, hard code the java version, then build and run:
```bash
./mvnw clean package
java -jar target/pricing-engine-subscription-1.0.0.jar
```

Otherwise, launch the app by simply:
```bash
cd pricing-engine-subscription
./mvnw spring-boot:run
```

### How to Use the API (Swagger)
Open Swagger UI: `http://localhost:8080/swagger-ui/index.html`

#### Create an Alert
**POST** `/alerts`
```json
{
  "symbol": "AAPL",
  "condition": "ABOVE",
  "threshold": 190
}
```

#### List Alerts
**GET** `/alerts`
```json
[
  {
    "id": "3ab73374-a5b3-4faa-976c-1544c79c5d64",
    "symbol": "AAPL",
    "condition": "ABOVE",
    "threshold": 190.0,
    "triggered": false
  }
]
```

#### Delete Alert
**DELETE** `/alerts/{id}`
- Returns `204 No Content` on success
- Returns `404 Not Found` if the alert does not exist

### Real-Time Market Data Streaming (Console Output)
The application continuously streams synthetic market prices every 500ms:
```
TICK [AAPL] price=185.72
TICK [GOOGL] price=139.88
TICK [MSFT] price=419.63
TICK [TSLA] price=175.41
```

When an alert condition is met, a one-shot notification is printed:
```
ALERT TRIGGERED: symbol=AAPL condition=ABOVE threshold=190.0 price=191.32
```

### Alert Semantics
- Market data streams continuously in real time
- Alerts are one-shot
- Once an alert is triggered, it is marked as triggered and will not fire again
- This prevents notification spam and mirrors real-world alert systems

### Validation and Error Handling
Input validation is enforced using Jakarta Bean Validation and handled centrally via a global exception handler.

Example error response:
```json
{
  "symbol": "Symbol must not be blank",
  "condition": "Condition must be specified (ABOVE or BELOW)",
  "threshold": "Threshold must be a positive number"
}
```

### How to Run Tests
Run all tests:
```bash
./mvnw test
```

Run a specific test:
```bash
./mvnw -Dtest=AlertEvaluatorTest test
```

### Testing Strategy
- **AlertEvaluatorTest** – core alert triggering logic
- **AlertServiceTest** – alert lifecycle (create, evaluate, delete)
- **AlertControllerIntegrationTest** – API validation and global exception handling
- Market data streaming is verified manually to avoid brittle timing-based tests.

### Design Decisions
- In-memory storage to keep the solution simple and self-contained
- Synthetic market data to avoid external dependencies
- Console logging for notifications
- Explicit Spring bean wiring for clarity and testability

## Future Improvements

- Persist alerts using a database (e.g., PostgreSQL via Spring Data JPA)
- Support alert re-arming or true price-crossing semantics (track previous price per symbol)
- Externalize configuration for symbols, initial prices, and tick intervals (Spring `@ConfigurationProperties`)
- Remove hard-coded symbols and load supported instruments dynamically from configuration or a reference data service
- Add authentication and authorization (Spring Security with JWT)
- Add metrics and monitoring (Spring Boot Actuator + Micrometer)

### Real-Time Push Notifications

- Push alert notifications to clients instead of console-only logging
- Possible implementations:
  - **WebSocket-based push** using Spring WebSocket (`spring-boot-starter-websocket`)
  - **Server-Sent Events (SSE)** using Spring MVC for lightweight streaming
  - **Message broker–based fanout** using Redis Pub/Sub or Kafka for horizontal scalability
- This would allow clients (UI, mobile app, or other services) to subscribe to live alert events in real time


### Contact
**Author:** Zahid Hossain  
**Email:** zahid@quantdev.co.uk  
Web: https://quantdev.co.uk/ (Everything about me with AI based conversation)    
Other similar project (Rest-Swagger) at my other project: https://github.com/zhossainny/var-api  
Interested reading my Java Low-Latency book "Java: How Low Can You Go?" Find it in a book store or Amazon globally  i.e: https://amzn.eu/d/5JQJEUE 

