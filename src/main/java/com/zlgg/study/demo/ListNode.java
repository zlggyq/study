package com.zlgg.study.demo;

/**
 * @author: wzl
 * Date: 2021-07-29
 * Time: 22:16
 * Description:
 */
public class ListNode implements Cloneable {
    public Integer val;

    public ListNode next;

    public ListNode(Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }
}
