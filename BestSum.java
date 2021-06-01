import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * 
 */
public class BestSum {


    /**
     * The function should return an array containing the shortest
     * combination of numbers that add up exactly to the target sum.
     * 
     * If there is a time for the shortest combination, you may return any
     * one of the shortest.
     * 
     * Recursive call - brute force (no memoization).
     * 
     * m = target sum
     * n = numbers.length
     * Time: O(n^m * m)  Space: O(m^2)
     */
    static int[] bestSum(int targetSum, int[] numbers) {

        // **** base case(s) ****
        if (targetSum == 0) return new int[0];
        if (targetSum < 0) return null;

        // **** initialization***
        int[] ans = null;

        // **** loop recursing for each value in numbers ****
        for (int i = 0; i < numbers.length; i++) {

            // **** remove this number from the target sum ****
            int rem = targetSum - numbers[i];

            // **** recursive call ****
            int[] arr = bestSum(rem, numbers);

            // **** check this array (if needed) ****
            if (arr != null) {

                // **** create and populate int[] tmp ****
                int[] tmp = new int[arr.length + 1];
                for (int j = 0; j < arr.length; j++)
                    tmp[j] = arr[j];
                tmp[arr.length] = numbers[i];
    
                // **** replace arr with tmp ****
                arr = tmp;

                // **** update answer (if needed) ****
                if (ans == null || arr.length < ans.length)
                    ans = arr;
            }
        }

        // **** return answer ****
        return ans;
    }


    /**
     * The function should return an array containing the shortest
     * combination of numbers that add up exactly to the target sum.
     * 
     * If there is a time for the shortest combination, you may return any
     * one of the shortest.
     * 
     * Entry point for recursion using memoization.
     * 
     * m = target sum
     * n = numbers.length
     * Time: O(m ^2 * n)  Space: O(m^2)
     */
    static int[] bestSumMemo(int targetSum, int[] numbers) {

        // **** sanity check(s) ****
        if (targetSum == 0) return new int[0]; 

        // **** initialization *****
        HashMap<Integer, List<Integer>> memo = new HashMap<>();

        // **** start recursion ****
        List<Integer> lst = bestSumMemo(targetSum, numbers, memo);

        // **** return answer ****
        return lst == null ? null : lst.stream().mapToInt(i -> i).toArray();
    }


    /**
     * Recursive call with memoization.
     */
    static List<Integer> bestSumMemo(   int targetSum,
                                        int[] numbers,
                                        HashMap<Integer, List<Integer>> memo) {

        // **** base case(s) ****
        if (memo.containsKey(targetSum)) return memo.get(targetSum);
        if (targetSum == 0) return new ArrayList<Integer>(); 
        if (targetSum < 0) return null;

        // **** initialization ****
        List<Integer> shortestCombination = null;

        // **** loop once per int in numbers ****
        for (int i = 0; i < numbers.length; i++) {

            // **** compute reminder ****
            int rem = targetSum - numbers[i];

            // **** generate remainder combination ****
            List<Integer> remainderCombination = bestSumMemo(rem, numbers, memo);

            // **** check for possible answer ****
            if (remainderCombination != null) {

                // **** make a copy of the remainder combination ****
                List<Integer> combination = new ArrayList<>(remainderCombination);

                // **** add current number ****
                combination.add(numbers[i]);

                // **** update the shortes combination (if needed) ****
                if (shortestCombination == null || combination.size() < shortestCombination.size())
                    shortestCombination = combination;
            }
        }
        
        // **** save for later use ****
        memo.put(targetSum, shortestCombination);

        // **** return shortest combination ****
        return shortestCombination;
    }


    /**
     * Test scaffold
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // **** initialization ****
        int[] ans       = null;
        long startTime  = -1;
        long endTime    = -1;

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read target sum ****
        int targetSum = Integer.parseInt(br.readLine().trim());

        // **** read int[] numbers ****
        int[] numbers = Arrays.stream(br.readLine().trim().split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        // **** close buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< targetSum: " + targetSum);
        System.out.println("main <<< numbers: " + Arrays.toString(numbers));


        // **** generate answer without memo ****
        startTime = System.currentTimeMillis();
        ans = bestSum(targetSum, numbers);
        endTime = System.currentTimeMillis();

        // **** display answer ****
        System.out.println("main <<< bestSum: " + Arrays.toString(ans));
        System.out.println("main <<< executionTime: " + (endTime - startTime) + " ms");


        // **** generate answer with memo ****
        startTime = System.currentTimeMillis();
        ans = bestSumMemo(targetSum, numbers);
        endTime = System.currentTimeMillis();

        // **** display answer ****
        System.out.println("main <<< bestSumMemo: " + Arrays.toString(ans));
        System.out.println("main <<< executionTime: " + (endTime - startTime) + " ms");
    }
}