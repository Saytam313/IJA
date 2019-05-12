/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Game.common;
/**
 * 
 * @author Aleš Tetur Šimon Matyáš
 */
public interface Game {
    /**
     * polozeni figurek na sachovnici pri zacatku hry
     * @param Figurka Figurka kterou chci polozit
     * @param StartOn Pole kam chci figurku polozit
     */
    public void create(Figure Figurka,Field StartOn);
    /**
     * Kontrola tahu v dane hre, vytvoreni zaznamu pro dany pohyb
     * @param figure Figurka kterou chci pohnout
     * @param field Pole na ktere figurkou hýbu
     * @return string se zapisem
     */
    public String move(Figure figure,Field field);
    /**
     * Tvorba zapisu
     * @param figurka Figurka kterou chci pohnout
     * @param moveTo Místo na které se ma figurka pohnout
     * @return string se zapisem
     */
    public String zapisCreator(Figure figurka,Field moveTo);
    /**
     * Prevod zapisu na souradnice 
     * @param zapis zapis
     * @return souradnice odkud a kam se figurka pohla 
     */
    public String zapisReader(String zapis);
    /**
     * Vrátí posledni odebranou figurku ze zasobniku a tu ze zasobniku smaže
     * @return posledni figurku
     */
    public Figure Get_DeadFig();
    /**
     * Vrátí predposledni figurku a smaze ji ze zasobniku
     * @return predposledni figurka
     */
    public Figure Get_PredposledniDeadFig();
    /**
     * prohodi predposledni a posledni figurku v zasobniku odebranych figurek
     */
    public void Switch_PosledniAPredposledni();
}
