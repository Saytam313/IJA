package ija.ija2018.homework2.common;
//pomazanka
import ija.ija2018.homework2.game.Board;
//chleba
public class Disk extends java.lang.Object implements Figure{
    private final int typ;
    private final boolean isWhite;
    public Field field;
    public Board board;
    //typ:0 Vez
    //typ:1 Pesak
    //typ:2 Disk na damu
    public Disk(int typ,Board board,boolean isWhite){
        this.board=board;
        this.typ=typ;
        this.isWhite=isWhite;
        this.field=null;
    }
    public Field myfield(){
        return this.field;
    }
    public void remove(){
        this.field=null;
    }
    public boolean isWhite(){
        return this.isWhite;
    }
    public Board getBoard(){
        return this.board;
    }
    public void put(Field field){
        this.field=field;
    }
    public String getState(){
        String color;
        String type;
        if(isWhite()){
            color="W";
        }else{
            color="B";
        }
        if(typ==0){
            type="V";
        }else{
            type="P";
        }
       
        return type+"["+ color +"]" + this.field.getCol()+":"+this.field.getRow();
    }
    
    public boolean move(Field moveTo){
         
        int diff =0;
        int Col_diff=0;
        int Row_diff=0;
        char dir;
        int tRow;
        int tCol;
        if(this.field==null){
            return false;      
        }else{
            tRow=this.field.getRow();
            tCol=this.field.getCol();
        }
        
        int mRow=moveTo.getRow();
        int mCol=moveTo.getCol();

        if(tRow!=mRow&&tCol!=mCol){
            Col_diff=tCol-mCol;
            Row_diff=tRow-mRow;
            diff=Math.abs(Col_diff);
            
            if(Math.abs(Row_diff)!=Math.abs(Col_diff)){
                return false;
            }
            if(Col_diff>0){

                if(Row_diff<0){
                    dir='T';//T=RU                   
                }else{
                    dir='F';//F=RD
                }
            }else{
                
                if(Row_diff<0){
                    dir='K';//K=LU                   
                }else{
                    dir='J';//J=LD
                }
            }
        }else if(tRow!=mRow){
            diff=tRow-mRow;
            if(diff>=0){
                dir='D';
            }else{
                diff=diff*(-1);
                dir='U';
            }
        }else if(tCol!=mCol){
            diff=tCol-mCol;
            if(diff>0){
                dir='L';
            }else{
                diff=diff*(-1);
                dir='R';
            }
        }else{
            return false;
        }
        Field nextfield=this.field;

        
        if(typ==0){   
            while(diff!=0){
                switch(dir){
                    case 'D':
                        nextfield=nextfield.nextField(Field.Direction.D);
                        //diff--;
                        break;
                    case 'U':
                        nextfield=nextfield.nextField(Field.Direction.U);
                        //diff++;
                        break;
                    case 'L':
                        nextfield=nextfield.nextField(Field.Direction.L);
                        //diff--;
                        break;
                    case 'R':   
                        nextfield=nextfield.nextField(Field.Direction.R);
                        //diff++;
                        break;
                    }
                    diff--;
                
                  
                if(!nextfield.isEmpty()){
                    if(nextfield.get().isWhite()!=this.isWhite){
                        nextfield.remove(nextfield.get()); 
 
                                
                    }else{
                        return false;    
                    }    
                }
            }
            this.field.remove(this);
            this.field=moveTo;
            moveTo.put(this);
            return true;
        }else if(typ==1){       
            
            if(dir=='U' && isWhite()){
                if(diff==2&&tRow==2){
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;
                }else if(diff==1){
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;   
                }else{
                    return false;
                }
            }else if(dir=='D' && !isWhite()){
                
                if(diff==2&&tRow==7){
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;  
                
                }else if(diff==1){
                    
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;   
                }else{
                    return false;
                }
            }else{
                return false;
            } 
        }else if(typ==2){

            if(diff==1){
                if(dir=='K'){
                    nextfield=nextfield.nextField(Field.Direction.LU);   
                }else if(dir=='T'){
                    nextfield=nextfield.nextField(Field.Direction.RU);
                }else{
                    return false;
                }
                
                  
                if(!nextfield.isEmpty()){ 
                        return false;    
                    }    
            }else{
                return false;
            }
        this.field.remove(this);
        this.field=moveTo;
        moveTo.put(this);
        return true;
        }else{
            return false;
        }
    }
    
