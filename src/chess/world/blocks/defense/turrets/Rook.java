package chess.world.blocks.defense.turrets;

import arc.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;

public class Rook extends ChessPiece {
	public Rook(String name){
		super(name);
	}
	public class RookBuild extends ChessPiece.ChessPieceBuild {
		public float probability = 0.02f;

		@Override
		public Seq<Point2> possibleMoves() {
			Seq<Point2> result = new Seq<Point2>(0);
			int x = this.tile.x;
			int y = this.tile.y;
			for(int i = 0; i < 3; i++) {
				int idx = Geometry.d4x[i];
				int idy = Geometry.d4y[i];
				int j;
				for(j = 1; isPossibleToMoveStrict(x + j * idx, y + j * idy); j++) result.add(new Point2(x + j * idx, y + j * idy));
				if(isPossibleToMove(x + j * idx, y + j * idy)) result.add(new Point2(x + j * idx, y + j * idy));
			}
			return result;
		}
	}
}
