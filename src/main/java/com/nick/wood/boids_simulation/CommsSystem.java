package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.vector.Vecd;

import java.util.ArrayList;

public class CommsSystem {

	private final ArrayList<Boid> boids;
	private final double lengthAwayComms2 = 1600;

	public CommsSystem(ArrayList<Boid> boids) {
		this.boids = boids;
	}

	public void updateComms() {

		boids.parallelStream().forEach(boid -> {

			for (Boid otherBoid : boids) {

				if (boid.getGoal().getGoalNumber() > otherBoid.getGoal().getGoalNumber()) {

					if (Math.abs(boid.getPosition().subtract(otherBoid.getPosition()).length2()) < lengthAwayComms2) {

						Vecd vecBetweenTwoPoints = otherBoid.getPosition().subtract(boid.getPosition());

						if (vecBetweenTwoPoints.dot(boid.getVelocity()) * ((vecBetweenTwoPoints.length()) * (boid.getVelocity().length())) < boid.getFovAngle()) {

							otherBoid.setGoal(boid.getGoal());

						}

					}

				}

			}
		});

	}
}
