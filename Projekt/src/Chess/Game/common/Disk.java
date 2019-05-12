package Chess.Game.common;

import Chess.Game.game.Board;
/**
 * 
 * @author xtetur01
 */
public class Disk extends java.lang.Object implements Figure{
    private final int typ;
    private final boolean isWhite;
    
    /**
     * Proměnná s polem.
     */
    public Field field;
    
    /**
     * Proměnná s hrací deskou.
     */
    public Board board;
    //typ:0 Pesak
    //typ:1 Vez
    //typ:2 Strelec
    //typ:3 Kun
    //typ:4 Kralovna
    //typ:5 Kral
    
    /**
     * Tato metoda nastavuje vlastnosti disku.
     * 
     * @param typ       typ disku (0-Pěšák, 1-Věž, 2-Střelec, 3-Jezdec, 4-Královna, 5-Král)
     * @param board     šachová deska, na které se disk nachází
     * @param isWhite   figurka je bílá nebo černá
     */
    public Disk(int typ,Board board,boolean isWhite) {
        this.board=board;
        this.typ=typ;
        this.isWhite=isWhite;
        this.field=null;
    }
    
    /**
     * Metoda vždy vrací políčko, na kterém se disk právě nachází.
     * 
     * @return 
     */
    @Override
    public Field myfield() {
        return this.field;
    }
    
    /**
     * Metoda oebírá disk z políčka.
     */
    @Override
    public void remove() {
        this.field=null;
    }
    
    /**
     * Metoda vžky vrací je-li disk býlí nebo černý.
     * 
     * @return (býlá-true, černá-false)
     */
    @Override
    public boolean isWhite() {
        return this.isWhite;
    }
    
    /**
     * Metoda vrací desku, na které se nachází disk.
     * 
     * @return 
     */
    @Override
    public Board getBoard() {
        return this.board;
    }
    
    /**
     *  Metoda vrací typ disku.
     * 
     * @return (0-Pěšák, 1-Věž, 2-Střelec, 3-Jezdec, 4-Královna, 5-Král)
     */
    @Override
    public int getType() {
        return this.typ;
    }
    
    /**
     * Tato metoda vkládá do disku políčko, na kterém se nachází.
     * 
     * @param field 
     */
    @Override
    public void put(Field field) {
        this.field=field;
    }
    
