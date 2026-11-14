# Guide complet — Installation, lancement et tests

Ce guide décrit les étapes à exécuter sur un poste Windows. Les captures doivent être prises après exécution réelle du projet : les images ne sont pas simulées.

## 1. Prérequis

Installer :
- JDK 21
- Maven 3.9 ou plus
- Node.js LTS et npm
- Git
- Postman (facultatif, Swagger UI peut servir aux tests REST)

Vérifier dans PowerShell :

```powershell
java -version
mvn -version
node -v
npm -v
git --version
```

**Capture 01 — Environnement** : enregistrer une capture PowerShell montrant les versions avec le nom `docs/screenshots/01-environnement.png`.

## 2. Récupérer le projet

```powershell
git clone https://github.com/sanaetimourti/Architectures-Micro-services--Spring-Cloud---Spring-AI---MCP---Angular.git
cd Architectures-Micro-services--Spring-Cloud---Spring-AI---MCP---Angular
```

Si le dépôt est déjà présent, récupérer les derniers changements :

```powershell
git pull origin main
```

**Capture 02 — Dépôt** : VS Code ou l'explorateur montrant les modules du projet, à enregistrer sous `docs/screenshots/02-structure-projet.png`.

## 3. Compiler les modules

Depuis la racine du dépôt :

```powershell
mvn clean verify
```

La compilation doit finir par `BUILD SUCCESS`. Si elle échoue, lire la première erreur `[ERROR]` et ne pas passer aux tests d'intégration avant de la corriger.

**Capture 03 — Compilation** : résultat réel de Maven avec `BUILD SUCCESS`, sous `docs/screenshots/03-build-success.png`.

## 4. Démarrer les microservices

Ouvrir un terminal PowerShell par service. Exécuter chaque commande depuis la racine du dépôt, et laisser les terminaux ouverts.

Terminal 1 — Eureka :

```powershell
mvn -pl discovery-service spring-boot:run
```

Terminal 2 — Config Server :

```powershell
mvn -pl config-server spring-boot:run
```

Terminal 3 — Customer-Service :

```powershell
mvn -pl custumer-service spring-boot:run
```

Terminal 4 — E-Bank-Service :

```powershell
mvn -pl e-bank-service spring-boot:run
```

Terminal 5 — API Gateway :

```powershell
mvn -pl api-gateway spring-boot:run
```

Ports :
- Eureka : 8761
- Config Server : 8888
- Customer-Service : 8082
- E-Bank-Service : 8083
- API Gateway : 8880

Ouvrir http://localhost:8761 pour vérifier le tableau de bord Eureka. Les services configurés comme clients doivent apparaître dans la liste des instances après leur démarrage.

**Capture 04 — Eureka** : tableau de bord montrant les instances réellement enregistrées, sous `docs/screenshots/04-eureka.png`.

## 5. Tester Customer-Service

Swagger UI (si disponible dans la configuration du module) :
http://localhost:8082/swagger-ui/index.html

Avec PowerShell :

```powershell
Invoke-RestMethod http://localhost:8082/api/customers
Invoke-RestMethod http://localhost:8082/api/customers/1
```

Créer un client :

```powershell
$body = @{ name = "Sara Benali"; email = "sara.benali@example.com" } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri http://localhost:8082/api/customers -ContentType "application/json" -Body $body
```

Mettre à jour et supprimer un client en remplaçant l'identifiant :

```powershell
$body = @{ name = "Sara B."; email = "sara.b@example.com" } | ConvertTo-Json
Invoke-RestMethod -Method Put -Uri http://localhost:8082/api/customers/1 -ContentType "application/json" -Body $body
Invoke-RestMethod -Method Delete -Uri http://localhost:8082/api/customers/1
```

**Capture 05 — API clients** : requête GET et réponse JSON dans Postman ou Swagger, sous `docs/screenshots/05-customer-api.png`.

## 6. Tester E-Bank-Service et OpenFeign

Lister les comptes :

```powershell
Invoke-RestMethod http://localhost:8083/api/accounts
Invoke-RestMethod http://localhost:8083/api/accounts/customer/1
```

