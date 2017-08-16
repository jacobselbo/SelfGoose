package bot;

import bot.commands.*;
import bot.entities.Config;
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

    public Listener(Config config) {
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
                new NickCmd(),
                new GifCmd(),
                new MemeCmd()
        };
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (Bot.afk && event.getMessage().getMentionedUsers().get(0).equals(event.getJDA().getSelfUser()) && !event.getAuthor().equals(event.getJDA().getSelfUser())) {
            if(Bot.afkreason.equals("")) {
                event.getChannel().sendMessage(event.getJDA().getSelfUser().getAsMention() + " is currently AFK").queue();
            } else {
                event.getChannel().sendMessage(event.getJDA().getSelfUser().getAsMention() + " is currently AFK for "+Bot.afkreason).queue();
            }
        }
        if (!event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        if (Bot.afk && event.getAuthor().equals(event.getJDA().getSelfUser()) && !event.getMessage().getRawContent().startsWith("<")) Bot.afk = false;
        System.out.println("[" + event.getAuthor().getName() + "] : " + event.getMessage().getRawContent());
        boolean cmdran = false;
        if(event.getMessage().getRawContent().startsWith(prefix)) {
            String[] args = event.getMessage().getRawContent().substring(prefix.length()).trim().split("\\s+",2);
            if(args.length < 2) args = new String[]{args[0], ""};
            for(Command command : commands) {
                if(command.name.equalsIgnoreCase(args[0])) {
                    command.run(args[1],event);
                    cmdran = true;
                }
            }
            if (!cmdran) {
                event.getMessage().editMessage("❌ ``"+args[0]+"`` isn't a command").queue(m -> {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
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
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Bot Loaded");
    }
}
