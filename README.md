# RandomItemXP Plugin

## Beschreibung

Das **RandomItemXP** Plugin für **Minecraft** fügt ein zufälliges Item-System hinzu, das auf den Leveln eines Spielers basiert. Wenn ein Spieler ein "Randomizer"-Item verwendet, werden zufällige Items basierend auf seiner Erfahrung generiert. Das Plugin bietet zudem die Möglichkeit, nur vom Spieler platzierte Blöcke zu droppen, während andere Blöcke nicht mehr droppen, wenn sie zerstört werden.

## Features

- **Randomizer-Item:** Ein spezielles Item, das dem Spieler zufällige Items basierend auf seiner XP (Erfahrung) gewährt.
- **Blöcke:** Nur vom Spieler platzierte Blöcke droppen Items, während andere Blöcke keine Items mehr abwerfen.
- **Mob-Drops:** Alle Mob-Drops werden entfernt, wenn das Plugin aktiviert ist.

## Installation

1. Lade das Plugin herunter.
2. Platziere die `.jar`-Datei im `plugins`-Ordner deines Minecraft-Servers.
3. Starte den Server neu, um das Plugin zu aktivieren.

## Verwendung

- **/getRandomizer:** Dieser Befehl gibt dem Spieler das "Randomizer"-Item, das verwendet werden kann, um zufällige Items zu erhalten.
- **/startlevelrandomizer:** Dieser Befehl startet das Plugin und dekativiert dementsprechend Block und Mob Drops
- **/stoplevelrandomizer:** Dieser Befehl deaktiviert das Plugin und aktiviert Block und Mob Drops wieder

### Wie funktioniert der Randomizer?

1. Der Spieler kann das "Randomizer"-Item verwenden (ein Stick), um zufällige Items basierend auf seiner Erfahrung zu erhalten.
2. Jeder benutzte Stick gibt dem Spieler zufällige Items und entfernt dabei seine Erfahrung.

### Block-Drop-Regel

- Nur Blöcke, die von einem Spieler platziert wurden, werden bei der Zerstörung Items droppen.
- Blöcke, die auf andere Weise platziert wurden, droppen keine Items mehr.
