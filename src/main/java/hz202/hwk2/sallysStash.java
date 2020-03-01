package hz202.hwk2;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public class sallysStash {///sallysStash start a game, get commands and modify checkerboards
  private checkerBoard board_a;///two boards for player A and B
  private checkerBoard board_b;
  private ArrayList<Character> stashSeq;
  private HashMap<Character, String> colorNames;
  private HashMap<Character, Integer> colorBlocks;

  public sallysStash() {
    stashSeq = new ArrayList<Character>() {{///set the color sequence of stashs
        add('G');
        add('G');
        add('P');
        add('P');
        add('P');
        add('R');
        add('R');
        add('R');
        add('B');
        add('B');
        add('B');
      }};
    colorNames = new HashMap<Character, String>();///set color to their full name
    colorNames.put('G', "Green");
    colorNames.put('P', "Purple");
    colorNames.put('R', "Red");
    colorNames.put('B', "Blue");
    colorBlocks = new HashMap<Character, Integer>();///set color to their # of blocks
    colorBlocks.put('G', 2);
    colorBlocks.put('P', 3);
    colorBlocks.put('R', 4);
    colorBlocks.put('B', 6);
  }

  public checkerBoard playerPlacement(Scanner scan, String playerName, String oppoName, checkerBoard board, checkerBoard oppoBoard) {///place the stashs
    System.out.println("Player "+playerName+", you are going place Sally’s stash on the board. Make sure Player "+oppoName+" isn’t looking! For each stack, type the coordinate of the upper left side of the stash, followed by either H (for horizontal) or V (for vertical) for green and purple ones, and U (for up) or R (for right) or D (for down) or L (for left) for red and blue ones. For example, M4H would place a stack horizontally starting at M4 and going to the right. You have\n\t\t2 Green stacks that are 1x2\n\t\t3 Purple stacks that are 1x3\n\t\t3 Red superstacks that are T-shape\n\t\t3 Blue crazystacks that are Z-shape\n");
    System.out.println("________________________________________________________");
    System.out.println("Press any key if you are ready:");
    scan.nextLine();
    for (int round = 0; round < 11; round++){///11 rounds for each player
      System.out.println("________________________________________________________");
      System.out.println("player "+playerName+"'s turn:");
      System.out.println("     Your tree                     Player "+oppoName+"'s tree");
      board.printBoard(oppoBoard);///print the checkerboard
      int print_round = round + 1;
      System.out.println("________________________________________________________");
      System.out.println("Player "+playerName+", where do you want to place the stack "+print_round+": a "+colorNames.get(stashSeq.get(round))+" stack?");
      if(round < 5){
      boolean validCmd = board.getPlaceCmd(scan, colorBlocks.get(stashSeq.get(round)), stashSeq.get(round));
      while(validCmd == false){
        validCmd = board.getPlaceCmd(scan,  colorBlocks.get(stashSeq.get(round)), stashSeq.get(round));
      }}
    else {
      boolean validCmd = board.getSuperCrazyPlaceCmd(scan, colorBlocks.get(stashSeq.get(round)), stashSeq.get(round));
      while(validCmd == false){
        validCmd = board.getSuperCrazyPlaceCmd(scan, colorBlocks.get(stashSeq.get(round)), stashSeq.get(round));
      }
    }
    }
    System.out.println("________________________________________________________");
    System.out.println("player "+playerName+"'s turn:");
    System.out.println("     Your tree                     Player "+oppoName+"'s tree");
    board.printBoard(oppoBoard);///display final placement
    System.out.println("________________________________________________________");
    System.out.println("This is your whole placement.");
    return board;
  }

  public boolean playerHit(Scanner scan, String playerName, String oppoName, checkerBoard board, checkerBoard oppoBoard) {///hit one block
    System.out.println("________________________________________________________");
    System.out.println("player "+playerName+"'s turn:");
    System.out.println("     Your tree                     Player " + oppoName + "'s tree");
    board.printBoard(oppoBoard);
    System.out.println("________________________________________________________");
    System.out.println("Player "+playerName+", which location do you want to hit on Player "+oppoName+"'s checkerboard?");
    boolean validCmd = board.getHitCmd(scan, oppoBoard, false);///get the hit location
    return validCmd;
  }

  public boolean playerAct(String actCmd, int numMove, int numSonar) {///get the player act choice
    if(actCmd.length() != 1){
      System.out.println("Invalid command!");
      return false;
    }
    if (actCmd.charAt(0) != 'D' && actCmd.charAt(0) != 'M' && actCmd.charAt(0) != 'S') {
      System.out.println("Invalid command!");
      return false;
    }
   
    if((actCmd.charAt(0) == 'M' && numMove == 0) || (actCmd.charAt(0) == 'S' && numSonar == 0)){
      System.out.println("You do not have more chances!");
      return false;
    }
    return true;
  }

  public boolean playerMove(Scanner scan, String playerName, String oppoName, checkerBoard board, checkerBoard oppoBoard) {///player movement
    System.out.println("________________________________________________________");
    System.out.println("player "+playerName+"'s turn:");
    System.out.println("     Your tree                     Player " + oppoName + "'s tree");
    board.printBoard(oppoBoard);
    System.out.println("________________________________________________________");
    System.out.println("Player "+playerName+", which stack do you want to move?");
    boolean validCmd = board.getMoveCmd(scan, oppoBoard, false);///get the move location
    return validCmd;
  }

  public boolean playerSonar(Scanner scan, String  playerName, String oppoName, checkerBoard board, checkerBoard oppoBoard) {///player sonar scan
    System.out.println("________________________________________________________");
    System.out.println("player "+playerName+"'s turn:");
    System.out.println("     Your tree                     Player " + oppoName + "'s tree");
    board.printBoard(oppoBoard);
    System.out.println("________________________________________________________");
    System.out.println("Player "+playerName+", which location do you want to sonar scan on Player "+oppoName+"'s checkerboard?");
    boolean validCmd = board.getSonarCmd(scan, oppoBoard);///get the sonar location
    return validCmd;
  }

  public boolean checkOver(checkerBoard board) {///check if there is unhit stashs. If not, the game is over
    ArrayList<ArrayList<block>> allBlocks = board.getAllBlocks();
    for (int i = 0; i < allBlocks.size();i++){
      ArrayList<block> rowBlocks = allBlocks.get(i);
      for (int j = 0; j < rowBlocks.size();j++){
        block blk = rowBlocks.get(j);
        if(blk.getOccupiedState() && !blk.getHitState()){
          return false;
        }
      }
    }
    return true;
  }

  public boolean checkMode(String mode) {///get the human/computer mode
    if(mode.length() != 1){
      System.out.println("Invalid command!");
      return false;
    }
    if(mode.charAt(0) != 'A' && mode.charAt(0) != 'B' && mode.charAt(0) != 'C' && mode.charAt(0) != 'D'){
      System.out.println("Invalid command!");
      return false;
    }
    return true;
  }

  public checkerBoard computerPlace() {///computer places the stashs
    checkerBoard board = new checkerBoard();
    
    StringBufferInputStream s = new StringBufferInputStream("s9v\ns0v\na9v\na0h\ni0v\nc3u\ne8r\no2d\nk5l\ng3u\nr4r\n");
    System.setIn(s);
    Scanner scan = new Scanner(System.in);
    for (int round = 0; round < 11; round ++){
      if(round < 5){
        
        board.getPlaceCmd(scan, colorBlocks.get(stashSeq.get(round)), stashSeq.get(round));
      }
      else {
        board.getSuperCrazyPlaceCmd(scan, colorBlocks.get(stashSeq.get(round)), stashSeq.get(round));
      }
    }
    return board;
  }

  public void computerAct(checkerBoard board, checkerBoard oppoBoard, int turn, char mode, String playerName){///computer choose an act
    
    if(turn == 10){
      StringBufferInputStream s = new StringBufferInputStream("k5\nc6d\n");
      System.setIn(s);
      Scanner scan = new Scanner(System.in);
      board.getMoveCmd(scan, oppoBoard, true);
      System.out.println("The computer player "+playerName+" used a special action.");
      return;
    }
    String letter = "ABCDEFGHIJKLMNOPQRST";
    String digit = "0123456789";
    
    Random r0 = new Random();
    Random r1 = new Random();
    char lCmd = letter.charAt(r0.nextInt(20));
    char dCmd = digit.charAt(r1.nextInt(10));
    String rCmd = Character.toString(lCmd) + Character.toString(dCmd);
    StringBufferInputStream sb = new StringBufferInputStream(rCmd);
    System.setIn(sb);
    Scanner scan = new Scanner(System.in);
    System.out.print("The computer player "+playerName);
    board.getHitCmd(scan, oppoBoard, true);
  }
  

  public void startGame() {///start a new game. Main function to start 
    board_a = new checkerBoard();
    board_b = new checkerBoard();
    Scanner scan = new Scanner(System.in);
    System.out.println(
        "Welcome to Sally's stash.Please choose from the following modes:\n\t\tA. player vs player\n\t\tB. player vs computer\n\t\tC. computer vs player\n\t\tD. computer vs. computer");
    boolean checkModeCmd = false;
    String mode = null;
    while (!checkModeCmd) {
      mode = scan.nextLine();
      mode = mode.toUpperCase();
      checkModeCmd = checkMode(mode);
    }
    if (mode.charAt(0) == 'A' || mode.charAt(0) == 'B') {
      this.playerPlacement(scan, "A", "B", board_a, board_b);///place the stashs
      
    }
    else {
      board_a = computerPlace();
      
    }
    if (mode.charAt(0) == 'A' || mode.charAt(0) == 'C') {
      System.out.println("Now switching to the other player. Press any key to continue:");
      scan.nextLine();
      this.playerPlacement(scan, "B", "A", board_b, board_a);
    
    System.out.println("Press any key to continue to take actions:");
    scan.nextLine();
    }
    else {
      board_b = computerPlace();
    }
    boolean isOverA = false;
    boolean isOverB = false;

    boolean status;
    String actCmd;
    int turn = 0;
    int numMove_a = 3;
    int numMove_b = 3;
    int numSonar_a = 3;
    int numSonar_b = 3;
    while (isOverA == false && isOverB == false) {///while the game is not over, continue hit
      ///try to hit
      if (mode.charAt(0) == 'A' || mode.charAt(0) == 'B') {
        System.out.println("Press any key to continue to A's round:");
        scan.nextLine();
        status = false;
        while (status == false) {
          System.out.println("________________________________________________________");
          System.out.println("Possible actions for Player A:");
          System.out.println("");
          System.out.println("D Dig in a square");
          System.out.println("M Move a stack to another square(" + numMove_a + " remaining)");
          System.out.println("S Sonar scan(" + numSonar_a + " remaining)");
          System.out.println("Player A, what would you like to do?");
          System.out.println("________________________________________________________");
          actCmd = scan.nextLine();
          actCmd = actCmd.toUpperCase();
          status = this.playerAct(actCmd, numMove_a, numSonar_a);
          if (!status) {
            continue;
          }
          if (actCmd.charAt(0) == 'D') {
            status = playerHit(scan, "A", "B", board_a, board_b);
            continue;
          }
          if (actCmd.charAt(0) == 'M') {
            status = playerMove(scan, "A", "B", board_a, board_b);
            if (!status) {
              continue;
            }
            numMove_a--;
          }
          if (actCmd.charAt(0) == 'S') {
            status = playerSonar(scan, "A", "B", board_a, board_b);
            if (!status) {
              continue;
            }
            numSonar_a--;
          }
        }
      }
      else {
        this.computerAct(board_a, board_b, turn, mode.charAt(0), "A");
        turn++;
      }
      isOverA = this.checkOver(board_b);
      if (isOverA) {
        System.out.println("Congratulations to Player A. You won the game!");
        break;
      }
      
      if (mode.charAt(0) == 'A' || mode.charAt(0) == 'C') {
        System.out.println("Press any key to continue to B's round:");
        scan.nextLine();
        status = false;
        while (status == false) {
          System.out.println("________________________________________________________");
          System.out.println("Possible actions for Player B:");
          System.out.println("");
          System.out.println("D Dig in a square");
          System.out.println("M Move a stack to another square(" + numMove_b + " remaining)");
          System.out.println("S Sonar scan(" + numSonar_b + " remaining)");
          System.out.println("Player B, what would you like to do?");
          System.out.println("________________________________________________________");
          actCmd = scan.nextLine();
          actCmd = actCmd.toUpperCase();
          status = this.playerAct(actCmd, numMove_b, numSonar_b);
          if (!status) {
            continue;
          }
          if (actCmd.charAt(0) == 'D') {
            status = playerHit(scan, "B", "A", board_b, board_a);
            continue;
          }
          if (actCmd.charAt(0) == 'M') {
            status = playerMove(scan, "B", "A", board_b, board_a);
            if (!status) {
              continue;
            }
            numMove_b--;
          }
          if (actCmd.charAt(0) == 'S') {
            status = playerSonar(scan, "B", "A", board_b, board_a);
            if (!status) {
              continue;
            }
            numSonar_b--;
          }
        }
      }
      else {///computer role
        this.computerAct(board_b, board_a, turn, mode.charAt(0), "B");
        turn++;
      }
      isOverB = this.checkOver(board_a);
      if (isOverB) {
        System.out.println("Congratulations to Player B. You won the game!");
        break;
      }
      
    }
  }
}
