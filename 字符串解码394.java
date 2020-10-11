import java.util.LinkedList;

public class 字符串解码394 {
    /*
    给定一个经过编码的字符串，返回它解码后的字符串。

编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。

你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。

此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。

示例 1：
输入：s = "3[a]2[bc]"
输出："aaabcbc"

示例 2：
输入：s = "3[a2[c]]"
输出："accaccacc"

示例 3：
输入：s = "2[abc]3[cd]ef"
输出："abcabccdcdcdef"

示例 4：
输入：s = "abc3[cd]xyz"
输出："abccdcdcdxyz"

     */

    public String decodeString(String s) {
        LinkedList<String> stack = new LinkedList<>();
        int len = s.length();
        int ptr = 0;
        while (ptr < len){
            char ch = s.charAt(ptr++);

            if (Character.isDigit(ch)){
                int from = ptr --;
                while (Character.isDigit(s.charAt(ptr))){ // 若有连续数字，例如23,将其连续找出
                    ++ptr;
                }
                stack.addLast(s.substring(from, ptr));

            }else if (Character.isLetter(ch) || ch == '['){
                stack.addLast(String.valueOf(ch));//转换为字符串

            }else {                                         // ] -> [ 取方括号内的元素
                LinkedList<String> sub = new LinkedList<>();
                while (!"[".equals(stack.peekLast())){
                    sub.addFirst(stack.removeLast()); // 反转添加
                }

                String str = String.join("", sub); // 将[]内的元素进行拼接-> 待重复元素

                stack.removeLast(); // 左括号出栈

                // 左括号前面，一定是一个数字,代表重复次数

                int repeat = Integer.valueOf(stack.removeLast());
                StringBuilder stringBuilder = new StringBuilder(str.length() * repeat);// 设定长度，带重读元素长度 * 重复次数
                while (repeat --> 0){ // 重复
                    stringBuilder.append(str);
                }
                stack.addLast(stringBuilder.toString());
            }
        }
        return String.join("", stack);
    }
}
