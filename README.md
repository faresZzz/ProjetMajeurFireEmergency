# ProjetMajeurFireEmergency



## Démarrage

Afin de demarrer notre projet depuis Eclipse, vous devez démarre le service SPAppFire en dernier. Car c'est celui-ci qui va initialiser les premieres requêtes, il a donc besoins que les autres services soit opérationnels afin de pouvoir y répondre.

## Port Communication LocalHost entre services

- Fire : 8080
- Vehicle : 8081
- Tower : 8082
- Facility : 8083


## Ensemble des endpoints

### Micro service SPAppFire

|   Methode     |       URI            |     Body          |    Valeur de retour    |
| :-----------: |   :-----------:      |   :-----------:   |    :-----------:       |
|   GET         |       /fires         |    None           |    Fire[]              |
|   GET         |       /fire/{idFire} |    None           |    Fire                |


### Micro service Tower

|   Methode     |           URI             |       Body        |   Valeur de retour    |
| :-----------: |       :-----------:       |   :-----------:   |   :-----------:       |
|   POST        |       /fireStarted        |    FireDto        |       void            |
|   PUT         |       /endFire/{idFire}   |    None           |       void            |
|   POST        |       /initTower          |    Fire[]         |       bool            |
|   PUT         |       /endAll             |    None           |       bool            |


### Micro service Facility

|   Methode     |           URI                 |       Body        |   Valeur de retour    |
| :-----------: |       :-----------:           |   :-----------:   |   :-----------:       |
|   POST        |       /handleFire/{idFacility}|    FireDto        |       bool            |
|   PUT         |       /endFire/{idFire}       |    None           |       void            |


### Micro service facility

|   Methode     |           URI                 |       Body        |   Valeur de retour    |
| :-----------: |       :-----------:           |   :-----------:   |   :-----------:       |
|   POST        |       /newMission/{idVehicle} |    FireDto        |       bool            |





## Video de presentation 
Lien vers notre video de presentation [ici](https://www.youtube.com/watch?v=-UT4rrQrz84)

## Membres de l'équipe
+ Maxime CURRAL
+ Romain GALMIER
+ Romain GAUD
+ Farès ZAGHOUANE 