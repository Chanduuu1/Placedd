import java.util.*;
public class GreedyAlgos{

    public static void activitySelection(int[] start, int[] end){
        int activities[][] = new int[end.length][3];
        for(int i = 0; i < start.length; i++){
            activities[i][0] = i;  // indices stored so later can retrive the correct act no after sorting
            activities[i][1] = start[i];
            activities[i][2] = end[i];
        }

        // Sorting this 2d-array on the basis of end time
        //lambda function
        Arrays.sort(activities, Comparator.comparingDouble(o -> o[2]));

        //now once they have been sorted apna normal greedy approach
        int maxAct = 0;
        ArrayList<Integer> ans = new ArrayList<>(); // iss arraylist me will store activities ke number

        // we always peek the first act acc to approach 
        maxAct = 1;
        ans.add(activities[0][0]);
        int lastEnded = activities[0][2];
        for(int i = 1; i < activities.length; i++){
            if(activities[i][1] >= lastEnded){
                maxAct++;
                ans.add(activities[i][0]);
                lastEnded = activities[i][2];
            }
        }
        System.out.println("Max activites that can be done " + maxAct);
        for(int i = 0; i < ans.size(); i++){
            System.out.println("A"+ans.get(i)+" Selected ");
            
        }
        System.out.println();
        
        
        
    }

    public static double fractKnapsack(double[] profit, double[]wt, int W){
        //sorting on the basis of pro/wt
        double ratio[] = new double[profit.length];
        for(int i = 0; i < profit.length; i++){
            ratio[i] = profit[i]/(double)wt[i];
        }

        // making a 2D array
        double itemData[][] = new double[profit.length][3];
        for(int i = 0; i < profit.length; i++){
            itemData[i][0] = ratio[i];
            itemData[i][1] = profit[i];
            itemData[i][2] = wt[i];
        }

        // sorting this wrt ratio(this will do it in asc order)
        Arrays.sort(itemData, Comparator.comparingDouble(o -> o[0]));

        // greedy approch
        double capacity = W;
        double val = 0;
        for(int i = profit.length-1; i >= 0 ; i--){
            if(capacity >= itemData[i][2]){
                capacity -= itemData[i][2];
                val += itemData[i][1];                
            }
            else if(capacity < itemData[i][2] && capacity > 0){
                val += ((capacity)/(itemData[i][2])) * itemData[i][1];
                capacity = 0;
                break;
            }
        }
        return val;
    }

    public static void indCoinsUsingRec(int val){
        if(val == 0){
            return;
        }
        int[] deno = {2000,500,100,50,20,10,5,2,1};
        for(int i = 0; i < deno.length; i++){
            if(deno[i] <= val){
                System.out.println(deno[i]);
                val = val - deno[i];
                indCoinsUsingRec(val);
                return;
            }
        } 

    }
    public static void indCoinsUsingWoRec(int val){ 
        int count = 0;
        int[] deno = {2000,500,100,50,20,10,5,2,1};
        for(int i = 0; i < deno.length; i++){
            if(deno[i] <= val){
                while(deno[i] <= val){
                    count++;
                    val -= deno[i];
                    System.out.println(deno[i]);
                }
            }
        } 
        System.out.println(count);    
    }

    public static int chocola(Integer[] horiCost, Integer[] vertCost){
        // problem available in SPOJ platform
        
        int n = horiCost.length;
        int m = vertCost.length;
        // sorting in desc
        Arrays.sort(vertCost, Collections.reverseOrder());
        Arrays.sort(horiCost, Collections.reverseOrder());

        int cost = 0;
        int h = 0, v = 0; // acts as pointers in the array
        int hP = 1, vP = 1; // initally hori pieces and vert pieces are the same i.e -> 1
        while(h < n && v < m){
            if(horiCost[h] >= vertCost[v]){
                cost += (vP*horiCost[h]); // horiCost * no of vertical pieces it will cut through
                h++; // move the pointer to next cost
                hP++;  // number of vert pieces will increase by 1
            } 
            else{
                cost += (hP*vertCost[v]); // vertCost * no of hori pieces it will cut through
                v++; // move the pointer to next cost
                vP++;  // number of vert pieces will increase by 1 
            }
        }

        // left overs
        while(h < n){
            cost += (vP*horiCost[h]); // horiCost * no of vertical pieces it will cut through
            h++; // move the pointer to next cost
            hP++;  // number of vert pieces will increase by 1
        }

        while(v < m){
            cost += (hP*vertCost[v]); // vertCost * no of hori pieces it will cut through
            v++; // move the pointer to next cost
            vP++;  // number of vert pieces will increase by 1 
        }

        return cost;


    }

    public static void main(String[] args){
        Integer horiCost[] = {2,1,3,1,4};
        Integer vertCost[] = {4,1,2};
        System.out.println(chocola(horiCost,vertCost));
        
    }
}