    /**
     * Tato metoda vrací řetězec, ve kterém je napsaná pozici disku, jeho typ a barva.
     * 
     * @return (typ["barva"]"pozice-sloupec:pozice-radek)
     */
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
    
    
    /**
     * Tato metoda posouvá Pěšáka při běžném pohybu vpřed. 
     * Nejdříve se odstraní figurka z původní pozice a potom se vloží na novou pozici.
     * 
     * @param moveTo    místo kam se pěšák posouvá
     * @return 
     */
    private boolean movePesak1(Field moveTo) {
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    /**
     * Tato metoda posouvá Pěšáka při vyhazování jiné figurky.
     * Nejdříve se odstraní figurka z původní pozice, potom se odstraní vyhazovaná figurka a nakonec se figurka vloží na novou pozici.
     * 
     * @param moveTo    místo kam se pěšák posouvá
     * @return 
     */
    private boolean movePesak2(Field moveTo) {
        this.field.remove(this);
        moveTo.remove(moveTo.get());
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    /**
     * Tato metoda posouvá všechny figurky (kromě Pěšáka) při vyhazování jiné figurky a při posouvání.
     * Nejdříve se zkontroluje jestli jde o posouvání nebo vyhazování. Pokud se jedná o vyhazování, a vyhazuje se figurka jiné barvy, 
     * odstraní se figurka z původní pozice, potom se odstraní vyhazovaná figurka a nakonec se figurka vloží na novou pozici.
     * Pokud by se jednalo o vyhození vlastní figurky, vrátí se false.
     * Pokud se jedná o obyčejný posun odstraní se figurka z původní pozice, potom se odstraní vyhazovaná figurka a nakonec se figurka vloží na novou pozici.
     * 
     * @param moveTo    místo kam se Pěšák posouvá
     * @return          úspěšnost přesunu
     */
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
    
    /**
     * Tato metoda je pouze pro figurky typu Král.
     * Protože Král se svým pohybem nesmí ohrozit, kontroluje tato matoda jeho pohyb ve vodorovném a ve svislém směru.
     * Pokud by se Král neměl svým pohybem dostat vodorovně či svisle před Věž nebo Krýlovnu, přičte se k celkovému součtu 1.
     * Pokud je Král v besprostřední blízkosti jiné figurky tak, že ji svým pohybem vyhodí a neohrozí se, přičte se k celkovému součtu 1.
     * Pokud je vedle Krále vlastní figurka, přičte se k celkovému součtu 1.
     * 
     * @param next      pozice, na kterou se král pohybuje
     * @param moveTo    pozice figurky ve vodorovném nebo ve svislém směru
     * @param ok        celkový součet
     * @return          vrací celkový součet
     */
    private int moveKR(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 1) && (next.get().getType() != 4)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    /**
     * Tato metoda je pouze pro figurky typu Král.
     * Protože Král se svým pohybem nesmí ohrozit, kontroluje tato matoda jeho pohyb v diagonálních směrech.
     * Pokud by se Král neměl svým pohybem dostat diagonálně před Střelce nebo Krýlovnu, přičte se k celkovému součtu 1.
     * Pokud je Král v besprostřední blízkosti jiné figurky tak, že ji svým pohybem vyhodí a neohrozí se, přičte se k celkovému součtu 1.
     * Pokud je vedle Krále vlastní figurka, přičte se k celkovému součtu 1.
     * 
     * @param next      pozice, na kterou se král pohybuje
     * @param moveTo    pozice figurky v diagonálním směru
     * @param ok        celkový součet
     * @return          vrací celkový součet
     */
    private int moveKD(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 2) && (next.get().getType() != 4)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    /**
     * Tato metoda je pouze pro figurky typu Král.
     * Protože Král se svým pohybem nesmí ohrozit, kontroluje tato matoda jeho pohyb ve směru Pěšců.
     * Pokud by se Král neměl svým pohybem dostat před Pěšce tak, že by ho Pěšec mohl vyhodit, přičte se k celkovému součtu 1.
     * Pokud je Král v besprostřední blízkosti jiné figurky tak, že ji svým pohybem vyhodí a neohrozí se, přičte se k celkovému součtu 1.
     * Pokud je vedle Krále vlastní figurka, přičte se k celkovému součtu 1.
     * 
     * @param next      pozice, na kterou se král pohybuje
     * @param moveTo    pozice figurky ve směru Pěšce
     * @param ok        celkový součet
     * @return          vrací celkový součet
     */
    private int moveKP(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 0)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    /**
     * Tato metoda je pouze pro figurky typu Král.
     * Protože Král se svým pohybem nesmí ohrozit, kontroluje tato matoda jeho pohyb ve směru Jezdce.
     * Pokud by se Král neměl svým pohybem dostat před Jezdce tak, že by ho Jezdec mohl vyhodit, přičte se k celkovému součtu 1.
     * Pokud je Král v besprostřední blízkosti jiné figurky tak, že ji svým pohybem vyhodí a neohrozí se, přičte se k celkovému součtu 1.
     * Pokud je vedle Krále vlastní figurka, , přičte se k celkovému součtu 1.
     * 
     * @param next      pozice, na kterou se král pohybuje
     * @param moveTo    pozice figurky ve směru Jezdce
     * @param ok        celkový součet
     * @return          vrací celkový součet
     */
    private int moveKJ(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 3)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    /**
     * Tato metoda je pouze pro figurky typu Král.
     * Protože Král se svým pohybem nesmí ohrozit, kontroluje tato matoda jeho pohyb ve směru dalšího Krále.
     * Pokud by se Král neměl svým pohybem dostat před Krále tak, že by ho Král mohl vyhodit, přičte se k celkovému součtu 1.
     * Pokud je Král v besprostřední blízkosti jiné figurky tak, že ji svým pohybem vyhodí a neohrozí se, přičte se k celkovému součtu 1.
     * Pokud je vedle Krále vlastní figurka, , přičte se k celkovému součtu 1.
     * 
     * @param next      pozice, na kterou se král pohybuje
     * @param moveTo    pozice figurky ve směru dalšího Krále
     * @param ok        celkový součet
     * @return          vrací celkový součet
     */
    private int moveKK(Field next, Field moveTo, int ok) {
        if(next.isEmpty()) {
            ok++;
        } else if(((next.get().getType() != 5)) || (next == moveTo) || (next.get().isWhite() == this.isWhite)) {
            ok++;
        }
        return ok;
    }
    
    /**
     * Tato metoda zjišťuje ohrožení Krále.
     * Pokud je obdržena figurka Krále a figurka, který Krále ohrožuje má jinou barvu než Král, přučte se k celkovému ohrožení Krále 1.
     * 
     * @param kral      figurka typu Král
     * @param figurka   figurka ohrožující Krále
     * @param ok        kontrolní součet ohrožení Krále
     * @return          vrací kontrolní součet ohrožení Krále
     */
    private int sachK(Field kral, Field figurka, int ok) {
        if(kral.isEmpty()) {
            return ok;
        } else if((kral.get().getType() == 5) && (kral.get().isWhite() != figurka.get().isWhite())) {
            ok++;
        }
        return ok;
    }
    
    /**
     * Metoda zjišťující jestli nastal šach.
     * Meoda dostane figurku, se kterou se právě hrálo. Dále nastaví součet celkového ohrožení krále ok na 0.
     * Dále zjistí o jaký typ figurky se jedná. Potom projde všechny možné směry figurky a zjistí jestli neohrožuje Krále pomocí metody sachK(Field kral, Field figurka, int ok).
     * Nakonec se podívá jestli je celkové ohrožení Krále ok větší jak 0. Pokud ano vrací se hodnota 1 jinak 0.
     * 
     * @param figurka   figurka, kterou se hrálo
     * @return          vrací sach(1-sach, 0-nic)
     */
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
    
    /**
     * Metoda zjišťující jestli nastal mat.
     * Pokud se táhne na místo kde je Král(vyhození Krále), vrátí se hodnota matu.
     * 
     * @param figurka   figurka nebo místo, na kterou se táhne
     * @return          vrací mat(2-mat, 1-nic)
     */
    public int mat(Field figurka) {
        if(figurka.isEmpty()) {
            return 0;
        } else if(figurka.get().getType() == 5) {
            return 2;
        } else {
            return 1;
        }
    }
    
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
    @Override
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
    
    /**
     * Metoda pro zpětný pohyb.
     * Tato metoda nic nekontroluje, protože veškeré pohyby se kontrolují při pohybu vpřed.
     * 
     * @param moveTo    místo kan se má táhnout
     * @return          vrací úspěšnost akce
     */
    @Override
    public boolean reverse_move(Field moveTo) {
        this.field.remove(this);
        this.field = moveTo;
        moveTo.put(this);
        return true;
    }
    
    /**
     * Matoda na zjištění dvou stejných Disků.
     * Matoda zjistí zda se dva disky shodují.
     * 
     * @param obj   objekt Disk
     * @return      vrací rovnost objektů
     */
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

    /**
     * Metoda vrací hashCode.
     * 
     * @return  vrací hashCode
     */
    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
}
