/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
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
package com.jagrosh.jmusicbot.commands.admin;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.AdminCommand;
import com.jagrosh.jmusicbot.settings.Settings;

/**
 *
 * @author aidswidjaja (adrian.id.au)
 */
public class BassboostEnabledCmd extends AdminCommand
{
    public BassboostEnabledCmd(Bot bot)
    {
        this.name = "bbadmin";
        this.help = "info on bass boost";
        this.arguments = "";
        this.aliases = bot.getConfig().getAliases(this.name);
    }
    
    @Override
    protected void execute(CommandEvent event)
    {

            event.reply("\uD83D\uDC1F Bass boosting is enabled by default across servers. Since the code this was forked from is a total pain to edit (like seriously java's only existential purpose is to be used to produce spaghetti code) there is no automatic way to disable bass boosting on a server-wide basis. Instead, if you would like bass boosting disabled in your server, please message aidswidjaja#2805. Thanks for your understanding!");

    }
}
