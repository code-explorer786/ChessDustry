package chess.world.blocks.defense.turrets;

import arc.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;

public class Knight extends ChessPiece {
	public Knight(String name){
		super(name);
	}
	public class KnightBuild extends ChessPiece.ChessPieceBuild {
		public float probability = super.probability / 3f;
		
		private final static Point2[] knightMoves = {
			new Point2(1,2),
			new Point2(2,1),
			new Point2(-1,2),
			new Point2(-2,1),
			new Point2(1,-2),
			new Point2(2,-1),
			new Point2(-1,-2),
			new Point2(-2,-1)
		};

		@Override
		public Seq<Point2> possibleMoves() {
			Seq<Point2> result = new Seq<Point2>(0);
			int x = this.tile.x;
			int y = this.tile.y;
			for(int i = 0; i < 8; i++) {
				int dx = knightMoves[i].x;
				int dy = knightMoves[i].y;
				if (isPossibleToMove(x + dx,y + dy)) result.add(new Point2(x + dx, y + dy));
			}
			return result;
		}
	}
}
