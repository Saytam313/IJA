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

    public Disk(int typ,Board board,boolean isWhite) {
        this.board=board;
        this.typ=typ;
        this.isWhite=isWhite;
        this.field=null;
    }
    
    @Override
    public Field myfield() {
        return this.field;
    }
    
    @Override
    public void remove() {
        this.field=null;
    }
    
    @Override
    public boolean isWhite() {
        return this.isWhite;
    }
    
    @Override
    public Board getBoard() {
        return this.board;
    }
    
    @Override
    public int getType() {
        return this.typ;
    }
    
    @Override
    public void put(Field field) {
        this.field=field;
    }
    
    @Override
    public String getState() {
        String color;
        String type;
        if(isWhite()) {
            color="W";
        } else if(!isWhite()) {
            color="B";
        } else {
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
    
    private int sachK(Field kral, Field figurka, int ok) {
        if(kral.isEmpty()) {
            return ok;
        } else if((kral.get().getType() == 5) && (kral.get().isWhite() != figurka.get().isWhite())) {
            ok++;
        }
        return ok;
    }
    
    public int sach(Field figurka) {
        
        Field next = figurka;
        int endU;
        int ok = 0;
        boolean mat;
        
        if(figurka.get().getType() == 1) {
            endU = 8 - figurka.getRow();
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.U);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.U);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            endU = figurka.getRow() - 1;
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.D);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.D);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            endU = figurka.getCol() - 1;
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.L);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.L);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            endU = 8 - figurka.getCol();
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.R);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.R);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);
        } else if(figurka.get().getType() == 2) {
            next = figurka;
            if((figurka.getCol() - 1) >= (8 - figurka.getRow())) {
                endU = 8 - figurka.getRow();
            } else {
                endU = figurka.getCol() - 1;
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.LU);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            next = figurka;
            if((figurka.getCol() - 1) >= (figurka.getRow() - 1)) {
                endU = figurka.getRow() - 1;
            } else {
                endU = figurka.getCol() - 1;
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.LD);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            next = figurka;
            if((8 - figurka.getCol()) >= (8 - figurka.getRow())) {
                endU = 8 - figurka.getRow();
            } else {
                endU = 8 - figurka.getCol();
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.RU);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            next = figurka;
            if((8 - figurka.getCol()) >= (figurka.getRow() - 1)) {
                endU = figurka.getRow() - 1;
            } else {
                endU = 8 - figurka.getCol();
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.RD);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);
        } else if(figurka.get().getType() == 0) {
            if(figurka.get().isWhite()) {
                if((figurka.getCol() > 1) && (figurka.getRow() < 8)) {
                    next = figurka.nextField(Field.Direction.LU);
                    ok = sachK(next, figurka, ok);
                }

                if((figurka.getCol() < 8) && (figurka.getRow() < 8)){
                    next = figurka.nextField(Field.Direction.RU);
                    ok = sachK(next, figurka, ok);
                }
            } else {
                if((figurka.getCol() > 1) && (figurka.getRow() > 1)) {
                    next = figurka.nextField(Field.Direction.LD);
                    ok = sachK(next, figurka, ok);
                }

                if((figurka.getCol() < 8) && (figurka.getRow() > 1)) {
                    next = figurka.nextField(Field.Direction.RD);
                    ok = sachK(next, figurka, ok);
                }
            }
        } else if(figurka.get().getType() == 3) {
            if((figurka.getCol() > 2) && (figurka.getRow() < 8)) {
                next = figurka.nextField(Field.Direction.U).nextField(Field.Direction.L).nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() > 2) && (figurka.getRow() > 1)) {
                next = figurka.nextField(Field.Direction.D).nextField(Field.Direction.L).nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() < 7) && (figurka.getRow() < 8)) {
                next = figurka.nextField(Field.Direction.U).nextField(Field.Direction.R).nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() < 7) && (figurka.getRow() > 1)) {
                next = figurka.nextField(Field.Direction.D).nextField(Field.Direction.R).nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() > 1) && (figurka.getRow() < 7)) {
                next = figurka.nextField(Field.Direction.U).nextField(Field.Direction.U).nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() > 1) && (figurka.getRow() > 2)) {
                next = figurka.nextField(Field.Direction.D).nextField(Field.Direction.D).nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() < 8) && (figurka.getRow() < 7)) {
                next = figurka.nextField(Field.Direction.U).nextField(Field.Direction.U).nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);
            }

            if((figurka.getCol() < 8) && (figurka.getRow() > 2)) {
                next = figurka.nextField(Field.Direction.D).nextField(Field.Direction.D).nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);
            }
        } else if(figurka.get().getType() == 4) {
            endU = 8 - figurka.getRow();
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.U);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.U);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            endU = figurka.getRow() - 1;
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.D);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.D);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            endU = figurka.getCol() - 1;
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.L);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.L);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            endU = 8 - figurka.getCol();
            if(endU != 0) {
                next = figurka.nextField(Field.Direction.R);
            }
            mat = false;
            while(endU > 1) {
                if(!mat) {
                    next = next.nextField(Field.Direction.R);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);
            
            if((figurka.getCol() - 1) >= (8 - figurka.getRow())) {
                endU = 8 - figurka.getRow();
            } else {
                endU = figurka.getCol() - 1;
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.LU);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            next = figurka;
            if((figurka.getCol() - 1) >= (figurka.getRow() - 1)) {
                endU = figurka.getRow() - 1;
            } else {
                endU = figurka.getCol() - 1;
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.LD);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            next = figurka;
            if((8 - figurka.getCol()) >= (8 - figurka.getRow())) {
                endU = 8 - figurka.getRow();
            } else {
                endU = 8 - figurka.getCol();
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.RU);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);

            next = figurka;
            if((8 - figurka.getCol()) >= (figurka.getRow() - 1)) {
                endU = figurka.getRow() - 1;
            } else {
                endU = 8 - figurka.getCol();
            }
            mat = false;
            while(endU > 0) {
                if(!mat) {
                    next = next.nextField(Field.Direction.RD);
                    if(!next.isEmpty()) {
                        mat = true;
                    }
                } else {
                    break;
                }
                endU--;
            }
            ok = sachK(next, figurka, ok);
        } else if(figurka.get().getType() == 5) {
            if((figurka.getCol() == 1) && (figurka.getRow() == 8)) {
                next = figurka.nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.D);
                ok = sachK(next, figurka, ok);
            } else if((figurka.getCol() == 8) && (figurka.getRow() == 8)) {
                next = figurka.nextField(Field.Direction.D);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            } else if((figurka.getCol() == 1) && (figurka.getRow() == 1)) {
                next = figurka.nextField(Field.Direction.U);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.R); 
                ok = sachK(next, figurka, ok);
            } else if((figurka.getCol() == 8) && (figurka.getRow() == 1)) {
                next = figurka.nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.U);
                ok = sachK(next, figurka, ok);
            } else if(figurka.getCol() == 1) {
                next = figurka.nextField(Field.Direction.U);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.D);
                ok = sachK(next, figurka, ok);
            } else if(figurka.getCol() == 8) {
                next = figurka.nextField(Field.Direction.D);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.U);
                ok = sachK(next, figurka, ok);
            } else if(figurka.getRow() == 8) {
                next = figurka.nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.D);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            } else if(figurka.getRow() == 1) {
                next = figurka.nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.U);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);
            } else {
                next = figurka.nextField(Field.Direction.LU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.U);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RU);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.R);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.RD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.D);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.LD);
                ok = sachK(next, figurka, ok);

                next = figurka.nextField(Field.Direction.L);
                ok = sachK(next, figurka, ok);
            }
        }
        
        if(ok > 0) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public int mat(Field figurka) {
        if(figurka.isEmpty()) {
            return 0;
        } else if(figurka.get().getType() == 5) {
            return 2;
        } else {
            return 1;
        }
    }
    
    public int move(Field moveTo) {
        
        int a = mat(moveTo);
        
        if(this.typ == 0) {
            if(this.field.getCol() == moveTo.getCol()) {
                if((this.field.getRow() < moveTo.getRow()) && isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 2) && moveTo.isEmpty()) {
                        Field next = moveTo.nextField(Field.Direction.D);
                        if(!next.isEmpty()) {
                            return -1;
                        }
                        if(!this.movePesak1(moveTo)) {
                            return -1;
                        }
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        if(!this.movePesak1(moveTo)) {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                } else if((this.field.getRow() > moveTo.getRow()) && !isWhite()) {
                    if(Math.abs(this.field.getRow() - moveTo.getRow()) == 2 && (this.field.getRow() == 7) && moveTo.isEmpty()) {
                        Field next = moveTo.nextField(Field.Direction.U);
                        if(!next.isEmpty()) {
                            return -1;
                        }
                        if(!this.movePesak1(moveTo)) {
                            return -1;
                        }
                    } else if((Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && moveTo.isEmpty()) {
                        if(!this.movePesak1(moveTo)) {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else if((Math.abs(this.field.getCol() - moveTo.getCol()) == 1) && (Math.abs(this.field.getRow() - moveTo.getRow()) == 1) && !moveTo.isEmpty()) {
                if(this.isWhite() && (this.field.getRow() < moveTo.getRow())) {
                    if(this.field.getCol() > moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    } else if(this.field.getCol() < moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    } 
                } else if(!this.isWhite() && (this.field.getRow() > moveTo.getRow())) {
                    if(this.field.getCol() > moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    } else if(this.field.getCol() < moveTo.getCol()) {
                        if(this.isWhite && !moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else if(!this.isWhite && moveTo.get().isWhite()) {
                            if(!this.movePesak2(moveTo)) {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    } 
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
            
        } else if(this.typ == 1) {
            if(this.field.getCol() == moveTo.getCol()) {
                if(this.field.getRow() > moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = this.field.getRow(); x > moveTo.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.U);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        
                        next = next.nextField(Field.Direction.D);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else if(this.field.getRow() == moveTo.getRow()) {
                if(this.field.getCol() > moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.R);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
           
        } else if(this.typ == 2) {
            if(Math.abs(this.field.getCol() - moveTo.getCol()) == Math.abs(this.field.getRow() - moveTo.getRow())) {
                if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.RD);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.RU);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.LD);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.LU);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
            
        } else if(this.typ == 3) {
            if(((this.field.getCol() + 1) == moveTo.getCol()) && ((this.field.getRow() + 2) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() + 2) == moveTo.getCol()) && ((this.field.getRow() + 1) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() + 1) == moveTo.getCol()) && ((this.field.getRow() - 2) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() + 2) == moveTo.getCol()) && ((this.field.getRow() - 1) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() - 1) == moveTo.getCol()) && ((this.field.getRow() - 2) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() - 2) == moveTo.getCol()) && ((this.field.getRow() - 1) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() - 1) == moveTo.getCol()) && ((this.field.getRow() + 2) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else if(((this.field.getCol() - 2) == moveTo.getCol()) && ((this.field.getRow() + 1) == moveTo.getRow())) {
                if(!this.moveLong(moveTo)) {
                    return -1;
                }
            } else {
                return -1;
            }           
        } else if(this.typ == 4) {
            if(this.field.getCol() == moveTo.getCol()) {
                if(this.field.getRow() > moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = this.field.getRow(); x > moveTo.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.U);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(this.field.getRow() < moveTo.getRow()) {
                    Field next = moveTo;
                    for(int x = moveTo.getRow(); x > this.field.getRow(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        
                        next = next.nextField(Field.Direction.D);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else if(this.field.getRow() == moveTo.getRow()) {
                if(this.field.getCol() > moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.R);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(this.field.getCol() < moveTo.getCol()) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.L);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else if(Math.abs(this.field.getCol() - moveTo.getCol()) == Math.abs(this.field.getRow() - moveTo.getRow())) {
                if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.RD);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(((this.field.getCol() - moveTo.getCol()) > 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = this.field.getCol(); x > moveTo.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        
                        next = next.nextField(Field.Direction.RU);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) < 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.LD);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else if(((this.field.getCol() - moveTo.getCol()) < 0) && ((this.field.getRow() - moveTo.getRow()) > 0)) {
                    Field next = moveTo;
                    for(int x = moveTo.getCol(); x > this.field.getCol(); x--) {
                        if(!next.isEmpty() && (next != moveTo)) {
                            return -1;
                        }
                        next = next.nextField(Field.Direction.LU);
                    }
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else if(this.typ == 5) {
            if((Math.abs(this.field.getCol() - moveTo.getCol()) <= 1) && (Math.abs(this.field.getRow() - moveTo.getRow()) <= 1)) {
                Field next = moveTo;
                int endU = 8 - moveTo.getRow();
                int ok = 0;
                boolean mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.U);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                endU = moveTo.getRow() - 1;
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.D);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                endU = moveTo.getCol() - 1;
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.L);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
                    } else {
                        break;
                    }
                    endU--;
                }
                ok = this.moveKR(next, moveTo, ok);
                
                next = moveTo;
                endU = 8 - moveTo.getCol();
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.R);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
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
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.LU);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
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
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.LD);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
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
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.RU);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
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
                mat = false;
                while(endU > 0) {
                    if(!mat) {
                        next = next.nextField(Field.Direction.RD);
                        if(!next.isEmpty()) {
                            mat = true;
                        }
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
                    if(!this.moveLong(moveTo)) {
                        return -1;
                    }
                } else {
                    return -1;
                }
                
            } else {
                return -1;
            }
        }
            
        if(a == 2) {
            return 2;
        } else {
            return sach(moveTo);
        }
    }
    
    @Override
    public boolean reverse_move(Field moveTo) {
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    @Override
    public boolean equals(java.lang.Object obj) {
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
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
}
