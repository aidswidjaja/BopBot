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
package com.jagrosh.jmusicbot.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.audio.QueuedTrack;
import com.jagrosh.jmusicbot.commands.DJCommand;
import com.jagrosh.jmusicbot.utils.FormatUtil;
import com.jagrosh.jmusicbot.utils.TimeUtil;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class PlaySAOCmd extends DJCommand
{
    private final String loadingEmoji;

    public PlaySAOCmd(Bot bot)
    {
        super(bot);
        this.loadingEmoji = bot.getConfig().getLoading();
        this.name = "initiate_pain";
        this.help = "my favourite anime opening (volume warning)";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.beListening = true;
        this.bePlaying = false;
    }
    
    @Override
    public void doCommand(CommandEvent event)
    {
        String args = "https://www.youtube.com/watch?v=w5PPqFF9arY"; //
        event.reply(loadingEmoji+" Loading... `["+args+"]`", m -> bot.getPlayerManager().loadItemOrdered(event.getGuild(), args, new ResultHandler(m,event,false)));
    }
    
    private class ResultHandler implements AudioLoadResultHandler
    {
        private final Message m;
        private final CommandEvent event;
        private final boolean ytsearch;
        
        private ResultHandler(Message m, CommandEvent event, boolean ytsearch)
        {
            this.m = m;
            this.event = event;
            this.ytsearch = ytsearch;
        }
        
        private void loadSingle(AudioTrack track)
        {
            if(bot.getConfig().isTooLong(track))
            {
                m.editMessage(FormatUtil.filter(event.getClient().getWarning()+" This track (**"+track.getInfo().title+"**) is longer than the allowed maximum: `"
                        + TimeUtil.formatTime(track.getDuration())+"` > `"+ TimeUtil.formatTime(bot.getConfig().getMaxSeconds()*1000)+"`")).queue();
                return;
            }

            if (event.getAuthor().getId().equals("376232547631497217")) {
                String addMsg = FormatUtil.filter(event.getClient().getError()+" \uD83D\uDE20\uD83D\uDE20\uD83D\uDE20  naughty justin-kun is not allowed to use this command https://i.gifer.com/B7sk.gif");

                m.editMessage(addMsg).queue();
            } else {
                AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
                int pos = handler.addTrackToFront(new QueuedTrack(track, event.getAuthor()))+1;
                String addMsg = FormatUtil.filter(event.getClient().getSuccess()+" F#%$ EVERYONE WHO THINKS SWORD ART ONLINE IS BAD YOU'RE JUST AN ELITIST F#%$ WHO LIKE TO HATE ON EVERY ANIME THEY WATCHED AND TRIGGER A VERY ANGELIC PERSON LIKE ME. YOU JUST DON'T GET THE FULL MEANING OF THE SHOW AND YOU ONLY LIKE TO HATE ON PEOPLE BECAUSE YOU'RE A BAD GUY AND NEEDS TO GO TO HELL. the show is so good that I call this anime the Cowboy Bebop of anime another masterpiece of the same liking but sword art online is better. WHY THE F#%$ YOU HATE IT SO MUCH EVEN THOUGH IT'S A MASTERPIECE OF A SHOW ??. maybe because YOU DON'T GET THE FULL MEANING AND THE SHOW AND THE AMOUNT OF DETAIL EACH CHARACTER IS GIVEN JUST LOOK AT KIRITO IS SO OP THAT EVERY WANTS TO BE LIKE HIM WELL WHO WOULD BE?. Who would become a badass guy defeating all the boses easily and live in a fantasy world full of swords, magic, and everything fantasy related things, and have a harem that want to have sex with you all day all night. SO IF YOU DON'T. THEN YOU JUST DON'T WANNA LIVE PORTRAY YOURSELF TO KIRITO AND HAVE A PERFECT F#%$ing LIFE. AND ANOTHER CHARACTER IS ASUNA. ASUNA IS SO [censored] THAT YOU WISH YOU WANT TO BE like your girlfriend she is so kind lovely sweet and will always be with you no matter what. So WHY DO YOU GUYS HATE IT SO MUCH \uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D YOU GUYS ARE MEAN AND ONLY PEOPLE WITH LOW IQ DON'T GET THE SHOW AND PEOPLE WITH HIGH IQ THINKS THIS ANIME IS A MASTERPIECE, SO F#%$ YOU PEOPLE WHO HATE THE SHOW. https://tenor.com/view/kirito-excalibruh-sao-sword-art-online-kain-gif-22905097");
                m.editMessage(addMsg).queue();
            }
        }
        
        @Override
        public void trackLoaded(AudioTrack track)
        {
            loadSingle(track);
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist)
        {
            AudioTrack single;
            if(playlist.getTracks().size()==1 || playlist.isSearchResult())
                single = playlist.getSelectedTrack()==null ? playlist.getTracks().get(0) : playlist.getSelectedTrack();
            else if (playlist.getSelectedTrack()!=null)
                single = playlist.getSelectedTrack();
            else
                single = playlist.getTracks().get(0);
            loadSingle(single);
        }

        @Override
        public void noMatches()
        {
            if(ytsearch)
                m.editMessage(FormatUtil.filter(event.getClient().getWarning()+" No results found for `"+event.getArgs()+"`.")).queue();
            else
                bot.getPlayerManager().loadItemOrdered(event.getGuild(), "ytsearch:"+event.getArgs(), new ResultHandler(m,event,true));
        }

        @Override
        public void loadFailed(FriendlyException throwable)
        {
            if(throwable.severity==FriendlyException.Severity.COMMON)
                m.editMessage(event.getClient().getError()+" Error loading: "+throwable.getMessage()).queue();
            else
                m.editMessage(event.getClient().getError()+" Error loading track.").queue();
        }
    }
}
