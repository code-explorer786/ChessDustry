package chess.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import mindustry.content.*;
import static mindustry.type.ItemStack.*;
import chess.world.blocks.defense.turrets.*;

public class ChessBlocks implements ContentList {
	public static Block pawn, bishop, knight, queen, rook;

	@Override
	public void load(){
		pawn = new Pawn("pawn"){{
			requirements(Category.turret, with(Items.copper, 1));
			health = 5;
		}};
		bishop = new Bishop("bishop"){{
			requirements(Category.turret, with(Items.copper, 4));
			health = 17;
		}};
		knight = new Knight("knight"){{
			requirements(Category.turret, with(Items.copper, 3));
			health = 15;
		}};
		queen = new Queen("queen"){{
			requirements(Category.turret, with(Items.copper, 8));
			health = 40;
		}};
		rook = new Rook("rook"){{
			requirements(Category.turret, with(Items.copper, 5));
			health = 25;
		}};
	}
}
