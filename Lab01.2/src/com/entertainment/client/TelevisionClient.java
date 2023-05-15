package com.entertainment.client;

import com.entertainment.Television;

class TelevisionClient {
    public static void main(String[] args) {
        Television tv1 = new Television();
        Television tv2 = new Television("RCA", 10);
        System.out.println(tv1);
        System.out.println(tv2);
        tv2.changeChannel(9);       //change channel to channel 9
        System.out.println(tv2);    // print new tv2
        System.out.println();       // print blank line

        // examine behavior of == and equals()
        Television tvA = new Television("Sony", 50);
        Television tvB = new Television("Sony", 50);

        System.out.println("tvA == tvB: "      + (tvA == tvB));     // == asks if they are the same object(they are not)
        System.out.println("tvA.equals(tvB): " + tvA.equals(tvB));  // .equals asks if they exhibit "equality"
    }
}