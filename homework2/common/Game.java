/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ija.ija2018.homework2.common;

public interface Game {
    public void create(Figure Figurka,Field StartOn);
    public boolean move(Figure figure,Field field);
    public void undo();
}