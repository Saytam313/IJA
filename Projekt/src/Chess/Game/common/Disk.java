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
    
    private int moveKR(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 1) && (next.get().getType() != 4)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    private int moveKD(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 2) && (next.get().getType() != 4)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    private int moveKP(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 0)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    private int moveKJ(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 3)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    private int moveKK(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 5)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    @Override
    public boolean move(Field moveTo){
        
        if(this.typ == 0) {
            if(this.field.getCol() == moveTo.getCol()) {
                if((this.field.getRow() < moveTo.getRow()) && isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 2) && moveTo.isEmpty()) {
                        Field next = moveTo.nextField(Field.Direction.D);
                        if(!next.isEmpty()) {
                            return false;
                        }
                        return this.movePesak1(moveTo);
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        return this.movePesak1(moveTo);
                    } else {
                        return false;
                    }
                } else if((this.field.getRow() > moveTo.getRow()) && !isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 7) && moveTo.isEmpty()) {
                        Field next = moveTo.nextField(Field.Direction.U);
                        if(!next.isEmpty()) {
                            return false;
                        }
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
                Field next = moveTo;
                int endU = 8 - moveTo.getRow();
                int ok = 0;
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.U);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                endU = moveTo.getRow() - 1;
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.D);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                endU = moveTo.getCol() - 1;
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.L);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                endU = 8 - moveTo.getCol();
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.R);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                if((moveTo.getCol() - 1) >= (8 - moveTo.getRow())) {
                    endU = 8 - moveTo.getRow();
                } else {
                    endU = moveTo.getCol() - 1;
                }
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.LU);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKD(next, moveTo, ok);
                
                next = moveTo;
                if((moveTo.getCol() - 1) >= (moveTo.getRow() - 1)) {
                    endU = moveTo.getRow() - 1;
                } else {
                    endU = moveTo.getCol() - 1;
                }
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.LD);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKD(next, moveTo, ok);
                
                next = moveTo;
                if((8 - moveTo.getCol()) >= (8 - moveTo.getRow())) {
                    endU = 8 - moveTo.getRow();
                } else {
                    endU = 8 - moveTo.getCol();
                }
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.RU);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKD(next, moveTo, ok);
                
                next = moveTo;
                if((8 - moveTo.getCol()) >= (moveTo.getRow() - 1)) {
                    endU = moveTo.getRow() - 1;
                } else {
                    endU = 8 - moveTo.getCol();
                }
                while(endU > 0) {
                    if(next.isEmpty()) {
                        next = next.nextField(Field.Direction.RD);
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKD(next, moveTo, ok);
                
                if(this.isWhite) {
                    if((moveTo.getCol() > 1) && (moveTo.getRow() < 8)) {
                        next = moveTo.nextField(Field.Direction.LU);
                        ok = this.moveKP(next, moveTo, ok);
                    } else {
                        ok++;
                    }
                        
                    if((moveTo.getCol() < 8) && (moveTo.getRow() < 8)){
                        next = moveTo.nextField(Field.Direction.RU);
                        ok = this.moveKP(next, moveTo, ok);
                    } else {
                        ok++;
                    }
                } else {
                    if((moveTo.getCol() > 1) && (moveTo.getRow() > 1)) {
                        next = moveTo.nextField(Field.Direction.LD);
                        ok = this.moveKP(next, moveTo, ok);
                    } else {
                        ok++;
                    }
                    
                    if((moveTo.getCol() < 8) && (moveTo.getRow() > 1)) {
                        next = moveTo.nextField(Field.Direction.RD);
                        ok = this.moveKP(next, moveTo, ok);
                    } else {
                        ok++;
                    }
                }
                
                if((moveTo.getCol() > 2) && (moveTo.getRow() < 8)) {
                    next = moveTo.nextField(Field.Direction.U).nextField(Field.Direction.L).nextField(Field.Direction.L);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() > 2) && (moveTo.getRow() > 1)) {
                    next = moveTo.nextField(Field.Direction.D).nextField(Field.Direction.L).nextField(Field.Direction.L);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() < 7) && (moveTo.getRow() < 8)) {
                    next = moveTo.nextField(Field.Direction.U).nextField(Field.Direction.R).nextField(Field.Direction.R);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() < 7) && (moveTo.getRow() > 1)) {
                    next = moveTo.nextField(Field.Direction.D).nextField(Field.Direction.R).nextField(Field.Direction.R);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() > 1) && (moveTo.getRow() < 7)) {
                    next = moveTo.nextField(Field.Direction.U).nextField(Field.Direction.U).nextField(Field.Direction.L);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() > 1) && (moveTo.getRow() > 2)) {
                    next = moveTo.nextField(Field.Direction.D).nextField(Field.Direction.D).nextField(Field.Direction.L);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() < 8) && (moveTo.getRow() < 7)) {
                    next = moveTo.nextField(Field.Direction.U).nextField(Field.Direction.U).nextField(Field.Direction.R);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() < 8) && (moveTo.getRow() > 2)) {
                    next = moveTo.nextField(Field.Direction.D).nextField(Field.Direction.D).nextField(Field.Direction.R);
                    ok = this.moveKJ(next, moveTo, ok);
                } else {
                    ok++;
                }
                
                if((moveTo.getCol() == 1) && (moveTo.getRow() == 8)) {
                    ok += 5;
                    next = moveTo.nextField(Field.Direction.R);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.RD);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.D);
                    ok = this.moveKK(next, moveTo, ok);
                } else if((moveTo.getCol() == 8) && (moveTo.getRow() == 8)) {
                    ok += 5;
                    next = moveTo.nextField(Field.Direction.D);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.LD);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.L);
                    ok = this.moveKK(next, moveTo, ok);
                } else if((moveTo.getCol() == 1) && (moveTo.getRow() == 1)) {
                    ok += 5;
                    next = moveTo.nextField(Field.Direction.U);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.RU);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.R); 
                    ok = this.moveKK(next, moveTo, ok);
                } else if((moveTo.getCol() == 8) && (moveTo.getRow() == 1)) {
                    ok += 5;
                    next = moveTo.nextField(Field.Direction.L);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.LU);
                    ok = this.moveKK(next, moveTo, ok);

                    next = moveTo.nextField(Field.Direction.U);
                    ok = this.moveKK(next, moveTo, ok);
                } else if(moveTo.getCol() == 1) {
                    ok += 3;
                    next = moveTo.nextField(Field.Direction.U);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.RU);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.R);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.RD);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.D);
                    ok = this.moveKK(next, moveTo, ok);
                } else if(moveTo.getCol() == 8) {
                    ok += 3;
                    next = moveTo.nextField(Field.Direction.D);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.LD);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.L);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.LU);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.U);
                    ok = this.moveKK(next, moveTo, ok);
                } else if(moveTo.getRow() == 8) {
                    ok += 3;
                    next = moveTo.nextField(Field.Direction.R);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.RD);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.D);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.LD);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.L);
                    ok = this.moveKK(next, moveTo, ok);
                } else if(moveTo.getRow() == 1) {
                    ok += 3;
                    next = moveTo.nextField(Field.Direction.L);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.LU);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.U);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.RU);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.R);
                    ok = this.moveKK(next, moveTo, ok);
                } else {
                    next = moveTo.nextField(Field.Direction.LU);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.U);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.RU);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.R);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.RD);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.D);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.LD);
                    ok = this.moveKK(next, moveTo, ok);
                    
                    next = moveTo.nextField(Field.Direction.L);
                    ok = this.moveKK(next, moveTo, ok);
                }
                
                if(ok == 26) {
                    return this.moveLong(moveTo);
                } else {
                    return false;
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
