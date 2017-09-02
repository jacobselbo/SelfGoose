package bot;

import bot.commands.*;
import bot.entities.Config;
import bot.entities.Emojis;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.ZoneId;

public class Listener extends ListenerAdapter {
    private String prefix;
    private ZoneId timezone;
    public static Command[] commands;
    public Emojis emojis;

    public Listener(Config config) {
        emojis = new Emojis();
        this.prefix = config.getPrefix();
        this.timezone = config.getZoneId();
        commands = new Command[]{
                new PingCmd(),
                new TimeCmd(timezone),
                new JavaCmd(),
                new GameCmd(),
                new ReactCmd(),
                new SayCmd(),
                new PollCmd(),
                new FlipCmd(),
                new CmdsCmd(prefix),
                new InfoCmd(),
                new ShootCmd(),
                new KickCmd(),
                new AfkCmd(),
                new EvalCmd(),
                new AvatarCmd(config),
                new Cal(),
                new NickCmd(config),
                new GifCmd(),
                new MemeCmd(),
                new SpamCmd(),
                new EmoteCmd(),
                new EmoteListCmd(emojis),
                new SetCmd(emojis),
                new DeleteCmd(emojis),
                new QuoteCmd(),
                new isfatcmd(config)
        };
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        boolean isCommand = false;
        if(event.getMessage().getRawContent().startsWith(prefix)) {
            String[] args = event.getMessage().getRawContent().substring(prefix.length()).trim().split("\\s+",2);
            if(args.length < 2) args = new String[]{args[0], ""};
            for(Command command : commands) {
                if(command.name.equalsIgnoreCase(args[0])) {
                    command.run(args[1],event);
                    isCommand = true;
                }
            }
            if (!isCommand) {
                event.getMessage().editMessage("❌ ``"+args[0]+"`` isn't a command").queue(m -> {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    m.delete().queue();
                });
            }
        } else if(event.getMessage().getRawContent().startsWith("selfhelp")) {
            event.getMessage().delete().queue();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Selfbot help", null, null);
            StringBuilder sbuilder = new StringBuilder("SelfGoose is a bot made in Java using the JDA library\n");
            sbuilder.append("This bot was made by **froghopperjacob**\n");
            sbuilder.append("The current prefix is `"+prefix+"`\n");
            builder.setDescription(sbuilder.toString());
            builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+Bot.version, event.getJDA().getSelfUser().getAvatarUrl()==null ? event.getJDA().getSelfUser().getDefaultAvatarUrl() : event.getJDA().getSelfUser().getAvatarUrl());
            event.getChannel().sendMessage(new MessageBuilder().setEmbed(builder.build()).build()).queue();
        }
        if(!isCommand) {
            StringBuilder builder = new StringBuilder();
            String content = event.getMessage().getRawContent();
            while(true)
            {
                int index1 = content.indexOf(":");
                int index2 = content.indexOf(":", index1+1);
                if(index2==-1)
                    break;
                String emoji = emojis.getEmoji(content.substring(index1+1, index2));
                if(emoji==null)
                {
                    builder.append(content.substring(0, index2));
                    content = content.substring(index2);
                }
                else
                {
                    builder.append(content.substring(0, index1));
                    builder.append(emoji);
                    content = content.substring(index2+1);
                }
            }
            builder.append(content);
            content = builder.toString();
            if(!content.equals(event.getMessage().getRawContent())) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setThumbnail(content);
                event.getMessage().editMessage(embedBuilder.build()).queue();
            }
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Bot Loaded");
    }
}
