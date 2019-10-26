import com.sun.jdi.event.StepEvent;
import com.sun.tools.javac.Main;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.File;

/**
 * 办理套餐的父类
 * @param price 套餐的费用
 *
 */
class ServicePackage{
    double price;
    ServicePackage(double price){
        this.price=price;
    }
}
/**
 * 电话卡类
 * @param cardNumber 此卡的卡号
 * @param userName 使用者的姓名
 * @param passWord 此卡的密码
 * @param comsumeAmount 消费的钱数
 * @param   money 此卡中的剩余费用
 * @param   realTalkTime 使用某套餐的通话时间
 *@param    realSMSCount 使用某套餐的发送信息的条数
 * @param realFlow 使用某套餐的使用流量
 * @param serPackage 此卡目前使用的套餐类型
 */
class MoblieCard{
    public    String cardNumber;
    public String userName;
    public   String passWord;
    public    double comsumeAmount;
    public double money;
    int realTalkTime;
    int realSMSCount;
    int realFlow;
    ServicePackage serPackage;
    public MoblieCard() {
        realTalkTime=realFlow=realSMSCount=0;
        comsumeAmount=0;
    }
}

/**
 * 消费情况的父类
 * @param con_cardNumber  消费的卡的卡号
 * @param type 消费的类型
 * @param ConsumeData 消费情况的具体数据
 */
class ConsumeInfo{
    private String con_cardNumber;
    String type;
    int ConsumeData;
    public ConsumeInfo(String cardNumber, String type, int consumeData) {
        this.con_cardNumber = cardNumber;
        this.type = type;
        ConsumeData = consumeData;
    }
}

/**
 * 嗖嗖用户的虚拟场景
 * @param type 消费的类型
 * @param data 消费的具体数据
 * @param  description 场景的描述
 */
class Scene{
    String type;
    int data;
    String description;
    public Scene(String type, int data, String description) {
        this.type = type;
        this.data = data;
        this.description = description;
    }
}

/**
 * 话痨套餐类
 * @param talkTime 记录某张卡办理的此套餐中通话时间的剩余量
 * @param smsCount 记录某张卡办理的此套餐中的短信条数的剩余量
 * @param TalkPrice 此套餐的办理费用
 */
class TalkPackage extends ServicePackage implements CallService,SendService{
    public int talkTime;
    public int smsCount;
    static int TalkPrice=58;
    public TalkPackage(double price) {
        super(price);
        talkTime=200;
        smsCount=50;
    }
   public int call(int minCount,MoblieCard card){
        return 0;
    }
  public   int send(int count,MoblieCard card){
        return 0;
    }
}

/**
 * 超人套餐
 * @param talkTime 记录某张卡办理的此套餐中通话时间的剩余量
 * @param smsCount 记录某张卡办理的此套餐中的短信条数的剩余量
 * @param flow 记录某张卡办理的此套餐中上网流量的剩余量
 * @param SuperPrice 此套餐的办理费用
 */
class SuperPackage extends ServicePackage implements  CallService,SendService,NetService{
   public int talkTime;
   public int smsCount;
   public int flow;
    static int SuperPrice=108;
   public SuperPackage(double price) {
        super(price);
        talkTime=200;
        smsCount=100;
        flow=1024;
    }
   public int netPlay(int flow,MoblieCard card){return 0;}
   public   int send(int count,MoblieCard card){
        return 0;
    }
   public int call(int minCount,MoblieCard card){return 0;}
}
/**
 * 网虫套餐
 * @param flow 记录某张卡办理的此套餐中上网流量的剩余量
 * @param NetPrice 此套餐的办理费用
 */
class NetPackage extends ServicePackage implements NetService{
    public int flow;
    static int NetPrice=68;
    public NetPackage(double price) {
        super(price);
        flow=5*1024;
    }
   public int netPlay(int flow,MoblieCard card){
        card.realFlow+=flow;
        return this.flow-flow;
    }
}
/**
 * 通话接口类
 */
interface CallService{
    int call(int minCount,MoblieCard card);
}
/**
 * 短信接口类
 */

interface SendService{
   int send(int count,MoblieCard card);
}
/**
 * 上网接口类
 */

interface NetService{
    int netPlay(int flow,MoblieCard card);
}
/**
 * 卡的数据以及卡中的消费情况的记录类
 * @param card 卡的情况
 * @param consumInfos 卡的消费情况
 */

class CardUtil{
    static Map<String,MoblieCard> card=new HashMap<>();
    static Map<String, List<ConsumeInfo>> consumInfos=new HashMap<>();
}

/**
 * 用户登录之后的菜单
 * 包括本月账单查询
 * 套餐余量查询
 * 打印消费详单
 * 更换套餐
 * 退网
 * 返回主菜单等方法
 * @param type 输入的数字，由此来判断该进入那个功能
 */
