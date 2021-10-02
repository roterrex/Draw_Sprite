/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Window {
  Grid grid;
  List<SpritePolygon> polygons;
  Optional<SpritePolygon> curPoly = Optional.empty();
  PrintResult printer;

  List<MenuItem> Buttons;
  SetUtil stngs;

  int TmpGridLineNum;

  Color fillCol = Color.white;
  Color lineColor = Color.black;

  Color customCol1 = Color.white;
  Color customCol2 = Color.white;
  Color customCol3 = Color.white;
  int selectedCol = 0;
  Color currentEntry;

  public Window(){
    stngs = SetUtil.getSettings();
    polygons = new ArrayList<SpritePolygon>();
    grid = Grid.getGrid();
    printer = PrintResult.getPrinter();
    newPoly();
    Buttons = createButtons();
  }

  public void mouseClicked(MouseEvent e, Color c, int gridNum){ 
    currentEntry = c;
    TmpGridLineNum = gridNum;

    Buttons = createButtons(); 
    Point mousePos = new Point(e.getX(), e.getY());   
    if(mousePos.x > stngs.GRID_SIZE.x){
      buttonClicked(mousePos);
    } else {
      if(curPoly.isPresent()){
        Optional<Node> nodeTmp = curPoly.get().getNodeAt(mousePos);
        if(e.getButton() == MouseEvent.BUTTON1 && !nodeTmp.isPresent()){
          curPoly.get().addNode(mousePos);
        } else if (e.getButton() == MouseEvent.BUTTON3){
          removePointAt(mousePos);
        }
      } else if (!curPoly.isPresent()){
        newPoly();
      }
    }
  }

  private void buttonClicked(Point mousePos){
    for (MenuItem b : Buttons) {
      if (b.contains(mousePos.x, mousePos.y)) {
        b.action.run();
        return;
      }
    }
  }

  private void newPoly(){
    curPoly = Optional.of(new SpritePolygon(fillCol, lineColor));
    polygons.add(curPoly.get());
  }

  void removePointAt(Point loc){
    if(curPoly.isPresent()){
      SpritePolygon poly = curPoly.get();
      Optional<Node> fndPoly = poly.getNodeAt(loc);
      if(fndPoly.isPresent()){
        poly.removeNode(fndPoly.get());
      }
    }
  }

  void deletePoly(){
    if(curPoly.isPresent()){
      if(polygons.size() == 1){
        resetPoly();
      } else {
        polygons.remove(curPoly.get());
      }
    }
  }

  void resetPoly(){
    if(curPoly.isPresent()){
      curPoly.get().poly.getLeft().reset();
    }
  }

  void nextPoly(){
    if(curPoly.isPresent() && polygons.size() > 1){
      int i = findCur();
      if(i == polygons.size()-1){
        curPoly = Optional.of(polygons.get(0));
      } else {
        curPoly = Optional.of(polygons.get(i+1));
      }
    }
  }
  void prevPoly(){
    if(curPoly.isPresent() && polygons.size() > 1){
      int i = findCur();
      if(i == 0){
        curPoly = Optional.of(polygons.get(polygons.size()-1));
      } else {
        curPoly = Optional.of(polygons.get(i-1));
      }
    }
  }

  void changeGridSize(){
    grid.setSize(TmpGridLineNum);
    for(SpritePolygon p : polygons){
      p.redrawNodes();
    }
  }

  private int findCur(){
    int i = 0;
    while(polygons.get(i) != curPoly.get() && i < polygons.size()){
      i++;
    }
    return i;
  }

  public void paint(Graphics g, Point mousePos){
    grid.paint(g);

    for(SpritePolygon p : polygons){
      if(curPoly.isPresent() && p == curPoly.get()){
        p.setLine(Color.red);
      }
      p.paint(g);
      p.setLine(Color.black);
    }

    for(MenuItem b : Buttons){
      b.paint(g);
    }
  }

  private List<MenuItem> createButtons(){
    Buttons = new ArrayList<MenuItem>();
    Buttons.add(new MenuItem("New Poly",new Point(stngs.UI_START.x, stngs.UI_START.y),
        new Point(180, 30), () -> newPoly(), Color.lightGray));


    final int buttonXSiz = 85;
    final int buttonYSiz = 30;
    String Col[] = {"Black", "D Grey", "Grey", "L Grey", "White", "Red", "Green", "Blue", 
        "Yellow", "Cyan", "Magenta", "Orange", "Pink", "Cust 1", "Cust 2", "Cust 3"};
    for(int j = 0; j < 8; j++){
      for(int i = 0; i < 2; i++){
        int k = j; int m = i;
        Color butColor = getCol(Col[j*2+i]);
        Buttons.add(new MenuItem(Col[j*2+i], 
            new Point(stngs.UI_START.x+(buttonXSiz + 10)*i, stngs.UI_START.y+(j+1)*40), 
            new Point(buttonXSiz, buttonYSiz), () -> colButtonPushed(Col[k*2+m], butColor), 
            butColor));
      }
    }

    Buttons.add(new MenuItem("SET",new Point(stngs.UI_START.x+125, stngs.UI_START.y+9*40),
        new Point(55, 30), () -> SetCustCol(selectedCol), Color.lightGray));

    Buttons.add(new MenuItem("Reset Poly",new Point(stngs.UI_START.x, stngs.UI_START.y+10*40),
        new Point(180, 30), () -> resetPoly(), Color.lightGray));

    Buttons.add(new MenuItem("delete",new Point(stngs.UI_START.x, stngs.UI_START.y+11*40),
        new Point(180, 30), () -> deletePoly(), Color.lightGray));

    Buttons.add(new MenuItem("    <-",new Point(stngs.UI_START.x, stngs.UI_START.y+12*40),
        new Point(85, 30), () -> prevPoly(), Color.lightGray));

    Buttons.add(new MenuItem("    ->",new Point(stngs.UI_START.x+95, stngs.UI_START.y+12*40),
        new Point(85, 30), () -> nextPoly(), Color.lightGray));

    Buttons.add(new MenuItem("Print",new Point(stngs.UI_START.x, stngs.UI_START.y+13*40),
        new Point(180, 30), () -> printer.print(polygons), Color.lightGray));

    //grid size set
    Buttons.add(new MenuItem("SET",new Point(stngs.UI_START.x+95, stngs.UI_START.y+14*40),
        new Point(85, 30), () -> changeGridSize(), Color.lightGray));

    return Buttons;
  }

  void colButtonPushed(String s, Color butColor){
    if(s == "Cust 1"){
      selectedCol = 1;
    } else if(s == "Cust 2"){
      selectedCol = 2;
    } else if(s == "Cust 3"){
      selectedCol = 3;
    }
    setCol(butColor);
  }

  void SetCustCol(int i){
    if(i == 1){
      customCol1 = currentEntry;
    } else if(i == 2){
      customCol2 = currentEntry;
    } else if(i == 3){
      customCol3 = currentEntry;
    }
  }

  void setCol(Color c){
    fillCol = c;
    curPoly.get().setFill(c);
  }

  Color getCol(String colString){
    if(colString == "Black"){
      return Color.black;
    } else if(colString == "D Grey"){
      return Color.darkGray;
    } else if(colString == "Grey"){
      return Color.gray;
    } else if(colString == "L Grey"){
      return Color.lightGray;
    } else if(colString == "White"){
      return Color.white;
    } else if(colString == "Red"){
      return Color.red;
    } else if(colString == "Green"){
      return Color.green;
    } else if(colString == "Blue"){
      return Color.blue;
    } else if(colString == "Yellow"){
      return Color.yellow;
    } else if(colString == "Cyan"){
      return Color.cyan;
    } else if(colString == "Magenta"){
      return Color.magenta;
    } else if(colString == "Orange"){
      return Color.orange;
    } else if(colString == "Pink"){
      return Color.pink;
    } else if(colString == "Cust 1"){
      return customCol1;
    } else if(colString == "Cust 2"){
      return customCol2;
    } else if(colString == "Cust 3"){
      return customCol3;
    } else {
      return Color.white;
    }
  }
}
