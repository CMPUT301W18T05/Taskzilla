/* PhoneNumberTest
 *  *
 * Version 1.0
 *
 * Feb 23, 2018
 *
 * Copyright 2018 Team05, CMPUT301, University of Alberta - All Rights Reserved
 *
 */

package com.cmput301w18t05.taskzilla;

/**
 * Created by Micheal-Nguyen on 2018-02-23.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

public class PhoneNumberTest extends ActivityInstrumentationTestCase2{
    public PhoneNumberTest(){
        super(com.cmput301w18t05.taskzilla.PhoneNumber.class);
    }

    /**
     * get PhoneNumber test
     * test if getPhoneNumber returns correct number of a new PhoneNumber
     *
     * @author Micheal-Nguyen
     */
    public void testGetPhoneNumber(){
        PhoneNumber phone = new PhoneNumber(7802324563L);
        assertEquals("7802324563",phone.toString());
    }

    /**
     * set PhoneNumber test
     * test if setPhoneNumber changes phone number to correct phone number.
     *
     * @author Micheal-Nguyen
     */
    public void testSetPhoneNumber(){
        PhoneNumber phone = new PhoneNumber(7802324563L);
        phone.setPhoneNumber(7809352312L);
        assertEquals("7802324563",phone.toString());

    }

}
