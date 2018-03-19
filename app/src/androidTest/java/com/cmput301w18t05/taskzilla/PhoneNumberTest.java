/*
 * Copyright 2018 (c) Andy Li, Colin Choi, James Sun, Jeremy Ng, Micheal Nguyen, Wyatt Praharenka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.cmput301w18t05.taskzilla;

/**
 * Created by Micheal-Nguyen on 2018-02-23.
 */


import android.test.ActivityInstrumentationTestCase2;


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
        PhoneNumber phone = new PhoneNumber("7802324563");
        assertEquals("7802324563",phone.toString());
    }

    /**
     * set PhoneNumber test
     * test if setPhoneNumber changes phone number to correct phone number.
     *
     * @author Micheal-Nguyen
     */
    public void testSetPhoneNumber(){
        PhoneNumber phone = new PhoneNumber("7802554563");
        phone.setPhoneNumber("7802324563");
        assertEquals("7802324563",phone.toString());

    }

}
