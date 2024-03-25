package test.splab.springgames.modules.member;

import lombok.Getter;

@Getter
public enum Level {
    GOLD, SILVER, BRONZE;

    public static Level of(String level) {
        if(level.equals("ALL")) return null;
        return Enum.valueOf(Level.class, level);
    }
}
