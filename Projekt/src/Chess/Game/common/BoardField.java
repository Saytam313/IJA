package Chess.Game.common;

public class BoardField extends java.lang.Object implements Field{
    public int row;
    public int col;
    public Figure disk;
    public Field nField[]=new Field[8];
    
    /**
     * Metoda inicializuje pole na desce.
     * 
     * @param col   sloupec
     * @param row   řádek
     */
    public BoardField(int col, int row){
        this.col=col;
        this.row=row;
        this.disk=null;
    }

    /**
     * Metoda generuje pole.
     * Tato matoda přidá pole na zasanou polohu na desku.
     * 
     * @param dirs  poloha
     * @param field pole
     */
    @Override
    public void addNextField(Field.Direction dirs, Field field){
              switch(dirs){
                case D: this.nField[0]=field;
                case L: this.nField[1]=field;
                case LD: this.nField[2]=field;
                case LU: this.nField[3]=field;
                case R: this.nField[4]=field;
                case RD: this.nField[5]=field;
                case RU: this.nField[6]=field;
                case U: this.nField[7]=field;
                default: break;
              }
    }

    /**
     * Metoda vrací souseda v určitém směru.
     * Metoda dostane polohu souseda, podle které vrátí pole sousedící se zadaným polem v daném směru.
     * 
     * @param dirs  směr
     * @return      vracé sousedící pole v daném směru
     */
    @Override
    public Field nextField(Field.Direction dirs){
            switch(dirs){
                case D: return this.nField[0];
                case L: return this.nField[1];
                case LD: return this.nField[2];
                case LU: return this.nField[3];
                case R: return this.nField[4];
                case RD: return this.nField[5];
                case RU: return this.nField[6];
                case U: return this.nField[7];
              }
        return null;
    }

    /**
     * Metoda vrátí disk vyskytující se na poli.
     * 
     * @return vrátí disk vyskytující se na poli
     */
    @Override
    public Figure get(){
        return this.disk;
    }
    
    /**
     * Metoda zjistí jestli je pole prázdné.
     * 
     * @return  vrací úspěšnost akce (true-prázdné pole, false-pole je obsazené)
     */
    @Override
    public boolean isEmpty(){
          
        if(this.disk==null){
            
            return true;
        }else{
            return false;
        }
    }
   
    /**
     * Metoda vloží figurku na pole.
     * Nejdřícve se zjistí, zda je pole prázdné. Pokud ano vloží se na něj figurka. Pokud ne vrací se neúspěch.
     * 
     * @param disk  figurka
     * @return      úspěšnost akce (true-úspěch, false-neúspěch)
     */
    @Override
    public boolean put(Figure disk){
        if(isEmpty()){
            this.disk = disk;
            disk.put(this);
            return true;
        }else{
            return false;        
        }
    }
    
    /**
     * Metoda odstraní figurku z pole.
     * Nejprve se zkontroluje že na poli je figurka, poté se smaže.
     * 
     * @param disk  figurka
     * @return      úspěšnost akce (true-úspěch, false-neúspěch)
     */
    @Override
    public boolean remove(Figure disk){
        if (!this.isEmpty() && this.disk==disk){
            disk.remove();
            
            this.disk=null;
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Matoda na zjištění dvou stejných polí na desce.
     * Matoda zjistí zda se dvě pole na desce shodují.
     * 
     * @param obj   objekt BoardField
     * @return      vrací rovnost objektů
     */
    @Override
    public boolean equals(java.lang.Object obj){
        if(obj instanceof BoardField){
            final BoardField obj2=(BoardField) obj;
            if((this.row==obj2.row) && (this.col==obj2.col)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metoda vrací hashCode.
     * 
     * @return  vrací hashCode
     */
    @Override
    public int hashCode(){
        return super.hashCode();
    }
    
    /**
     * Metoda vrací řádek pole na desce.
     * 
     * @return  vrací řádek pole
     */
    @Override
    public int getRow(){
        return this.row;
    }
    
    /**
     * Metoda vrací sloupec pole na desce.
     * 
     * @return  vrací sloupec pole
     */
    @Override
    public int getCol(){
        return this.col;
    }
}
