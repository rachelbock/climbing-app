package com.rage.clamber;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit Test for the SkillLevelDataValidation class.
 */
public class SkillLevelInputTest {

    /**
     * Tests that valid input will return the appropriate response.
     */
    @Test
    public void testValidSkillLevelInput(){
        int skillLevelDigit = SkillLevelDataValidation.getSkillLevel("10");
        Assert.assertEquals(10, skillLevelDigit);
        int skillLevelCapB = SkillLevelDataValidation.getSkillLevel("B");
        Assert.assertEquals(-1, skillLevelCapB);
        int skillLevelLowB = SkillLevelDataValidation.getSkillLevel("b");
        Assert.assertEquals(-1, skillLevelLowB);
    }

    /**
     * Tests that with invalid data, the invalid data constant will be returned.
     */
    @Test
    public void testInvalidSkillLevelInput(){
        int skillLevelLetter = SkillLevelDataValidation.getSkillLevel("Q");
        Assert.assertEquals(SkillLevelDataValidation.INVALID_DATA, skillLevelLetter);
        int skillLevelHighNum = SkillLevelDataValidation.getSkillLevel("20");
        Assert.assertEquals(SkillLevelDataValidation.INVALID_DATA, skillLevelHighNum);
        int skillLevelLowNum = SkillLevelDataValidation.getSkillLevel("-4");
        Assert.assertEquals(SkillLevelDataValidation.INVALID_DATA, skillLevelLowNum);
        int skillLevelEmpty = SkillLevelDataValidation.getSkillLevel("");
        Assert.assertEquals(SkillLevelDataValidation.INVALID_DATA, skillLevelEmpty);
        int skillLevelNull = SkillLevelDataValidation.getSkillLevel(null);
        Assert.assertEquals(SkillLevelDataValidation.INVALID_DATA, skillLevelNull);
    }

}
