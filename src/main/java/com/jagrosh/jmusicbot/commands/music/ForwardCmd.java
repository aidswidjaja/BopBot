/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
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
package com.jagrosh.jmusicbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.commands.MusicCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

/**
 *
 * @author aidswidjaja <adrian.id.au>
 */
public class ForwardCmd extends MusicCommand
{
    public ForwardCmd(Bot bot)
    {
        super(bot);
        this.name = "forward";
        this.help = "scrub forward in the currently playing track";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
    }

    @Override
    public void doCommand(CommandEvent event)
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        Message m = handler.getNowPlaying(event.getJDA());
        if(m==null)
        {
            event.reply(handler.getNoMusicPlaying(event.getJDA()));
            bot.getNowplayingHandler().clearLastNPMessage(event.getGuild());
        }
        else
        {
            event.reply(m, msg -> bot.getNowplayingHandler().setLastNPMessage(msg));
        }
    }
}
