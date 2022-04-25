package chess.world.blocks.defense.turrets;

import java.lang.*;
import arc.*;
import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.*;

public class ChessPiece extends Block {
	public ChessPiece(String name) {
		super(name);
		group = BlockGroup.turrets;
		destructible = true;
		update = true;
		size = 1;
		solid = true;
	}
	public class ChessPieceBuild extends Building implements ControlBlock {
		public float probability = 1f/60;
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

		public boolean isPossibleToMoveStrict(int x, int y){
			Tile t = Vars.world.tile(x, y);
			return t != null && t.block() == Blocks.air;
		}

		public boolean isPossibleToCapture(int x, int y){
			Tile t = Vars.world.tile(x, y);
			return t != null && t.block() != Blocks.air && t.team() != team && t.team() != Team.derelict;
		}

		public boolean isPossibleToMove(int x, int y){
			Tile t = Vars.world.tile(x, y);
			return t != null && (t.block() == Blocks.air || t.team() != Team.derelict && t.team() != team);
		}

		public boolean isPossibleToMoveStrict(Point2 p) { return isPossibleToMoveStrict(p.x, p.y); }
		public boolean isPossibleToCapture(Point2 p) { return isPossibleToCapture(p.x, p.y); }
		public boolean isPossibleToMove(Point2 p) { return isPossibleToMove(p.x, p.y); }

		public Seq<Point2> possibleMoves(){
			return new Seq<Point2>(0);
		}

		// Thanks sh1penfire and GlennFolker!
		// Thank you sk7725 for making this easier! (i took some code from XeloUtils.java in BetaMindy)
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

		@Override
		public void draw(){
			Draw.reset();
			Draw.color(this.team.color);
			// Building.java
			Draw.rect(block.region, x, y, block.rotate ? rotdeg() : 0);

			if(this.isControlled()){
				Draw.alpha(0.5f);
				Draw.z(Layer.end);
				TextureRegion t = Core.atlas.find("chess-capture");
				Seq<Point2> pMoves = this.possibleMoves();
				for(Point2 p : pMoves){
					Draw.rect(t, p.x*8, p.y*8, 0);
				}
			}
		}

		// Thanks buthed010203 and SMOLKEYS!
		@Override
		public void drawTeam(){}
	}
}
