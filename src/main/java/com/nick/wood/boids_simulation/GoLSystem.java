package com.nick.wood.boids_simulation;

import com.nick.wood.maths.objects.Vecd;
import com.nick.wood.maths.objects.Vector;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GoLSystem {
	private final ArrayList<Boid> boids;
	private final ArrayList<Vecd> deadPositions;
	private final AtomicBoolean updateDrawing;
	private final double lengthAway2 = 800;
	private double reproduceLenghtAway = 400;
	private final int overPopLim = 4;
	private final int repro = 2;
	private final Random rand = new Random();
	private final int desiredPopulation = 20000;

	public GoLSystem(ArrayList<Boid> boids, AtomicBoolean updateDrawing) {
		this.boids = boids;
		this.updateDrawing = updateDrawing;
		deadPositions = new ArrayList<>();
	}

	public void update() {

		if (boids.size() > desiredPopulation) {
			reproduceLenghtAway/=2;
		} else {
			reproduceLenghtAway*=2;
		}

		for (ListIterator<Boid> iter = boids.listIterator(); iter.hasNext(); ) {

			Boid boid = iter.next();

			ArrayList<Boid> boidsClose = new ArrayList<>();

			for (Boid otherBoid : boids) {

				if (!boid.equals(otherBoid)) {

					if (Math.abs(boid.getPos().subtract(otherBoid.getPos()).length2()) < lengthAway2) {

						boidsClose.add(otherBoid);

					}

				}

			}

			if (boidsClose.size() > overPopLim) {
				deadPositions.add(boid.getPos());
				iter.remove();
			}

		}

		ArrayList<Boid> newBoids = new ArrayList<>();

		for (ListIterator<Vecd> iter = deadPositions.listIterator(); iter.hasNext(); ) {

			Vecd pos = iter.next();

			ArrayList<Vecd> posClose = new ArrayList<>();

			for (Vecd otherPos : deadPositions) {

				if (!pos.equals(otherPos)) {

					if (Math.abs(pos.subtract(otherPos).length2()) < reproduceLenghtAway) {

						posClose.add(otherPos);

					}

				}

			}

			if (posClose.size() > repro) {
				newBoids.add(reproduce(posClose));
				iter.remove();
			}

		}

		boids.addAll(newBoids);
		updateDrawing.set(true);

		System.out.println(boids.size());
	}

	private Boid reproduce(ArrayList<Vecd> positions) {

		Vecd center = Vector.create(0.0, 0.0);

		for (Vecd pos : positions) {

			center = center.add(pos);

		}

		center = center.scale(1.0/positions.size());

		return new Boid(center,
				Vector.create(rand.nextInt(10) - 5, rand.nextInt(10) - 5),
				new Goal(Vector.create(0.0, 0.0), -1, false),
				Math.PI/8);

	}
}
