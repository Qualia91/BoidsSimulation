package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.Vecd;
import com.nick.wood.maths.objects.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BoidSystem {


	private final Random rand = new Random();

	private final int mins[];
	private final int maxs[];

	private final double limitedSpeed;
	private final double lengthAwayGroup2;
	private final double lengthAwayMin2;
	private final double boundScale;
	private final double velocityMatchScale;
	private final double antiCollideScale;
	private final double perceivedCenterScale;
	private final double goalScale;
	private final int numberOfBoids;

	private Goal goal;

	private final ArrayList<Boid> boids;

	private final Vecd ZERO;

	public BoidSystem(int[] mins, int[] maxs, ArrayList<Boid> boids, BoidSystemData boidSystemData) {

		assert mins.length == maxs.length;

		this.limitedSpeed = boidSystemData.getLimitedSpeed();
		this.lengthAwayGroup2 = boidSystemData.getLengthAwayGroup2();
		this.lengthAwayMin2 = boidSystemData.getLengthAwayMin2();
		this.boundScale = boidSystemData.getBoundScale();
		this.velocityMatchScale = boidSystemData.getVelocityMatchScale();
		this.antiCollideScale = boidSystemData.getAntiCollideScale();
		this.perceivedCenterScale = boidSystemData.getPerceivedCenterScale();
		this.goalScale = boidSystemData.getGoalScale();
		this.numberOfBoids = boidSystemData.getNumberOfBoids();

		this.mins = mins;
		this.maxs = maxs;

		ZERO = Vector.zero(mins.length);

		this.boids = boids;

		goal = new Goal(ZERO, -1, false);

		for (int i = 0; i < numberOfBoids; i++) {

			boids.add(new Boid(
					Vector.create(getVector(mins.length, (index) -> (double) rand.nextInt(maxs[index] - mins[index]) + mins[index])),
					Vector.create(getVector(mins.length, (index) -> (double) rand.nextInt(10) - 5)),
					goal,
					Math.PI/8));
		}

	}

	private double[] getVector(int length, Function<Integer, Double> function) {
		double[] doubles = new double[length];

		for (int i = 0; i < length; i++) {
			doubles[i] = function.apply(i);
		}

		return doubles;
	}

	public void updatePositions(double timeStep) {

		boids.parallelStream().forEach(boid -> {

			boid.setVel(
					limitVelocity(
							boid.getVel()
									.add(perceivedCenter(boid))
									.add(antiCollide(boid))
									.add(velocityMatch(boid))
									.add(bound(boid))
									.add(goal(boid))));

			boid.setPos(boid.getPos().add(boid.getVel().scale(timeStep)));
		});


	}

	public void updateSensors() {

		boids.parallelStream().forEach(boid -> {

			double distBetweenSqrd = Math.abs(goal.getPos().subtract(boid.getPos()).length2());

			if (distBetweenSqrd < lengthAwayGroup2) {

				Vecd vecdBetweenTwoPoints = goal.getPos().subtract(boid.getPos());

				if (vecdBetweenTwoPoints.dot(boid.getVel()) * ((vecdBetweenTwoPoints.length()) * (boid.getVel().length())) < boid.getFov()) {

					boid.setGoal(goal);

				}

			}

		});

	}

	private Vecd goal(Boid boid) {
		if (boid.getGoal().isActive()) {
			return boid.getGoal().getPos().subtract(boid.getPos()).normalise().scale(goalScale);
		} else if (goal.isActive()) {
			return goal.getPos().subtract(boid.getPos()).normalise().scale(goalScale);
		}
		return ZERO;
	}

	private Vecd bound(Boid boid) {

		double[] elems = new double[mins.length];

		for (int i = 0; i < mins.length; i++) {
			if (boid.getPos().get(i) < mins[i]) {
				elems[i] = (mins[i] - boid.getPos().get(i)) * boundScale;
			}
			else if (boid.getPos().get(i) > maxs[i]) {
				elems[i] = (maxs[i] - boid.getPos().get(i)) * boundScale;
			}
		}

		return Vector.create(elems);
	}

	private Vecd limitVelocity(Vecd vel) {

		if (vel.length() > limitedSpeed) {

			vel = vel.normalise().scale(limitedSpeed);

		}

		return vel;
	}

	private Vecd velocityMatch(Boid boid) {

		double[] vElems = new double[mins.length];
		int num = 0;

		for (Boid otherBoid : boids) {

			if (!boid.equals(otherBoid)) {

				if (Math.abs(boid.getPos().subtract(otherBoid.getPos()).length2()) < lengthAwayGroup2) {

					Vecd vecdBetweenTwoPoints = otherBoid.getPos().subtract(boid.getPos());

					if (vecdBetweenTwoPoints.dot(boid.getVel()) * ((vecdBetweenTwoPoints.length()) * (boid.getVel().length())) < boid.getFov()) {

						for (int i = 0; i < mins.length; i++) {
							vElems[i] += otherBoid.getVel().get(i);
						}

						num++;

					}

				}

			}

		}

		if (num == 0) return ZERO;

		final int finalNum = num;

		return Vector.create(
				getVector(vElems.length, (index) -> (vElems[index] / finalNum) - (boid.getVel().get(index) / velocityMatchScale)));

	}

	private Vecd antiCollide(Boid boid) {

		double[] e = new double[mins.length];

		for (Boid otherBoid : boids) {

			if (!boid.equals(otherBoid)) {

				if (Math.abs(boid.getPos().subtract(otherBoid.getPos()).length2()) < lengthAwayMin2) {

					for (int i = 0; i < mins.length; i++) {
						e[i] -= (otherBoid.getPos().get(i) - boid.getPos().get(i)) * antiCollideScale;
					}

				}
			}

		}

		return Vector.create(e);

	}

	private Vecd perceivedCenter(Boid boid) {

		double[] e = new double[mins.length];
		int num = 0;

		for (Boid otherBoid : boids) {

			if (!boid.equals(otherBoid)) {

				if (Math.abs(boid.getPos().subtract(otherBoid.getPos()).length2()) < lengthAwayGroup2) {

					Vecd vecdBetweenTwoPoints = otherBoid.getPos().subtract(boid.getPos());

					if (vecdBetweenTwoPoints.dot(boid.getVel()) * ((vecdBetweenTwoPoints.length()) * (boid.getVel().length())) < boid.getFov()) {


						for (int i = 0; i < mins.length; i++) {
							e[i] += otherBoid.getPos().get(i);
						}

						num++;

					}

				}

			}

		}

		if (num == 0) return ZERO;


		final int finalNum = num;

		return Vector.create(
				getVector(e.length, (index) -> ((e[index] / finalNum) - boid.getPos().get(index)) / perceivedCenterScale));

	}

	public List<Boid> getBoids() {
		return boids;
	}

	public void setGoal(Goal goal) {

		this.goal = goal;

	}

	public Goal getGoal() {
		return goal;
	}
}
