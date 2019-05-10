/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Game.common;

import Chess.Game.game.Board;

public interface Figure {

    public boolean isWhite();
    public boolean move(Field moveTo);
    public boolean reverse_move(Field moveTo);
    public Board getBoard();
    public int getType();
    public void put(Field field);
    public String getState();
    public void remove();
    public Field myfield();
}