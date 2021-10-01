/**
 * Copyright 2021, Joel Perry, joel.perry@students.com.au
 * last edited: 1/10/21
 * 
 * This software is free for use or modification and may be re-released with credit
 */

/** Generic class able to hold 3 of any types*/
public class Trio<L,M,R> {
  private L left;
  private M middle;
  private R right;

  /** Creates new trio
  * @param L left datatype
  * @param l data to go in left
  * @param M middle datatype
  * @param m data to go in middle
  * @param R right datatype
  * @param r data to go in right
  */
  public Trio(L l, M m, R r) {
    left = l;
    middle = m;
    right = r;
  }

  /** gets data from left
  * @param L left datatype
  * @return data in left
  */
  public L getLeft() {
    return left;
  }
  /** gets data from middle
  * @param M middle datatype
  * @return data in middle
  */
  public M getMiddle() {
    return middle;
  }
  /** gets data from right
  * @param R right datatype
  * @return data in right
  */
  public R getRight() {
    return right;
  }

  /** sets data to left
  * @param L left datatype
  * @param l data to go in left
  */
  public void setLeft(L l) {
    left = l;
  }
  /** sets data to middle
  * @param M middle datatype
  * @param m data to go in middle
  */
  public void setMiddle(M m) {
    middle = m;
  }
  /** sets data to right
  * @param R right datatype
  * @param r data to go in right
  */
  public void setRight(R r) {
    right = r;
  }
}