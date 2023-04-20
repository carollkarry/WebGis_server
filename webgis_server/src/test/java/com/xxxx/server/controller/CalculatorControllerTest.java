package com.xxxx.server.controller;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CalculatorControllerTest {
    CalculatorController calculator=new CalculatorController();
    /**
     * Integer area,Integer cube, Integer baby, Integer member, Integer heavy, Integer price
     */


    @org.testng.annotations.Test

    public void testFreeLuggage() {
        //System.out.println("test");
        assertEquals(calculator.freeLuggage(0,1,1,1,90,500),75.0);//aicdefghp
        assertEquals(calculator.freeLuggage(0,1,1,2,90,500),150.0);//abjdefghp
        assertEquals(calculator.freeLuggage(0,1,0,2,90,500),225.0);//abckefghp
        assertEquals(calculator.freeLuggage(0,1,1,0,90,500),300.0);//abcdlfghp
        assertEquals(calculator.freeLuggage(0,1,0,0,90,500),375.0);//abcdemghp
        assertEquals(calculator.freeLuggage(0,2,0,0,90,500),450.0);//abcdefnhp
        assertEquals(calculator.freeLuggage(0,3,0,0,90,500),525.0);//abcdefgop
        assertEquals(calculator.freeLuggage(1,1,1,1,90,500),0.0);//abcdefghp
    }

    /**
     *  Integer areaOut, Integer cubeOut,  Integer babyOut, Integer memberOut
     */
    @org.testng.annotations.Test
    public void testCostInternational() {
        int []luaggagem={20,23,26,29,32};
        int []size={78,67,89,100,120};
        calculator.luggage=luaggagem;
        calculator.size=size;
        assertEquals(calculator.costInternational(1,1,1,1),1400.0);

    }
//Integer cube,Integer baby,Integer member,Double m,Double m1,Double m2,int[]luggage
    @org.testng.annotations.Test
    public void testJudge2() {
        int []luaggagem={20,23,26,29,32};
        int []size={78,67,89,100,120};
        calculator.luggage=luaggagem;
        calculator.size=size;
        assertEquals(calculator.judge2(1,1,1,1400.0,2000.0,3000.0,luaggagem),1400.0);//alcdefghij
        assertEquals(calculator.judge2(1,1,2,1400.0,2000.0,3000.0,luaggagem),1400.0);//abmdefghij
        assertEquals(calculator.judge2(1,1,3,1400.0,2000.0,3000.0,luaggagem),3400.0);//abcnefghij
        assertEquals(calculator.judge2(1,0,1,1400.0,2000.0,3000.0,luaggagem),3400.0);//abcdofghij
        assertEquals(calculator.judge2(1,0,3,1400.0,2000.0,3000.0,luaggagem),6400.0);//abcdepghij
        assertEquals(calculator.judge2(2,1,1,1400.0,2000.0,3000.0,luaggagem),6400.0);//abcdefrhij
        assertEquals(calculator.judge2(2,1,3,1400.0,2000.0,3000.0,luaggagem),6400.0);//abcdefgsij
        assertEquals(calculator.judge2(2,0,3,1400.0,2000.0,3000.0,luaggagem),6400.0);//abcdefghtj

    }

//int[]luggage,int[]size,int HeavyNotSize23,int HeavyNotSize32,int NotHeavySize,int HeavyAndSize
    @org.testng.annotations.Test
    public void testGetOutOfHeavyAndSizeMoney() {

        int []luaggagem={24,29,20,30};
        int []size={78,67,160,170};
        assertEquals(calculator.getOutOfHeavyAndSizeMoney(luaggagem,size,380,980,980,1400),3740.0);
    }
//int[]specialLuggage,int []specialKinds
    @org.testng.annotations.Test
    public void testGetSpecialMoney() {
        int []specialLuggage={10,26,40,10,26,40,10,26,10,26,4,6,20,30};
        int []kinds={2,2,2,3,3,3,4,4,5,5,6,7,7,7};
        assertEquals(calculator.getSpecialMoney(specialLuggage,kinds),45990.0);

    }
//Integer areaOut,Integer cubeOut,Integer babyOut,Integer memberOut,int[]luggage
    @Test
    public void testCostOut() {
        int []luaggagem={20,23,26,29,32};
        int []size={78,67,89,100,120};
        calculator.luggage=luaggagem;
        calculator.size=size;
        assertEquals(calculator.costOut(1,1,1,1,luaggagem),1400.0);//ahcdefg
        assertEquals(calculator.costOut(1,2,1,1,luaggagem),8740.0);//ahcdefg
        assertEquals(calculator.costOut(2,1,1,1,luaggagem),1100.0);//abidefg
        assertEquals(calculator.costOut(2,2,1,1,luaggagem),5450.0);//abidefg
        assertEquals(calculator.costOut(3,1,1,1,luaggagem),1170.0);//abcjefg
        assertEquals(calculator.costOut(3,2,1,1,luaggagem),6790.0);//abcjefg
        assertEquals(calculator.costOut(4,1,1,1,luaggagem),1380.0);//abcdkfg
        assertEquals(calculator.costOut(4,2,1,1,luaggagem),7120.0);//abcdkfg
        assertEquals(calculator.costOut(5,1,1,1,luaggagem),830.0);//abcdelg
        assertEquals(calculator.costOut(5,2,1,1,luaggagem),4770.0);//abcdelg
    }
//Integer cubeOut,Integer babyOut,Integer memberOut,int[]luggage,Double m,Double m1,Double m2
    @Test
    public void testJudge3() {

        int []luaggagem={20,23,26,29,32};
        int []size={78,67,89,100,120};
        calculator.luggage=luaggagem;
        calculator.size=size;
        //calculator.judge3(1,1,1,luaggagem,1170.0,1170.0,1590.0);
        assertEquals(calculator.judge3(1,1,1,luaggagem,1170.0,1170.0,1590.0),1170.0);//aih
        assertEquals(calculator.judge3(3,1,3,luaggagem,1170.0,1170.0,1590.0),3930.0);//abjdefgh
        assertEquals(calculator.judge3(3,1,2,luaggagem,1170.0,1170.0,1590.0),3930.0);//abcdefgh
        assertEquals(calculator.judge3(3,0,3,luaggagem,1170.0,1170.0,1590.0),5520.0);//abcdkfgh
    }

    @Test
    public void testTest1() {
        Map<String,Object> map=new HashMap<>();
        map.put("heavies",20);
        map.put("heavies",23);
        map.put("size",130);
        map.put("size",100);
        Map<String,Object> resultMap=new HashMap<>();
        int i=0;
        resultMap.put("code",0);
        resultMap.put("msg","");
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        list.add(map);
        assertEquals(calculator.test(list),resultMap);
    }

    @Test
    public void testSpecial() {
        Map<String,Object> map=new HashMap<>();
        map.put("heavies",20);
        map.put("heavies",23);
        map.put("kinds",2);
        map.put("kinds",3);
        Map<String,Object> resultMap=new HashMap<>();
        int i=0;
        resultMap.put("code",0);
        resultMap.put("msg","");
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        list.add(map);
        assertEquals(calculator.special(list),resultMap);
    }
}