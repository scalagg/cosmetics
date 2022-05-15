package com.minexd.cosmetics.customizer

import com.minexd.cosmetics.category.emote.Emote
import com.minexd.cosmetics.category.emote.EmotesBoxCosmetic
import gg.scala.commons.acf.ConditionFailedException
import gg.scala.commons.annotations.commands.customizer.CommandManagerCustomizer
import gg.scala.commons.command.ScalaCommandManager
import net.evilblock.cubed.util.CC

/**
 * @author GrowlyX
 * @since 5/15/2022
 */
object CosmeticsCommandCustomizer
{
    @CommandManagerCustomizer
    fun customize(
        manager: ScalaCommandManager
    )
    {
        manager.commandContexts.registerContext(Emote::class.java) {
            var matchingEmote: Emote? = null
            val firstArg = it.popFirstArg()

            for (emote in EmotesBoxCosmetic.emotes)
            {
                if (emote.getId().equals(firstArg, ignoreCase = true))
                {
                    matchingEmote = emote
                    break
                }
            }

            if (matchingEmote == null)
            {
                throw ConditionFailedException("No emote matching ${CC.YELLOW}${firstArg}${CC.RED} exists.")
            }

            return@registerContext matchingEmote
        }
    }
}
