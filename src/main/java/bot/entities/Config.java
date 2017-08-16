package bot.entities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.List;

public class Config {
    private String userToken;
    private String prefix;
    private ZoneId zoneId;
    public Config() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("config.txt"));
        for(String str : lines) {
            String[] parts = str.split("=",2);
            String key = parts[0].trim().toLowerCase();
            String value = parts.length>1 ? parts[1].trim() : null;
            switch(key) {
                case "token":
                    userToken = value;
                    break;
                case "prefix":
                    if(value == null) {
                        prefix = ">";
                        System.out.println("Prefix was defined empty so set prefix to default >");
                    } else {
                        prefix = value;
                    }
                    break;
                case "timezone":
                    if(value == null) {
                        zoneId = ZoneId.systemDefault();
                        System.out.println("Timezone was defined empty so set timezone to system deafult!");
                    } else {
                        try {
                            zoneId = ZoneId.of(value);
                        } catch(Exception e) {
                            zoneId = ZoneId.systemDefault();
                            System.out.println("\""+value+"\" is not a valid timezone; using system default!");
                        }
                    }
            }
        }
        if(userToken == null)
            System.out.println("No token provided in config file!");
        if(prefix == null) {
            System.out.println("No prefix provided in config file so setting prefix to default >");
            prefix = ">";
        }
        if(zoneId == null) {
            System.out.println("No timezone provided in config file so setting timezone to system deafult!");
            zoneId = ZoneId.systemDefault();
        }
    }

    public String getUserToken() {
        return userToken;
    }

    public String getPrefix() {
        return prefix;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
