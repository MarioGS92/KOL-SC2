# King Of Ladder - Starcraft II

## 1. Introducción
Backend para la competición de KOL-SC2. Recibe información de los players y devuelve su posición en el ladder de 1v1.

Requisitos:
- Java 17 o superior.
- Claves de acceso API Battle.net

## 2. Arranque

KOL es un servicio de Spring Boots para Java.
    
    java -jar kol.jar --kol.oauth.clientId={idCliente} --kol.oauth.clientSecret={secretCode}

O por docker:

### 2.1. Argumentos
KOI requiere una ID y clave API de Battle.net. Estos deberán ser introducidos como argumentos en la línea de comandos:

- kol.oauth.clientId
- kol.oauth.clientSecret

## 3. API

### 3.1. Obtener perfil de un jugador
    POST /api/profile

- Entrada:
    - Body: URL de perfil de battle.net
- Salida:
    - Profile

### 3.2. Obtener resumen de ladder de un jugador
    POST /api/profile/ladder/summary

- Entrada:
    - Body: URL de perfil de battle.net
- Salida:
    - LadderSummary

### 3.3. Obtener un ladder asociado a un jugador
    POST /api/profile/ladder/{ladderId}

- Entrada:
  - Body: URL de perfil de battle.net
  - {ladderId}: ID del ladder de StarCraft II a obtener
- Salida:
  - LadderTeams

### 3.4. Obtener información agrupada de rango y perfil de un jugador
    POST /api/profile/grouped
- Entrada:
    - Body: URL de perfil de battle.net
- Salida:
    - Player

### 3.4. Obtener información agrupada de rango y perfil de varios jugadores
    POST /api/profile/grouped/list
- Entrada:
    - Body: Lista de URL de perfil de battle.net
- Salida:
    - Player[]

// *Nota: Sí... son métodos POST... no son GET... ya... y??? Y??!!!. Y tan ancho que me he quedado haciendolo.*
