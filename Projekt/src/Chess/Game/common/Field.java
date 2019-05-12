/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Game.common;

/**
 *
 * @author Šimon
 */
/**
 * Rozhraní k polím, které lze umístit na hrací desku.
 */
public interface Field {

    /**
     * Možné směry sousedů pole.
     */
    public enum Direction{
        /**
         * Dole (Down) od pole.
         */
        D,
        
        /**
         * Vlevo (Left) od pole.
         */
        L,
        
        /**
         * Vlevo dole po diagonále (Left-Down) od pole.
         */
        LD,
        
        /**
         * Vlevo nahoru po diagonále (Left-Up) od pole.
         */
        LU,
        
        /**
         * Vpravo (Right) od pole.
         */
        R,
        
        /**
         * Vpravo dole po diagonále (Right-Down) od pole.
         */
        RD,
        
        /**
         * Vpravo nahoru po diagonále (Right-Up) od pole.
         */
        RU,
        
        /**
         * Nahoru (Up) od pole.
         */
        U   
    }
    
    /**
     * Tato matoda přidá pole na zasanou polohu na desku.
     * 
     * @param dirs  poloha
     * @param field pole
     */
    public void addNextField(Field.Direction dirs, Field field);
    
    /**
     * Vrací figurku, která leží na poli.
     * 
     * @return vrací figurku
     */
    public Figure get();
    
    /**
     * Test, zda je pole prázdné.
     * 
     * @return vrací úspěšnost akce
     */
    public boolean isEmpty();
    
    /**
     * Metoda vrací souseda v určitém směru.
     * Metoda dostane polohu souseda, podle které vrátí pole sousedící se zadaným polem v daném směru.
     * 
     * @param dirs  směr
     * @return      vracé sousedící pole v daném směru
     */
    public Field nextField(Field.Direction dirs);
    
    /**
     * Metoda vloží figurku na pole.
     * Nejdřícve se zjistí, zda je pole prázdné. Pokud ano vloží se na něj figurka. Pokud ne vrací se neúspěch.
     * 
     * @param disk  figurka
     * @return      úspěšnost akce (true-úspěch, false-neúspěch)
     */
    public boolean put(Figure disk);
    
    /**
     * Metoda odstraní figurku z pole.
     * Nejprve se zkontroluje že na poli je figurka, poté se smaže.
     * 
     * @param disk  figurka
     * @return      úspěšnost akce (true-úspěch, false-neúspěch)
     */
    public boolean remove(Figure disk);
    
    /**
     * Metoda vrací řádek pole na desce.
     * 
     * @return  vrací řádek pole
     */
    public int getRow();
    
    /**
     * Metoda vrací sloupec pole na desce.
     * 
     * @return  vrací sloupec pole
     */
    public int getCol();

}
