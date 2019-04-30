package ija.ija2018.homework2.common;

public class BoardField extends java.lang.Object implements Field{
    public int row;
    public int col;
    public Figure disk;
    public Field nField[]=new Field[8];
    
    public BoardField(int col, int row){
        this.col=col;
        this.row=row;
        this.disk=null;
    }


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

    @Override
    public Figure get(){
        return this.disk;
    }
    
    @Override
    public boolean isEmpty(){
          
        if(this.disk==null){
            
            return true;
        }else{
            return false;
        }
    }
   
    public boolean put(Figure disk){
        if(isEmpty()){
            this.disk=disk;
            disk.put(this);
            return true;
        }else{
            return false;        
        }
    }
    public boolean remove(Figure disk){
        if (!this.isEmpty() && this.disk==disk){
            

            disk.remove();
            
            this.disk=null;
            return true;
        }else{
            return false;
        }
    }
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
    
    @Override
    public int hashCode(){
        return super.hashCode();
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }






}
