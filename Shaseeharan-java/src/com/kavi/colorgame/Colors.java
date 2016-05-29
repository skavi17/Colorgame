package com.kavi.colorgame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Colors {
	
	red("r", "R"),
    orange("o", "O"),
    yellow("j", "J"),
    green("v", "V"),
    blue("b", "B"),
    violet("i", "I");
//*énumération des couleurs */

    private String uncontrolled;
    private String controlled;

    Colors(String uncontrolled, String controlled) {
        this.uncontrolled = uncontrolled;
        this.controlled = controlled;
    }

    public String getUncontrolled() {
        return uncontrolled;
    }

    public void setUncontrolled(String uncontrolled) {
        this.uncontrolled = uncontrolled;
    }
    //* afficher une lettre minuscule si c'est une case non contrôlée */

    public String getControlled() {
        return controlled;
    }

    public void setControlled(String controlled) {
        this.controlled = controlled;
    }
    //* afficher une lettre majuscule si c'est une case contrôlée */

    public static Colors findByUnControlled(String uncontrolled) {
        for (Colors colors : Colors.values()) {
            if (colors.uncontrolled.equals(uncontrolled)) {
                return colors;
            }
        }
        return null;
    }
    //*affiche toutes les couleurs non contrôlées*/

 

	public static String listOptions(List<Colors> usedColors) {
        Colors[] values = (Colors[]) Colors.values();
        String options = "";
        for (int i = 0; i < values.length; i++) {
            if (!usedColors.contains(values[i])) {
                options += values[i].uncontrolled;
                Pattern pattern = Pattern.compile(", ");
                Matcher matcher = pattern.matcher(options);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }

                if (count != values.length - (1+usedColors.size())) {
                    options += ", ";
                }
            }
        }
        return options;
    }

    
    public String toString() {
        return "Colors{" +
                "uncontrolled='" + uncontrolled + '\'' +
                ", controlled='" + controlled + '\'' +
                '}';
    }

    private static final List<Colors> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    public static Colors random() {
        return (Colors) VALUES.get(RANDOM.nextInt(SIZE));
    }
}