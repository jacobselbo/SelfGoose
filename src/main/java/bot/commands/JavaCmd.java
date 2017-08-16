package bot.commands;

import bot.Bot;
import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCmd extends Command {

    public JavaCmd() {
        this.name = "java";
        this.description = "runs java code using JavaCompiler";
        this.arguments = "<code>";
        this.type = Type.EDIT_ORIGINAL;
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        try {
            String source = "package bot.eval;\n" +
                    "\n" +
                    "import net.dv8tion.jda.core.JDA;\n" +
                    "import net.dv8tion.jda.core.entities.Guild;\n" +
                    "import net.dv8tion.jda.core.entities.Message;\n" +
                    "import net.dv8tion.jda.core.entities.MessageChannel;\n" +
                    "import net.dv8tion.jda.core.events.message.MessageReceivedEvent;\n" +
                    "\n" +
                    "public class Test {\n" +
                    "    private Message message;\n" +
                    "    private MessageReceivedEvent event;\n" +
                    "    private Guild guild;\n" +
                    "    private MessageChannel channel;\n" +
                    "    private JDA jda;\n" +
                    "    static {}\n" +
                    "    public Test() {}\n" +
                    "    public static void execute(Message message,MessageReceivedEvent event,Guild guild,MessageChannel channel,JDA jda) {\n" +
                    "        System.out.println(\"Eval loading....\");\n" + args + "\n" +
                    "    }\n" +
                    "}";
            // Save source in .java file.
            File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            File root = new File(jar.getParentFile().getPath()+"\\eval");
            root.mkdir();
            if(root.listFiles() != null) {
                for(File f : root.listFiles()) {
                    f.delete();
                }
            }
            File sourceFile = new File(root, "Test.java");
            sourceFile.getParentFile().mkdirs();
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
            // Compile source file.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourceFile.getPath());
            // Load and instantiate compiled class.
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
            Class<?> cls = Class.forName("bot.eval.Test",true,classLoader);
            cls.newInstance();
            for(Method m : cls.getDeclaredMethods()) {
                m.invoke(null,event.getMessage(),event,event.getGuild(),event.getChannel(),event.getJDA());
                System.out.println(m.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
