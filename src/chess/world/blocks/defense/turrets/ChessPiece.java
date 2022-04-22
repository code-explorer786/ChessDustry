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

public class ChessPiece extends Block {
	public ChessPiece(String name) {
		super(name);
		group = BlockGroup.turrets;
		update = true;
		size = 1;
	}
	public class ChessPieceBuild extends Building implements ControlBlock {
		public final float probability = 0.1f;
		public @Nullable BlockUnitc unit;
		
		// thank you Router.java
		@Override
		public Unit unit(){
			if(unit == null){
				unit = (BlockUnitc)UnitTypes.block.create(team);
				unit.tile(this);
			}
			return (Unit) unit;
		}

		public boolean isPossibleToMoveStrict(Point2 p){
			Tile t = Vars.world.tile(p.x, p.y);
			if (t == null || t.block() != Blocks.air) return false;
			return true;
		}

		public boolean isPossibleToCapture(Point2 p){
			Tile t = Vars.world.tile(p.x, p.y);
			if (t == null || t.block() == Blocks.air || t.team() == team) return false;
			return true;
		}

		public boolean isPossibleToMove(Point2 p){
			Tile t = Vars.world.tile(p.x, p.y);
			if (t == null || t.team() == team) return false;
			return true;
		}

		public Seq possibleMoves(){
			return new Seq(0);
		}

		// Thanks sh1penfire and GlennFolker!
		public void moveTo(int x, int y){
			Tile originalTile = this.tile;
			Tile t = Vars.world.tile(x,y);
			t.setBlock(this.block, this.team, this.rotation, () -> this);
			this.unit();
			this.unit.tile(this);

			originalTile.build = null;
			originalTile.setAir();
		}
		
		@Override
		public void updateTile(){
			if(!this.isControlled()){
				//TODO: servers.
				if (Mathf.random() > probability) return;
				Seq<Point2> possMoves = this.possibleMoves();
				if (possMoves.isEmpty()) return;
				Point2 pnt = possMoves.random();
				moveTo(pnt.x, pnt.y);
				return;
			};
			Point2 pnt = new Point2((int) this.unit().aimX/8, (int) this.unit().aimY/8);
			if(this.possibleMoves().contains(pnt)) moveTo(pnt.x, pnt.y);
		}
	}
}
