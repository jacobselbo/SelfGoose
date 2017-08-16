package bot.commands;

import bot.Bot;
import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class EvalCmd extends Command {

    public EvalCmd() {
        this.name = "js";
        this.description = "runs java code using Nashorn";
        this.arguments = "<code>";
        this.type = Type.EDIT_ORIGINAL;
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
        Bot b = new Bot();
        se.put("event", event);
        se.put("jda", event.getJDA());
        se.put("guild", event.getGuild());
        se.put("channel", event.getChannel());
        se.put("message", event.getMessage());
        se.put("Bot", b);
        try
        {
            String eval = (String) se.eval(args);
            reply("```java\n"+args+" ```"+"Evaluated Successfully:\n```\n"+eval+" ```", event);
        }
        catch(Exception e)
        {
            reply("```java\n"+args+" ```"+"An exception was thrown:\n```\n"+e+" ```", event);
        }
    }
}
