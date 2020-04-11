package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.Vecd;

import java.util.UUID;

public class Boid {

	private final UUID uuid;
	private Vecd pos;
	private Vecd vel;
	private Goal goal;
	private double fov;

	public Boid(Vecd pos, Vecd vel, Goal goal, double fov) {
		this.uuid = UUID.randomUUID();
		this.pos = pos;
		this.vel = vel;
		this.goal = goal;
		this.fov = fov;
	}

	public Vecd getPos() {
		return pos;
	}

	public void setPos(Vecd pos) {
		this.pos = pos;
	}

	public Vecd getVel() {
		return vel;
	}

	public void setVel(Vecd vel) {
		this.vel = vel;
	}

	public double getFov() {
		return fov;
	}

	public void setFov(double fov) {
		this.fov = fov;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public UUID getUuid() {
		return uuid;
	}
}
