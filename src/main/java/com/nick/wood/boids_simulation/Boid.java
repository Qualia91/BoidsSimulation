package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.vector.Vecd;

import java.util.UUID;

public class Boid {

	private final UUID uuid;
	private Vecd position;
	private Vecd velocity;
	private Goal goal;
	private double fov;

	public Boid(Vecd position, Vecd velocity, Goal goal, double fov) {
		this.uuid = UUID.randomUUID();
		this.position = position;
		this.velocity = velocity;
		this.goal = goal;
		this.fov = fov;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Vecd getPosition() {
		return position;
	}

	public void setPosition(Vecd position) {
		this.position = position;
	}

	public Vecd getVelocity() {
		return velocity;
	}

	public void setVelocity(Vecd velocity) {
		this.velocity = velocity;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public double getFov() {
		return fov;
	}

	public void setFov(double fov) {
		this.fov = fov;
	}
}
