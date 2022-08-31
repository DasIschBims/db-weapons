package dev.dasischbims.items

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.dasischbims.INSTANCE
import dev.dasischbims.itemStringToItem
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import java.io.File

object CraftingRecipes {
    data class CustomRecipe(val result: String, val shape: List<String>, val ingredients: HashMap<Char, String>)

    internal fun loadRecipes() {
        val file = File(INSTANCE.dataFolder.path + "/recipes.json")
        if (file.exists()) {
            val customRecipes = jacksonObjectMapper().readValue<HashMap<String, CustomRecipe>>(file)
            customRecipes.forEach { (name, recipeObj) ->
                val namespacedKey = NamespacedKey(INSTANCE, name)
                val recipe = ShapedRecipe(namespacedKey, itemStringToItem(recipeObj.result))
                val shape = recipeObj.shape
                try {
                    recipe.shape(shape[0], shape[1], shape[2])
                    recipeObj.ingredients.forEach {
                        recipe.setIngredient(it.key, itemStringToItem(it.value))
                    }
                    Bukkit.addRecipe(recipe)
                } catch (ex: ArrayIndexOutOfBoundsException) {
                    println("Out of index at recipe $name")
                }
            }
        } else {
            file.createNewFile()
            jacksonObjectMapper().writeValue(file, HashMap<String, CustomRecipe>())
        }
    }
}