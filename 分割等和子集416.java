import org.junit.Test;

/*
* 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
注意:
每个数组中的元素不会超过 100
数组的大小不会超过 200
*
示例 1:
输入: [1, 5, 11, 5]
输出: true
解释: 数组可以分割成 [1, 5, 5] 和 [11].
 

示例 2:
输入: [1, 2, 3, 5]
输出: false
解释: 数组不能分割成两个元素和相等的子集.

* */
public class 分割等和子集416 {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
        }
        if (sum % 2 == 1){
            return false;
        }else {
            int target = sum / 2;
            return subset(nums, nums.length - 1, target);
        }
    }
    public boolean subset(int[] nums, int i, int target){
        if (target == 0){
            return true;
        }else if (nums[i] > target){
            return subset(nums, i - 1, target);
        }else if (i == 0){
          return nums[0] == target; // 如果到了最后一个，return语句返回true， 则符合
        } else {
            boolean a = subset(nums, i - 1, target - nums[i]); // 选
            boolean b = subset(nums, i - 1, target);
            return a || b;
        }
    }

    @Test
    public void test(){
        int[] nums = {1, 5, 11, 5};
        boolean b = canPartition(nums);
        System.out.println(b);
    }

    public boolean another(int[] nums){
        int len = nums.length;
        if (len < 2){
            return false;
        }else {
            int sum = 0, maxNum = 0;
            for (int num : nums){
                sum += num;
                maxNum = Math.max(maxNum, num);
            }
            if (sum % 2 != 0){
                return false;
            }else {
                int target = sum / 2;
                if (maxNum >target){
                    return false;
                }else {
                    boolean[][] dp = new boolean[len][target + 1];
                    for (int i = 0; i < len; i++){
                        dp[i][0] = true;
                    }
                    dp[0][nums[0]] = true; // nums[i] = target-> true
                    for (int i = 0; i < len; i++) {
                        int num = nums[i];
                        for (int j = 0; j <= target; j++) {
                            if (j >= num){
                                dp[i][j] = dp[i - 1][j] | dp[i - 1][j - num];
                            }else { // 不满足情况
                                dp[i][j] = dp[i-1][j];
                            }
                        }
                    }
                    return dp[len - 1][target];
                }
            }
        }
    }
}
