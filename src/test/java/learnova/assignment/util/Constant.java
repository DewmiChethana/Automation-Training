package learnova.assignment.util;

import static java.util.Map.ofEntries;

import java.util.Map;

public class Constant
{
    private static final String BASEUTLAPI = "https://api.learnova-dev.skillsurf.lk";
    private static final String BASEURLUI = "https://learnova-qa.skillsurf.lk/";

    private static final Map<String,Integer> mounthsMap = ofEntries(
      Map.entry("January", 1),
      Map.entry("February", 2),
      Map.entry("March", 3),
      Map.entry("April", 4),
      Map.entry("May", 5),
      Map.entry("June", 6),
      Map.entry("July", 7),
      Map.entry("August", 8),
      Map.entry("September", 9),
      Map.entry("October", 10),
      Map.entry("November", 11),
      Map.entry("December", 12)
    );

    private static final Map<String,Integer> daysMap = ofEntries(
      Map.entry("Monday", 1),
      Map.entry("Tuesday", 2),
      Map.entry("Wednesday", 3),
      Map.entry("Thursday", 4),
      Map.entry("Friday", 5),
      Map.entry("Saturday", 6),
      Map.entry("Sunday", 7)
    );

    private static final Map<Integer,String> dateSuffixMap = ofEntries(
      Map.entry(1,"st"),
      Map.entry(2,"nd"),
      Map.entry(3,"rd"),
      Map.entry(4,"th"),
      Map.entry(5,"th"),
      Map.entry(6,"th"),
      Map.entry(7,"th"),
      Map.entry(8,"th"),
      Map.entry(9,"th"),
      Map.entry(10,"th"),
      Map.entry(11,"th"),
      Map.entry(12,"th"),
      Map.entry(13,"th"),
      Map.entry(14,"th"),
      Map.entry(15,"th"),
      Map.entry(16,"th"),
      Map.entry(17,"th"),
      Map.entry(18,"th"),
      Map.entry(19,"th"),
      Map.entry(20,"th"),
      Map.entry(21,"st"),
      Map.entry(22,"nd"),
      Map.entry(23,"rd"),
      Map.entry(24,"th"),
      Map.entry(25,"th"),
      Map.entry(26,"th"),
      Map.entry(27,"th"),
      Map.entry(28,"th"),
      Map.entry(29,"th"),
      Map.entry(30,"th"),
      Map.entry(31, "st")
    );

    public static String getBaseUrlApi() {
        return BASEUTLAPI;
    }

    public static String getBaseUrlUi() {
        return BASEURLUI;
    }

    public static Map<String,Integer> getMounthsMap()
    {
        return mounthsMap;
    }

    public static Map<String,Integer> getDaysMap()
    {
        return daysMap;
    }

    public static Map<Integer,String> getDateSuffixMap()
    {
        return dateSuffixMap;
    }
}
