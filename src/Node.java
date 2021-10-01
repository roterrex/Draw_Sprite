/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.awt.Point;
import java.awt.Color;

public class Node {
  Point lineNum;
  Color col;

  SetUtil stngs;

  public Node(Point inLineNum, Color inCol){
    stngs = SetUtil.getSettings();
    
    lineNum = inLineNum;
    col = inCol;
  }

  public void setPos(Point inLineNum){
    lineNum = inLineNum;
  }
  public void setPos(int x, int y){
    lineNum = new Point(x,y);
  }

  public void setCol(Color inCol){
    col = inCol;
  }

  public Point getPos(){
    return lineNum;
  }
}
