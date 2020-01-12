# World

### world.getChunks()
*Gets all chunks in the bots world*
* Returns `List<ChunkColumn>`

####world.getEntities() 
*Gets all entities in the bots world*
* Returns `List<Entity>`

### world.getPlayers()
*Gets all players on the server*
* Returns `List<Player>`

### world.getBlock(int x, int y, int z)
*Gets the block at the location specific*
* `x` - Block's X coordinate
* `y` - Block's Y coordinate
* `z` - Block's Z coordinate
* Returns `Block`

### world.placeBlock(Bot bot, Vector3d blockLocation, Vector3d offset)
*Places block at specified location*
* `bot` - Bot that you want to place the block
* `blockLocation` - Location of the block to place off of
* `offset` - Vector of the offset *0, -1, 0 would place under*
* Returns `Void`