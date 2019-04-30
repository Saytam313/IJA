/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ija.ija2018.homework2.common;

/**
 *
 * @author Šimon
 */
public interface Field {

    public enum Direction{
        D,L,LD,LU,R,RD,RU,U   
    }
    
    public void addNextField(Field.Direction dirs, Field field);
    public Figure get();
    public boolean isEmpty();
    public Field nextField(Field.Direction dirs);
    public boolean put(Figure disk);
    public boolean remove(Figure disk);
    public int getRow();
    public int getCol();

}
