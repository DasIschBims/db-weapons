package dev.dasischbims.items

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.dasischbims.INSTANCE
import dev.dasischbims.customItemsMap
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.io.File

object CustomItems {

    data class CustomItem(val id: String, val material: String, val name: String, val lore: List<String>? = null, val cmd: Int? = null, val enchantments: HashMap<String, Int>? = null, val isUnbreakable: Boolean? = null)

    internal fun loadItems() {
        val file = File(INSTANCE.dataFolder.path + "/items.json")
        if(file.exists()){
            jacksonObjectMapper().readValue<HashMap<String, CustomItem>>(file).forEach { itemObj ->
                customItemsMap[itemObj.key] = itemObj.value.toItemStack()
            }
            println(customItemsMap)
        } else {
            file.createNewFile()
            jacksonObjectMapper().writeValue(file, hashMapOf<String, CustomItem>())
        }
    }

    private fun CustomItem.toItemStack(): ItemStack {
        val itemStack = ItemStack(Material.valueOf(material.uppercase()))
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(Component.text(name))
        val componentArray = ArrayList<Component>()
        lore?.forEach {
            componentArray.add(Component.text(it))
        }
        itemMeta.lore(componentArray)
        if (isUnbreakable != null && isUnbreakable != false) itemMeta.isUnbreakable = isUnbreakable
        if (cmd != null) itemMeta.setCustomModelData(cmd)
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    fun defaultCustomItem(m: Material, displayName: String, lore: MutableList<String>, cmd: Int=0, data: HashMap<String, String>?=null): ItemStack {
        val itemStack = ItemStack(m)
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(Component.text(displayName))
        val componentArray = ArrayList<Component>()
        lore.forEach {
            componentArray.add(Component.text(it))
        }
        itemMeta.lore(componentArray)
        itemMeta.setCustomModelData(cmd)
        data?.forEach {
            itemMeta.persistentDataContainer.set(NamespacedKey(INSTANCE, it.key), PersistentDataType.STRING, it.value)
        }
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}