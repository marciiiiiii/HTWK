# MIB-F Code Conventions

Es wurde sich auf die folgenden Konventionen bei der Programmierung geeinigt. Stand 11.12.2022

## 1. Formatierung
- Blockanfang allein in einer Zeile
- pro Zeile maximal eine Anweisung
- stark zusammenhängende Codeabschnitte dürfen keine Leerzeilen enthalten
- zwei Leerzeilen nach jeder Methode
- Klassenaufbau: Attribute -> Methoden -> Getter/Setter

## 2. Namensgebung
- Bezeichner müssen selbsterklärend sein
- Abkürzungen sind mit sorgfalt zu wählen
- keine Zahlen in Bezeichnern (ausgenommen sind Teile von Animationen)
- englische Bezeichner
- Bezeichner von Bool-Variabeln sollen einen Zustand ausdrücken der wahr oder falsch sein kann
- Methoden/Attribute/Variabeln im camelCase
- Klassennamen im CamelCase

## 3. Größenbeschränkungen
- maximale Verschachtelungstiefe: 4
- Lines Of Code im optimalfall 20 pro Mehtode (Game Haupmethode ausgenommen)

## 4. Kommentare
### 4.1 Allgemein
- Kommentare und Dokumentation sind kurz und aussagekräftig zu verfassen
- innerhalb von Methoden sollte zur Übersichtlichkeit kommentiert werden, was abschnittsweise passiert
### 4.2 Klassen
- @brief (Kurzbeschreibung)
- @detail (Detailbeschreibung)

### 4.3 Methoden/Funktionen
- @brief (Kurzbeschreibung)
- @detail (Detailbeschreibung; nur bei komplexen Methoden notwendig)
- @param (alle zu übergebenden Parameter)
- @return (Rückgabewert)

### 4.4 Getter/Setter
- Dokumentation minimal
