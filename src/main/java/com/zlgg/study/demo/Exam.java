package com.zlgg.study.demo;

import cn.hutool.core.date.TimeInterval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author: wzl
 * Date: 2021-07-29
 * Time: 21:18
 * Description:
 */
public class Exam {

    //[1,2,3,5,8,9] 8    [2,3]

    //[9,2,3,0,0] 3 [1,2,3] 3   [1,2,2,3,3,9]

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws CloneNotSupportedException {
        int num = 1000000;
        ListNode listNode=new ListNode(num);
        ListNode orign = (ListNode) listNode.clone();
        while (num!=0) {
            num--;
            ListNode listNode1 = new ListNode(num);
            listNode.next = listNode1;
            listNode = listNode1;
        }
        System.out.println("结束");

        TimeInterval timeInterval = new TimeInterval();
        List<Integer> val = new ArrayList<>();
        val.add(listNode.val);
        ListNode next = listNode.getNext();
        while (next!=null) {
            val.add(next.val);
            next = next.getNext();
        }
        Collections.reverse(val);
        System.out.println(val);
        long l = timeInterval.intervalRestart();
        System.out.println(l/1000+"s");

        ListNode znext=null;
        ListNode pre=null;
        while(listNode!=null){
            znext=listNode.next;
            listNode.next=pre;
            pre=listNode;
            listNode=znext;
        }
        long l2 = timeInterval.interval();
        System.out.println(l2/1000+"s");
    }

    /**
     * 判断一个数是否是回文数 方案一
     * @return
     */
    private static boolean wayOne(int n) {
        String inpNum = n + "";
        int m = (int) Math.floor(inpNum.length()/2);
        char[] chars = inpNum.toCharArray();
        boolean result = true;
        for (int i = 0; i < m; i++) {
            char first = chars[i];
            char last = chars[chars.length-i-1];
            if (first!=last) {
               result = false;
            }
        }
        return result;
    }

    /**
     * 判断一个数是否是回文数 方案二
     * @return
     */
    private static boolean wayOne2(int n) {
        if(n<0) return false;
        int pre=0;
        int a=n;
        while (n!=0){
            pre=pre*10+n%10;
            n=n/10;
        }
        return pre== a?true:false;
    }
}
