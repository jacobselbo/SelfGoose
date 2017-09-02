package bot.commands;

import bot.Bot;
import bot.Command;
import bot.utils.Http;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class InfoCmd extends Command {
    private Http http;


    public InfoCmd() {
        this.name = "whois";
        this.arguments = "<player>";
        this.description = "Gets the roblox info of <player>";
        http = new Http();
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        Message m = event.getChannel().sendMessage("Getting info on ``"+args+"``. Please wait").complete();
        Integer userid;
        Integer badgelength = 0;
        String ingroup = "false";
        String role = "None";
        String profile = "";
        Integer friendcount = 0;
        try {
            JSONObject user = new JSONObject(http.sendGet("https://api.roblox.com/users/get-by-username?username="+args).toString());
            userid = user.getInt("Id");
        } catch (Exception e) {
            m.editMessage(":x: Invalid user").queue();
            return;
        }

        try {
            Document doc = Jsoup.connect("https://www.roblox.com/users/"+userid+"/profile").get();
            
            JSONObject badgesobj = new JSONObject(http.sendGet("https://www.roblox.com/badges/roblox?userId="+userid+"&imgWidth=110&imgHeight=110&imgFormat=png").toString());
            JSONArray badges = badgesobj.getJSONArray("RobloxBadges");
            badgelength = badges.length();
            JSONArray friendar = new JSONArray(http.sendGet("http://api.roblox.com/users/"+userid+"/friends").toString());
            friendcount = friendar.length();
            profile = "https://www.roblox.com/users/"+userid+"/profile";
            ingroup = http.sendGet("https://www.roblox.com/Game/LuaWebService/HandleSocialRequest.ashx?method=IsInGroup&playerid="+userid+"&groupid=3256759").toString().substring(22,26);
            if (ingroup.equals("fals")) {
                ingroup = "false";
            }
            role = http.sendGet("https://www.roblox.com/Game/LuaWebService/HandleSocialRequest.ashx?method=GetGroupRole&playerid="+userid+"&groupid=3256759").toString();
        } catch(Exception e) {
            m.editMessage(":x: Error").queue();
            e.printStackTrace();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        if(event.getGuild()!=null)
            builder.setColor(event.getGuild().getSelfMember().getColor());
        builder.setAuthor("RBX Profile data for "+args+"",null,null);

        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("RBX Username - "+args + "\n");
        sbuilder.append("RBX UserId - " + userid + "\n");
        sbuilder.append("RBX Profile URL - " + profile + "\n");
        sbuilder.append("Number of RBX Badges - " + badgelength + "\n");
        sbuilder.append("Number of Friends - " + friendcount + "\n");
        sbuilder.append("In Void's Group - " + ingroup +"\n");
        if (ingroup.equalsIgnoreCase("true")) {
            sbuilder.append("Group role - " + role + "\n");
        }
        builder.setTimestamp(event.getMessage().getCreationTime());
        builder.setDescription(sbuilder.toString());
        builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+Bot.version, "https://www.roblox.com/headshot-thumbnail/image?userId="+userid+"&width=420&height=420&format=png");
        m.editMessage(new MessageBuilder().setEmbed(builder.build()).build()).queue();
    }
}
