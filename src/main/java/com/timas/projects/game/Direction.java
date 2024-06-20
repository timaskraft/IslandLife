package com.timas.projects.game;

public enum Direction
{
   CENTER(0,0),
   UP(0, -1),
   RIGHT(1, 0),
   DOWN(0, 1),
   LEFT(-1, 0);

   private final int deltaX;
   private final int deltaY;

   Direction(){
      this.deltaX = 0;
      this.deltaY = 0;
   }

   Direction(int deltaX, int deltaY) {
      this.deltaX = deltaX;
      this.deltaY = deltaY;
   }

   public int getDeltaX() {
      return deltaX;
   }

   public int getDeltaY() {
      return deltaY;
   }
}