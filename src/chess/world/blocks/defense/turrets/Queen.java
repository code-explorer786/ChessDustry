package chess.world.blocks.defense.turrets;

import arc.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;

public class Queen extends ChessPiece {
	public Queen(String name){
		super(name);
	}
	public class QueenBuild extends ChessPiece.ChessPieceBuild {
		public float probability = super.probability / 8f;

		@Override
		public Seq<Point2> possibleMoves() {
			Seq<Point2> result = new Seq<Point2>(0);
			int x = this.tile.x;
			int y = this.tile.y;
			for(int i = 0; i < 8; i++) {
				int idx = Geometry.d8[i].x;
				int idy = Geometry.d8[i].y;
				int j;
				for(j = 1; isPossibleToMoveStrict(x + j * idx, y + j * idy); j++) result.add(new Point2(x + j * idx, y + j * idy));
				if(isPossibleToMove(x + j * idx, y + j * idy)) result.add(new Point2(x + j * idx, y + j * idy));
			}
			return result;
		}
	}
}
