package Gameplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import Global.Static;
import Niveau.Block;

public class GameMovement {
	private float cubeY;
	private float gravity = 0.5f;
	private float jumpStrength = -15;
	private float velocityY = 0;
	private float nextY;
	private boolean jumping = false;
	private float deltaX, deltaY, speed;
	private float nextFloor, nextCeiling;
	private int jumpTime;
	private boolean jumpStarted = false;

	public GameMovement(float cubeY, float deltaX, float deltaY) {
		this.nextFloor = 0;
		this.nextCeiling = 91 * 100;
		this.cubeY = cubeY;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.setSpeed(1);
		Static.setModState(0);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta, Block[] blocks) {
		Input input = gc.getInput();

		// Handle collision with object of Catégorie 2 :
		Block[] listInter = blocks;
		if (listInter != null) {
			for (int i = 0; i < listInter.length; i++) {
				// Check one by one, then apply what needs to be done

				// Gamemode Portals
				if (listInter[i].getIndexBlock() == 1) {
					System.out.println("klfdkldf");
					Static.setModState(0);
				}
				if (listInter[i].getIndexBlock() == 2) {
					Static.setModState(1);
				}
				if (listInter[i].getIndexBlock() == 3) {
					Static.setModState(2);
				}
				if (listInter[i].getIndexBlock() == 4) {
					Static.setModState(3);
				}
				if (listInter[i].getIndexBlock() == 5) {
					Static.setModState(4);
				}
				if (listInter[i].getIndexBlock() == 6) {
					Static.setModState(5);
				}
				if (listInter[i].getIndexBlock() == 7) {
					Static.setModState(6);
				}
				if (listInter[i].getIndexBlock() == 8) {
					Static.setModState(7);
				}

				// Gravity Portal
				if (listInter[i].getIndexBlock() == 9 && gravity < 0) {
					gravity *= -1;
				}
				if (listInter[i].getIndexBlock() == 10 && gravity > 0) {
					gravity *= -1;
				}

				// Speed changer
				if (listInter[i].getIndexBlock() == 11) {
					System.out.println("fdkldf");
					this.setSpeed(1);
				} else if (listInter[i].getIndexBlock() == 12) {
					this.speed = (float) 1.5;
				} else if (listInter[i].getIndexBlock() == 13) {
					this.speed = (float) 2.0;
				} else if (listInter[i].getIndexBlock() == 14) {
					this.speed = (float) 3.0;
				} else if (listInter[i].getIndexBlock() == 15) {
					this.speed = (float) 4.0;
				}
			}
		}

		// Debug aspect

		if (input.isKeyPressed(Input.KEY_R)) {
			gravity *= -1;
			Static.setGravity(gravity);
		}
		/*
		 * 
		 * if (input.isKeyPressed(Input.KEY_0)) { Static.setModState(0); } else if
		 * (input.isKeyPressed(Input.KEY_1)) { Static.setModState(1); } else if
		 * (input.isKeyPressed(Input.KEY_2)) { Static.setModState(2); } else if
		 * (input.isKeyPressed(Input.KEY_3)) { Static.setModState(3); } else if
		 * (input.isKeyPressed(Input.KEY_4)) { Static.setModState(4); } else if
		 * (input.isKeyPressed(Input.KEY_5)) { Static.setModState(5); } else if
		 * (input.isKeyPressed(Input.KEY_6)) { Static.setModState(6); } else if
		 * (input.isKeyPressed(Input.KEY_7)) { Static.setModState(7); }
		 */

		// Cube = Mode 0
		else if (Static.getModState() == 0) {
			boolean onGround;
			float boundary;

			if (gravity > 0) {
				onGround = cubeY <= nextFloor;
				boundary = nextFloor;
			} else {
				onGround = cubeY >= nextCeiling;
				boundary = nextCeiling;
			}

			// Jump handler
			if (input.isKeyDown(Input.KEY_SPACE) && onGround) {
				jumping = true;
				velocityY = (gravity > 0) ? -jumpStrength : +jumpStrength;
			}

			// Nextfloor/ceilling check
			if (gravity > 0 && nextY > nextCeiling) {
				cubeY = 0;
				velocityY = 0;
			}

			// Falling aspect
			if (jumping || !onGround) {
				nextY = cubeY + velocityY;

				if ((gravity > 0 && nextY < boundary) || (gravity < 0 && nextY > boundary)) {
					cubeY = boundary;
					velocityY = 0;
					jumping = false;
				} else {
					cubeY = nextY;
					velocityY -= gravity;
				}
			}
		}

		// Ship = Mode 0
		else if (Static.getModState() == 1) {
			if (!input.isKeyDown(Input.KEY_SPACE)) {
				velocityY -= gravity;
			} else {
				// Reverse the effect of gravity
				velocityY += gravity;
			}

			nextY = cubeY + velocityY;

			if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}
		}

