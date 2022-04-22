package chess.world.blocks.defense.turrets;

import java.lang.*;
import arc.*;
import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.*;

public class Pawn extends ChessPiece {
	public Pawn(String name) {
		super(name);
	}
	public class PawnBuild extends ChessPiece.ChessPieceBuild {
		public boolean hasMovedOnce = false;

		private final Prov<Point2> step1 = () -> new Point2(1, 0).rotate(rotation).add(this.tile.x, this.tile.y);
		private final Prov<Point2> step2 = () -> new Point2(2, 0).rotate(rotation).add(this.tile.x, this.tile.y);
		private final Prov<Point2> capt1 = () -> new Point2(1, 1).rotate(rotation).add(this.tile.x, this.tile.y);
		private final Prov<Point2> capt2  = () -> new Point2(1, -1).rotate(rotation).add(this.tile.x, this.tile.y);

		// public boolean enPassantDanger = false;
		// public int enPassantDangerX = 0;
		// public int enPassantDangerY = 0;

		@Override
		public Seq possibleMoves(){
			Seq<Point2> result = new Seq(0);
			if (!hasMovedOnce){
				if (isPossibleToMoveStrict(step1.get()) && isPossibleToMoveStrict(step2.get()))
					result.add(step2.get());
			}
			if (isPossibleToMoveStrict(step1.get())) result.add(step1.get());
			if (isPossibleToCapture(capt1.get())) result.add(capt1.get());
			if (isPossibleToCapture(capt2.get())) result.add(capt2.get());
			return result;
		}
	}
}
