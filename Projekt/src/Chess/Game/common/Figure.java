/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Game.common;

import Chess.Game.game.Board;
/**
 * Rozhraní reprezentuje jednu figuru. Figurka může nabývat dvou barev - bílá nebo černá.
 * 
 * @author Aleš Tetur Šimon Matyáš
 */
public interface Figure {

    /**
     * Metoda vžky vrací je-li figurka býlá nebo černá.
     * 
     * @return (býlá-true, černá-false)
     */
    public boolean isWhite();
    
    /**
     * Metoda zajišťující správný pohyb figurek.
     * Nejdříve se zjistá, jestli se nejedná o vyhození Krále. Výsledek se uloží do a.
     * Poté se zjistí o jaký typ figurky se jedná. Dále se zkontroluje směr pohybu pro daný typ figurky. Pokud je vše v pořádku,
     * vrátí se hodnota úspěšnosti akce. Pokud během procesu nastane chyba, vrátí se hodnota -1 a proces se ukončí.
     * <p>
     * Správnost pohybu se kontroluje pomosí pomocných metod.
     * <p>
     * Pro Pěšáky při tahu je to funkce movePesak1(Field moveTo), při vyhazování je to funkce movePesak2(Field moveTo).
     * Pro Jezdce se nevyužívá zvláštní funkce. Protože Jezcec se pohybuje pouze na izolovaná políčka, kontroluje se pohyb rovnou.
     * Pro Krále je funkcí více, kvůli tomu, že se nesmí svím pohybem ohrozit. To kontrolují funkce moveKR(Field next, Field moveTo, int ok), 
     * moveKD(Field next, Field moveTo, int ok), moveKP(Field next, Field moveTo, int ok), moveKJ(Field next, Field moveTo, int ok), moveKK(Field next, Field moveTo, int ok)
     * Pro ostatní figurky to je funkce moveLong(Field moveTo).
     * @param moveTo    místo kan se má táhnout
     * @return          vrací úspěšnost akce (0-vpořádku, 1-šach, 2-mat, -1-nelze táhnout)
     */
    public int move(Field moveTo);
    
    /**
     * Metoda pro zpětný pohyb.
     * Tato metoda nic nekontroluje, protože veškeré pohyby se kontrolují při pohybu vpřed.
     * 
     * @param moveTo    místo kan se má táhnout
     * @return          vrací úspěšnost akce
     */
    public boolean reverse_move(Field moveTo);
    
    /**
     * Metoda vrací desku, na které se nachází figurka.
     * 
     * @return vrací desku
     */
    public Board getBoard();
    
    /**
     *  Metoda vrací typ figurky.
     * 
     * @return (0-Pěšák, 1-Věž, 2-Střelec, 3-Jezdec, 4-Královna, 5-Král)
     */
    public int getType();
    
    /**
     * Tato metoda vkládá do figurky pole, na kterém se nachází.
     * 
     * @param field pole
     */
    public void put(Field field);
    
    /**
     * Tato metoda vrací řetězec, ve kterém je napsaná pozici figurky, její typ a barvu.
     * 
     * @return (typ["barva"]"pozice-sloupec:pozice-radek)
     */
    public String getState();
    
    /**
     * Metoda oebírá figurku z políčka.
     */
    public void remove();
    
    /**
     * Metoda vždy vrací pole, na kterém se figurka právě nachází.
     * 
     * @return vrací pole
     */
    public Field myfield();
}
