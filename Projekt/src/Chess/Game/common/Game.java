/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chess.Game.common;

public interface Game {
    public void create(Figure Figurka,Field StartOn);
    public String move(Figure figure,Field field);
    public String zapisCreator(Figure figurka,Field moveTo);
    public String zapisReader(String zapis);
    public Figure Get_DeadFig();
    public Figure Get_PredposledniDeadFig();
    public void Switch_PosledniAPredposledni();
}