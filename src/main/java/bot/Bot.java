package bot;

import bot.entities.Config;
import bot.utils.Http;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.time.ZoneId;

public class Bot {
    public static String version = "1.0";
    public static boolean afk;
    public static String afkreason;

    public static void main(String[] args) throws Exception {
        Http http = new Http();
        Config config;
        try {
            config = new Config();
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
        new JDABuilder(AccountType.CLIENT)
                .setToken(config.getUserToken())
                .addEventListener(new Listener(config))
                .buildBlocking();
    }
}