		// Ball = Mode 0
		else if (Static.getModState() == 2) {
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				if (cubeY == nextFloor || cubeY == nextCeiling) {
					gravity *= -1;
					velocityY = 0;
				}
			}

			velocityY -= gravity;
			nextY = cubeY + velocityY;

			if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}
		}

		// Wave = Mode 0
		else if (Static.getModState() == 3) {
			if (input.isKeyDown(Input.KEY_SPACE)) {
				nextY = cubeY + gravity * 10;
			}

			else if (!input.isKeyDown(Input.KEY_SPACE)) {
				nextY = cubeY - gravity * 10;
			}

			if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}

			if (nextY > nextCeiling) {
				if (nextY > Static.getScreenHeight() - 100) {
					cubeY = Static.getScreenHeight() - 100;
					velocityY = 0;
				}

				else {
					Static.setIsDead(true);
					gravity = 0.5f;
					cubeY = 0;
				}
			}

			else if (nextY < nextFloor) {
				if (nextY < 0) {
					cubeY = 0;
					velocityY = 0;
				} else {
					Static.setIsDead(true);
					cubeY = 0;
					gravity = 0.5f;

				}
			}

			else {
				cubeY = nextY;
			}
		}

		// Spider = Mode 0
		else if (Static.getModState() == 4) {
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				if (cubeY == nextFloor) {
					cubeY = nextCeiling - 1;
					gravity *= -1;
					velocityY = 0;
				}

				if (cubeY == nextCeiling) {
					cubeY = nextFloor + 1;
					gravity *= -1;
					velocityY = 0;
				}
			}

			velocityY -= gravity;
			nextY = cubeY + velocityY;

			if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}

			input.clearKeyPressedRecord();
		}

		// UFO = Mode 0
		else if (Static.getModState() == 5) {
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				velocityY = gravity * -30;
			}

			velocityY += gravity;
			nextY = cubeY - velocityY;

			if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}
		}

		// Swing Copter = Mode 0
		else if (Static.getModState() == 6) {
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				gravity *= -1;
			}

			velocityY += gravity;
			nextY = cubeY - velocityY;

			if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}
			input.clearKeyPressedRecord();
		}

		// Robot Mode = Mode 0
		else if (Static.getModState() == 7) {
			System.out.println(jumpTime + " ; " + velocityY);
			boolean onGround;
			float boundary;

			if (gravity > 0) {
				onGround = cubeY <= nextFloor;
				boundary = nextFloor;
			} else {
				onGround = cubeY >= nextCeiling;
				boundary = nextCeiling;
			}

			if (input.isKeyPressed(Input.KEY_SPACE) && onGround) {
				jumpStarted = true;
			}

			if (input.isKeyDown(Input.KEY_SPACE) && jumpStarted) {
				jumping = true;
				jumpTime++;
			}

			if (!input.isKeyDown(Input.KEY_SPACE) || jumpTime >= 27) {
				jumpStarted = false;
				jumping = false;
				jumpTime = 0;
			}

			if (jumping) {
				velocityY += gravity;
			} else {
				velocityY -= gravity;
			}

			nextY = cubeY + velocityY;

			if (nextY < nextFloor) {
				cubeY = nextFloor;
				velocityY = 0;
			} else if (nextY > nextCeiling) {
				cubeY = nextCeiling;
				velocityY = 0;
			} else {
				cubeY = nextY;
			}
		}

	}

	public void adjustVelocityOnCollision(float[] nextMaxDeltaY) {
		this.nextFloor = nextMaxDeltaY[0];
		this.nextCeiling = nextMaxDeltaY[1];
	}

	public float getCubeY() {
		return cubeY;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}