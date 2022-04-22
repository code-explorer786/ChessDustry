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
	public static Block pawn;

	@Override
	public void load(){
		pawn = new Pawn("pawn"){{
			requirements(Category.turret, with(Items.copper, 1));
			destructible = true;
			rotate = true;
			health = 0x7FFFFFFF;
			size = 1;
		}};
	}
}
