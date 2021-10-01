/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
public class Grid {
  Point gridLineNum;
  static final Point defaultGridSize = new Point(10,10);
  int lineSpaceX;
  int lineSpaceY;

  SetUtil stngs;

  private static Grid unique;

  private Grid(){
    gridLineNum = defaultGridSize;
    stngs = SetUtil.getSettings();
    lineSpaceX = stngs.GRID_SIZE.x /(gridLineNum.x+1);
    lineSpaceY = stngs.GRID_SIZE.y /(gridLineNum.y+1);
  }
  public static Grid getGrid(){
    if(unique == null){
      unique = new Grid();
    }
    return unique;
  }

  public void setSize(Point inGridLineNum){
    gridLineNum = inGridLineNum;
    lineSpaceX = stngs.GRID_SIZE.x /(gridLineNum.x+1);
    lineSpaceY = stngs.GRID_SIZE.y /(gridLineNum.y+1);
  }

  public Point getClosestInter(Point p){
    int rowNum = p.x/lineSpaceX;
    int colNum = p.y/lineSpaceY;

    if(p.x % lineSpaceX > lineSpaceX/2){rowNum++;}
    if(p.y % lineSpaceY > lineSpaceY/2){colNum++;}

    return new Point(rowNum, colNum);
  }

  public void paint(Graphics g){
    g.setColor(Color.LIGHT_GRAY);

    for(int i = 1; i <= gridLineNum.x+1; i++){
      g.drawLine(lineSpaceX*i, 0, lineSpaceX*i, stngs.GRID_SIZE.y);
    }
    for(int i = 1; i <= gridLineNum.y; i++){
      g.drawLine(0, lineSpaceY*i, stngs.GRID_SIZE.x, lineSpaceY*i);
    }

    g.setColor(Color.black);
    g.drawLine(stngs.GRID_SIZE.x, 0, stngs.GRID_SIZE.x, stngs.GRID_SIZE.y);
  }
}
