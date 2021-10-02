/*
* created by: 
*   Matthew Roberts
*   Damian Jurd
*   Dominic Verity
*
* Modified for purpose by: Joel Perry
* last edited: 1/10/21
* 
* based on provided COMP2000 Milestone 2 code with edits to allow color modification
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;

/** Stors and draws items in pop up menu */
class MenuItem extends Rectangle {
  Runnable action;
  String display;
  static Font displayFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
  Color fill;

  /** creates new MenuItem
   * @param display String to display
   * @param x X location to draw
   * @param y Y location to draw
   * @param action Action to run if clicked
   */
  MenuItem(String display, Point loc, Point Size, Runnable action, Color fill) {
    super(loc.x,loc.y, Size.x, Size.y);
    this.display = display;
    this.action = action;
    this.fill = fill;
  }

  /** Paints the menu item
   * @param g Graphics
   */
  void paint(Graphics g) {
    g.setColor(fill);
    g.fillRect(x, y, width, height);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, width, height);
    g.drawRect(x - 1, y - 1, width + 2, height + 2);
    g.setFont(displayFont);
    g.drawString(display, x + 8, y + 23);
  }
}