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
		rotate = true;
	}
	public class PawnBuild extends ChessPiece.ChessPieceBuild {
		public boolean hasMovedOnce = false;

		private final Prov<Point2> step1 = () -> new Point2(1, 0).rotate(rotation).add(this.tile.x, this.tile.y);
		private final Prov<Point2> step2 = () -> new Point2(2, 0).rotate(rotation).add(this.tile.x, this.tile.y);
		private final Prov<Point2> capt1 = () -> new Point2(1, 1).rotate(rotation).add(this.tile.x, this.tile.y);
		private final Prov<Point2> capt2  = () -> new Point2(1, -1).rotate(rotation).add(this.tile.x, this.tile.y);

		public @Nullable Tile enPassantDanger;

		public static boolean enPassantablePawn(Tile tile, Tile me){
			return tile != null
				&& tile.block() instanceof Pawn
				&& ((PawnBuild) tile.build).enPassantDanger != null
				&& ((PawnBuild) tile.build).enPassantDanger == me;
		}

		public static boolean enPassantable(Tile tile){
			if(tile == null) return false;
			for(int i = 0; i < 4; i++){
				if(enPassantablePawn(tile.nearby(i), tile)) return true;
			}
			return false;
		}

		public static void enPassant(Tile tile){
			if(tile == null) return;
			for(int i = 0; i < 4; i++){
				Tile near = tile.nearby(i);
				if(enPassantablePawn(near, tile)){
					near.setAir();
				}
			}
		}

		@Override
		public boolean isPossibleToCapture(Point2 p){
			return enPassantable(Vars.world.tile(p.x, p.y)) || super.isPossibleToCapture(p);

		}

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

		@Override
		public void moveTo(int x, int y){
			enPassantDanger = null;
			if(x == step2.get().x && y == step2.get().y) enPassantDanger = this.tile.nearby(this.rotation);
			boolean doPassant = (x == capt1.get().x && y == capt1.get().y)
			                      || (x == capt2.get().x && y == capt2.get().y);
			super.moveTo(x, y);
			this.hasMovedOnce = true;
			if(doPassant) enPassant(this.tile);
		}
	}
}
