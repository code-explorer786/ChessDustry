package chess;

import chess.content.*;
import arc.*;
import mindustry.*;
import mindustry.mod.*;

public class ChessMod extends Mod{

    public ChessMod(){}

    @Override
    public void loadContent(){
       (new ChessBlocks()).load(); 
    }

}
