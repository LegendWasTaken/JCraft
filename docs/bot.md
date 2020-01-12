# Bot

## new Bot(String username, String host, Integer port)
*Creates a bot without mojang authentication*
* `username` - Username you want the bot to use
* `host` - Target server's IP
* `port` Target server's port
* Returns `Bot`

## new Bot(String email, String password, String host, Integer port)
*Creates a bot with mojang authentication*
* `email` - Minecraft account's email
* `password` - Minecraft accounts's password
* `host` - Target server's IP
* `port` Target server's port
* Returns `Bot`

## bot.connect()
*Connect the bot to the server*
* Returns `void` 

## bot.disconnect(String reason)
*Disconnect the bot from the server*
* `reason` - The reason for why the bot disconnected
* Returns `void`

## bot.chat(String message)
*Sends a chat message from the bot account*
* `message` - The message that you want to be sent
* Returns `void`

