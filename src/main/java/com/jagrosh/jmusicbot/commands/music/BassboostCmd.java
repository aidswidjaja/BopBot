/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
 * Copyright 2021 aidswidjaja <aidswidjaja.github.io>.
 *
 * This class has been licensed under GPL v3.0 as it constitutes a
 * derivative work from bjm021/momobot, available at
 * https://github.com/bjm021/momobot/
 *
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.jagrosh.jmusicbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.audio.PlayerManager;
import com.jagrosh.jmusicbot.commands.MusicCommand;
import com.jagrosh.jmusicbot.settings.Settings;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.Permission;

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;
import net.dv8tion.jda.api.entities.Guild;

/**
 *
 * @author aidswidjaja (adrian.id.au)
 */
public class BassboostCmd extends MusicCommand
{

    public BassboostCmd(Bot bot)
    {
        super(bot);
        this.name = "bassboost";
        this.arguments = "[on|off]";
        this.help = "toggles bass boost on/off";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = true;
    }

    public static final float[] BASS_BOOST = {0.2f, 0.15f, 0.1f, 0.05f, 0.0f, -0.05f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f};

    public static EqualizerFactory equalizer;

    boolean bassBoost = false;

    @Override
    public void doCommand(CommandEvent event)
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        this.equalizer = new EqualizerFactory();

        handler.getPlayer().setFilterFactory(equalizer);

        if(event.getArgs().isEmpty())
        {
            if (bassBoost) {
                event.reply("\uD83D\uDC1F  Bass boost is currently **on**! Use `-bassboost on/off` to toggle it."); // 🐟

            }
            else
            {
                event.reply("\uD83C\uDFA3  Bass boost is currently **off**! Use `-bassboost on/off` to toggle it."); // 🎣
            }
        }
        else if(event.getArgs().equalsIgnoreCase("true") || event.getArgs().equalsIgnoreCase("on")) // turns bass boost on
        {
            if (bassBoost) { // if bass boost is already on, then tell the user
                event.reply("\uD83D\uDC1F  Bass boost is already **on!!** (rip your ears...)"); // 🐟
            }
            else // user asks for bass boost to turn on so let's give the people what they want
            {
                event.reply("\uD83D\uDC1F  Turning bass boost **on**! It might take a couple seconds..."); // 🐟

                for (int i = 0; i < BASS_BOOST.length; i++) {
                    equalizer.setGain(i, BASS_BOOST[i] + 2);
                }

                for (int i = 0; i < BASS_BOOST.length; i++) {
                    equalizer.setGain(i, -BASS_BOOST[i] + 1);

                }

                bassBoost = true;
            }
        }
        else if (event.getArgs().equalsIgnoreCase("false") || event.getArgs().equalsIgnoreCase("off")) // turns bass boost off
        {
            if (bassBoost) { // if bass boost is already on, then turn it off
                event.reply("\uD83C\uDFA3 Turning bass boost **off**. It might take a couple seconds..."); // 🎣
                handler.getPlayer().setFilterFactory(null);

                bassBoost = false;
            }
            else
            { // bass boost is already off, so tell the user
                event.reply("\uD83C\uDFA3 Bass boost is already **off**."); // 🎣
            }
        }
    }
}
