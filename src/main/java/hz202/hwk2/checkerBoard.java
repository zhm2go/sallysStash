package hz202.hwk2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;;

public class checkerBoard {
  private int width = 10;
  private int height = 20;
  private ArrayList<stash> stashs;
  private ArrayList<ArrayList<block>> allBlocks;

  private HashMap<Character, String> colorNames;

  public checkerBoard() {///start a new checkerboard
    stashs = new ArrayList<stash>();
    allBlocks = new ArrayList<ArrayList<block>>();
    for (int h = 0; h <= 'T'; h++) {
      allBlocks.add(new ArrayList<block>());
    }
    for (int i = 'A'; i <= 'T'; i++){///create all 200 blocks on the checkerboard
     
      ArrayList<block> rowBlocks = new ArrayList<block>();
      for (int j = 0; j < 10; j++){
        rowBlocks.add(new block());
       
      }
      allBlocks.set(i, rowBlocks);
    }
    colorNames = new HashMap<Character, String>();
    colorNames.put('G', "Green");
    colorNames.put('P', "Purple");
    colorNames.put('R', "Red");
    colorNames.put('B', "Blue");
  }

  public ArrayList<ArrayList<block>> getAllBlocks() {///get allblocks on the checkerboard.For print use.
    return allBlocks;
  }
  
