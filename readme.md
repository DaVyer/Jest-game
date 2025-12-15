# Lo02 : Jest
#### Tristan CREMONA, Gwendal RODRIGUES

Bienvenue dans notre projet de jeu JEST réalisé dans le cadre de la matière Lo02, de notre cursus à l'UTT.
Ce jeu est un jeu de cartes, je vous incite, si vous ne les connaissez pas, à lire les règles, présentes dans le dossier : "image/PROJET LO02 A25.pdf"

## Prérequis : 
Avant de lancer notre jeu, assurez vous d'avoir la version 25 de java.
## Comment Lancer le jeu : 
- 1) Compiler le jeu
    - Ouvrez un éditeur de commande, utilisant JAVA
    - Rendez vous dans le dossier d'origine 'JEST-GAME'
    - Puis taper la ligne suivante dans
`javac src/Main.java src/classe/*.java`

- 2) Lancer les fichiers 
`java -cp src Main`

Le terminal devrait vous renvoyer : 
```
================================
        BIENVENUE DANS JEST      
================================
```
Cela signifie que le jeu s'est bien lancé

## Commandes disponibles : 
### À tout moment de la partie : 
- `help` : Permet d'acceder à l'aide des commandes
- `exit` : Permet de quitter à tout moments

### Pendant la partie
- `load` : Permet de lancer une partie sauvegardée
- `new` : Permet de lancer une nouvelle partie
- `manche` : Permet de lancer une nouvelle manche, au début de la partie, ou à la fin d'une manche.
- `save` : Permet d'enregistrer l'état d'une partie
- `status` : Permet de connaitre le status de la partie (joueurs, nombre de carte dans la pioche....)
- `h` : Permet, à la création des joueurs, d'indiquer que le joueur est humain
- `r` : Permet, à la création des joueurs, d'indiquer que le joueur est un robot
- `0|1|2....` : Permet de choisir la carte ou l'offre indiquée