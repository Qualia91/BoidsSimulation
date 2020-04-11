package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.Vecd;

public class Goal {

	private final Vecd pos;
	private final int goalNumber;
	private final boolean active;

	public Goal(Vecd pos, int goalNumber, boolean active) {
		this.pos = pos;
		this.goalNumber = goalNumber;
		this.active = active;
	}

	public Vecd getPos() {
		return pos;
	}

	public int getGoalNumber() {
		return goalNumber;
	}

	public boolean isActive() {
		return active;
	}
}
