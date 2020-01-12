# Events

## ChatEvent(String message, Bot bot)
When the chat event fires, there will be a string passed in as the direct message given from the server (Might be JSON)

## DisconnectEvent(Bot bot, String reason)
Disconnect event fires, when the bot gets disconnected from the server. If there's a reason for the disconnnect, it'll pass as a string

## LoginEvent(Bot bot)
Login event fires whenever the bot logs on to the server

## NewEntityEvent(Bot bot, Entity entity)
NewEntity event fires when a new entity gets loaded into the bots world.

## NewPlayerEvent(Bot bot, Player player)
NewPlayer event fires whenever a new player logs on (Or the bot just logged on)

## TeleportEvent(Bot bot)
Teleport event gets called whenever the **server** changes the position of the bot forcefully
