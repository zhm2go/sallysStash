package hz202.hwk2;

public class block {///define each block on the checkerboard
  private int x;///the coordinate may not be in use
  private int y;
  private boolean isOccupied;
  private boolean isMiss;
  private boolean isHit;
  private stash master;
  private char color;
  private boolean isDig;
  
  public block() {///initialize a block
   
    isOccupied = false;
    isMiss = false;
    isHit = false;
    master = null;
    isDig = false;
  }
  
  

  public boolean getOccupiedState() {///next few are set or get the block's state
    return isOccupied;
  }
  public boolean getMissState() {
    return isMiss;
  }
  public boolean getHitState() {
    return isHit;
  }

  public boolean getDigState() {
    return isDig;
  }

  public char getColor() {
    return color;
  }

  public void setMaster(stash new_stash) {
    master = new_stash;
    color = new_stash.getColor();
    isOccupied = true;
  }

  public void rmMaster() {
    master = null;
    isOccupied = false;
    isHit = false;
  }

  public stash getMaster() {
    return master;
  }

  public void setHitState(boolean x) {
    isHit = x;
  }

  public void setDigState(boolean x) {
    isDig = x;
  }

  public void setMissState(boolean x) {
    isMiss = x;
  }
}
