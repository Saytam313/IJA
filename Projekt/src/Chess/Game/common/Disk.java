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
                type="K";
                break;
            case 4:
                type="Q";
                break;
            case 5:
                type="Kr";
                break;
            default:
                type="E";
                break;
        }
       
        return type+"["+ color +"]" + this.field.getCol()+":"+this.field.getRow();
    }
    
    public boolean move(Field moveTo){
        
        if(this.typ == 0) {
            if(this.field.getCol() == moveTo.getCol()) {
                if((this.field.getRow() < moveTo.getRow()) && isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 2) && moveTo.isEmpty()) {
                        this.field.remove(this);
                        this.field = moveTo;
                        moveTo.put(this);
                        return true;
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        this.field.remove(this);
                        this.field = moveTo;
                        moveTo.put(this);
                        return true;
                    } else {
                        return false;
                    }
                } else if((this.field.getRow() > moveTo.getRow()) && !isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 7) && moveTo.isEmpty()) {
                        this.field.remove(this);
                        this.field = moveTo;
                        moveTo.put(this);
                        return true;
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        this.field.remove(this);
                        this.field = moveTo;
                        moveTo.put(this);
                        return true;
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
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else {
                            return false;
                        }
                    } else if(this.field.getCol() < moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    } 
                } else if(!this.isWhite() && (this.field.getRow() > moveTo.getRow())) {
                    if(this.field.getCol() > moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else {
                            return false;
                        }
                    } else if(this.field.getCol() < moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            this.field.remove(this);
                            moveTo.remove(moveTo.get());
                            this.field = moveTo;
                            moveTo.put(this);
                            return true;
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
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        
                        next = next.nextField(Field.Direction.D);
                    }
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
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
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
                } else if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.RU);
                    }
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
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LD);
                    }
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
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LU);
                    }
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
                } else {
                    return false;
                }
            } else {
                return false;
            }
            
        } else if(this.typ == 3) {
            if(((this.field.getCol() + 1) == moveTo.getCol()) && ((this.field.getRow() + 2) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() + 2) == moveTo.getCol()) && ((this.field.getRow() + 1) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() + 1) == moveTo.getCol()) && ((this.field.getRow() - 2) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() + 2) == moveTo.getCol()) && ((this.field.getRow() - 1) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() - 1) == moveTo.getCol()) && ((this.field.getRow() - 2) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() - 2) == moveTo.getCol()) && ((this.field.getRow() - 1) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() - 1) == moveTo.getCol()) && ((this.field.getRow() + 2) == moveTo.getRow())) {
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
            } else if(((this.field.getCol() - 2) == moveTo.getCol()) && ((this.field.getRow() + 1) == moveTo.getRow())) {
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
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        
                        next = next.nextField(Field.Direction.D);
                    }
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
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
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
                } else if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        
                        next = next.nextField(Field.Direction.RU);
                    }
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
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LD);
                    }
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
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.LU);
                    }
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
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if(this.typ == 5) {
            if((Math.abs(this.field.getCol() - moveTo.getCol()) <= 1) && (Math.abs(this.field.getRow() - moveTo.getRow()) <= 1)) {
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
            } else {
                return false;
            }
        }
            
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    public boolean reverse_move(Field moveTo) {
        if(this.typ == 1) {
            if(this.field.getCol() == moveTo.getCol()) {
                if(this.field.getRow() >= moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = this.field.getRow(); x > moveTo.getRow(); x--) {
                        if(!next.isEmpty()) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.R);
                    }
                    next.remove(this);
                    moveTo.put(this);
                    return true;
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty()) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
                    next.remove(this);
                    moveTo.put(this);
                    return true;
                }
        } else if(this.field.getRow() == moveTo.getRow()) {
                if(this.field.getCol() >= moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty()) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.U);
                    }
                    next.remove(this);
                    moveTo.put(this);
                    return true;
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty()) {
                            return false;
                        }
                        next = next.nextField(Field.Direction.D);
                    }
                    next.remove(this);
                    moveTo.put(this);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if(this.typ == 0) {
            if(this.field.getRow() == moveTo.getRow()) {
                if((this.field.getCol() > moveTo.getCol()) && !isWhite()) {
                    if(Math.abs(this.field.getCol() - moveTo.getCol()) == 2 && this.field.getRow() == 1) {
                        Field next = moveTo;
                        next.remove(this);
                        moveTo.put(this);
                        return true;
                    } else if(Math.abs(this.field.getCol() - moveTo.getCol()) == 1) {
                        Field next = moveTo;
                        next.remove(this);
                        moveTo.put(this);
                        return true;
                    } else {
                        return false;
                    }
                } else if((this.field.getCol() < moveTo.getCol()) && isWhite()) {
                    if(Math.abs(this.field.getCol() - moveTo.getCol()) == 2 && this.field.getRow() == 8) {
                        Field next = moveTo;
                        next.remove(this);
                        moveTo.put(this);
                        return true;
                    } else if(Math.abs(this.field.getCol() - moveTo.getCol()) == 1) {
                        Field next = moveTo;
                        next.remove(this);
                        moveTo.put(this);
                        return true;
                    } else {
                        return false;
                    }
                }
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
