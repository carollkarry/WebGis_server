package com.xxxx.server.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CalculatorController {
    public int[]luggage=new int[20];
    public  int[]size=new int[20];
    private final int[]OutLuggage=new int [20];//记录超重
    private final int[]OutSize=new int [20];//记录超尺寸
    public final int[]specialLuggage=new int[20];
    public int[] specialKinds=new int[20];
    private int areaOut;
    private int cubeOut;
    private int memberOut;
    private int babyOut;

    @GetMapping("calculator")
    public String calculator()
    {
        return "calculator";
    }



    /**
     *计算国内航线托运费用
     * @param area 飞行目的地，分为境内、境外（其中境外注意细分）,area=0代表境内
     * @param cube 舱位 cube=1表示头等舱，依次类推
     * @param baby 是否携带婴儿 baby=1表示携带
     * @param member 会员属于哪种 无会员时member=0,白金为member=1，金卡、银卡与星空联盟卡为2、、
     * @param heavy 行李重量
     * @param price 经济舱价格
     * @return 返回需要交的费用
     */

    @ApiOperation(value = "计算国内费用")
    @GetMapping("/calculator/total")
    public Double freeLuggage(Integer area,Integer cube, Integer baby, Integer member, Integer heavy, Integer price)  {
        /*
         * 国内运输计重制
         */
        System.out.println("area"+area);
        System.out.println(cube);
        System.out.println(baby);
        System.out.println(member);
        System.out.println(heavy);
        double result=0.0;
       if(area==0)
       {
           if(cube==1 && baby==1&& member==1 &&heavy>80){
               result=(heavy-80)*price*0.015;}
           else if((cube==1&&baby==1&&(member==2||member==3))||(cube==1&&baby==0&&member==1)||(cube==2&&baby==1&&member==1)) {
               if (heavy > 70) result = (heavy - 70) * price * 0.015;
           }
           else if((cube==1&&baby==0&&(member==2||member==3))||(cube==2&&baby==1&&(member==2||member==3))||(cube==3&&baby==1&&member==1))
           {
               if(heavy>60) result=(heavy-60)*price*0.015;
           }
           else if ((cube==1&&baby==1&&member==0)||(cube==2&&baby==0&&(member==2||member==3))||(cube==3&&baby==1&&(member==2||member==3))||(cube==3&&baby==0&&member==1)){
               if(heavy>50)  result= (heavy-50)*price*0.015;

           }
           else if((cube==1&&baby==0&&member==0)||(cube==2&&baby==1&&member==0)||(cube==3&&baby==0&&(member==2||member==3))){
               if(heavy>40) result = (heavy-40)*price*0.015;
           }
           else if((cube==2&&baby==0&&member==0)||(cube==3&&baby==1&&member==0)){
               if (heavy>30)  result= (heavy-30)*price*0.015;
           }
           else if(cube==3&&baby==0&&member==0){
               if(heavy>20) result= (heavy-20)*price*0.015;
           }
       }
        System.out.println(result);
       result+=getSpecialMoney(specialLuggage,specialKinds);
        return result;
    }

    /**
     *
     * @param Luggage 行李信息列表
     * @return 得到行李的重量和尺寸，以便后续计算
     */
    @ApiOperation(value = "测试")
    @PostMapping("/calculator/test")
    public Map<String,Object> test(@RequestBody List<Map<String,Object>> Luggage){
       Map<String,Object> resultMap=new HashMap<>();
       int i=0;
       resultMap.put("code",0);
       resultMap.put("msg","");
       for(Map map:Luggage){

              // System.out.println(map.get(s)+"");
               Object object=map.get("heavies");
               int x=Integer.parseInt(object.toString());
               luggage[i]=x;

               System.out.println(x);
               Object objectSize=map.get("size");
               size[i]=Integer.parseInt(objectSize.toString());
               i++;
       }
       return  resultMap;
    }

    /**
     *
     * @param Special 特殊行李列表
     * @return 从前端获取特殊行李信息
     */
    @ApiOperation(value = "特殊行李")
    @PostMapping("/calculator/special")
    public Map<String,Object> special(@RequestBody List<Map<String,Object>>Special){
        Map<String,Object> resultMap=new HashMap<>();
        int i=0;
        resultMap.put("code",0);
        resultMap.put("msg","");
        for(Map map:Special){

            // System.out.println(map.get(s)+"");
            Object object=map.get("heavies");
            int x=Integer.parseInt(object.toString());
            specialLuggage[i]=x;

            System.out.println(x);
            Object object1=map.get("kinds");
            specialKinds[i]=Integer.parseInt(object1.toString());
            System.out.println("kinds:"+specialKinds[i]);
            i++;
        }
        return  resultMap;
    }
    /**
     * 计算国际航线托运费用
     * @param areaOut 表示航线区域
     * @param cubeOut 舱位,1表示舱位为头等舱，公务舱；2表示悦享经济舱，超级经济舱；3表示经济舱
     * @param babyOut 是否携带婴儿
     * @param memberOut 会员是哪种,1表示终生白金卡、白卡、金卡、银卡，2表示星空联盟金卡，3表示无会员
     * @return 返回需要的费用
     */
    @ApiOperation(value = "计算国际航线费用")
    @GetMapping("/calculator/international")
    public Double costInternational( Integer areaOut, Integer cubeOut,  Integer babyOut, Integer memberOut){
        double money=0.0;
        this.areaOut=areaOut;
        this.cubeOut=cubeOut;
        this.babyOut=babyOut;
        this.memberOut=memberOut;
        money=costOut(areaOut,cubeOut,babyOut,memberOut,luggage);
        return money;
    }

    /**
     *
     * @param areaOut 区域
     * @param cubeOut 舱位
     * @param babyOut 是否有婴儿
     * @param memberOut 会员
     * @param luggage 行李列表
     * @return money
     */
    public Double costOut(Integer areaOut,Integer cubeOut,Integer babyOut,Integer memberOut,int[]luggage){
        int number23=0;//记录23KG以内的行李有多少件
        int number32=0;//记录32KG以内的行李有多少件
        int outOf=0;//记录超出的部分
        System.out.println("luggage");
        Double []remain = new Double[0];
        double money=0;
        /**
         * 航线在区域一
         */
        if(areaOut==1){
            double m=1400;
            double m1=2000;
            double m2=3000;
            int HeavyNotSize23=380;
            int HeavyNotSize32=980;
            int NotHeavySize=980;
            int HeavyAndSize=1400;
            money=judge2(cubeOut,babyOut,memberOut,m,m1,m2, luggage);

           if(cubeOut!=1)
               money+=getOutOfHeavyAndSizeMoney(OutLuggage,OutSize,HeavyNotSize23,HeavyNotSize32,NotHeavySize,HeavyAndSize);
            System.out.println(money);
        }
        /**
         * 区域二
         */
        else if(areaOut==2){
            double m=1100;
            double m2=1590;
            int HeavyNotSize23=280;
            int HeavyNotSize32=690;
            int NotHeavySize=690;
            int HeavyAndSize=1100;
            money=judge2(cubeOut,babyOut,memberOut,m,m,m2, luggage);
            if(cubeOut!=1)
                money+=getOutOfHeavyAndSizeMoney(OutLuggage,OutSize,HeavyNotSize23,HeavyNotSize32,NotHeavySize,HeavyAndSize);

        }

        else if(areaOut==3){
            int HeavyNotSize23=520;
            int HeavyNotSize32=1170;
            int NotHeavySize=1170;
            int HeavyAndSize=1590;
            double m=1170;
            double m2=1590;
            money=judge3(cubeOut,babyOut,memberOut,luggage,m,m,m2);
           if(cubeOut!=1)
               money+=getOutOfHeavyAndSizeMoney(OutLuggage,OutSize,HeavyNotSize23,HeavyNotSize32,NotHeavySize,HeavyAndSize);

        }
        else if(areaOut==4)
        {
            int HeavyNotSize23=690;
            int HeavyNotSize32=1040;
            int NotHeavySize=1040;
            int HeavyAndSize=2050;
            double m=1380;
            double m2=1590;
            money=judge3(cubeOut,babyOut,memberOut,luggage,m,m,m2);
            if(cubeOut!=1)
                money+=getOutOfHeavyAndSizeMoney(OutLuggage,OutSize,HeavyNotSize23,HeavyNotSize32,NotHeavySize,HeavyAndSize);

        }
        else
        {
            int HeavyNotSize23=210;
            int HeavyNotSize32=520;
            int NotHeavySize=520;
            int HeavyAndSize=830;
            double m=830;
            double m1=1100;
            double m2=1590;
            money=judge2(cubeOut,babyOut,memberOut,m,m1,m2, luggage);
           if(cubeOut!=1)
               money+=getOutOfHeavyAndSizeMoney(OutLuggage,OutSize,HeavyNotSize23,HeavyNotSize32,NotHeavySize,HeavyAndSize);

        }
        money+=getSpecialMoney(specialLuggage,specialKinds);
        return money;
    }

    /**
     *
     * @param cubeOut 舱位
     * @param babyOut 是否有婴儿
     * @param memberOut 会员
     * @param luggage 行李列表
     * @param m 超额第一件价格
     * @param m1 超额第两件价格
     * @param m2 超额第三件价格
     * @return money
     */
    public Double judge3(Integer cubeOut, Integer babyOut, Integer memberOut, int[] luggage,Double m, Double m1, Double m2){
        double money=0.0;
        if(cubeOut==1||cubeOut==2)
            money=judge2(cubeOut,babyOut,memberOut,m,m1,m2, luggage);
        else
        {
            int number23;
            int number32=0;
            if(babyOut==1)
            {
                if(memberOut==3)
                    number23 =2;
                else
                    number23 =3;

                money=getMoney(number23,number32, luggage,m,m1,m2);
            }
            else {
                if(memberOut==3)
                    number23 =1;
                else
                    number23 =2;
                money=getMoney(number23,number32, luggage,m,m1,m2);
            }
        }
        return money;
    }

    /**
     * 对各种情况的判定
     * @param cube 舱位
     * @param baby 婴儿
     * @param member 会员
     * @param m 超额
     * @param m1 超额
     * @param m2 超额
     * @param luggage 行李
     * @return 返回money
     */
    public Double judge2(Integer cube,Integer baby,Integer member,Double m,Double m1,Double m2,int[]luggage){
        int number23=0;//记录23KG以内的行李有多少件
        int number32=0;//记录32KG以内的行李有多少件
        double money = 0;
        if(cube==1&&baby==1&&member==1){
            number32=3;
            number23=1;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        else if(cube==1&&baby==1&&member==2){
            number23=2;
            number32=2;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        else if((cube==1&&baby==1&&member==3)||(cube==1&&baby==0&&member==2)){
            number23=1;
            number32=2;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        else if(cube==1&&baby==0&&member==1){

            number32=3;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        else if(cube==1&&baby==0&&member==3){
            number32=2;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        else if((cube==2||cube==3)&&baby==1&&(member==1||member==2))
        {
            number23=4;
            money=getMoney(number23,number32,luggage,m,m1,m2);

        }
        else if(((cube==2||cube==3)&&baby==1&&member==3)||((cube==2||cube==3)&&baby==0&&(member==1||member==2)))
        {
            number23=3;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        else if((cube==2||cube==3)&&baby==0&&member==3)
        {
            number23=2;
            money=getMoney(number23,number32,luggage,m,m1,m2);
        }
        return money;
    }
    /**
     * 计算超出件数时，需要缴纳的费用
     * @param number23 23额度
     * @param number32 32KG额度
     * @param Luggage 行李重量列表
     * @param m 第一件行李的价格
     * @param m1 第二件行李的价格
     * @param m2 第三件行李的价格
     * @return 返回money
     */
    public Double getMoney(int number23, int number32, int[] Luggage , Double m, Double m1, Double m2){
        System.out.println(number23);
        double money=0.0;
       int j=0;
        for (Integer integer : Luggage) {
            int i=0;
          if(integer>0){
              System.out.println(integer);
              if (integer <= 23 && number23 > 0&&size[i]>=60&&size[i]<=158) {
                  number23--;
                  System.out.println("number23:"+number23);
              } else if (((integer <= 32 && integer > 23) || (integer <= 32 && number23 == 0)) && number32 > 0&&size[i]>=60&&size[i]<=158) {
                  number32--;
                  System.out.println("number32");
              } else {
                  System.out.println("Out");
                  OutLuggage[j]=integer;
                  OutSize[j]=size[i];
                  j++;
              }
          }
          i++;
        }

        if(j==1){
            money=m;
        }
        else if(j==2){
            money=m+m1;
        }
        else if(j!=0){
            money=m+m1;
            j=j-2;
            while (j>0){
               money+=m2;
               j--;
            }
        }
        return money;
    }

    public Double getOutOfHeavyAndSizeMoney(int[]luggage,int[]size,int HeavyNotSize23,int HeavyNotSize32,int NotHeavySize,int HeavyAndSize){
        double money=0;
        for(int i=0;i<luggage.length;i++)
        {
            if(luggage[i]>23&&luggage[i]<=28&&size[i]>=60&&size[i]<=158){
                money+=HeavyNotSize23;
            }
            if(luggage[i]>28&&luggage[i]<=32&&size[i]>=60&&size[i]<=158){
                money+=HeavyNotSize32;
            }
            if(luggage[i]>2&&luggage[i]<=23&&size[i]>158&&size[i]<=203){
                money+=NotHeavySize;
            }
            if(luggage[i]>23&&luggage[i]<=32&&size[i]>=158&&size[i]<=203){
                money+=HeavyAndSize;
            }
        }
        return money;
    }

    /**
     *
     * @param specialLuggage 特殊行李列表
     * @param specialKinds 特殊行李种类列表
     * @return 返回需要的费用
     */

    public Double getSpecialMoney(int[]specialLuggage,int []specialKinds){
        double money=0.0;
        for(int i=0;i<specialLuggage.length;i++){
            if(specialKinds[i]==2){
                if(specialLuggage[i]>=2&&specialLuggage[i]<=23) money+=2600;
                else if(specialLuggage[i]>23&&specialLuggage[i]<=32) money+=3900;
                else if(specialLuggage[i]>32&&specialLuggage[i]<=45) money+=5200;
            }
            else if(specialKinds[i]==3){
                if(specialLuggage[i]>=2&&specialLuggage[i]<=23) money+=1300;
                else if(specialLuggage[i]>23&&specialLuggage[i]<=32) money+=2600;
                else if(specialLuggage[i]>32&&specialLuggage[i]<=45) money+=3900;
            }
            else if(specialKinds[i]==4){
                if(specialLuggage[i]>=2&&specialLuggage[i]<=23) money+=490;
                else if(specialLuggage[i]>23&&specialLuggage[i]<=32) money+=3900;
            }
            else if(specialKinds[i]==5){
                if(specialLuggage[i]>=2&&specialLuggage[i]<=23) money+=1300;
                else if(specialLuggage[i]>23&&specialLuggage[i]<=32) money+=2600;
            }
            else if(specialKinds[i]==6){
                if(specialLuggage[i]>=2&&specialLuggage[i]<=5) money+=1300;
            }
            else if(specialKinds[i]==7){
                if(specialLuggage[i]>=2&&specialLuggage[i]<=8) money+=3900;
                else if(specialLuggage[i]>8&&specialLuggage[i]<=23) money+=5200;
                else if(specialLuggage[i]>23&&specialLuggage[i]<=32) money+=7800;
            }
        }
        return money;
    }
}
