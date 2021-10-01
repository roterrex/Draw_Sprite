/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

import java.awt.Point;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class SpritePolygon{
  Trio<Polygon,Color,Color> poly;
  List<Node> nodes;
  
  public SpritePolygon(Color filCol, Color linCol){
    poly = new Trio<Polygon,Color,Color>(new Polygon(), filCol, linCol);
    nodes = new ArrayList<Node>();
  }

  public void setFill(Color col){
    poly.setMiddle(col);
  }
  public void setLine(Color col){
    poly.setRight(col);
  }

  public Optional<Node> getNodeAt(Point loc){
    Point line = Grid.getGrid().getClosestInter(loc);
    for(Node n : nodes){
      if(n.lineNum.x == line.x && n.lineNum.y == line.y){
        return Optional.of(n);
      }
    }
    return Optional.empty();
  }

  public void addNode(Point loc){
    Point NodeNum = Grid.getGrid().getClosestInter(loc);
    addPolyPnt(NodeNum);
    nodes.add(new Node(NodeNum, Color.black));
  }

  public void addPolyPnt(Point NodeNum){
    poly.getLeft().addPoint(NodeNum.x*Grid.getGrid().lineSpaceX,
        NodeNum.y*Grid.getGrid().lineSpaceY);
  }

  public void removeNode(Node nr){
    poly.getLeft().reset();
    nodes.remove(nr);

    for(Node n : nodes){
      addPolyPnt(n.lineNum);
    }
  }

  public void paint(Graphics g){
    g.setColor(poly.getMiddle());
    g.fillPolygon(poly.getLeft());
    g.setColor(poly.getRight());
    g.drawPolygon(poly.getLeft());

  }
}
