package com.me.mygdxgame.constants;

public class AntSimulatorConstants {
	
	public static final int SIZE_FACTOR = 2;
	
	public static final int INITIAL_FOOD_LEVEL = 300;
	public static final int INITIAL_NB_ANTS=10;
	public static final int INITIAL_NB_FOOD_SOURCES= 5 * SIZE_FACTOR;
	
	public static final float ANT_SPEED=2f;
	public static final float ANT_FRAME_DURATION=0.06f;
	
	public static final int ANT_DIR_CHANGE_FREQUENCY = 300;
	public static final float FOOD_FIND_DISTANCE=80f;
	
	public static final float MAX_FOOD_CARRIED = 5.0f;
	public static final float FOOD_HARVESTED_PER_TICK = 0.1f;
	public static final float FOOD_SPAWN_FREQUENCY = 30.0f;//in seconds

	public static final float ANT_FOOD_COST=30.0f;
	
	public static final boolean DEBUG_MODE = false;

}
