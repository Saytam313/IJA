/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ija.ija2018.homework2.common;

import ija.ija2018.homework2.game.Board;

public interface Figure {

    public boolean isWhite();
    public boolean move(Field moveTo);
    public boolean reverse_move(Field moveTo);
    public Board getBoard();
    public void put(Field field);
    public String getState();
    public void remove();
    public Field myfield();
}