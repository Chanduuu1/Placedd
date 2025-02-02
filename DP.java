import java.util.*;
public class DP{
    

    //climbing stairs
    public static int countWays(int n){
        // base case
        if(n==0){
            return 1;
        }
        if(n<0){
            return 0;
        }
        return countWays(n-1) + countWays(n-2); 
    }
    public static int countWaysMemoized(int n, int ways[]){
        // base case
        if(n==0){
            return 1;
        }
        if(n<0){
            return 0;
        }
        if(ways[n] != 0){
            return ways[n]; /// will return ways till nth step directly instead of rec
        }
        ways[n] = countWaysMemoized(n-1,ways) + countWaysMemoized(n-2,ways); 
        return ways[n];
    }
    public static int countWaysTabluation(int n, int ways[]){
        // intialization
        ways[0] = 1; // ground floor pr hi rehne ke 1 tareke hai wahi raho
        ways[1] = 1;
        for(int  i = 2; i <= n; i++){
            ways[i] = ways[i-1] + ways[i-2];
        }

        return ways[n];
    }


    // 0-1 knapsack normal recursion (bohot kuch documentation likh diya hai, sirf code dekhna ho  toh comments remove kardena kahi aur copy paste karkr)
    public static int knapsack(int val[], int wt[], int W, int n){
        // base case
        if(W == 0 || n == 0){
            return 0; // (feel nahi aaya toh) samaj initially hi knapsack ka capacity 0 diya hai, ya shuru se hi koi items hi nahi hai jisme hum yeh sab perform kare, so donno
                      // hi casses me total profit 0 hi aaega na, toh usko yaha mimic karwaya hia
        }

        if(wt[n-1] <= W){ // n-1 isilye because 1st item 0th index me stored hoga,similarly last item n-1th index me stored hoga
            //include kar rahe hai toh mera value + aage jo bhi dalega woh
            int ans1 = val[n-1] + knapsack(val, wt, W - wt[n-1], n-1);
            
            // me exclude bhi kar sakta hu, agar exclude kiya toh jo exclude hua hai (current element) uska value count nahi hoga instead jo aage aaega woh
            int ans2 = knapsack(val, wt, W, n-1);


            // abh dekhna hai , include kara toh faida hua ki exclude kara toh
            return Math.max(ans1,ans2);
        }

        else{ // mtlb item ko kanpsack me jaga nahi, tabh toh exclude k=hi karna padega
            return knapsack(val, wt, W, n-1); //kuch nahi aage badho esa
        }       
    }
    public static int knapsackMemoization(int val[], int wt[], int W, int n, int dp[][]){ // O(n * W)
        // base case
        if(W == 0 || n == 0){
            return 0; 
        }

        if(dp[n][W] != -1){
            return dp[n][W];
        }

        if(wt[n-1] <= W){    
            int ans1 = val[n-1] + knapsackMemoization(val, wt, W - wt[n-1], n-1,dp);

            int ans2 = knapsackMemoization(val, wt, W, n-1,dp);

            dp[n][W] =  Math.max(ans1,ans2);
            return dp[n][W];
        }

        else{ 
            dp[n][W] = knapsackMemoization(val, wt, W, n-1,dp); 
            return dp[n][W];
        }       
    }
    public static int knapsackTabulation(int val[], int wt[], int W){
        int n = val.length;
        int dp[][] = new int[n+1][W+1];

        //step1 initialization
        for(int i = 0; i < dp.length; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j < dp[0].length; j++){
            dp[0][j] = 0;
        }

        // tabulating
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < W+1; j++){
                // find value and wieght 
                int v = val[i-1]; // value of ith element
                int w = wt[i-1]; // wt of " " " "
                if(w <= j){  //valid
                
                // apna toh include hi karlo + aage dekh i-1 elemets ko j-w capacity se max profit kese nikale? uske liye uska table pr search kr details and buid
                int incldProfit = v + dp[i-1][j-w];
                // me excld kar kr baaki possibilites explore kr raha
                int excldProfit = dp[i-1][j];


                //anth me jo zyada profitable!
                dp[i][j] = Math.max(incldProfit,excldProfit);
                }

                else{
                    int excldProfit = dp[i-1][j];
                    dp[i][j] = excldProfit;
                }
            }
        }


        // just for clarity dp matrix ko dekhle 
        printDP(dp);

        return dp[n][W]; // as expected, ans ekdum nichle right corner me hoga
    }
    public static void printDP(int dp[][]){
        for(int i = 0; i < dp.length; i++){
            for(int j = 0; j < dp[0].length; j++){
                System.out.print(dp[i][j]+ " ");
            }
            System.out.println();
            
        }
    }
    

    // Target sum tabulation
    public static boolean targetSumTabulation(int arr[], int sum){ // )(n * Targetsum)
        int n = arr.length;
        boolean dp[][] = new boolean[n+1][sum+1];
        // base case initialization. *Note - sirf trye wala initialzation karna, because the entire 2d array is by default initializaed with false so wapas false ke jaga pr false initialze karna iss....
        // i = items, j = tS
        for(int i = 1; i < n+1; i++){
            dp[i][0] = true;
        }

        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < sum+1; j++){
                int v = arr[i-1];
                //incld
                if(v <= j && dp[i-1][j-v] == true){
                    dp[i][j] = true;
                }
                //excld
                else if(dp[i-1][j] == true){
                    dp[i][j] = true;
                }

            }
        }

        printDP2(dp);
        return dp[n][sum];
    }
    public static void printDP2(boolean dp[][]){
        for(int i = 0; i < dp.length; i++){
            for(int j = 0; j < dp[0].length; j++){
                System.out.print(dp[i][j]+ " ");
            }
            System.out.println();
            
        }
    }



    // coin change 
    public static int coinChange(int coins[], int sum){
        int n = coins.length;
        int dp[][] = new int[n+1][sum+1];

        //initializse - sum/billAmt is 0
        // i -> coins; j -> sum/change
        for(int i = 0; i < n+1; i++){
            dp[i][0] = 1;
        }
        //by default 0 assigned karta hai java we need not do it but still kayda maintain
        for(int j = 1; j < sum+1; j++){
            dp[0][j] = 0;
        }

        // filling!
        //O(n*SUM)
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < sum+1; j++){
                if(coins[i-1] <= j){ //valid
                    dp[i][j] = dp[i][j-coins[i-1]] + dp[i-1][j];
                }
                else{ //invalid
                    dp[i][j] = dp[i-1][j];
                }

            }
        }

        return dp[n][sum];
    }


    //Rod cutting
    public static int rodCutting(int lengthOfRod[], int price[], int rodLength){
        int n = price.length;
        int dp[][] = new int[n+1][rodLength+1];
        // initilaize step me 0 se hi initialize karna hai jojava alsready kr bheta hai so need to do it again
        for(int i = 1; i<n+1; i++){
            for(int j = 1; j < rodLength+1; j++){
                //valid
                if(lengthOfRod[i-1] <= j){
                    dp[i][j] = Math.max(price[i-1] + dp[i][j-lengthOfRod[i-1]], dp[i-1][j]);
    
                }
                //invalid
                else{
                    dp[i][j] = dp[i-1][j];
               }
            }
        }   
        return dp[n][rodLength];
    }


    //lcs
    public static int lcs(String str1,  int n, String str2, int m){
        if(n == 0 || m == 0){
            return 0;
        }

        if(str1.charAt(n-1) == str2.charAt(m-1)){
            return lcs(str1,n-1,str2,m-1) + 1; // same wali case
        }
        else{
            int ans1 = lcs(str1, n-1, str2,m);
            int ans2 = lcs(str1, n, str2,m-1);
            return Math.max(ans1,ans2);
        }
    }
    //lcs memoization
    public static int lcsMemoization(String str1,  int n, String str2, int m, int dp[][]){
        if(n == 0 || m == 0){
            return 0;
        }

        if(dp[n][m] != -1){
            return dp[n][m];
        }
        if(str1.charAt(n-1) == str2.charAt(m-1)){
            dp[n][m] = lcsMemoization(str1,n-1,str2,m-1,dp) + 1; // same wali case
            return dp[n][m];
        }
        else{
            int ans1 = lcsMemoization(str1, n-1, str2,m,dp);
            int ans2 = lcsMemoization(str1, n, str2,m-1,dp);
            dp[n][m] = Math.max(ans1,ans2);
            return dp[n][m];
        }
    }
    //lcs Tabluation O(n*m)
    public static int lcsTabluation(String str1, String str2){
        int n = str1.length();
        int m = str2.length();
        
        int dp[][] = new int[n+1][m+1];
        // initialize with 0 base case
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < m+1; j++){
                if(i==0 || j==0){
                    dp[i][j] = 0;
                }
            }
        }

        //fill
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < m+1; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else{
                    int ans1 = dp[i-1][j];
                    int ans2 = dp[i][j-1];
                    dp[i][j] = Math.max(ans1,ans2);
                }
            }
        }

        return dp[n][m];
    }



    //longest common subStirng
    public static int lcsubStr(String str1, String str2){
        int n = str1.length();
        int m = str2.length();
        int finalAns = 0;

        int dp[][] = new int[n+1][m+1];
        // initialize with 0 base case
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < m+1; j++){
                if(i==0 || j==0){
                    dp[i][j] = 0;
                }
            }
        }

        //fill
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < m+1; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                    finalAns = Math.max(finalAns, dp[i][j]); // kya muje naya longest mila? yeh check karne ke liye
                }
                else{
                    dp[i][j] = 0;
                }
            }
        }
        return finalAns;
    }



    // LIS
    public static int lis(int arr[]){
        // for unique elements, platforms me sab corner cases jesa hota hai jaha duplicate de dete hai values
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < arr.length; i++){
            set.add(arr[i]);
        }

        int arr2[] = new int[set.size()]; 
        int i = 0;
        for(int num : set){
            arr2[i] = num;
            i++;
        } // sare unique elemsnts ko store karwa diya

        Arrays.sort(arr2); // ascending

        return lcsOnArr(arr, arr2);
    }
    public static int lcsOnArr(int arr1[], int arr2[]){
        int n = arr1.length;
        int m = arr2.length;
        
        int dp[][] = new int[n+1][m+1];
        // initialize with 0 base case
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < m+1; j++){
                if(i==0 || j==0){
                    dp[i][j] = 0;
                }
            }
        }

        //fill
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < m+1; j++){
                if(arr1[i-1] == arr2[j-1]){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else{
                    int ans1 = dp[i-1][j];
                    int ans2 = dp[i][j-1];
                    dp[i][j] = Math.max(ans1,ans2);
                }
            }
        }

        return dp[n][m];
    }
    


    // edit distance
    public static int editDistance(String s1, String s2){
        int n = s1.length();
        int m = s2.length();
        int dp[][] = new int[n+1][m+1];

        // initalize
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < m+1; j++){
                if(i == 0){
                    dp[i][j] = j;
                }
                if(j == 0){
                    dp[i][j] = i;
                }
            }
        }

        //bottom up step
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < m+1; j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }
                else{
                    int add = dp[i][j-1] + 1;
                    int del = dp[i-1][j] + 1;
                    int rep = dp[i-1][j-1] + 1;
                    dp[i][j] = Math.min(add,Math.min(del,rep));

                }
            }
        }

        return dp[n][m];
    }


    // string converson using lcs logic
    // string conversion using edit distance logic
    // do both hw diya hai di ne
    


    //wildcard matchi
    // Hard level
    //O(n*m)
    public static boolean isMatch(String s, String p){
        int n = s.length();
        int m = p.length();
        boolean dp[][] = new boolean[n+1][m+1];

        // initialize
        dp[0][0] = true;
        //pattern = " "
        for(int i = 1; i < n+1; i++){
            dp[i][0] = false;
        }   
        //s = " "
        for(int j = 1; j < m + 1; j++){
            if(p.charAt(j-1) == '*')
            dp[0][j] = dp[0][j-1];
        }


        //fill code bottom up
        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < m+1; j++){
                // case -> ith char == jth char || jth char = ?
                if(s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?'){
                    dp[i][j] = dp[i-1][j-1];
                }
                else if(p.charAt(j-1) == '*'){
                    dp[i][j] = dp[i-1][j] || dp[i][j-1];
                } 
                else{
                    dp[i][j] = false;
                }
            }
        }


        return dp[n][m];
    }


    // catlan number using rec
    public static int catalanRec(int n){
        if(n==0 || n ==1){
            return 1;
        }
        int ans = 0; // Cn
        for(int i = 0; i <= n -1; i++){
            ans += catalanRec(i) * catalanRec(n-i-1);
        }

        return ans;
    }
    // catlan number using memoization
    public static int catalanMem(int n, int[]dp){
        if(n==0 || n ==1){
            return 1;
        }
        if(dp[n] != -1){
            return dp[n];
        }
        int ans = 0; // Cn
        for(int i = 0; i <= n -1; i++){
            ans += catalanMem(i,dp) * catalanMem(n-i-1,dp);
            
        }
        dp[n] = ans;
    
        return dp[n];
    }

    //catlan using tabilation O(n^2)
    public static int catalanTab(int n){
        int dp[] = new int[n+1];
        dp[0]= 1;
        dp[1]= 1;

        for(int i = 2; i <= n; i++){
            for(int j = 0; j < i; j++){
                dp[i] += dp[j] * dp[i-j-1];
            }
        }

        return dp[n];
    }


    // count BST
    public static int countBst(int n){
        int dp[] = new int[n+1];
        dp[0]= 1;
        dp[1]= 1;

        for(int i = 2; i <= n; i++){
            //Ci -> BST (i node) -> dp[i]
            for(int j = 0; j < i; j++){
                int left = dp[j];
                int right = dp[i-j-1];
                dp[i] += left * right;
            }
        }

        return dp[n];
    }


    //Mountain ranges
    public static int mountainRanges(int n){
        int dp[] = new int[n+1];
        dp[0]= 1;
        dp[1]= 1;

        for(int i = 2; i <= n; i++){
            //i pairs -> mountain ranges => Ci
            for(int j = 0; j < i; j++){
                int inside = dp[j];
                int outside = dp[i-j-1];
                dp[i] += inside * outside; // ci = cj * ci - j - 1;
            }
        }

        return dp[n];
    }



    //mcm rec
    public static int mcm(int[] arr, int i, int j){
        if(i == j){
            return 0;
        }

        int finalAns = Integer.MAX_VALUE;
        for(int k = i; k <= j-1; k++){
            int cost1 = mcm(arr, i, k); // Ai...Ak => arr[i-1] x arr[k]
            int cost2 = mcm(arr, k+1, j);  // Ai+1...Ak => arr[k] x arr[j]
            int cost3 = arr[i-1] * arr[k] * arr[j];
            int totalCost = cost1 + cost2 + cost3;
            finalAns = Math.min(finalAns, totalCost);
        }

        return finalAns;
    }
    //mcm mem
    public static int mcmMem(int[][] dp, int[] arr, int i, int j){
        if(i == j){
            return 0;
        }

        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int finalAns = Integer.MAX_VALUE;
        for(int k = i; k <= j-1; k++){
            int cost1 = mcmMem(dp,arr, i, k); // Ai...Ak => arr[i-1] x arr[k]
            int cost2 = mcmMem(dp,arr, k+1, j);  // Ai+1...Ak => arr[k] x arr[j]
            int cost3 = arr[i-1] * arr[k] * arr[j];
            int totalCost = cost1 + cost2 + cost3;
            finalAns = Math.min(finalAns, totalCost);
        }
        dp[i][j] = finalAns;
        return dp[i][j];
    }
    public static int mcmTab(int arr[]){
        int n = arr.length;
        int dp[][] = new int[n][n];

        // initialization
        for(int i = 0; i < n; i++){
            dp[i][i] = 0;
        }

        //botom up
        for(int len = 2; len <= n-1; len++){
            for(int i = 1; i <= n - len; i++){
                int j = i + len -1; // col
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = i; k <= j-1; k++){
                    int cost1 = dp[i][k];
                    int cost2 = dp[k+1][j];
                    int cost3 = arr[i-1] * arr[k] * arr[j];
                    dp[i][j] = Math.min(dp[i][j], cost1 + cost2 + cost3);
                }
            }
        }

        // aswer stored in
        return dp[1][n-1];

    }



    // Minimum partioning question
    public static int minPartition(int arr[]){
        int n = arr.length;
        int sum = 0;
        for(int i = 0; i <arr.length; i++){
            sum += arr[i];
        }

        // W = sum/2
        int W = sum/2;

        int dp[][] = new int[n+1][W+1];
        // skinpping the initialization step because no use in java, inbuilt 0 hi hota

        for(int i = 1; i < n+1; i++){
            for(int j = 1; j < W+1; j++){
                if(arr[i-1] <= j){ // valid
                    dp[i][j] = Math.max(arr[i-1] + dp[i-1][j-arr[i-1]], dp[i-1][j]);
                }
                else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        int sum1 = dp[n][W];
        int sum2 = sum - sum1;
        return Math.abs(sum1-sum2);

    }



    public static int minJumps(int nums[]){
        int n = nums.length;
        int dp[]  = new int[n];
        Arrays.fill(dp,-1);
        dp[n-1] = 0;

        for(int i = n-2; i >= 0; i--){
            int steps = nums[i];
            int ans = Integer.MAX_VALUE;
            for(int j=i+1; j <= i+steps && j < n; j++){
                if(dp[j] != -1){
                    ans = Math.min(ans, dp[j] + 1);
                }
            }
            if(ans != Integer.MAX_VALUE){
                dp[i] = ans;
            }    
        }

        // final ans yahi hoga , see cell meaning
        return dp[0];
    }


    public static void main(String args[]){
        int[] arr = {2,3,1,1,4};
        System.out.println(minJumps(arr));
        
        

    }
}