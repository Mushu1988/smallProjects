package yahtzee4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author maris
 */
public class Yahtzee4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
        {

        // Declarations
        final int NUM_OF_DICE = 5;
        final int SIDES_OF_DICE = 6;
        final int NUM_OF_ROUNDS = 13;
        
        int playerAmount;
        int[] diceRoll = new int[NUM_OF_DICE];      // array to score dice roll
        int[][] scoreBoard = new int[NUM_OF_ROUNDS][6];  // array for scoreboard
        char rollAgain = 'N';                       // user wants to roll again
        int reRollAvailable = 2;                    // how many rerolls available
        int diceToReRoll = 0;                       // how nany dice to rereoll
        int score = 0;                              // used to add up score for this round
        int[] numOfSide= new int[6];
        boolean is3OfKind=false, is4OfKind=false, isFullhouse=false, isSSS=false, isLSS=false, isYahtzee=false;
        String winnerName="";
        int winnerScore=0;
        
        Random rnd = new Random();
        Scanner kb = new Scanner(System.in);
        
        
        // Display game title and instructions
        System.out.println("\t\t\t\t\t   Welcome to Yahtzee Game!"
                + "\nYou can roll your dice up to 3 times. In any roll, you can choose to keep some dice and re-roll others. Every player rolls"
                + "\nthe dice 3 times each for 13 rounds. Each box can only be filled in once. On each turn, you must choose which box to fill"
                + "\nin based on your best scoring option. If you are unable to entera score, you must enter a 0 in one of your boxes."
                + "\n\t\t\t\t\t\t  Good luck!\n");
        
        //Asking about the number of players
        System.out.println("\t\t\tHow many players will be playing (enter number between 1 and 5 inclusive)?");
        playerAmount=kb.nextInt();
        kb.nextLine();
        
        int[] totalScore = new int[playerAmount];   // total score
        
        //Validating if the number of players entered correctly
        while(playerAmount<1 || playerAmount>5)
        {
            System.out.println("Invalid number. Please, enter number between 1 and 5 inclusive");
            playerAmount=kb.nextInt();
            kb.nextLine();
        }
        
        // Ask players for their names
        String[] playerName = new String[playerAmount];                     // players' names
        for(int count=0; count<playerAmount; count++)
        {
            System.out.println("\t\t\t\t\tPlease, enter the name of the " + (count+1) + " player:");
            playerName[count] = kb.nextLine();
        }
        
        //Sorting players` names in alphabetical order
        Arrays.sort(playerName);
        System.out.println("\nYou will be playing in the following order: " + Arrays.toString(playerName) + "\n");
        
            // Display empty scoreboard with player's name
            for (int ind=0; ind<NUM_OF_ROUNDS; ind++)
                {
                    for (int count=0; count<playerAmount; count++)
                    {
                    scoreBoard[ind][count]=-1;
                    }
                }
       
            displayScoreboard(scoreBoard, playerName, totalScore);

            System.out.println("");


            // This loop handles the number of rounds required
            for (int ind=1; ind <=NUM_OF_ROUNDS; ind++ )
            {
                //Looping game to use for multiple players
                for (int player=0; player<playerAmount; player++)
                {
                    // Display round number and player`s turn
                    System.out.println ("\n\nROUND " + ind + " for " + playerName[player] + "\n");
                    
                    //Setting the counter for number of Sides to 0
                    for (int count=0; count<6; count++)
                    {
                        numOfSide[count]=0;
                    }

                    // Roll five dice and store values in array
                    for (int dice=0; dice< NUM_OF_DICE; dice++)
                    {
                        diceRoll[dice] = rnd.nextInt(SIDES_OF_DICE) + 1;
                    }

                    // Display the roll of the dice
                    System.out.println("YOU ROLLLED:");
                    for (int dice=0; dice < NUM_OF_DICE; dice++)
                    {
                        System.out.println("\t\tDice no. #" + (dice+1) + ": " + diceRoll[dice]);
                    }

                    // Handle two rerolls of the dice selected (if required)
                    reRollAvailable = 2;
                    do  
                    {
                        // Tell user how many rerolls are available to them
                        System.out.println("\nYou have " + reRollAvailable + " re-roll(s) left.");
                        // Ask user if they want to reroll any dice
                        System.out.print("Do you want to reroll any dice? (Y/N)? ");
                        rollAgain = kb.nextLine().charAt(0);
                        
                        //Validating player`s entry. Use Y or N only
                        while(!(rollAgain=='y') && !(rollAgain=='Y') && !(rollAgain=='n') && !(rollAgain=='N'))
                        {
                            System.out.println("Invalid choice. Please, enter Y or N");
                            rollAgain = kb.nextLine().charAt(0);
                        }
                        
                        if (rollAgain == 'y') rollAgain = 'Y';
                        if (rollAgain == 'Y' || rollAgain == 'y')
                        {
                            // Ask user which dice to reroll
                            System.out.print("How many dice do you want to reroll? ");
                            diceToReRoll = kb.nextInt();
                            //Validating player`s entry.
                            while(diceToReRoll<1 || diceToReRoll>5)
                            {
                                System.out.println("Invalid number. Please, enter number between 1 and 5 inclusive");
                                diceToReRoll=kb.nextInt();
                            }
                            
                            //If 5 is entered, reroll all dices
                            if(diceToReRoll==5)
                            {
                                for (int dice=0; dice<diceToReRoll; dice++)
                                {
                                    diceRoll[dice] = rnd.nextInt(SIDES_OF_DICE) + 1;   
                                }
                            } else 
                            {
                                // Loop through the number dice to be rerolled
                                for (int dice=1; dice<=diceToReRoll; dice++)
                                {
                                    // Ask user for dice number to reroll, reroll and store it
                                    System.out.print("Enter dice number to reroll (e.g. 1): ");
                                    int diceNumb = kb.nextInt();

                                    //Checking if the player entered a valid dice number
                                    while (diceNumb<1 || diceNumb>5)
                                    {
                                        System.out.print("Invalid entry. Please, enter dice number to reroll (number from 1 to 5 inclusive):");
                                        diceNumb = kb.nextInt();
                                    }

                                    // Reroll dice number as requested
                                    diceRoll[diceNumb-1] = rnd.nextInt(SIDES_OF_DICE) + 1;   
                                }
                            } 
                            
                            // Display the roll of the dice
                            System.out.println("YOUR DICE ARE NOW:");
                            for (int dice=0; dice < NUM_OF_DICE; dice++)
                            {
                                System.out.println("\t\tDice no. #" + (dice+1) + ": " + diceRoll[dice]);
                            } 
                            // Decrement number of rolls avaible
                            reRollAvailable --;
                            // Remove crlf from input buffer  
                            kb.nextLine();
                        }
                    } while (reRollAvailable > 0 && rollAgain=='Y');
                    
                    displayScoreboard(scoreBoard, playerName, totalScore);

                    // Ask user which box they want to allocate their score.
                    System.out.print("Which box would you like to allocate your score? (e.g. 1 for Ones, 7 for Three of a kind) "); 
                    int boxToScore = kb.nextInt();
                    kb.nextLine();
                        
                    //Validating player`s choice
                    while (boxToScore<1 || boxToScore>13)
                    {
                        System.out.println("Invalid entry. Please, enter a number from 1 to 13 inclusive:");
                        boxToScore = kb.nextInt();
                        kb.nextLine();
                    }
                    
                    //Checking if the box is already taken and asking for a new choice if it is taken
                    while (scoreBoard[boxToScore-1][player]>=0)
                    {
                        System.out.println("The score for this box was already saved. Please, choose another box (number 1 to 13 inclusive):");
                        boxToScore = kb.nextInt();
                        kb.nextLine();
                        
                        //Validating player`s choice
                        while (boxToScore<1 || boxToScore>13)
                        {
                            System.out.println("Invalid entry. Please, enter a number from 1 to 13 inclusive:");
                            boxToScore = kb.nextInt();
                            kb.nextLine();
                        }
                    }

                    //Calculating number of appearances of each side for the current round
                    for (int dice=0; dice< NUM_OF_DICE; dice++)
                        {
                            if (diceRoll[dice]==1) numOfSide[0]++;
                            else if (diceRoll[dice]==2) numOfSide[1]++;
                            else if (diceRoll[dice]==3) numOfSide[2]++;
                            else if (diceRoll[dice]==4) numOfSide[3]++;
                            else if (diceRoll[dice]==5) numOfSide[4]++;
                            else numOfSide[5]++;
                        }
                    
                    //Setting special conditions flags to false
                    is3OfKind=false; 
                    is4OfKind=false;
                    isYahtzee=false;
                    isSSS=false;
                    isLSS=false;
                    isFullhouse=false;
                    
                    //Checking if special conditions are met
                    for (int index=0; index<6; index++)
                        if (numOfSide[index]==3) is3OfKind=true;

                    for (int index=0; index<6; index++)
                        if (numOfSide[index]==4) is4OfKind=true;

                    for (int index=0; index<6; index++)
                        if (numOfSide[index]==5) isYahtzee=true;
                    
                    //Converting dice Roll results array into list to check for the number sequences
                    List<Integer> list = convertIntArrayToList(diceRoll);
                    if ((list.contains(1) && list.contains(2) && list.contains(3) && list.contains(4)) || (list.contains(2) && list.contains(3) && list.contains(4) && list.contains(5)) || (list.contains(3) && list.contains(4) && list.contains(5) && list.contains(6))) isSSS=true;

                    if ((list.contains(1) && list.contains(2) && list.contains(3) && list.contains(4) && list.contains(5)) || (list.contains(2) && list.contains(3) && list.contains(4) && list.contains(5) && list.contains(6))) isLSS=true;
                    
                    //Sorting dice Roll results array to find 2 of one type and 3 of the other type
                    Arrays.sort(diceRoll);
                    if ((diceRoll[0]==diceRoll[1] && diceRoll[2]==diceRoll[3] && diceRoll[3]==diceRoll[4]) || (diceRoll[0]==diceRoll[1] && diceRoll[1]==diceRoll[2] && diceRoll[3]==diceRoll[4])) isFullhouse=true;
                    
                    //Calculating scores for all game scenarios
                    switch(boxToScore)
                    {
                        case 1:
                            // Count and add only ones
                            score=numOfSide[0]*1;
                            break;
                        case 2:
                            // Count and add only twos
                            score=numOfSide[1]*2;
                            break;
                        case 3:
                            // Count and add only threes
                            score=numOfSide[2]*3;
                            break;
                        case 4:
                            // Count and add only fours
                            score=numOfSide[3]*4;
                            break;
                        case 5:
                            // Count and add only fives
                            score=numOfSide[4]*5;
                            break;
                        case 6:
                            // Count and add only sixes
                            score=numOfSide[5]*6;
                            break;
                        case 7:
                            // Three of a kind (add total of all dice)
                            if (is3OfKind)
                            score=totalDiceSum(numOfSide);
                            else score=0;
                            break;
                        case 8:
                            // Four of a kind (add total of all dice)
                            if (is4OfKind)
                            score=totalDiceSum(numOfSide);
                            else score=0;
                            break;
                        case 9:
                            // Full house (score 25)
                            // Three of one number and two of another
                            if (isFullhouse) score=25;
                            else score=0;
                            break;
                        case 10:
                            // Small straight (sequence of 4) (score 30)
                            if (isSSS) score=30;
                            else score=0;
                            break;
                        case 11:
                            // Large straight (sequence of 5) (score 40)
                            if (isLSS) score=40;
                            else score=0;
                            break;
                        case 12:
                            // Yahtzee (six dice the same) (score 50)
                            if (isYahtzee) score=50;
                            else score=0;
                            break;
                        case 13:
                            // Chance (add the total of all the dice)
                            score=totalDiceSum(numOfSide);
                            break;
                    }

                    // Allocate score to box on scoreboard chosen
                    scoreBoard[boxToScore-1][player] = score;

                    //Setting total score to 0
                    for (int ord=1; ord<playerAmount; ord++)
                    {
                        totalScore[player]=0;
                    }
                    
                    // Calculate total score
                    for (int number=0; number < NUM_OF_ROUNDS; number++ )
                    {
                        if (scoreBoard[number][player]>=0)
                            totalScore[player]=totalScore[player]+scoreBoard[number][player];
                    }
                        

                    // Display scoreboard
                     displayScoreboard(scoreBoard, playerName, totalScore);
                } 
            }
        
        
        // At the end of the game display the final scoreboard
        displayScoreboard(scoreBoard, playerName, totalScore);
        
        //Finding the highest score and the winner`s name
        for (int ind=0; ind<playerAmount; ind++)
        {
            if (totalScore[ind]>winnerScore)
            {
                winnerScore=totalScore[ind];
                winnerName=playerName[ind];
            }
        }
        
        //Announcing winner
        System.out.println("The winner is " + winnerName + " with a score of " + winnerScore + " ponts. Congratulations!");
    }
    
    /**
     * This method displays the scoreboard (FOR MULTIPLE PLAYER)
     * @param scoreBoard 2 dimensional integer array that holds the scoreboard
     * @param playerName String that holds players` names 
     * @param totalScore Integer array that holds totals
     */
    public static void displayScoreboard(int[][] scoreBoard, String[] playerName, int[] totalScore)
    {
        System.out.print("\n| \t\t\t|");
        for (int player=0; player<playerName.length; player++)
        {System.out.print("\t" + playerName[player] + "\t|");}
        System.out.println();
        System.out.print("| 1 | Ones\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[0][player]>=0)
        System.out.print("\t" + scoreBoard[0][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 2 | Twos\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[1][player]>=0)
        System.out.print("\t" + scoreBoard[1][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 3 | Threes\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[2][player]>=0)
        System.out.print("\t" + scoreBoard[2][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 4 | Fours\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[3][player]>=0)
        System.out.print("\t" + scoreBoard[3][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 5 | Fives\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[4][player]>=0)
        System.out.print("\t" + scoreBoard[4][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 6 | Sixes\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[5][player]>=0)
        System.out.print("\t" + scoreBoard[5][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 7 | Three of a kind\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[6][player]>=0)
        System.out.print("\t" + scoreBoard[6][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 8 | Four of a kind\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[7][player]>=0)
        System.out.print("\t" + scoreBoard[7][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 9 | Full house\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[8][player]>=0)
        System.out.print("\t" + scoreBoard[8][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 10 | SSSequence of 4\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[9][player]>=0)
        System.out.print("\t" + scoreBoard[9][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 11 | LSSequence of 5\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[10][player]>=0)
        System.out.print("\t" + scoreBoard[10][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 12 | YAHTZEE\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[11][player]>=0)
        System.out.print("\t" + scoreBoard[11][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| 13 | Chance\t\t|");
        for (int player=0; player<playerName.length; player++)
        {if (scoreBoard[12][player]>=0)
        System.out.print("\t" + scoreBoard[12][player]+ "\t|");
        else System.out.print("\t_\t|");}
        System.out.println();
        System.out.print("| Total  \t\t|");
        for (int player=0; player<playerName.length; player++)
        {System.out.print("\t" + totalScore[player]+ "\t|");}
        System.out.println();
    }
    
    /**
     * Converting dice Roll results array into list to check for the number sequences
     * The code was seen at https://www.mkyong.com/java/java-how-to-convert-a-primitive-array-to-list/
     * @param diceRoll Integer array holding results of dice roll
     * @return List with results of dice roll
     */
    private static List<Integer> convertIntArrayToList(int[] diceRoll) 
    {
        List<Integer> list = new ArrayList<>();
        for (int i : diceRoll) 
        {
            list.add(i);
        }
        return list;
    }
    
    /**
     * Calculating the sum of all dice
     * @param array Integer array holding results of dice roll
     * @return The sum of all dice in the round
     */
    public static int totalDiceSum(int[] array) 
    {
        int sum = 0;
        for (int index = 0; index < array.length; index++) 
        {
        sum = sum + (array[index]*(index+1));
        }
        return sum;
    }
    
}