class SubMain{
   private int type;
   public SubMain(MoblieCard a){
        show(a);
    }
   private void show(MoblieCard a){
        System.out.println("*******嗖嗖移动用户菜单*********");
        System.out.println("1:本月账单查询\n2:套餐余量查询\n3：打印消费详单\n4：套餐变更\n5：办理退网\n请选择（输入1~5选择功能，其他键返回上一级：）");
        Scanner scan=new Scanner(System.in);
        if(scan.hasNext())
            type=scan.nextInt();
        switch (type){
            case 1:
            {
                show_1(a);
                System.out.println("\n请亲亲输入任意键返回用户菜单哦(^＿－)");
                Scanner scan2=new Scanner(System.in);
                if(scan2.hasNext())
                    show(a);
                break;
            }
            case 2:{
                show_2(a);
                System.out.println("\n请亲亲输入任意键返回用户菜单哦(^＿－)");
                Scanner scan2=new Scanner(System.in);
                if(scan2.hasNext())
                    show(a);
                break;
            }
            case 3:
            {
                show_3(a);
                System.out.println("\n请亲亲输入任意键返回用户菜单哦(^＿－)");
                Scanner scan2=new Scanner(System.in);
                if(scan2.hasNext())
                    show(a);
                break;
            }
            case 4: {
                show_4(a);
                System.out.println("\n请亲亲输入任意键返回用户菜单哦(^＿－)");
                Scanner scan2=new Scanner(System.in);
                if(scan2.hasNext())
                    show(a);
                break;
            }
            case 5:{
                show_5(a);
                break;
            }
            default: {
                show_6();
                break;
            }
        }
    }
    //本月账单查询
   private void show_1(MoblieCard a){
    System.out.println("********本月账单查询*********");
    System.out.println("您的卡号"+a.cardNumber+"当月账单：");
    DecimalFormat con_format=new DecimalFormat("#.0");
        System.out.println("套餐资费："+con_format.format(a.serPackage.price)+"元\n合计："+con_format.format(a.serPackage.price+a.comsumeAmount)+"元\n账户余额："+con_format.format(a.money)+"元");
    }
    //套餐余量查询
   private void show_2(MoblieCard a){
       System.out.println("您的卡号是："+a.cardNumber+"，套餐内剩余：");
       DecimalFormat server_format=new DecimalFormat("###.0");
       if(a.serPackage instanceof TalkPackage){
            System.out.println("通话时长："+server_format.format(((TalkPackage) a.serPackage).talkTime)+"分钟\n"+"短信条数："+((TalkPackage) a.serPackage).smsCount+"条");
       }
       if(a.serPackage instanceof NetPackage){
           int remain_flow=((NetPackage)a.serPackage).flow;
           System.out.println("上网流量："+server_format.format(remain_flow/1024)+"GB");
       }
       if(a.serPackage instanceof SuperPackage){
           int remain_flow=((SuperPackage)a.serPackage).flow;
           System.out.println("通话时长："+server_format.format(((SuperPackage)a.serPackage).talkTime)+"分钟\n"+"短信条数："+((SuperPackage) a.serPackage).smsCount+"条\n"+"上网流量："+server_format.format(remain_flow/1024)+"GB");
       }
    }
    //打印消费详单
   private void show_3(MoblieCard a){
       String a1="";
       a1+="******"+a.cardNumber+"消费记录*******\r\n";
       a1+="序号     类型      数据（通话（分钟）/上网(MB）/短信（条）\r\n";

        for(int i=0;i<CardUtil.consumInfos.get(a.cardNumber).size();i++){
            a1+=(i+1)+".        "+CardUtil.consumInfos.get(a.cardNumber).get(i).type+"      "+CardUtil.consumInfos.get(a.cardNumber).get(i).ConsumeData+"\r\n";
        }
       String path = "D:/JAVA exercise/homework1";
       File file = new File(path,"consume.txt");
       if(file.exists()){
           System.out.println(file.getName()+"创建成功");
       }
       try {
           FileWriter fileWriter = new FileWriter(file);
          fileWriter.write(a1);
           fileWriter.close(); // 关闭数据流
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
    //更换套餐
   private void show_4(MoblieCard a){
       System.out.println("*****套餐变更******");
       System.out.print("1.话痨套餐 2：网虫套餐 3：超人套餐 请选择（序号）：");
       Scanner scan=new Scanner(System.in);
       int st=0;
       if(scan.hasNext())
           st=scan.nextInt();
       switch (st){
           case 1:{
               if(a.serPackage instanceof TalkPackage)
                   System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
               else {
                   if(a.money>=TalkPackage.TalkPrice){
                       a.serPackage = new TalkPackage(58);
                       a.money -= TalkPackage.TalkPrice;
                       a.realFlow=a.realSMSCount=a.realTalkTime=0;
                       System.out.println("恭喜您由更换套餐成功，现在您的套餐为：话痨套餐");
                   }
                   else{
                       System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
                   }
               }
               break;
           }
           case 2:{
               if(a.serPackage instanceof NetPackage)
                   System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
               else {
                   if(a.money>=NetPackage.NetPrice){
                       a.serPackage = new NetPackage(68);
                       a.money -= NetPackage.NetPrice;
                       a.realFlow=a.realSMSCount=a.realTalkTime=0;
                       System.out.println("恭喜您由更换套餐成功，现在您的套餐为：网虫套餐");
                   }
                   else {
                       System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
                   }
               }
               break;
           }
           case 3:{
               if(a.serPackage instanceof SuperPackage)
                   System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
               else{
                   if(a.money>=SuperPackage.SuperPrice){
                       a.serPackage = new SuperPackage(78);
                       a.money -=SuperPackage.SuperPrice;
                       a.realFlow=a.realSMSCount=a.realTalkTime=0;
                       System.out.println("恭喜您由更换套餐成功，现在您的套餐为：超人套餐");
                   }
                   else{
                       System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
                   }
               }
               break;
           }
           default:{
               System.out.println("输入有误请重新输入");
               show_4(a);
               break;
           }

       }
    }
    //退网
   private void show_5(MoblieCard a){
       CardUtil.card.remove(a.cardNumber);
       System.out.println("卡号"+a.cardNumber+"办理退网成功！\n谢谢使用");
    }
    //其他键返回主菜单
   private void show_6(){
       MainMenu1 menu2=new MainMenu1();
   }
}

/**
 * 主菜单
 * 包括用户注册
 * 用户登录
 * 使用嗖嗖
 * 话费充值
 * 资费说明
 * 退出系统的等功能
 * @param type 输入的数字，由此来判断该进入那个功能
 */
class MainMenu1{
   private int type;
    private Scene a=new Scene("通话",90,"问候客户，谁知其如此难缠，通话90分钟");
    private Scene b=new Scene("短信",50,"通知朋友手机换号，发送短信50条");
    private Scene c=new Scene("短信",5,"参与环节保护事实方案问卷调查，发送短信5条");
    private Scene d=new Scene("通话",30,"询问母亲身体状况，本地通话30分钟");
    private Scene e=new Scene("上网",500,"闲的无聊的时候刷抖音，上网流量500MB");
    private Scene f=new Scene("上网",30,"上网查资料，耗费流量30MB");
    MainMenu1()
    {
        preShow();
    }
    private void preShow(){
        System.out.println("*****************欢迎使用嗖嗖移动大厅业务**********************************");
        System.out.println("1.用户登录 2.用户注册 3.使用嗖嗖 4.话费充值 5.资费说明 6.退出系统");
        System.out.print("请选择：");
        show();
    }
    private void show() {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNext())
                type = scan.nextInt();
            switch (type) {
                case 1: {
                    show_1();
                    System.out.println("\n请亲亲输入任意键返回主菜单哦(^＿－)");
                    Scanner scan2=new Scanner(System.in);
                    if(scan2.hasNext())
                    preShow();
                    break;
                }
                case 2: {
                    show_2();
                    System.out.println("\n请亲亲输入任意键返回主菜单哦(^＿－)");
                    Scanner scan2=new Scanner(System.in);
                    if(scan2.hasNext())
                        preShow();
                    break;
                }
                case 3: {
                    show_3();
                    System.out.println("\n请亲亲输入任意键返回主菜单哦(^＿－)");
                    Scanner scan2=new Scanner(System.in);
                    if(scan2.hasNext())
                        preShow();
                    break;
                }
                case 4: {
                    show_4();
                    System.out.println("\n请亲亲输入任意键返回主菜单哦(^＿－)");
                    Scanner scan2=new Scanner(System.in);
                    if(scan2.hasNext())
                        preShow();
                    break;
                }
                case 5: {
                    show_5();
                    System.out.println("\n请亲亲输入任意键返回主菜单哦(^＿－)");
                    Scanner scan2=new Scanner(System.in);
                    if(scan2.hasNext())
                        preShow();
                    break;
                }
                case 6: {
                    show_6();
                    break;
                }
                default: {
                    System.out.println("输入错误请重新输入");
                    System.out.print("请选择：");
                    show();
                    break;
                }
            }
        }
    //用户登录
    private void show_1(){
        System.out.print("\n请输入手机卡号:");
        Scanner scan1=new Scanner(System.in);
        String num="";
        if(scan1.hasNext())
            num=scan1.next();
        boolean contain_num=CardUtil.card.containsKey(num);
        if(contain_num) {
            System.out.print("\n请输入密码:");
            Scanner scan2 = new Scanner(System.in);
            String pass = "";
            if (scan2.hasNext())
                pass = ""+scan2.next(); String value="";
                value= CardUtil.card.get(num).passWord;

            if (value.equals(pass)) {
                { System.out.println("尊敬的嗖嗖移动用户您好，欢迎您登录嗖嗖移动(*^▽^*)\n");
                    SubMain submain = new SubMain(CardUtil.card.get(num));
                }
            } else {
                System.out.println("输入的密码有误，请重新输入手机号和密码!");
                show_1();
            }
        }
        else{
                System.out.println("输入的手机号有误，请重新输入!");
                show_1();
            }
        }
    //用户注册
    private void show_2(){
        System.out.println("******可选择的卡号******");
        long  cardNum[]={0,0,0,0,0,0,0,0,0};
        String result[]={"139","139","139","139","139","139","139","139","139"};
        //生成9个不重复的号码
        for(int i=0;i<9;i++) {
                cardNum[i] = (long) (0 + Math.random() * (100000000 -0+ 1));
                result[i]=result[i]+cardNum[i];
        }
        System.out.println("1: "+result[0]+"    "+"2: "+result[1]+"    "+"3: "+result[2]+"    \n"+"4: "+result[3]+"    "+"5: "+result[4]+"    "+"6: "+result[5]+"    \n"+"7: "+result[6]+"    "+"8: "+result[7]+"    "+"9: "+result[8]+"    ");
        System.out.print("请输入卡号（1~9的序号）："); MoblieCard a=new MoblieCard();
        Scanner scan1=new Scanner(System.in);
       int n=0;
        if(scan1.hasNext()){
             n=scan1.nextInt();
            a.cardNumber=result[n-1];
        }
        System.out.print("1:话痨套餐   2：网虫套餐   3：超人套餐  ，请选择套餐（输入序号）：");
        int packageType;
        Scanner scan2=new Scanner(System.in);
        String package_type_chinese="";
        if(scan2.hasNext()){
            packageType=scan2.nextInt();
            switch (packageType){
                case 1:
                {
                    a.serPackage=new TalkPackage(TalkPackage.TalkPrice);
                    package_type_chinese="话痨套餐";
                    break;
                }
                case 2:{
                    a.serPackage=new NetPackage(NetPackage.NetPrice);
                    package_type_chinese="网虫套餐";
                    break;
                }
                case 3:{
                    a.serPackage=new SuperPackage(SuperPackage.SuperPrice);
                    package_type_chinese="超人套餐";
                    break;
                }
                default:
                    System.out.println("输入错误");
            }
    }
        System.out.print("请输入姓名：");
        Scanner scan_name=new Scanner(System.in);
        String name="";
        if(scan_name.hasNext())
            name=scan_name.next();
        a.userName=name;
        System.out.print("\n请输入密码：");
        Scanner  scan_password=new Scanner(System.in);
        String password="";
        if(scan_password.hasNext())
           password=scan_password.next();
        a.passWord=password;
        System.out.print("\n请输入预存话费金额：");
        Scanner  scan_permoney=new Scanner(System.in);
        double permoney=0;
        if(scan_permoney.hasNext())
            permoney=scan_permoney.nextDouble();
        a.money=permoney;
        if(a.money<a.serPackage.price)
            a.money = fee(a.money, a.serPackage.price);
            a.money-=a.serPackage.price;
            boolean contain=CardUtil.card.containsKey(a.cardNumber);
            if(contain) {
                System.out.println("该卡号已进行过注册请重新选择");
                show_2();
            }
            else {
                CardUtil.card.put(a.cardNumber, a);
                System.out.println("\n注册成功！卡号：" + a.cardNumber + " 用户名：" + a.userName + " 当前余额：" + a.money + "\n" + "套餐类型：" + package_type_chinese);
            }
}
    private double fee(double  money,double m) {
        System.out.print("\n您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
        Scanner scan_money = new Scanner(System.in);
        double money2 = 0;
        if (scan_money.hasNext())
            money2 = scan_money.nextDouble();
        money += money2;
        if (money < m)
            fee(money, m);
        else
            return money;
        return 0;
    }

    //资费说明———文件的io
   private void show_5() {
        String path = "D:/JAVA exercise/homework1";
        File file = new File(path,"consumeInfo.txt");
        if(file.exists()){
            System.out.println(file.getName()+"创建成功");
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            String a_w = "******资费说明********\r\n套餐类型：话痨套餐\r\n通话时长：200分钟\r\n短信条数：50条\r\n月资费：58元\r\n\r\n——————————————————\r\n套餐类型：网虫套餐\r\n上网流量：5GB\r\n月资费：68元\r\n\r\n——————————————————\r\n套餐类型：超人套餐\r\n通话时长：200分钟\r\n短信条数：100条\r\n上网流量：1GB\r\n月资费：78元\r\n\r\n——————————————————\r\n超出套餐计费：\r\n通话时长：0.2元/分钟\r\n短信条数：0.1元/条\r\n上网流量：0.1元/MB";
            fileWriter.write(a_w);
            fileWriter.close(); // 关闭数据流
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String lineTxt = null;
            // 从缓冲区中逐行读取代码，调用readLine()方法
            while ((lineTxt = br.readLine()) != null) {
                    System.out.println(lineTxt); // 逐行输出文件内容
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //话费充值
    private void show_4(){
        System.out .print("请输入充值卡号：");
        Scanner scan_1=new Scanner(System.in);
        String put_num="";
        if(scan_1.hasNext())
            put_num=scan_1.next();
        boolean contain=CardUtil.card.containsKey(put_num);
        if(contain){
            System.out.print("\n请输入充值金额：");
            Scanner scan_2=new Scanner(System.in);
            double money1=0;
            if(scan_2.hasNext())
            {
                money1=scan_2.nextDouble();
                CardUtil.card.get(put_num).money+=money1;
                System.out.println("充值成功，当前话费余额为"+CardUtil.card.get(put_num).money+"元");
            }
        }
        else{
            System.out.println("没有此用户，请重新输入");
            show_4();
        }

    }
    //退出系统
   private void show_6(){
        System.out.print("退出系统成功");
        System.exit(1);
    }
    //使用嗖嗖
   private void show_3(){
    System.out.print("请输入手机卡号：");
    Scanner scan=new Scanner(System.in);
    String num="";
        if(scan.hasNext()){
             num=scan.next();
         }
        ArrayList<ConsumeInfo> arrayList = new ArrayList<ConsumeInfo>();
        sousou_extend(num,arrayList);
       CardUtil.consumInfos.put(num, arrayList);
    }
        //嗖嗖功能的辅助方法
     private void sousou_extend(String num,ArrayList<ConsumeInfo> arrayList){
         int scene_num=(int)(1+Math.random()*(6-1+1));
         switch (scene_num){
             /*    Scene a=new Scene("通话",90,"问候客户，谁知其如此难缠，通话90分钟");*/
             case 1:
             {
                 System.out.println(a.description);
                 ConsumeInfo con = new ConsumeInfo(num, a.type, a.data);
                 if(CardUtil.card.get(num).serPackage instanceof TalkPackage)
                 {
                     if( ((TalkPackage) CardUtil.card.get(num).serPackage).talkTime-a.data>0) {
                         ((TalkPackage) CardUtil.card.get(num).serPackage).talkTime -= a.data;
                         CardUtil.card.get(num).realTalkTime += a.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");

                     }
                     else {
                         if (((TalkPackage) CardUtil.card.get(num).serPackage).talkTime<=0) {
                             if( CardUtil.card.get(num).money<0.2*a.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.2 * a.data;
                             CardUtil.card.get(num).comsumeAmount += 0.2 * a.data;
                             CardUtil.card.get(num).realTalkTime += a.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((TalkPackage) CardUtil.card.get(num).serPackage).talkTime - a.data) * 0.2) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((TalkPackage) CardUtil.card.get(num).serPackage).talkTime - a.data) * 0.2;
                             CardUtil.card.get(num).comsumeAmount += -(((TalkPackage) CardUtil.card.get(num).serPackage).talkTime - a.data) * 0.2;
                             ((TalkPackage) CardUtil.card.get(num).serPackage).talkTime = 0;
                             CardUtil.card.get(num).realTalkTime += a.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 if(CardUtil.card.get(num).serPackage instanceof NetPackage) {
                         System.out.println("您的套餐不支持此项消费！\n");
                         sousou_extend(num,arrayList);
                         break;
                 }
                 if(CardUtil.card.get(num).serPackage instanceof SuperPackage) {
                     if (((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - a.data > 0) {
                         ((SuperPackage) CardUtil.card.get(num).serPackage).talkTime -= a.data;
                         CardUtil.card.get(num).realTalkTime += a.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((SuperPackage) CardUtil.card.get(num).serPackage).talkTime < a.data) {
                             if (CardUtil.card.get(num).money < 0.2 * a.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.2 * a.data;
                             CardUtil.card.get(num).comsumeAmount += 0.2 * a.data;
                             CardUtil.card.get(num).realTalkTime += a.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         } else {
                             if (CardUtil.card.get(num).money < -(((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - a.data) * 0.2) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - a.data) * 0.2;
                             CardUtil.card.get(num).comsumeAmount += -(((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - a.data) * 0.2;
                             ((SuperPackage) CardUtil.card.get(num).serPackage).talkTime = 0;
                             CardUtil.card.get(num).realTalkTime += a.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 System.out.println("输入数字1继续消费，其他按键退出消费");
                 Scanner scan2 = new Scanner(System.in);
                 String k = "";
                 if (scan2.hasNext()) {
                     k = "" + scan2.next();
                     if (k.equals("1"))
                         sousou_extend(num,arrayList);
                 }
                 break;
             }
             /*  Scene b=new Scene("短信",50,"通知朋友手机换号，发送短信50条");*/
             case 2:{

                 System.out.println(b.description);

                 ConsumeInfo con = new ConsumeInfo(num, b.type, b.data);

                 if(CardUtil.card.get(num).serPackage instanceof TalkPackage)
                 {
                     if( ((TalkPackage) CardUtil.card.get(num).serPackage).smsCount-b.data>0) {
                         ((TalkPackage) CardUtil.card.get(num).serPackage).smsCount -= b.data;
                         CardUtil.card.get(num).realSMSCount += b.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((TalkPackage) CardUtil.card.get(num).serPackage).smsCount <=0) {
                             if( CardUtil.card.get(num).money< 0.1 * b.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.1 * b.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * b.data;
                             CardUtil.card.get(num).realSMSCount += b.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((TalkPackage) CardUtil.card.get(num).serPackage).smsCount - b.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((TalkPackage) CardUtil.card.get(num).serPackage).smsCount - b.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount += -(((TalkPackage) CardUtil.card.get(num).serPackage).smsCount - b.data) * 0.1;
                             ((TalkPackage) CardUtil.card.get(num).serPackage).smsCount = 0;
                             CardUtil.card.get(num).realSMSCount += b.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 if(CardUtil.card.get(num).serPackage instanceof NetPackage)
                 {
                     System.out.println("您的套餐不支持此项消费！\n");
                     sousou_extend(num,arrayList);
                     break;
                 }
                 if(CardUtil.card.get(num).serPackage instanceof SuperPackage) {
                     if (((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - b.data > 0) {
                         ((SuperPackage) CardUtil.card.get(num).serPackage).smsCount -= b.data;
                         CardUtil.card.get(num).realSMSCount += b.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((SuperPackage) CardUtil.card.get(num).serPackage).smsCount<=0) {
                             if( CardUtil.card.get(num).money<0.1*b.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.1 * b.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * b.data;
                             CardUtil.card.get(num).realSMSCount += b.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - b.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - b.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount += -(((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - b.data) * 0.1;
                             ((SuperPackage) CardUtil.card.get(num).serPackage).smsCount = 0;
                             CardUtil.card.get(num).realSMSCount += b.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 System.out.println("输入数字1继续消费，其他按键退出消费");
                 Scanner scan2 = new Scanner(System.in);
                 String k = "";
                 if (scan2.hasNext()) {
                     k = "" + scan2.next();
                     if (k.equals("1"))
                         sousou_extend(num,arrayList);

                 }
                 break;
             }

//        Scene c=new Scene("短信",5,"参与环节保护事实方案问卷调查，发送短信5条");
             case 3:{
                 System.out.println(c.description);

                 ConsumeInfo con = new ConsumeInfo(num, c.type, c.data);

                 if(CardUtil.card.get(num).serPackage instanceof TalkPackage)
                 {
                     if( ((TalkPackage) CardUtil.card.get(num).serPackage).smsCount-c.data>0) {
                         ((TalkPackage) CardUtil.card.get(num).serPackage).smsCount -= c.data;
                         CardUtil.card.get(num).realSMSCount += c.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((TalkPackage) CardUtil.card.get(num).serPackage).smsCount <=0)
                         {  if( CardUtil.card.get(num).money<0.1 * c.data) {
                             System.out.println("话费余额不足请充值后再进行消费！");
                             break;
                            }
                             CardUtil.card.get(num).money -= 0.1 * c.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * c.data;
                             CardUtil.card.get(num).realSMSCount += c.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((TalkPackage) CardUtil.card.get(num).serPackage).smsCount - c.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((TalkPackage) CardUtil.card.get(num).serPackage).smsCount - c.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount +=-(((TalkPackage) CardUtil.card.get(num).serPackage).smsCount - c.data) * 0.1;
                             ((TalkPackage) CardUtil.card.get(num).serPackage).smsCount = 0;
                             CardUtil.card.get(num).realSMSCount += c.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 if(CardUtil.card.get(num).serPackage instanceof NetPackage)
                 {   System.out.println("您的套餐不支持此项消费！\n");
                     sousou_extend(num,arrayList);
                     break;
                 }
                 if(CardUtil.card.get(num).serPackage instanceof SuperPackage) {
                     if (((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - c.data > 0) {
                         ((SuperPackage) CardUtil.card.get(num).serPackage).smsCount -= c.data;
                         CardUtil.card.get(num).realSMSCount += c.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {

                         if (((SuperPackage) CardUtil.card.get(num).serPackage).smsCount<=0)
                         { if( CardUtil.card.get(num).money<0.1*c.data) {
                             System.out.println("话费余额不足请充值后再进行消费！");
                             break;
                         }
                             CardUtil.card.get(num).money -= 0.1 * c.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * c.data;
                             CardUtil.card.get(num).realSMSCount += c.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - c.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - c.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount +=-(((SuperPackage) CardUtil.card.get(num).serPackage).smsCount - c.data) * 0.1;
                             ((SuperPackage) CardUtil.card.get(num).serPackage).smsCount = 0;
                             CardUtil.card.get(num).realSMSCount += c.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 System.out.println("输入数字1继续消费，其他按键退出消费");
                 Scanner scan2 = new Scanner(System.in);
                 String k = "";
                 if (scan2.hasNext()) {
                     k = "" + scan2.next();
                     if (k.equals("1"))
                         sousou_extend(num,arrayList);
                 }
                 break;
             }
             /*   Scene d=new Scene("通话",30,"询问母亲身体状况，本地通话30分钟");*/
             case 4:{
                 System.out.println(d.description);

                 ConsumeInfo con = new ConsumeInfo(num, d.type, d.data);

                 if(CardUtil.card.get(num).serPackage instanceof TalkPackage)
                 {
                     if( ((TalkPackage) CardUtil.card.get(num).serPackage).talkTime-d.data>0) {
                         ((TalkPackage) CardUtil.card.get(num).serPackage).talkTime -= d.data;
                         CardUtil.card.get(num).realTalkTime += d.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((TalkPackage) CardUtil.card.get(num).serPackage).talkTime <=0) {
                             if( CardUtil.card.get(num).money<0.2 * d.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.2 * d.data;
                             CardUtil.card.get(num).comsumeAmount += 0.2 * d.data;
                             CardUtil.card.get(num).realTalkTime += d.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((TalkPackage) CardUtil.card.get(num).serPackage).talkTime - d.data) * 0.2) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((TalkPackage) CardUtil.card.get(num).serPackage).talkTime - d.data) * 0.2;
                             CardUtil.card.get(num).comsumeAmount += -(((TalkPackage) CardUtil.card.get(num).serPackage).talkTime - d.data) * 0.2;
                             ((TalkPackage) CardUtil.card.get(num).serPackage).talkTime = 0;
                             CardUtil.card.get(num).realTalkTime += d.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 if(CardUtil.card.get(num).serPackage instanceof NetPackage) {
                     System.out.println("您的套餐不支持此项消费！\n");
                     sousou_extend(num,arrayList);
                     break;
                 }
                 if(CardUtil.card.get(num).serPackage instanceof SuperPackage) {
                     if (((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - d.data > 0) {
                         ((SuperPackage) CardUtil.card.get(num).serPackage).talkTime -= d.data;
                         CardUtil.card.get(num).realTalkTime += d.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((SuperPackage) CardUtil.card.get(num).serPackage).talkTime <=0) {
                             if( CardUtil.card.get(num).money< 0.2 * d.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.2 * d.data;
                             CardUtil.card.get(num).comsumeAmount += 0.2 * d.data;
                             CardUtil.card.get(num).realTalkTime += d.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - d.data) * 0.2) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - d.data) * 0.2;
                             CardUtil.card.get(num).comsumeAmount += -(((SuperPackage) CardUtil.card.get(num).serPackage).talkTime - d.data) * 0.2;
                             ((SuperPackage) CardUtil.card.get(num).serPackage).talkTime = 0;
                             CardUtil.card.get(num).realTalkTime += d.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 System.out.println("输入数字1继续消费，其他按键退出消费");
                 Scanner scan2 = new Scanner(System.in);
                 String k = "";
                 if (scan2.hasNext()) {
                     k = "" + scan2.next();
                     if (k.equals("1"))
                         sousou_extend(num,arrayList);
                 }
                 break;
             }

             /*    Scene e=new Scene("上网",500,"闲的无聊的时候刷抖音，上网流量500MB");*/
             case 5:{

                 System.out.println(e.description);

                 ConsumeInfo con = new ConsumeInfo(num, e.type, e.data);

                 if(CardUtil.card.get(num).serPackage instanceof NetPackage)
                 {
                     if( ((NetPackage) CardUtil.card.get(num).serPackage).flow-e.data>0) {
                         ((NetPackage) CardUtil.card.get(num).serPackage).flow -= e.data;
                         CardUtil.card.get(num).realFlow += e.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((NetPackage) CardUtil.card.get(num).serPackage).flow<=0) {
                             if( CardUtil.card.get(num).money<0.1 * e.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.1 * e.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * e.data;
                             CardUtil.card.get(num).realFlow += e.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((NetPackage) CardUtil.card.get(num).serPackage).flow - e.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((NetPackage) CardUtil.card.get(num).serPackage).flow - e.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount +=-(((NetPackage) CardUtil.card.get(num).serPackage).flow - e.data) * 0.1;
                             ((NetPackage) CardUtil.card.get(num).serPackage).flow = 0;
                             CardUtil.card.get(num).realFlow += e.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 if(CardUtil.card.get(num).serPackage instanceof TalkPackage) {
                     System.out.println("您的套餐不支持此项消费！\n");
                     sousou_extend(num,arrayList);
                     break;
                 }
                 if(CardUtil.card.get(num).serPackage instanceof SuperPackage) {
                     if (((SuperPackage) CardUtil.card.get(num).serPackage).flow - e.data > 0) {
                         ((SuperPackage) CardUtil.card.get(num).serPackage).flow -= e.data;
                         CardUtil.card.get(num).realFlow += e.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((SuperPackage) CardUtil.card.get(num).serPackage).flow <=0) {
                             if( CardUtil.card.get(num).money<0.1 * e.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.1 * e.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * e.data;
                             CardUtil.card.get(num).realFlow += e.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((SuperPackage) CardUtil.card.get(num).serPackage).flow - e.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((SuperPackage) CardUtil.card.get(num).serPackage).flow - e.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount +=-(((SuperPackage) CardUtil.card.get(num).serPackage).flow - e.data) * 0.1;
                             ((SuperPackage) CardUtil.card.get(num).serPackage).flow = 0;
                             CardUtil.card.get(num).realFlow += e.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 System.out.println("输入数字1继续消费，其他按键退出消费");
                 Scanner scan2 = new Scanner(System.in);
                 String k = "";
                 if (scan2.hasNext()) {
                     k = "" + scan2.next();
                     if (k.equals("1"))
                         sousou_extend(num,arrayList);
                 }
                 break;
             }

             /*  Scene f=new Scene("上网",30,"上网查资料，耗费流量30MB");*/
             case 6:
             {
                 System.out.println(f.description);

                 ConsumeInfo con = new ConsumeInfo(num, f.type, f.data);

                 if(CardUtil.card.get(num).serPackage instanceof NetPackage)
                 {
                     if( ((NetPackage) CardUtil.card.get(num).serPackage).flow-f.data>0) {
                         ((NetPackage) CardUtil.card.get(num).serPackage).flow -= f.data;
                         CardUtil.card.get(num).realFlow += f.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((NetPackage) CardUtil.card.get(num).serPackage).flow<=0) {
                             if( CardUtil.card.get(num).money<0.1 * f.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.1 * f.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * f.data;
                             CardUtil.card.get(num).realFlow += f.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((NetPackage) CardUtil.card.get(num).serPackage).flow - f.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((NetPackage) CardUtil.card.get(num).serPackage).flow - f.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount +=-(((NetPackage) CardUtil.card.get(num).serPackage).flow - f.data) * 0.1;
                             ((NetPackage) CardUtil.card.get(num).serPackage).flow = 0;
                             CardUtil.card.get(num).realFlow += f.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 if(CardUtil.card.get(num).serPackage instanceof TalkPackage)
                 {
                     System.out.println("您的套餐不支持此项消费！\n");
                     sousou_extend(num,arrayList);
                     break;
                 }
                 if(CardUtil.card.get(num).serPackage instanceof SuperPackage) {
                     if (((SuperPackage) CardUtil.card.get(num).serPackage).flow - f.data > 0) {
                         ((SuperPackage) CardUtil.card.get(num).serPackage).flow -= f.data;
                         CardUtil.card.get(num).realFlow += f.data;
                         arrayList.add(con);
                         System.out.println("已添加一条消费记录");
                     }
                     else {
                         if (((SuperPackage) CardUtil.card.get(num).serPackage).flow <=0) {
                             if( CardUtil.card.get(num).money< 0.1 * f.data) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money -= 0.1 * f.data;
                             CardUtil.card.get(num).comsumeAmount += 0.1 * f.data;
                             CardUtil.card.get(num).realFlow += f.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                         else {
                             if( CardUtil.card.get(num).money<-(((SuperPackage) CardUtil.card.get(num).serPackage).flow - f.data) * 0.1) {
                                 System.out.println("话费余额不足请充值后再进行消费！");
                                 break;
                             }
                             CardUtil.card.get(num).money += (((SuperPackage) CardUtil.card.get(num).serPackage).flow - f.data) * 0.1;
                             CardUtil.card.get(num).comsumeAmount +=  -(((SuperPackage) CardUtil.card.get(num).serPackage).flow - f.data) * 0.1;
                             ((SuperPackage) CardUtil.card.get(num).serPackage).flow = 0;
                             CardUtil.card.get(num).realFlow += f.data;
                             arrayList.add(con);
                             System.out.println("已添加一条消费记录");
                         }
                     }
                 }
                 System.out.println("输入数字1继续消费，其他按键退出消费");
                 Scanner scan2 = new Scanner(System.in);
                 String k = "";
                 if (scan2.hasNext()) {
                     k = "" + scan2.next();
                     if (k.equals("1"))
                         sousou_extend(num,arrayList);
                 }
                 break;
             }
             default:
                 break;
         }
     }
    }
 public class first {
    public static void main(String []args){
        MainMenu1 menu=new MainMenu1();
    }
}