    public boolean reverse_move(Field moveTo){
         
        int diff =0;
        int Col_diff=0;
        int Row_diff=0;
        char dir;
        int tRow;
        int tCol;
        if(this.field==null){
            return false;      
        }else{
            tRow=this.field.getRow();
            tCol=this.field.getCol();
        }
        
        int mRow=moveTo.getRow();
        int mCol=moveTo.getCol();

        if(tRow!=mRow&&tCol!=mCol){
            Col_diff=tCol-mCol;
            Row_diff=tRow-mRow;
            diff=Math.abs(Col_diff);
            
            if(Math.abs(Row_diff)!=Math.abs(Col_diff)){
                return false;
            }
            if(Col_diff>0){

                if(Row_diff<0){
                    dir='T';//T=RU                   
                }else{
                    dir='F';//F=RD
                }
            }else{
                
                if(Row_diff<0){
                    dir='K';//K=LU                   
                }else{
                    dir='J';//J=LD
                }
            }
        }else if(tRow!=mRow){
            diff=tRow-mRow;
            if(diff>=0){
                dir='D';
            }else{
                diff=diff*(-1);
                dir='U';
            }
        }else if(tCol!=mCol){
            diff=tCol-mCol;
            if(diff>0){
                dir='L';
            }else{
                diff=diff*(-1);
                dir='R';
            }
        }else{
            return false;
        }
        Field nextfield=this.field;

        
        if(typ==0){   
            while(diff!=0){
                switch(dir){
                    case 'D':
                        nextfield=nextfield.nextField(Field.Direction.D);
                        //diff--;
                        break;
                    case 'U':
                        nextfield=nextfield.nextField(Field.Direction.U);
                        //diff++;
                        break;
                    case 'L':
                        nextfield=nextfield.nextField(Field.Direction.L);
                        //diff--;
                        break;
                    case 'R':   
                        nextfield=nextfield.nextField(Field.Direction.R);
                        //diff++;
                        break;
                    }
                    diff--;
                
                  
                if(!nextfield.isEmpty()){
                    if(nextfield.get().isWhite()!=this.isWhite){
                        nextfield.remove(nextfield.get()); 
 
                                
                    }else{
                        return false;    
                    }    
                }
            }
            this.field.remove(this);
            this.field=moveTo;
            moveTo.put(this);
            return true;
        }else if(typ==1){       
            
            if(dir=='D' && isWhite()){
                if(diff==2&&tRow==2){
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;
                }else if(diff==1){
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;   
                }else{
                    return false;
                }
            }else if(dir=='U' && !isWhite()){
                
                if(diff==2&&tRow==7){
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;  
                
                }else if(diff==1){
                    
                    this.field.remove(this);
                    this.field=moveTo;
                    moveTo.put(this);
                    return true;   
                }else{
                    return false;
                }
            }else{
                return false;
            } 
        }else if(typ==2){

            if(diff==1){
                if(dir=='F'){
                    nextfield=nextfield.nextField(Field.Direction.LU);   
                }else if(dir=='J'){
                    nextfield=nextfield.nextField(Field.Direction.RU);
                }else{
                    return false;
                }
                  
                if(!nextfield.isEmpty()){ 
                        return false;    
                    }    
            }else{
                return false;
            }
        this.field.remove(this);
        this.field=moveTo;
        moveTo.put(this);
        return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean equals(java.lang.Object obj)
    {
        if (obj instanceof Disk) {
            final Disk obj2=(Disk) obj;
            if (this.field==obj2.field){
                if (this.isWhite==obj2.isWhite) {
                    return (true);
                } 
            }          
        }
        return (false);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }





}
