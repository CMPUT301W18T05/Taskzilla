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
 * Created by wyatt on
 */

/**
 * PhoneNumber
 *
 * phone number class to handle phone number types
 */
public class PhoneNumber {

    private String phone;

    public PhoneNumber(String phone) {
        this.phone = phone;
    }

    /**
     * set phone number
     * @param phone the phone number
     * @author Micheal-Nguyen
     */
     void setPhoneNumber(String phone){
        this.phone = phone;
    }

    /**z
     * convert phone number from long to string
     * @return the phone number
     * @author Micheal-Nguyen
     */
    public String toString() {
        return phone;
    }
}




