package com.nick.wood.boids_simulation;

public class BoidSystemData {

	private double limitedSpeed = 5;
	private double lengthAwayGroup2 = 400;
	private double lengthAwayMin2 = 25;
	private double boundScale = 1000;
	private double velocityMatchScale = 10;
	private double antiCollideScale = 10;
	private double perceivedCenterScale = 10;
	private double goalScale = 5;
	private int numberOfBoids = 10;

	public BoidSystemData() {

	}

	public double getLimitedSpeed() {
		return limitedSpeed;
	}

	public void setLimitedSpeed(double limitedSpeed) {
		this.limitedSpeed = limitedSpeed;
	}

	public double getLengthAwayGroup2() {
		return lengthAwayGroup2;
	}

	public void setLengthAwayGroup2(double lengthAwayGroup2) {
		this.lengthAwayGroup2 = lengthAwayGroup2;
	}

	public double getLengthAwayMin2() {
		return lengthAwayMin2;
	}

	public void setLengthAwayMin2(double lengthAwayMin2) {
		this.lengthAwayMin2 = lengthAwayMin2;
	}

	public double getBoundScale() {
		return boundScale;
	}

	public void setBoundScale(double boundScale) {
		this.boundScale = boundScale;
	}

	public double getVelocityMatchScale() {
		return velocityMatchScale;
	}

	public void setVelocityMatchScale(double velocityMatchScale) {
		this.velocityMatchScale = velocityMatchScale;
	}

	public double getAntiCollideScale() {
		return antiCollideScale;
	}

	public void setAntiCollideScale(double antiCollideScale) {
		this.antiCollideScale = antiCollideScale;
	}

	public double getPerceivedCenterScale() {
		return perceivedCenterScale;
	}

	public void setPerceivedCenterScale(double perceivedCenterScale) {
		this.perceivedCenterScale = perceivedCenterScale;
	}

	public double getGoalScale() {
		return goalScale;
	}

	public void setGoalScale(double goalScale) {
		this.goalScale = goalScale;
	}

	public int getNumberOfBoids() {
		return numberOfBoids;
	}

	public void setNumberOfBoids(int numberOfBoids) {
		this.numberOfBoids = numberOfBoids;
	}
}
