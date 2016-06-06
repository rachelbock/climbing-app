package com.rage.clamber;

/**
 * Class for skill level conatants. Used in new user dialog fragment and update user dialog fragment.
 */
public class SkillLevelDataValidation {

    public static final int INVALID_DATA = -8000;
    public static final int MIN_SKILL = 0;
    public static final int MAX_SKILL = 12;

    /**
     * Method to determine whether the user input skill level is valid.
     *
     * @param skillLevel - the user input string
     * @return - an integer value that will be stored in the database. If the string is not valid
     * it returns a defined invalid int to check for.
     */

    public static int getSkillLevel(String skillLevel) {

        int userSkill = INVALID_DATA;
        if (skillLevel == null) {
            return userSkill;
        } else if (skillLevel.equals("b") || skillLevel.equals("B")) {
            userSkill = -1;
        } else if (skillLevel.matches("[0-9]+")) {

            if (Integer.parseInt(skillLevel) >= MIN_SKILL && Integer.parseInt(skillLevel) <= MAX_SKILL) {
                userSkill = Integer.parseInt(skillLevel);
            }
        }

        return userSkill;
    }
}
