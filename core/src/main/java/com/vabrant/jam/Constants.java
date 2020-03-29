package com.vabrant.jam;

public class Constants {
	
	public static final boolean DEBUG = false;
	
	public static final int GAME_WIDTH = 1080;
	public static final int GAME_HEIGHT = 720;
	
	public static final int LANE_SECTION_OFFSET = 76;
	public static final int LANE_SECTION_WIDTH = 152;
	
	//width and height of the deployable part of the platform
	public static final int PLATFORM_WIDTH = 912;
	public static final int PLATFORM_HEIGHT = 50;
	public static final int PLATFORM_Y_OFFSET = 110;
	
	public static final String GAME_ATLAS = "textures/game.atlas";
	
	public enum Direction {
		LEFT,
		RIGHT
	}
	
	public enum PlayerType {
		HUMAN,
		AI
	}
	
	public enum DeployableType {
		NONE(0),
		GOVERNMENT(0),
		TANK(3),
		SHIP(6),
		CHOPPER(5),
		NUKE(10);
		
		private int cost;
		
		private DeployableType(int cost) {
			this.cost = cost;
		}
		
		public int getElixrCost() {
			return cost;
		}
	}
	
	public enum Lane {
		LANE_1,
		LANE_2
	}
	
	public enum LaneSection {
		LANE_SECTION_1,
		LANE_SECTION_2, 
		LANE_SECTION_3
	}

}
