package hz202.hwk2;

import java.util.ArrayList;

public class stash {
  private ArrayList<block> blocks;
  private int numBlocks;
  private char color;

  public stash(int numblocks, char Color) {///define a placed stash
    numBlocks = numblocks;
    color = Color;
    blocks = new ArrayList<block>();
  }

  public void addBlock(block blk) {
    blocks.add(blk);
    blk.setMaster(this);
  }

  public void setBlock(int num, block blk) {
    blocks.set(num, blk);
    blk.setMaster(this);
  }
  
  public ArrayList<block> getBlocks(){ ///this method may be in use in version 2
    return blocks;
    }

  public char getColor() {
    return color;
  }

  public int getNum() {
    return numBlocks;
  }

  public void clearBlocks() {
    blocks = new ArrayList<block>();
  }
}