Créer un compte de démonstration :

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8083/api/accounts?customerId=1&accountType=COURANT&initialBalance=250.00"
```

Tester l'appel E-Bank vers Customer-Service via OpenFeign et le Circuit Breaker :

```powershell
Invoke-RestMethod http://localhost:8083/api/banking/customers/1
```

Si Customer-Service est arrêté, l'appel doit être géré par le fallback du Circuit Breaker. Cette vérification est à réaliser dans un environnement local de test uniquement.

**Capture 06 — OpenFeign** : réponse de l'endpoint bancaire, sous `docs/screenshots/06-openfeign.png`.
**Capture 07 — Fallback** : réponse de secours après arrêt contrôlé de Customer-Service, sous `docs/screenshots/07-circuit-breaker.png`.

## 7. Tester API Gateway

```powershell
Invoke-RestMethod http://localhost:8880/customer-service/api/customers
```

La Gateway doit transférer la requête vers le service découvert par Eureka.

**Capture 08 — Gateway** : réponse obtenue via le port 8880, sous `docs/screenshots/08-api-gateway.png`.

## 8. Démarrer et tester le chatbot Spring AI

Définir la clé API uniquement dans la session PowerShell. Ne jamais la placer dans un fichier suivi par Git :

```powershell
$env:OPENAI_API_KEY = "VOTRE_CLE_API"
mvn -pl ai-agent-service spring-boot:run
```

Tester l'API :

```powershell
$body = @{ message = "Bonjour, peux-tu m'aider à consulter un client ?" } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri http://localhost:8090/api/ai/chat -ContentType "application/json" -Body $body
```

Le modèle peut appeler les méthodes annotées `@Tool` pour interroger Customer-Service et E-Bank-Service. Ces outils nécessitent que les deux services cibles soient démarrés.

**Capture 09 — Chatbot** : requête et réponse réelles, sans afficher la clé API, sous `docs/screenshots/09-chatbot.png`.

## 9. Tester le serveur MCP

Le module IA inclut le starter MCP WebMVC et enregistre un fournisseur de callbacks basé sur les outils bancaires. Le point d'accès MCP et le protocole exact dépendent de la configuration effective de la version Spring AI retenue.

Démarrer le module IA, puis utiliser un client MCP compatible configuré avec le transport HTTP du serveur local. Vérifier que les outils de consultation client et comptes apparaissent et lancer un appel de test. Ne pas considérer le test comme réussi avant d'avoir obtenu une réponse du client MCP.

**Capture 10 — Client MCP** : liste réelle des outils et résultat d'un appel, sous `docs/screenshots/10-mcp-client.png`.

## 10. Lancer l'interface Angular

Dans un nouveau terminal :

```powershell
cd frontend-angular
npm install
npm start
```

Ouvrir http://localhost:4200. Envoyer un message dans l'interface. Le service IA doit être actif sur le port 8090.

**Capture 11 — Angular** : interface avec une conversation de test réelle, sous `docs/screenshots/11-angular-chat.png`.

## 11. Telegram et Discord

Les intégrations Telegram et Discord ne sont pas encore configurées dans ce dépôt. Elles nécessitent des comptes/bots, des tokens et des webhooks ou endpoints d'interaction. Les secrets doivent rester dans des variables d'environnement et ne doivent jamais être ajoutés au dépôt. Ne pas joindre de capture de ces intégrations avant qu'elles soient réellement développées et testées.

## 12. Historique Git

Les modifications du projet doivent être ajoutées par commits successifs. Ne pas utiliser `git reset --hard`, `git rebase` ou `git push --force` sur l'historique que l'enseignant doit consulter.

Vérifier l'historique :

```powershell
git log --oneline --decorate -15
git status
```

**Capture 12 — Historique Git** : terminal montrant les commits successifs, sous `docs/screenshots/12-git-history.png`.

## 13. Dossier des captures

Créer le dossier :

```powershell
New-Item -ItemType Directory -Force docs/screenshots
```

Enregistrer les captures indiquées dans les étapes précédentes avec les noms exacts. Masquer les clés API, tokens, adresses e-mail privées et autres données sensibles avant de partager les images.

## État de réalisation

- La structure multi-modules, les contrôleurs principaux, la configuration de découverte, la Gateway, les outils IA/MCP et le client Angular sont présents dans le dépôt.
- Les tests doivent être exécutés localement avec les prérequis et les services démarrés.
- La compilation GitHub Actions a signalé une erreur d'import d'annotations MCP ; une correction a été poussée et doit être confirmée par une nouvelle exécution réussie.
- Telegram et Discord restent à développer/configurer.
- Les captures listées sont des preuves à produire depuis une exécution réelle ; ce guide ne prétend pas qu'elles ont déjà été prises.
