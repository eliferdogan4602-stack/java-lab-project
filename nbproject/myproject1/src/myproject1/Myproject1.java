package myproject1;

import java.util.Scanner;

public class Myproject1 {

    public static void main(String[] args) {
       
     
        Scanner scanner = new Scanner(System.in);

        String[] teams = {"Team A", "Team B", "Team C", "Team D"};
        int numTeams = teams.length;

        int[] played = new int[numTeams];
        int[] wins = new int[numTeams];
        int[] draws = new int[numTeams];
        int[] losses = new int[numTeams];
        int[] gf = new int[numTeams]; 
        int[] ga = new int[numTeams]; 
        int[] points = new int[numTeams];

        
        int[][] fixtures = {
            {0, 1}, 
            {2, 3}, 
            {0, 2}, 
            {1, 3}, 
            {0, 3}, 
            {1, 2}  
        };

        int numMatches = fixtures.length;
        int[][] scores = new int[numMatches][2];

        
        System.out.println("--- TOURNAMENT FIXTURE ---");
        for (int i = 0; i < numMatches; i++) {
            System.out.println("Match " + (i + 1) + ": " + teams[fixtures[i][0]] + " vs " + teams[fixtures[i][1]]);
        }
        System.out.println();

        
        System.out.println("--- ENTER MATCH SCORES ---");
        for (int i = 0; i < numMatches; i++) {
            int tA = fixtures[i][0];
            int tB = fixtures[i][1];

            System.out.print("Match " + (i + 1) + ": " + teams[tA] + " vs " + teams[tB] + " - " + teams[tA] + " goals: ");
            scores[i][0] = scanner.nextInt();

            System.out.print("Match " + (i + 1) + ": " + teams[tA] + " vs " + teams[tB] + " - " + teams[tB] + " goals: ");
            scores[i][1] = scanner.nextInt();

            int scoreA = scores[i][0];
            int scoreB = scores[i][1];

            
            played[tA]++;
            played[tB]++;

           
            gf[tA] += scoreA;
            ga[tA] += scoreB;

            gf[tB] += scoreB;
            ga[tB] += scoreA;

            
            if (scoreA > scoreB) {
                wins[tA]++;
                points[tA] += 3;
                losses[tB]++;
            } else if (scoreA < scoreB) {
                wins[tB]++;
                points[tB] += 3;
                losses[tA]++;
            } else {
                draws[tA]++;
                draws[tB]++;
                points[tA] += 1;
                points[tB] += 1;
            }
        }
        System.out.println();

        
        System.out.println("--- MATCH RESULTS ---");
        for (int i = 0; i < numMatches; i++) {
            System.out.println("Match " + (i + 1) + ": " + teams[fixtures[i][0]] + " " + scores[i][0] + " - " + scores[i][1] + " " + teams[fixtures[i][1]]);
        }
        System.out.println();

       
        int[] order = {0, 1, 2, 3};

        for (int i = 0; i < numTeams - 1; i++) {
            for (int j = 0; j < numTeams - 1 - i; j++) {
                int idx1 = order[j];
                int idx2 = order[j + 1];

                int gd1 = gf[idx1] - ga[idx1];
                int gd2 = gf[idx2] - ga[idx2];

                boolean swap = false;

                if (points[idx2] > points[idx1]) {
                    swap = true;
                } else if (points[idx2] == points[idx1]) {
                    if (gd2 > gd1) {
                        swap = true;
                    } else if (gd2 == gd1 && gf[idx2] > gf[idx1]) {
                        swap = true;
                    }
                }

                if (swap) {
                    int temp = order[j];
                    order[j] = order[j + 1];
                    order[j + 1] = temp;
                }
            }
        }
       
        System.out.println("--- STANDINGS TABLE ---");
        System.out.printf("%-10s %-3s %-3s %-3s %-3s %-3s %-3s %-4s %-6s\n", 
            "Team", "P", "W", "D", "L", "GF", "GA", "GD", "Points");

        for (int i = 0; i < numTeams; i++) {
            int idx = order[i];
            int gd = gf[idx] - ga[idx];
            System.out.printf("%-10s %-3d %-3d %-3d %-3d %-3d %-3d %-4d %-6d\n",
                teams[idx], played[idx], wins[idx], draws[idx], losses[idx], gf[idx], ga[idx], gd, points[idx]);
        }
        System.out.println();

      
        int championIdx = order[0];
        System.out.println("Tournament Champion: " + teams[championIdx]);

        scanner.close();
    }
}