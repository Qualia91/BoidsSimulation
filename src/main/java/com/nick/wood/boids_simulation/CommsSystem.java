package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.Vecd;

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

					if (Math.abs(boid.getPos().subtract(otherBoid.getPos()).length2()) < lengthAwayComms2) {

						Vecd vecBetweenTwoPoints = otherBoid.getPos().subtract(boid.getPos());

						if (vecBetweenTwoPoints.dot(boid.getVel()) * ((vecBetweenTwoPoints.length()) * (boid.getVel().length())) < boid.getFov()) {

							otherBoid.setGoal(boid.getGoal());

						}

					}

				}

			}
		});

	}
}
