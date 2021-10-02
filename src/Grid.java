/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 2/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
public class Grid {
  Point gridLineNum;
  float lineSpaceX;
  float lineSpaceY;

  private static SetUtil stngs;

  private static Grid unique;

  private Grid(){
    stngs = SetUtil.getSettings();
    setSize(10);
  }
  public static Grid getGrid(){
    if(unique == null){
      unique = new Grid();
    }
    return unique;
  }

  public void setSize(int inGridLineNum){
    gridLineNum = new Point(inGridLineNum, inGridLineNum);
    lineSpaceX = (float)stngs.GRID_SIZE.x /(gridLineNum.x);
    lineSpaceY = (float)stngs.GRID_SIZE.y /(gridLineNum.y);
  }

  public Point getClosestInter(Point p){
    int rowNum = (int)(p.x/lineSpaceX);
    int colNum = (int)(p.y/lineSpaceY);

    if(p.x % lineSpaceX > lineSpaceX/2){rowNum++;}
    if(p.y % lineSpaceY > lineSpaceY/2){colNum++;}

    return new Point(rowNum, colNum);
  }

  public int nodeNumToGridPos(int i, boolean inX){
    if(inX)
      return (int)(lineSpaceX*i)+stngs.GRID_START.x; 
    return (int)(lineSpaceY*i)+stngs.GRID_START.y;
  }

  public void paint(Graphics g){
    g.setColor(Color.white);
    g.fillRect(stngs.GRID_START.x, stngs.GRID_START.y, stngs.GRID_SIZE.x, stngs.GRID_SIZE.y);
    
    g.setColor(Color.LIGHT_GRAY);
    for(int i = 1; i < gridLineNum.x; i++){
      g.drawLine(nodeNumToGridPos(i, true), stngs.GRID_START.y, 
          nodeNumToGridPos(i, true), stngs.GRID_SIZE.y+stngs.GRID_START.y);
    }
    for(int i = 1; i < gridLineNum.y; i++){
      g.drawLine(stngs.GRID_START.x, nodeNumToGridPos(i, false), 
          stngs.GRID_SIZE.x+stngs.GRID_START.x, nodeNumToGridPos(i, false));
    }

    g.setColor(Color.black);
    g.drawRect(stngs.GRID_START.x, stngs.GRID_START.y, stngs.GRID_SIZE.x, stngs.GRID_SIZE.y);
  }
}
