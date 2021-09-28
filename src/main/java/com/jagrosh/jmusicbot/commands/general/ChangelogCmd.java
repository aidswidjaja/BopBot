/*
 * Copyright 2017 John Grosh <john.a.grosh@gmail.com>.
 * Copyright 2021 aidswidjaja <adrian.id.au>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.settings.Settings;
import com.jagrosh.jmusicbot.utils.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 *
 * @author aidswidjaja <adrian.id.au>
 */
public class ChangelogCmd extends Command
{

    public ChangelogCmd(Bot bot)
    {
        this.name = "changelog";
        this.help = "what's new with BopBot?";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        MessageBuilder builder = new MessageBuilder();

        // yummy hardcoded changelog!!!

        EmbedBuilder ebuilder = new EmbedBuilder()
                .setColor(event.getSelfMember().getColor())
                .setTitle("\uD83E\uDD54 Check out what's new with BopBot") // 🥔
                .setDescription("• Added new aliases for existing commands which can be found on [our website](https://bopbot.adrian.id.au)" +
                        "\n • Added the new `stop` command which clears the queue and stops currently playing music, but doesn't disconnect the bot" +
                        "\n • See what's new on BopBot with the new `changelog` command (yep this one!)" +
                        "\n • Fixed the bass boost feature, which lets you add bass boost to any track with the new `bassboost` command" +
                        "\n\n**Check out our website at [bopbot.adrian.id.au](https://bopbot.adrian.id.au)**")
                .setFooter("You're using version 1.1.3 of BopBot" +
                        "\nFeature requests can be messaged to aidswidjaja#2805");
        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
    }
    
}

// to-do: allow server owners to enable/disable on a server-specific basis. reuse prefix logic and enable if-else switches for the ultimate if-else nest
