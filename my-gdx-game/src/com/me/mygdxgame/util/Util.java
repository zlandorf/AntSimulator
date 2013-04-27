package com.me.mygdxgame.util;

import com.badlogic.gdx.math.Vector2;

public class Util {

	public static Vector2 getRandomWorldPosition(float maxX, float maxY) {
		Vector2 randomGoal = new Vector2();
		randomGoal.x = (float)(Math.random() * maxX + 1);
		randomGoal.y = (float)(Math.random() * maxY + 1);
		return randomGoal;
	}
}
