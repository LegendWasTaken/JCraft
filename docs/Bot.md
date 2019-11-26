# Bot Documentation
 
  ## World
  The world is currently the **most** advanced feature in this library. Extensive documntation is written on it.
  
  ### world.getBlock(Vector3D location)
  * `location` - The absolute location in the world, that you want to get the block data of.
  * Returns a `Block` with the information of the block @ the location in the world.
  
  ### world.placeBlock(Block placeBlock, Direction placeDirection)
  * `placeBlock` - The block the bot will attempt to place off of.
  * `placeDirection` - The direction the bot will attempt to place the block on.
  
  ## Location
  The location handling is **somewhat** basic. It can do most things that people will need, but there are 100% missing features. To [contribute](https://github.com/LegendWasTaken/JCraft/blob/master/docs/README.md) read the requirements on the README.

  ### location.look(Block block) *#In development*
  * `block` - The block you want bot to look at

  ### location.look(Direction direction)
  * `direction` - The direction you want the player to look at

  ### location.look(float yaw, float pitch)
  * `yaw` - The yaw you want the player to look at
  * `pitch` - The pitch you want the player to look at
  
  ### location.getYaw()
  * Returns a `float` of the bots current yaw.
  
  ### location.getPitch()
  * Returns a `float` of the bots current pitch.
  
  ### location.getX()
  * Returns a `double` of the bots current X location.
  
  ### location.getY()
  * Returns a `double` of the bots current Y location.
  
  ### location.getZ()
  * Returns a `double` of the bots current Z location.
  
  ## Console
  Console is the bots console, this is where you apply all debug information. This is constantly being improved, and worked on.
  
  ### console.log(String message)
  * `message` - The message that gets logged, with the default logging scheme. `[HH:MM:SS INFO - ${username}] ${message}` Username being the bots username
  
  ### console.debug(String message)
  * `message` - The message that gets debugged, with the default debugging scheme. `[HH:MM:SS DEBUG - ${username}] ${message}` Username being the bots username
  
  ### console.setLogging(Boolean to)
  * `to` - Set if the bots console, should be outputting log messages
  
  ### console.setDebugging(Boolean to)
  * `to` - Set if the bots console, should be outputting debug messages
  
  ### console.isLogging()
  * returns a `Boolean` of if the bots console will currently send logs
  
  ### console.isDebugging()
  * returns a `Boolean` of if the bots console will currently send debug messages
  
  ## Server 
  This is the bots server, this will have a lot of features that have to do with the server. *#All of these are planned, and not yet implemented*
  
  ### server.getAllPlayers()
  * returns a `Player[]` of all of the players currently online on the server.
  
  ### server.getPlayerAmount()
  * returns an `Integer` of how many players are currently online.
  
  ### server.getDefaultGamemode()
  * returns a `String` of the default game mode
  
  ## Chat
  Chat is, well. Chat, there's not much to be done here. If you find anything that can be added. Refer to Refer to [this](https://github.com/LegendWasTaken/JCraft/blob/master/docs/README.md) on requirements, and insutrctions on how to contribute.
  
  ### chat.sendMessage(String message)
  * `message` - The message that you want to send
  
  ## Inventory
  The inventory management is **very** basic, and can be expanded on with relative ease. Refer to [this](https://github.com/LegendWasTaken/JCraft/blob/master/docs/README.md) on requirements, and insutrctions on how to contribute.
  
  ### inventory.setHeldSlot(int slot)
  * `slot` - The slot that you want to set as the current held slot
  
  ### inventory.getItemAtSlot(int slot)
  * Return an `ItemStack` of the item in the hotbar slot you specify
  
  ### inventory.getItemAt(int slot)
  * Returns an `ItemStack` of the item at the slot you specify *#In development with extra windows 
  