  public boolean getPlaceCmd(Scanner scan, int num, char color){///get the place stash command
    String cmd = scan.nextLine();
    if(cmd.length() != 3){
      System.out.println("Please type exact 3 characters:");
      return false;
    }
    if(!Character.isLetter(cmd.charAt(0)) || !Character.isDigit(cmd.charAt(1)) || ! Character.isLetter(cmd.charAt(2))){
       System.out.println("Please type 3 valid characters:");
       return false;
    }
    cmd = cmd.toUpperCase();
   
    if(cmd.charAt(0) < 'A' || cmd.charAt(0) > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    if(cmd.charAt(2) != 'V' && cmd.charAt(2) != 'H'){
      System.out.println("Please type valid orientation character:");
      return false;
    }
    if(cmd.charAt(2) == 'V' && cmd.charAt(0) + num - 1 > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    int cmd2 = Character.getNumericValue(cmd.charAt(1));
    if(cmd.charAt(2) == 'H' && cmd2 + num - 1 > 9){
      System.out.println("Location out of checkboard!");
      return false;
    }
    for (int k = 0; k < num; k++){///check overlap blocks
          if(cmd.charAt(2) == 'V'){
            if(allBlocks.get(cmd.charAt(0)+k).get(cmd2).getOccupiedState()){
              System.out.println("Overlapped block!");
              return false;
            }
          }
          if(cmd.charAt(2) == 'H'){
            if(allBlocks.get(cmd.charAt(0)).get(cmd2+k).getOccupiedState()){
              System.out.println("Overlapped block!");
              return false;
            }
          }
        }
    stash new_stash = new stash(num, color);
    for (int k = 0; k < num; k++){///really place blocks
          if(cmd.charAt(2) == 'V'){
            new_stash.addBlock(allBlocks.get(cmd.charAt(0)+k).get(cmd2));
            ///allBlocks.get(cmd.charAt(0)+k).get(cmd2).setMaster(new_stash);
          }
          if(cmd.charAt(2) == 'H'){
            new_stash.addBlock(allBlocks.get(cmd.charAt(0)).get(cmd2+k));
            ///allBlocks.get(cmd.charAt(0)).get(cmd2+k).setMaster(new_stash);
          }
        }
    stashs.add(new_stash);
    return true;
  }

  public boolean checkBoundary(char color, String cmd) {///check the boundary of red and blue stashs
    if(color == 'R'){///if red one
      if(cmd.charAt(2) == 'U'){
        if(cmd.charAt(0) + 1 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))-1 < 0 || Character.getNumericValue(cmd.charAt(1))+1 > 9){
          return true;
        }
      }
      if(cmd.charAt(2) == 'R'){
        if(cmd.charAt(0) + 2 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))+1 > 9){
          return true;
        }
      }
      if(cmd.charAt(2) == 'D'){
        if(cmd.charAt(0) + 1 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))+2 > 9){
          return true;
        }
      }
      if(cmd.charAt(2) == 'L'){
        if(cmd.charAt(0) + 2 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))-1 < 0){
          return true;
        }
      }
    }
    if(color == 'B'){///if blue one
      if(cmd.charAt(2) == 'U'){
        if(cmd.charAt(0) + 4 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))+1 > 9){
          return true;
        }
      }
      if(cmd.charAt(2) == 'R'){
        if(cmd.charAt(0) - 1 < 'A'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))+4 > 9){
          return true;
        }
      }
      if(cmd.charAt(2) == 'D'){
        if(cmd.charAt(0) + 4 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))-1 < 0){
          return true;
        }
      }
      if(cmd.charAt(2) == 'L'){
        if(cmd.charAt(0) + 1 > 'T'){
          return true;
        }
        if(Character.getNumericValue(cmd.charAt(1))+4 > 9){
          return true;
        }
      }
    }
    return false;
  }

  public boolean checkOverlap(char color, String cmd) {///check if overlap of red or blue stashs
    char cmd0 = cmd.charAt(0);
    int cmd1 = Character.getNumericValue(cmd.charAt(1));
    if(color == 'R'){///if red one
      if(cmd.charAt(2) == 'U'){
        if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1-1).getOccupiedState()){
          return true;
        }
      }
      if(cmd.charAt(2) == 'R'){
        if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+2).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1+1).getOccupiedState()){
          return true;
        }
      }
      if(cmd.charAt(2) == 'L'){
        if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+2).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1-1).getOccupiedState()){
          return true;
        }
      }
       if(cmd.charAt(2) == 'D'){
        if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0).get(cmd1+2).getOccupiedState()){
          return true;
        }
      }
    }
    if(color == 'B'){///if blue one
      if(cmd.charAt(2) == 'U'){
        if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+2).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+2).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0+3).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0+4).get(cmd1+1).getOccupiedState()){
          return true;
        }
      }
      if(cmd.charAt(2) == 'R'){
         if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0).get(cmd1+2).getOccupiedState() || allBlocks.get(cmd0-1).get(cmd1+2).getOccupiedState() || allBlocks.get(cmd0-1).get(cmd1+3).getOccupiedState() || allBlocks.get(cmd0-1).get(cmd1+4).getOccupiedState()){
          return true;
        }
      }
      if(cmd.charAt(2) == 'D'){
         if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+2).get(cmd1).getOccupiedState() || allBlocks.get(cmd0+2).get(cmd1-1).getOccupiedState() || allBlocks.get(cmd0+3).get(cmd1-1).getOccupiedState() || allBlocks.get(cmd0+4).get(cmd1-1).getOccupiedState()){
          return true;
        }
      }
      if(cmd.charAt(2) == 'L'){
         if(allBlocks.get(cmd0).get(cmd1).getOccupiedState() || allBlocks.get(cmd0).get(cmd1+1).getOccupiedState() || allBlocks.get(cmd0).get(cmd1+2).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1+2).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1+3).getOccupiedState() || allBlocks.get(cmd0+1).get(cmd1+4).getOccupiedState()){
          return true;
        }
      }
    }
    return false;
  }


  public stash finalPlace(char color, String cmd, int num) {///place the blue and red stash
    char cmd0 = cmd.charAt(0);
    int cmd1 = Character.getNumericValue(cmd.charAt(1));
    stash new_stash = new stash(num, color);
    
    if(color == 'R'){///red one
      if (cmd.charAt(2) == 'U') {
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1-1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1+1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
      }
      if (cmd.charAt(2) == 'R') {
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+2).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1+1));
      }
      if (cmd.charAt(2) == 'D') {
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1+2));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1+1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1+1));
      }
      if (cmd.charAt(2) == 'L') {
        new_stash.addBlock(allBlocks.get(cmd0+2).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1-1));
      }
    }
    if(color == 'B'){///blue one
      if (cmd.charAt(2) == 'U') {
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+2).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+2).get(cmd1+1));
        new_stash.addBlock(allBlocks.get(cmd0+3).get(cmd1+1));
        new_stash.addBlock(allBlocks.get(cmd0+4).get(cmd1+1));
      }
      if (cmd.charAt(2) == 'R') {
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1+1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1+2));
        new_stash.addBlock(allBlocks.get(cmd0-1).get(cmd1+2));
        new_stash.addBlock(allBlocks.get(cmd0-1).get(cmd1+3));
        new_stash.addBlock(allBlocks.get(cmd0-1).get(cmd1+4));
      }
      if (cmd.charAt(2) == 'D') {
        new_stash.addBlock(allBlocks.get(cmd0+4).get(cmd1-1));
        new_stash.addBlock(allBlocks.get(cmd0+3).get(cmd1-1));
        new_stash.addBlock(allBlocks.get(cmd0+2).get(cmd1-1));
        new_stash.addBlock(allBlocks.get(cmd0+2).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
      }
      if (cmd.charAt(2) == 'L') {
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1+4));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1+3));
        new_stash.addBlock(allBlocks.get(cmd0+1).get(cmd1+2));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1+2));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1+1));
        new_stash.addBlock(allBlocks.get(cmd0).get(cmd1));
      }
    }
    return new_stash;
  }

  public boolean getSuperCrazyPlaceCmd(Scanner scan, int num, char color) {///main function for red and blue stashs
    String cmd = scan.nextLine();
    if(cmd.length() != 3){
      System.out.println("Please type exact 3 characters:");
      return false;
    }
    if(!Character.isLetter(cmd.charAt(0)) || !Character.isDigit(cmd.charAt(1)) || ! Character.isLetter(cmd.charAt(2))){
       System.out.println("Please type 3 valid characters:");
       return false;
    }
    cmd = cmd.toUpperCase();
   
    if(cmd.charAt(0) < 'A' || cmd.charAt(0) > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    if(cmd.charAt(2) != 'U' && cmd.charAt(2) != 'R' && cmd.charAt(2) != 'D' && cmd.charAt(2) != 'L'){
      System.out.println("Please type valid orientation character:");
      return false;
    }
    boolean isOut = this.checkBoundary(color, cmd);
    if(isOut){
      System.out.println("Location out of checkboard!");
      return false;
    }
    boolean isOverlap = this.checkOverlap(color, cmd);
    if(isOverlap){
      System.out.println("Overlapped block!");
      return false;
    }
    stash new_stash = this.finalPlace(color, cmd, num);
    stashs.add(new_stash);
    return true;
  }

  public boolean getHitCmd(Scanner scan, checkerBoard oppoBoard, boolean isComputer) {///get hit command
    String cmd = scan.nextLine();
    if(cmd.length() != 2){
      System.out.println("Invalid command!");
      return false;
    }
    if(!Character.isLetter(cmd.charAt(0)) || !Character.isDigit(cmd.charAt(1))){
       System.out.println("Invalid command!");
       return false;
    }
    cmd = cmd.toUpperCase();
    if(cmd.charAt(0) < 'A' || cmd.charAt(0) > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    int cmd2 = Character.getNumericValue(cmd.charAt(1));
    if(oppoBoard.getAllBlocks().get(cmd.charAt(0)).get(cmd2).getHitState()){
      if(!isComputer){
        System.out.println("You have hit this stack before!");}
      else {
        System.out.println(" hit a stack that has been hit!");
      }
    }
    else if(oppoBoard.getAllBlocks().get(cmd.charAt(0)).get(cmd2).getOccupiedState()){
      block hit_block = oppoBoard.getAllBlocks().get(cmd.charAt(0)).get(cmd2);
      hit_block.setHitState(true);
      hit_block.setDigState(true);
      hit_block.setMissState(false);
      if(!isComputer){
        System.out.println("You found a stack!");}
      else {
        System.out.println(" found a "+colorNames.get(hit_block.getMaster().getColor())+" stack at "+cmd+"!");}
    }
    else {
      block hit_block = oppoBoard.getAllBlocks().get(cmd.charAt(0)).get(cmd2);
      hit_block.setMissState(true);
      hit_block.setDigState(false);
      if(!isComputer){
        System.out.println("You missed!");}
      else {
        System.out.println(" missed!");
      }
    }
    return true;
  }

  public char sonarCheckBlock(int cmd0, int cmd1, checkerBoard oppoBoard) {///sonar check the number of each kind of blocks
    if(cmd0 < 'A' || cmd0 > 'T' || cmd1 < 0 || cmd1 > 9){
      return 'N';
    }
    block sonarBlock = oppoBoard.getAllBlocks().get(cmd0).get(cmd1);
    if(sonarBlock.getOccupiedState()){
      char color = sonarBlock.getMaster().getColor();
      return color;
    }
    return 'N';
  }

  public boolean moveStash(String cmd, stash chosenStash) {
    char color = chosenStash.getColor();
    int num = chosenStash.getNum();
    if(cmd.length() != 3 ){
      System.out.println("Invalid command!");
      return false;
    }
    if(!Character.isLetter(cmd.charAt(0)) || !Character.isDigit(cmd.charAt(1)) || ! Character.isLetter(cmd.charAt(2))){
       System.out.println("Invalid command!");
       return false;
    }
    cmd = cmd.toUpperCase();
    if(cmd.charAt(0) < 'A' || cmd.charAt(0) > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    char cmd0 = cmd.charAt(0);
    int cmd1 = Character.getNumericValue(cmd.charAt(1));
    char cmd2 = cmd.charAt(2);
    if (color == 'G' || color == 'P') {
      if (cmd2 != 'H' && cmd2 != 'V') {
        System.out.println("Invalid command!");
        return false;
      }
      if (cmd2 == 'V' && cmd0 + num - 1 > 'T') {
        System.out.println("Location out of checkboard!");
        return false;
      }
      ///int cmd2 = Character.getNumericValue(cmd.charAt(1));
      if (cmd2 == 'H' && cmd1 + num - 1 > 9) {
        System.out.println("Location out of checkboard!");
        return false;
      }
      for (int k = 0; k < num; k++){///check overlap blocks
          if(cmd2 == 'V'){
            if(allBlocks.get(cmd0+k).get(cmd1).getOccupiedState() && allBlocks.get(cmd0+k).get(cmd1).getMaster() != chosenStash){
              System.out.println("Overlapped block!");
              return false;
            }
          }
          if(cmd2 == 'H'){
            if(allBlocks.get(cmd0).get(cmd1+k).getOccupiedState() && allBlocks.get(cmd0).get(cmd1+k).getMaster() != chosenStash){
              System.out.println("Overlapped block!");
              return false;
            }
          }
      }
      ArrayList<Boolean> isHits = new ArrayList<Boolean>();
      for(int l = 0; l < num; l++){
        isHits.add(chosenStash.getBlocks().get(l).getHitState());
        chosenStash.getBlocks().get(l).rmMaster();
      }
      for (int k = 0; k < num; k++){///really place blocks
          if(cmd2 == 'V'){
            
            allBlocks.get(cmd.charAt(0)+k).get(cmd1).setHitState(isHits.get(k));
            chosenStash.setBlock(k, allBlocks.get(cmd.charAt(0)+k).get(cmd1));
            
          }
          if(cmd2 == 'H'){
            allBlocks.get(cmd.charAt(0)).get(cmd1+k).setHitState(isHits.get(k));
            chosenStash.setBlock(k, allBlocks.get(cmd.charAt(0)).get(cmd1+k));
            
          }
        }
    }
    if(color == 'R' || color == 'B'){///for  red and blue stashs
      if(cmd2 != 'U' && cmd2 != 'R' && cmd2 != 'D' && cmd2 != 'L'){
        System.out.println("Invalid command!");
        return false;
      }
      boolean isOut = this.checkBoundary(color, cmd);
      if(isOut){
        System.out.println("Location out of checkboard!");
        return false;
      }
      ArrayList<Boolean> allHits = new ArrayList<Boolean>();
      for (int br = 0; br < num; br++){
        allHits.add(chosenStash.getBlocks().get(br).getHitState());
        chosenStash.getBlocks().get(br).rmMaster();
      }
      chosenStash.clearBlocks();
      boolean isOverlap = this.checkOverlap(color, cmd);
      if(isOverlap){
        return false;
      }
      chosenStash = finalPlace(color, cmd, num);
      for (int rb = 0; rb < num; rb++){
        chosenStash.getBlocks().get(rb).setHitState(allHits.get(rb));
      }
    }
    return true;
  }
  
  public boolean getMoveCmd(Scanner scan, checkerBoard oppoBoard, boolean isComputer) {
    ///get the move command
    String cmd = scan.nextLine();
    cmd = cmd.toUpperCase();
    if(cmd.length() != 2){
      System.out.println("Invalid commmand!");
      return false;
    }
    if(!Character.isLetter(cmd.charAt(0)) || !Character.isDigit(cmd.charAt(1))){
       System.out.println("Invalid command!");
       return false;
    }
    if(cmd.charAt(0) < 'A' || cmd.charAt(0) > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    char cmd0 = cmd.charAt(0);
    int cmd1 = Character.getNumericValue(cmd.charAt(1));
    if(!this.allBlocks.get(cmd0).get(cmd1).getOccupiedState()){
      System.out.println("No stack on the location chosen!");
      return false;
    }
    stash chosenStash = this.allBlocks.get(cmd0).get(cmd1).getMaster();
    if(!isComputer){
      System.out.println("________________________________________________________");
      System.out.println("Where do you want to move the "+colorNames.get(chosenStash.getColor())+" stack to?");
      System.out.println("________________________________________________________");
    }
    String moveCmd = scan.nextLine();
    boolean status = moveStash(moveCmd, chosenStash);
    return status;
  }
  public boolean getSonarCmd(Scanner scan, checkerBoard oppoBoard) {///get the sonar command
    String cmd = scan.nextLine();
    cmd = cmd.toUpperCase();
    if(cmd.length() != 2){
      System.out.println("Not valid command!");
      return false;
    }
    if(!Character.isLetter(cmd.charAt(0)) || !Character.isDigit(cmd.charAt(1))){
      System.out.println("Not valid command!");
       return false;
    }
    if(cmd.charAt(0) < 'A' || cmd.charAt(0) > 'T'){
      System.out.println("Location out of checkboard!");
      return false;
    }
    int numGreen = 0;
    int numPurple = 0;
    int numRed = 0;
    int numBlue = 0;
    char cmd0 = cmd.charAt(0);
    int cmd1 = Character.getNumericValue(cmd.charAt(1));
    for (int i = cmd0 - 3; i <= cmd0 + 3; i++){
      for (int j = cmd1-Math.abs(3 - Math.abs(cmd0 - i)); j <= cmd1+Math.abs(3 - Math.abs(cmd0 - i)) ; j++){
       
        char color = sonarCheckBlock(i, j, oppoBoard);
        if(color == 'G'){
          numGreen++;
        }
        if(color == 'P'){
          numPurple++;
        }
        if(color == 'R'){
          numRed++;
        }
        if(color == 'B'){
          numBlue++;
        }   
      }
    }
    System.out.println("________________________________________________________");
    System.out.println("Green Stacks occupy "+numGreen+" squares");
    System.out.println("Purple Stacks occupy "+numPurple+" squares");
    System.out.println("Red Stacks occupy "+numRed+" squares");
    System.out.println("Blue Stacks occupy "+numBlue+" squares");
    System.out.println("________________________________________________________");
    return true;
  }
  public void printBoard(checkerBoard oppoBoard){///print the board
    System.out.println("  0|1|2|3|4|5|6|7|8|9              0|1|2|3|4|5|6|7|8|9  ");
    for (int i = 'A'; i <= 'T'; i++){
      for (int j = 0; j < 4 * width + 16; j++){
        if(j == 0 || j == 22 || j == 33 || j == 55){
          System.out.print((char)i);
        }
        else if((j >= 3 && j <= 19 && j % 2 == 1) || (j >= 36 && j <= 52 && j % 2 == 0)){
          System.out.print("|");
        }
        else {
          if(j <= 20 && j >= 2){///print your board
            block check_block = allBlocks.get(i).get((j - 2) / 2);
            if(check_block.getOccupiedState() && check_block.getHitState()){
              System.out.print('*');
            }
            else if(check_block.getOccupiedState() && !check_block.getHitState()){
              System.out.print(check_block.getColor());
            }
            else {
              System.out.print(" ");
            }
          }
          else if(j >= 35 && j <= 53){///print the enemy's board
            ArrayList<ArrayList<block>> oppoBlocks = oppoBoard.getAllBlocks();
            block check_block = oppoBlocks.get(i).get((j - 35) / 2);
            if(check_block.getDigState()){
              System.out.print(check_block.getColor());
            }
            else if(check_block.getMissState()){
              System.out.print('X');
            }
            else {
              System.out.print(" ");
            }
          }
          else {
            System.out.print(" ");
          }
        }
      }
      System.out.println("");
    }
    System.out.println("  0|1|2|3|4|5|6|7|8|9              0|1|2|3|4|5|6|7|8|9  "); 
    
  }
}
