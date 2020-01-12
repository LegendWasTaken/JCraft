# Inventory
    
### inventory.getItem(int slot)
*Gets item at the slot index (Remember it starts at 0)*
* `slot` - Slot number
* Returns `ItemStack`

### inventory.setHeldItem(int slot, Bot bot)
*Set the hotbar slot item*
* `slot` - Hotbar slot (0 - 8)
* `bot` - Bot that you want to change slot
* Returns `void`

### inventory.getHeldItem()
*Returns the item that is in the bots hand*
* Returns `ItemStack`

### inventory.dropAllItems(Bot bot, boolean fast)
* `bot` - Bot that you want to drop all items
* `fast` - If there should be a delay between dropping the items
* Returns `void`

