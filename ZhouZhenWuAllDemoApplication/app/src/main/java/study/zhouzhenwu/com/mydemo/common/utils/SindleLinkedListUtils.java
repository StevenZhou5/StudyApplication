package study.zhouzhenwu.com.mydemo.common.utils;

import study.zhouzhenwu.com.mydemo.common.module.SingleLinkedListNode;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/11
 * 类简介：和单向链表操作相关的工具类
 */

public class SindleLinkedListUtils {
    /**
     * 将单列表从节点位置之后的所有节点的value转换成用"->"分割的String
     *
     * @param targetNode 目标节点
     * @return
     */
    public static String listNodeToString(SingleLinkedListNode targetNode) {
        StringBuilder sb = new StringBuilder();
        SingleLinkedListNode target = targetNode;
        while (target != null) {
            sb.append(target.value + "->");
            target = target.next;
        }
        return sb.subSequence(0, sb.length() - 2).toString();
    }

    /**
     * 初始化一个随机单链表
     *
     * @return
     */
    public static SingleLinkedListNode initSingleLinkedLists() {
        int maxValue = 10; // 默认最大值为10；
        int length = (int) (Math.random() * 9) + 1; // 默认长度为1-10中的随机长度
        return initSingleLinkedLists(maxValue, length);
    }

    public static SingleLinkedListNode initSingleLinkedLists(int maxValue, int length) {
        SingleLinkedListNode root = new SingleLinkedListNode((int) (Math.random() * maxValue));
        SingleLinkedListNode target = root;
        for (int i = 0; i < length; i++) {
            target.next = new SingleLinkedListNode((int) (Math.random() * maxValue));
            target = target.next;
        }
        return root;
    }

    /*---------------------------- 单向链表反转 -------------------------- */

    /**
     * 递归法反转
     *
     * @param root
     */
    public static SingleLinkedListNode reverse(SingleLinkedListNode root) {
        SingleLinkedListNode resultNode;
        if (root.next == null) {
            resultNode = root; // 反转后的链表的根节点是开始链表的尾节点
            return resultNode;
        }
        SingleLinkedListNode next = root.next; // 暂存next节点（这个next节点反转后将会变成翻转后链表的尾节点，随意反转方法不需要返回值）
        resultNode = reverse(next); // （递归核心）反转以next节点为根节点的
        root.next = null;  // 当前节点的next置为空，他将作为反转后链表的尾节点
        next.next = root; // 将反转后的next节点的指向root完成反转
        return resultNode;
    }



    /*---------------------------- 两个单向链表相加 -------------------------- */

    /**
     * 将两个单向链表相加
     *
     * @param l1 1—>3-8
     * @param l2 2->7-5
     * @return 3->0->4->1
     */
    public static SingleLinkedListNode addTwoNumbers(SingleLinkedListNode l1, SingleLinkedListNode l2) {
        // step1：初始化要计算过程中要用到的临时成员变量
        SingleLinkedListNode currentNode1 = l1; // 目标1节点
        SingleLinkedListNode currentNode2 = l2; // 目标2节点
        SingleLinkedListNode currentResultNode = new SingleLinkedListNode(0);// 存放当结算结果的Node
        SingleLinkedListNode resultNode = currentResultNode;
        int current = 0; // 暂存加和进位

        // step2：进行逐步遍历计算
        while (currentNode1 != null || currentNode2 != null || current != 0) {
            int sum = sumValue(currentNode1, currentNode2) + current; // 计算当前数字加和
            int value1 = sum % 10; // 个位数
            current = sum / 10; // 10位数

            currentResultNode.value = value1;

            currentNode1 = currentNode1 == null ? null : currentNode1.next;
            currentNode2 = currentNode2 == null ? null : currentNode2.next;
            if (currentNode1 != null || currentNode2 != null || current != 0) {
                currentResultNode.next = new SingleLinkedListNode(0);
                currentResultNode = currentResultNode.next;
            }
        }
        currentResultNode.next = null;
        return resultNode;

    }

    private static int sumValue(SingleLinkedListNode l1, SingleLinkedListNode l2) {
        int i1 = l1 == null ? 0 : l1.value;
        int i2 = l2 == null ? 0 : l2.value;
        return i1 + i2;
    }
}
