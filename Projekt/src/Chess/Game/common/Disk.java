package Chess.Game.common;

import Chess.Game.game.Board;

public class Disk extends java.lang.Object implements Figure{
    private final int typ;
    private final boolean isWhite;
    public Field field;
    public Board board;
    //typ:0 Pesak
    //typ:1 Vez
    //typ:2 Strelec
    //typ:3 Kun
    //typ:4 Kralovna
    //typ:5 Kral

    public Disk(int typ,Board board,boolean isWhite){
        this.board=board;
        this.typ=typ;
        this.isWhite=isWhite;
        this.field=null;
    }
    
    @Override
    public Field myfield(){
        return this.field;
    }
    
    @Override
    public void remove(){
        this.field=null;
    }
    
    @Override
    public boolean isWhite(){
        return this.isWhite;
    }
    
    @Override
    public Board getBoard(){
        return this.board;
    }
    
    @Override
    public int getType(){
        return this.typ;
    }
    
    @Override
    public void put(Field field){
        this.field=field;
    }
    
    @Override
    public String getState(){
        String color;
        String type;
        if(isWhite()){
            color="W";
        }else if(!isWhite()){
            color="B";
        }else{
            color="E";
        }
        switch (typ) {
            case 0:
                type="P";
                break;
            case 1:
                type="V";
                break;
            case 2:
                type="S";
                break;
            case 3:
                type="J";
                break;
            case 4:
                type="D";
                break;
            case 5:
                type="K";
                break;
            default:
                type="E";
                break;
        }
       
        return type+"["+ color +"]" + this.field.getCol()+":"+this.field.getRow();
    }
    
    private boolean movePesak1(Field moveTo) {
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    private boolean movePesak2(Field moveTo) {
        this.field.remove(this);
        moveTo.remove(moveTo.get());
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    private boolean moveLong(Field moveTo) {
        if(!moveTo.isEmpty()) {
            if(this.isWhite == moveTo.get().isWhite()) {
                return false;
            }
        }
        this.field.remove(this);
        moveTo.remove(moveTo.get());
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    @Override
    public boolean move(Field moveTo){
        if(this.field == moveTo) {
            return false;
        }
        
        if(this.isWhite() != getBoard().wTurn) {
            return false;
        } else if(getBoard().wTurn){
            getBoard().wTurn = false;
        } else {
            getBoard().wTurn = true;
        }
        
        if(this.typ == 0) {
            if(this.field.getCol() == moveTo.getCol()) {
                if((this.field.getRow() < moveTo.getRow()) && isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 2) && moveTo.isEmpty()) {
                        return this.movePesak1(moveTo);
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        return this.movePesak1(moveTo);
                    } else {
                        return false;
                    }
                } else if((this.field.getRow() > moveTo.getRow()) && !isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 7) && moveTo.isEmpty()) {
                        return this.movePesak1(moveTo);
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        return this.movePesak1(moveTo);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if((Math.abs(this.field.getCol() - moveTo.getCol()) == 1) && (Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && !moveTo.isEmpty()) {
                if(this.isWhite() && (this.field.getRow() < moveTo.getRow())) {
                    if(this.field.getCol() > moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else {
                            return false;
                        }
                    } else if(this.field.getCol() < moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    } 
                } else if(!this.isWhite() && (this.field.getRow() > moveTo.getRow())) {
                    if(this.field.getCol() > moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else {
                            return false;
                        }
                    } else if(this.field.getCol() < moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            return this.movePesak2(moveTo);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    } 
                } else {
                    return false;
                }
            } else {
                return false;
            }
            
        } else if(this.typ == 1) {
            if(this.field.getCol() == moveTo.getCol()) {
                if(this.field.getRow() > moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = this.field.getRow(); x > moveTo.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.U);
                    }
                    return this.moveLong(moveTo);
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        
                        next = next.nextField(Field.Direction.D);
                    }
                    return this.moveLong(moveTo);
                } else {
                    return false;
                }
            } else if(this.field.getRow() == moveTo.getRow()) {
                if(this.field.getCol() > moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.R);
                    }
                    return this.moveLong(moveTo);
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
                    return this.moveLong(moveTo);
                } else {
                    return false;
                }
            } else {
                return false;
            }
           
        } else if(this.typ == 2) {
            if(Math.abs(this.field.getCol() - moveTo.getCol()) == Math.abs(this.field.getRow() - moveTo.getRow())) {
                if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.RD);
                    }
                    return this.moveLong(moveTo);
                } else if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.RU);
                    }
                    return this.moveLong(moveTo);
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LD);
                    }
                    return this.moveLong(moveTo);
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LU);
                    }
                    return this.moveLong(moveTo);
                } else {
                    return false;
                }
            } else {
                return false;
            }
            
        } else if(this.typ == 3) {
            if(((this.field.getCol() + 1) == moveTo.getCol()) && ((this.field.getRow() + 2) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() + 2) == moveTo.getCol()) && ((this.field.getRow() + 1) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() + 1) == moveTo.getCol()) && ((this.field.getRow() - 2) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() + 2) == moveTo.getCol()) && ((this.field.getRow() - 1) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() - 1) == moveTo.getCol()) && ((this.field.getRow() - 2) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() - 2) == moveTo.getCol()) && ((this.field.getRow() - 1) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() - 1) == moveTo.getCol()) && ((this.field.getRow() + 2) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else if(((this.field.getCol() - 2) == moveTo.getCol()) && ((this.field.getRow() + 1) == moveTo.getRow())) {
                return this.moveLong(moveTo);
            } else {
                return false;
            }           
        } else if(this.typ == 4) {
            if(this.field.getCol() == moveTo.getCol()) {
                if(this.field.getRow() > moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = this.field.getRow(); x > moveTo.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.U);
                    }
                    return this.moveLong(moveTo);
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        
                        next = next.nextField(Field.Direction.D);
                    }
                    return this.moveLong(moveTo);
                } else {
                    return false;
                }
            } else if(this.field.getRow() == moveTo.getRow()) {
                if(this.field.getCol() > moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.R);
                    }
                    return this.moveLong(moveTo);
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
                    return this.moveLong(moveTo);
                } else {
                    return false;
                }
            } else if(Math.abs(this.field.getCol() - moveTo.getCol()) == Math.abs(this.field.getRow() - moveTo.getRow())) {
                if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.RD);
                    }
                    return this.moveLong(moveTo);
                } else if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        
                        next = next.nextField(Field.Direction.RU);
                    }
                    return this.moveLong(moveTo);
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LD);
                    }
                    return this.moveLong(moveTo);
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LU);
                    }
                    return this.moveLong(moveTo);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if(this.typ == 5) {
            if((Math.abs(this.field.getCol() - moveTo.getCol()) <= 1) && (Math.abs(this.field.getRow() - moveTo.getRow()) <= 1)) {
                return this.moveLong(moveTo);
            } else {
                return false;
            }
        }
            
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    @Override
    public boolean reverse_move(Field moveTo) {
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
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
