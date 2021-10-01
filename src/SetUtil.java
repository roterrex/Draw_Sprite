/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.awt.Point;

public class SetUtil {
  static SetUtil unique;

  private SetUtil(){

  }
  public static SetUtil getSettings(){
    if(unique == null){
      unique = new SetUtil();
    }
    return unique;
  }

  final Point WINDOW_SIZE = new Point(1000,800);
  final Point GRID_SIZE = new Point(800,800);
  
}
