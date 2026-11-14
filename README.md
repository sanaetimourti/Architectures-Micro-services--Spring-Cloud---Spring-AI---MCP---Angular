# EBank Microservices Platform

Projet pédagogique basé sur Spring Boot, Spring Cloud, OpenFeign, Resilience4j, Eureka, API Gateway, Spring AI et MCP.

## Modules

| Module | Port | Rôle |
|---|---:|---|
| discovery-service | 8761 | Registre Eureka |
| config-server | 8888 | Configuration centralisée (profil native) |
| customer-service (dossier `custumer-service`) | 8082 | API CRUD clients + H2 |
| e-bank-service | 8083 | Service bancaire, OpenFeign et Circuit Breaker |
| api-gateway | 8880 | Gateway avec découverte dynamique |
| ai-agent-service | 8090 | API chatbot Spring AI et dépendance MCP WebMVC |

## Prérequis

- Java 21
- Maven 3.9+
- Une clé API OpenAI uniquement si le module IA est utilisé.

Ne jamais enregistrer de clé API dans Git. Définir la variable d'environnement `OPENAI_API_KEY` dans le terminal avant de lancer le module IA.

## Compilation

Depuis la racine :

```bash
mvn clean verify
```

## Démarrage (terminaux séparés)

Lancer dans cet ordre :

```bash
mvn -pl discovery-service spring-boot:run
mvn -pl config-server spring-boot:run
mvn -pl custumer-service spring-boot:run
mvn -pl e-bank-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Pour le module IA, définir `OPENAI_API_KEY`, puis :

```bash
mvn -pl ai-agent-service spring-boot:run
```

## Tests REST

- Eureka : http://localhost:8761
- Customer : `GET http://localhost:8082/api/customers`
- Customer by ID : `GET http://localhost:8082/api/customers/1`
- Create customer : `POST http://localhost:8082/api/customers` avec JSON `{"name":"Sara","email":"sara@example.com"}`
- E-Bank via Feign + Circuit Breaker : `GET http://localhost:8083/api/banking/customers/1`
- Comptes bancaires : `GET http://localhost:8083/api/accounts`, `GET http://localhost:8083/api/accounts/customer/1`, `POST http://localhost:8083/api/accounts?customerId=1&accountType=COURANT&initialBalance=250.00`
- Swagger Customer : http://localhost:8082/swagger-ui/index.html
- Swagger E-Bank : http://localhost:8083/swagger-ui/index.html
- Gateway discovery route : `GET http://localhost:8880/customer-service/api/customers`
- AI chat : `POST http://localhost:8090/api/ai/chat` avec JSON `{"message":"Bonjour"}`

## MCP

Le module IA expose les outils MCP `find_customer`, `list_customer_accounts` et `get_banking_customer`. Le transport MCP HTTP est fourni par le starter Spring AI MCP WebMVC. Les outils interrogent les API locales Customer et E-Bank.

## Sécurité

Les clés, tokens Telegram/Discord et autres secrets doivent être fournis par variables d'environnement ou gestionnaire de secrets. Ne pas les committer.

## Angular interface

Dans un autre terminal :

```bash
cd frontend-angular
npm install
npm start
```

Ouvrir http://localhost:4200. L'interface appelle le endpoint REST du module IA. Les notifications Telegram/Discord ne sont pas configurées dans le dépôt : elles nécessitent les identifiants de bots et des URL de webhook fournis par leur propriétaire.
