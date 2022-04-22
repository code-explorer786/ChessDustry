package chess.world.blocks.defense.turrets;

import java.lang.*;
import arc.*;
import arc.func.*;
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

public class Pawn extends Block {
	public Pawn(String name) {
		super(name);
		group = BlockGroup.turrets;
		update = true;
	}
	public class PawnBuild extends Building implements ControlBlock {
		public @Nullable BlockUnitc unit;
		public boolean hasMovedOnce = false;

		private Prov<Point2> step1 = () -> new Point2(1, 0).rotate(rotation).add(this.tile.x, this.tile.y);
		private Prov<Point2> step2 = () -> new Point2(2, 0).rotate(rotation).add(this.tile.x, this.tile.y);
		private Prov<Point2> capt1 = () -> new Point2(1, 1).rotate(rotation).add(this.tile.x, this.tile.y);
		private Prov<Point2> capt2  = () -> new Point2(1, -1).rotate(rotation).add(this.tile.x, this.tile.y);

		// public boolean enPassantDanger = false;
		// public int enPassantDangerX = 0;
		// public int enPassantDangerY = 0;

		// thank you Router.java
		@Override
		public Unit unit(){
			if(unit == null){
				unit = (BlockUnitc)UnitTypes.block.create(team);
				unita.tile(this);
			}
			return (Unit) unit;
		}

		private boolean isPossibleToMove(Point2 p){
			Tile t = Vars.world.tile(p.x, p.y);
			if (t == null || t.block() != Blocks.air) return false;
			return true;
		}

		private boolean isPossibleToCapture(Point2 p){
			Tile t = Vars.world.tile(p.x, p.y);
			if (t == null || t.block() == Blocks.air || t.team() == team) return false;
			return true;
		}

		public Seq possibleMoves(){
			Seq result = new Seq(0);
			if (!hasMovedOnce){
				if (isPossibleToMove(step2.get()))
					result.add(step2.get());
			}
			if (isPossibleToMove(step1.get())) result.add(step1.get());
			if (isPossibleToCapture(capt1.get())) result.add(capt1.get());
			if (isPossibleToCapture(capt2.get())) result.add(capt2.get());
			return result;
		}

		public void moveTo(int x, int y){
			Tile t = Vars.world.tile(x,y);
			t.setBlock(block, team, rotation);
			t.build.remove();
			t.build = this;
			this.tile.setAir();
			this.tile = t;
			this.set(x*8, y*8);
			this.unit();
			this.unit.tile(this);
			this.hasMovedOnce = true;
		}
		
		@Override
		public void updateTile(){
			if(!this.isControlled()){
				//TODO: servers.
				if (Mathf.random() > 1/20) return;
				Seq possMoves = this.possibleMoves();
				if (possMoves.size() == 0) return;
				Point2 pnt = possMoves.random();
				moveTo(pnt.x, pnt.y);
				return;
			};
			Point2 pnt = new Point2((int) this.unit().aimX/8, (int) this.unit().aimY/8);
			if(this.possibleMoves().contains(pnt)) moveTo(pnt.x, pnt.y);
		}
	}
}
