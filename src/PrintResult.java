/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class PrintResult {
  
  static PrintResult unique;
  FileWriter file;

  private PrintResult(){
    try{
      file = new FileWriter("Sprite.txt");
      /*file = new File("Sprite.txt");
      if(!file.createNewFile()){
        for(int i = 0; i < 20 && !file.createNewFile(); i++){
          file = new File("Sprite"+i+".txt");
        }
      }*/
    } catch (IOException e) {
      System.out.println("An error occurred. main");
      //e.printStackTrace();
    }
    
  }
  public static PrintResult getPrinter(){
    if(unique == null){
      unique = new PrintResult();
    }
    return unique;
  }

  public void print(List<SpritePolygon> polygons){
    try {

      file.write("  //to work this code should be copied into a setPoly method"+System.lineSeparator());
      file.write("  //you must also include the Trio generic class in your folder and class");
      file.write(System.lineSeparator());
      file.write("  //To access the sprite you can either apply it to a variable or return it");
      file.write(System.lineSeparator());
      file.write("  //Printing it is then similar to the stock code except polygons must be fetched with"); 
      file.write(System.lineSeparator());
      file.write("  //.getLeft() fill color with .getMiddle() and line color with .getRight()");
      file.write(System.lineSeparator());
      file.write("  //The app can only create black lines at present but you can edit the created code to ");
      file.write("get colored lines"+System.lineSeparator()+System.lineSeparator());

      file.write("  //Citation do not remove"+System.lineSeparator());
      file.write("  //Created by Draw_Sprite app, free for use with credit"+System.lineSeparator());
      file.write("  //https://github.com/roterrex/Draw_Sprite/tree/master/src"+System.lineSeparator());
      file.write(System.lineSeparator());

      file.write("  List<Trio<Polygon, Color, Color>> sprite = new ArrayList<Trio<Polygon,Color,");
      file.write("Color>>();"+System.lineSeparator());
      file.write("  Point locXY = new Point(loc.x + 0, loc.y + 0); // change the 0's to adjust ");
      file.write("position"+System.lineSeparator());
      file.write(System.lineSeparator());

      printColors(polygons);
      printPolygons(polygons);

      file.close();
    } catch (IOException e) {
      System.out.println("An error occurred. print");
      //e.printStackTrace();
    }
  }

  private void printPolygons(List<SpritePolygon> polygons){
    int i = 0;
    for(SpritePolygon polyT : polygons){
      List<Node> poly = polyT.nodes;  
      int colNum = findCol(polyT.poly.getMiddle(), polygons);  
      String mainCol = "";
      String lineCol = "";
      if(colNum != -1){
        mainCol = "c"+((Integer)colNum).toString();
      }
      colNum = findCol(polyT.poly.getRight(), polygons); 
      if(colNum != -1){
        lineCol = "c"+((Integer)colNum).toString();
      }

      try {
      file.write("  Polygon p"+i+" = new Polygon();"+System.lineSeparator());
      for(Node n : poly){
        file.write("  body.addPoint(locXY.x + scale.x*"+n.lineNum.x+",");
        file.write("locXY.y + scale.y*"+n.lineNum.y+");"+System.lineSeparator());
      }    
      file.write("  sprite.add(new Trio<Polygon,Color,Color>(p"+i+","+mainCol+","+lineCol+"));");
      file.write(System.lineSeparator());
      file.write(System.lineSeparator());
      } catch (IOException e) {
        System.out.println("An error occurred. polygon");
        //e.printStackTrace();
      }
      i++;
    }
  }

  private void printColors(List<SpritePolygon> polygons){
    List<Color> cols = getColorList(polygons);
    int i = 1;   
    try {
      for(Color col : cols){
        file.write("  final Color c"+i+" = new Color("+col.getRed()+","+col.getGreen()+",");
        file.write(col.getBlue()+");"+System.lineSeparator());
        
        i++;
      }
      file.write(System.lineSeparator());
    } catch (IOException e) {
      System.out.println("An error occurred. color");
      //e.printStackTrace();
    }
  }

  private int findCol(Color c1, List<SpritePolygon> polygons){
    List<Color> colList = getColorList(polygons);
    int i = 1;
    for(Color col : colList){
      if(equalsCol(c1, col)){
        return i;
      }
      i++;
    }
    return -1;
  }
  private boolean equalsCol(Color c1, Color c2){
    if(c1.getRGB() == c2.getRGB())
      return true;
    return false;
  }

  private List<Color> getColorList(List<SpritePolygon> polygons){
    boolean exists = false;
    List<Color> colList = new ArrayList<Color>();
    colList.add(polygons.get(0).poly.getRight());
    for(SpritePolygon poly : polygons){

      for(Color c : colList){
        if(equalsCol(c, poly.poly.getMiddle())){
          exists = true;
        }
      }
      if(exists == false){
        colList.add(poly.poly.getMiddle());
      }
      exists = false;
    }
    return colList;
  }
}
