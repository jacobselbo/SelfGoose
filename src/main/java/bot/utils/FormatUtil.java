package bot.utils;

import java.util.List;

public class FormatUtil {
    public static String join(List<String> strings, String joiner) {
        if(strings.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder(strings.get(0));
        for(int i=1;i<strings.size();i++) {
            sb.append(joiner).append(strings.get(i));
        }
        return sb.toString();
    }
}
